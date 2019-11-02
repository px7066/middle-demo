package com.github.dubbo.consumer.filter;


import com.alibaba.dubbo.rpc.*;

public class LogFilter implements Filter {


    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        Result result = null;
        long takeTime = 0L;

        try{
            long startTime = System.currentTimeMillis();

            //before filter
            System.out.println("before filter");
            result = invoker.invoke(invocation);
            if (result.getException() instanceof Exception)
            {
                throw new Exception(result.getException());
            }

            takeTime = System.currentTimeMillis() - startTime;
            System.out.println("TakeTime: " + takeTime);
            //after filter
            System.out.println("after filter");
        }
        catch(Exception e){
            e.printStackTrace();
            result = new RpcResult(e);
            return result;
        }

        return result;
    }
}
