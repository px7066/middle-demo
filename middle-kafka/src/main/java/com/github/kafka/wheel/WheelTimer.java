package com.github.kafka.wheel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicLong;

public class WheelTimer {

    private static final Logger log = LoggerFactory.getLogger(WheelTimer.class);

    private static final int INSTANCE_COUNT_LIMIT = 64;

    private static final AtomicInteger INSTANCE_COUNTER = new AtomicInteger();

    private static final long MILLISECOND_NANOS;

    private final Thread workerThread;

    private final Work work;

    private volatile long startTime;

    private static final AtomicIntegerFieldUpdater<WheelTimer> WORKER_STATE_UPDATER;

    public static final int WORKER_STATE_INIT = 0;
    public static final int WORKER_STATE_STARTED = 1;
    public static final int WORKER_STATE_SHUTDOWN = 2;

    private volatile int workerState;

    private final long tickDuration;

    private final WheelTimer.WheelBucket[] wheel;

    private final int mask;

    private final AtomicLong pendingTimeouts;

    private final Queue<WheelTimer.WheelTask> tasks;

    private final Queue<WheelTimer.WheelTask> cancelledTasks;

    private final CountDownLatch startTimeInitialized;


    static {
        MILLISECOND_NANOS = TimeUnit.MILLISECONDS.toNanos(1L);
        WORKER_STATE_UPDATER = AtomicIntegerFieldUpdater.newUpdater(WheelTimer.class, "workerState");
    }

    public WheelTimer() {
        this(Executors.defaultThreadFactory());
    }

    public WheelTimer(ThreadFactory threadFactory) {
        this(threadFactory, 100L, TimeUnit.MILLISECONDS);
    }

    public WheelTimer(long tickDuration, TimeUnit timeUnit) {
        this(Executors.defaultThreadFactory(), tickDuration, timeUnit);
    }

    public WheelTimer(ThreadFactory threadFactory, long tickDuration, TimeUnit unit) {
        this(threadFactory, tickDuration, unit, 512);
    }

    public WheelTimer(ThreadFactory threadFactory, long tickDuration, TimeUnit unit, int ticksPerWheel) {
        this.startTimeInitialized = new CountDownLatch(1);
        this.work = new Work();
        this.wheel = createWheel(ticksPerWheel);
        this.pendingTimeouts = new AtomicLong(0L);
        long duration = unit.toNanos(tickDuration);
        this.mask = this.wheel.length - 1;
        if (duration >= 9223372036854775807L / (long)this.wheel.length) {
            throw new IllegalArgumentException(String.format("tickDuration: %d (expected: 0 < tickDuration in nanos < %d", tickDuration, 9223372036854775807L / (long)this.wheel.length));
        } else {
            tasks = new LinkedBlockingDeque<>();
            cancelledTasks = new LinkedBlockingQueue<>();
            if (duration < MILLISECOND_NANOS) {
                log.warn("Configured tickDuration {} smaller then {}, using 1ms.", tickDuration, MILLISECOND_NANOS);
                this.tickDuration = MILLISECOND_NANOS;
            } else {
                this.tickDuration = duration;
            }
            this.workerThread = threadFactory.newThread(work);
        }
    }

    private static WheelBucket[] createWheel(int ticksPerWheel) {
        if (ticksPerWheel <= 0) {
            throw new IllegalArgumentException("ticksPerWheel must be greater than 0: " + ticksPerWheel);
        } else if (ticksPerWheel > 1073741824) {
            throw new IllegalArgumentException("ticksPerWheel may not be greater than 2^30: " + ticksPerWheel);
        } else {
            ticksPerWheel = normalizeTicksPerWheel(ticksPerWheel);
            WheelTimer.WheelBucket[] wheel = new WheelTimer.WheelBucket[ticksPerWheel];

            for(int i = 0; i < wheel.length; ++i) {
                wheel[i] = new WheelTimer.WheelBucket();
            }

            return wheel;
        }
    }

    private static int normalizeTicksPerWheel(int ticksPerWheel) {
        int normalizedTicksPerWheel;
        for(normalizedTicksPerWheel = 1; normalizedTicksPerWheel < ticksPerWheel; normalizedTicksPerWheel <<= 1) {
        }
        return normalizedTicksPerWheel;
    }

    public void start(){
        switch (WORKER_STATE_UPDATER.get(this)){
            case 0:
                if (WORKER_STATE_UPDATER.compareAndSet(this, 0, 1)) {
                    this.workerThread.start();
                }
            case 1:
                break;
            case 2:
                throw new IllegalStateException("cannot be started once stopped");
            default:
                throw new Error("Invalid WorkerState");
        }
        while(this.startTime == 0L) {
            try {
                this.startTimeInitialized.await();
            } catch (InterruptedException var2) {
            }
        }
    }

    private static final class WheelBucket{
        private WheelTimer.WheelTask head;
        private WheelTimer.WheelTask tail;

        public void addTask(WheelTask task) {
            assert task.bucket == null;

            task.bucket = this;
            if (this.head == null) {
                this.head = this.tail = task;
            } else {
                this.tail.next = task;
                task.prev = this.tail;
                this.tail = task;
            }
        }

        public void expireTimeouts(long deadline) {
            WheelTimer.WheelTask next;
            for(WheelTimer.WheelTask task = this.head; task != null; task = next) {
                next = task.next;
                if (task.remainingRounds <= 0L) {
                    next = this.remove(task);
                    if (task.deadline > deadline) {
                        throw new IllegalStateException(String.format("timeout.deadline (%d) > deadline (%d)", task.deadline, deadline));
                    }

                    task.expire();
                } else if (task.isCancelled()) {
                    next = this.remove(task);
                } else {
                    --task.remainingRounds;
                }
            }
        }

        private WheelTask remove(WheelTask task) {
            WheelTimer.WheelTask next = task.next;
            if (task.prev != null) {
                task.prev.next = next;
            }

            if (task.next != null) {
                task.next.prev = task.prev;
            }

            if (task == this.head) {
                if (task == this.tail) {
                    this.tail = null;
                    this.head = null;
                } else {
                    this.head = next;
                }
            } else if (task == this.tail) {
                this.tail = task.prev;
            }

            task.prev = null;
            task.next = null;
            task.bucket = null;
            task.timer.pendingTimeouts.decrementAndGet();
            return next;
        }

        public void clearTasks(Set<WheelTask> unProcessTask) {
            while(true) {
                WheelTimer.WheelTask task = this.pollTask();
                if (task == null) {
                    return;
                }

                if (!task.isExpired() && !task.isCancelled()) {
                    unProcessTask.add(task);
                }
            }
        }

        private WheelTask pollTask() {
            WheelTimer.WheelTask head = this.head;
            if (head == null) {
                return null;
            } else {
                WheelTimer.WheelTask next = head.next;
                if (next == null) {
                    this.tail = this.head = null;
                } else {
                    this.head = next;
                    next.prev = null;
                }

                head.next = null;
                head.prev = null;
                head.bucket = null;
                return head;
            }
        }
    }

    public final class Work implements Runnable{

        private final Set<WheelTask> unProcessTask;

        private long tick;

        public Work() {
            this.unProcessTask = new HashSet<>();
        }

        @Override
        public void run() {
            WheelTimer.this.startTime = System.nanoTime();
            if (WheelTimer.this.startTime == 0L) {
                WheelTimer.this.startTime = 1L;
            }
            WheelTimer.this.startTimeInitialized.countDown();
            int idx;

            WheelTimer.WheelBucket bucket;
            do{
                long deadline = this.waitForNextTick();
                if (deadline > 0L) {
                    idx = (int)(this.tick & (long)WheelTimer.this.mask);
                    this.processCancelledTasks();
                    bucket = WheelTimer.this.wheel[idx];
                    this.transferTasksToBuckets();
                    bucket.expireTimeouts(deadline);
                    ++this.tick;
                }
            }while (WORKER_STATE_UPDATER.get(WheelTimer.this) == 1);
            WheelTimer.WheelBucket[] var5 = WheelTimer.this.wheel;
            int var2 = var5.length;
            for(idx = 0; idx < var2; ++idx) {
                bucket = var5[idx];
                bucket.clearTasks(this.unProcessTask);
            }

            while(true) {
                WheelTimer.WheelTask task = WheelTimer.this.tasks.poll();
                if (task == null) {
                    this.processCancelledTasks();
                    return;
                }

                if (!task.isCancelled()) {
                    this.unProcessTask.add(task);
                }
            }
        }

        private void processCancelledTasks() {
            while(true) {
                WheelTimer.WheelTask task = WheelTimer.this.cancelledTasks.poll();
                if (task == null) {
                    return;
                }

                try {
                    task.remove();
                } catch (Throwable var3) {
                    if (WheelTimer.log.isWarnEnabled()) {
                        WheelTimer.log.warn("An exception was thrown while process a cancellation task", var3);
                    }
                }
            }
        }

        private void transferTasksToBuckets() {
            for(int i = 0; i < 100000; ++i) {
                WheelTimer.WheelTask task = WheelTimer.this.tasks.poll();
                if (task == null) {
                    break;
                }

                if (task.state() != 1) {
                    long calculated = task.deadline / WheelTimer.this.tickDuration;
                    task.remainingRounds = (calculated - this.tick) / (long)WheelTimer.this.wheel.length;
                    long ticks = Math.max(calculated, this.tick);
                    int stopIndex = (int)(ticks & (long)WheelTimer.this.mask);
                    WheelTimer.WheelBucket bucket = WheelTimer.this.wheel[stopIndex];
                    bucket.addTask(task);
                }
            }
        }

        private long waitForNextTick() {
            long deadline = WheelTimer.this.tickDuration * (this.tick + 1L);
            while(true) {
                long currentTime = System.nanoTime() - WheelTimer.this.startTime;
                long sleepTimeMs = (deadline - currentTime + 999999L) / 1000000L;
                if (sleepTimeMs <= 0L) {
                    if (currentTime == -9223372036854775808L) {
                        return -9223372036854775807L;
                    }

                    return currentTime;
                }

                try {
                    Thread.sleep(sleepTimeMs);
                } catch (InterruptedException var8) {
                    if (WheelTimer.WORKER_STATE_UPDATER.get(WheelTimer.this) == 2) {
                        return -9223372036854775808L;
                    }
                }
            }
        }

        public Set<WheelTask> unprocessedTasks() {
            return Collections.unmodifiableSet(this.unProcessTask);
        }
    }

    private static final class WheelTask {
        private static final int ST_INIT = 0;
        private static final int ST_CANCELLED = 1;
        private static final int ST_EXPIRED = 2;

        private static final AtomicIntegerFieldUpdater<WheelTimer.WheelTask> STATE_UPDATER = AtomicIntegerFieldUpdater.newUpdater(WheelTimer.WheelTask.class, "state");

        private final long deadline;

        private final WheelTimer timer;

        private volatile int state = 0;

        WheelTimer.WheelTask next;
        WheelTimer.WheelTask prev;
        WheelTimer.WheelBucket bucket;
        long remainingRounds;

        public WheelTask(long deadline, WheelTimer timer, Runnable runnable) {
            this.deadline = deadline;
            this.timer = timer;
            this.runnable = runnable;
        }

        private Runnable runnable;

        public Runnable getRunnable() {
            return runnable;
        }

        public void setRunnable(Runnable runnable) {
            this.runnable = runnable;
        }

        public int state() {
            return this.state;
        }

        public void expire() {
            if (this.compareAndSetState(0, 2)) {
                try {
                    runnable.run();
                } catch (Throwable var2) {
                    if (WheelTimer.log.isWarnEnabled()) {
                        WheelTimer.log.warn("An exception was thrown by " + Runnable.class.getSimpleName() + '.', var2);
                    }
                }

            }
        }

        private boolean compareAndSetState(int expected, int state) {
            return STATE_UPDATER.compareAndSet(this, expected, state);
        }

        public boolean isCancelled() {
            return this.state() == 1;
        }

        public boolean isExpired() {
            return this.state() == 2;
        }

        public boolean cancel() {
            if (!this.compareAndSetState(0, 1)) {
                return false;
            } else {
                this.timer.cancelledTasks.add(this);
                return true;
            }
        }

        public void remove() {
            WheelTimer.WheelBucket bucket = this.bucket;
            if (bucket != null) {
                bucket.remove(this);
            } else {
                this.timer.pendingTimeouts.decrementAndGet();
            }
        }
    }


    public WheelTimer.WheelTask newTimeout(Runnable run, long delay, TimeUnit unit) {
//        long pendingTimeoutsCount = this.pendingTimeouts.incrementAndGet();
        this.start();
        long deadline = System.nanoTime() + unit.toNanos(delay) - this.startTime;
        if (delay > 0L && deadline < 0L) {
            deadline = 9223372036854775807L;
        }

        WheelTimer.WheelTask task = new WheelTimer.WheelTask(deadline, this, run);
        this.tasks.add(task);
        return task;
    }

    public Set<WheelTask> stop() {
        if (Thread.currentThread() == this.workerThread) {
            throw new IllegalStateException(WheelTimer.class.getSimpleName() + ".stop() cannot be called from " + WheelTask.class.getSimpleName());
        } else {
            boolean closed;
            if (!WORKER_STATE_UPDATER.compareAndSet(this, 1, 2)) {
                if (WORKER_STATE_UPDATER.getAndSet(this, 2) != 2) {
                    INSTANCE_COUNTER.decrementAndGet();
                }

                return Collections.emptySet();
            } else {
                boolean var7 = false;

                try {
                    var7 = true;
                    closed = false;

                    while(this.workerThread.isAlive()) {
                        this.workerThread.interrupt();

                        try {
                            this.workerThread.join(100L);
                        } catch (InterruptedException var8) {
                            closed = true;
                        }
                    }

                    if (closed) {
                        Thread.currentThread().interrupt();
                        var7 = false;
                    } else {
                        var7 = false;
                    }
                } finally {
                    if (var7) {
                        INSTANCE_COUNTER.decrementAndGet();

                    }
                }

                INSTANCE_COUNTER.decrementAndGet();

                return this.work.unprocessedTasks();
            }
        }
    }
}
