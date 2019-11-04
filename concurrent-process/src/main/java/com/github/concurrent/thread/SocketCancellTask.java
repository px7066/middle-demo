package com.github.concurrent.thread;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * <p>socket取消样例</p>
 *
 * @author <a href="mailto:7066450@qq.com">panxi</a>
 * @version 1.0.0
 * @since 1.0
 */
public class SocketCancellTask extends AbstractSocketUsingTask<String>{

    /**
     * Computes a result, or throws an exception if unable to do so.
     *
     * @return computed result
     * @throws Exception if unable to compute a result
     */
    @Override
    public String call() throws Exception {
        Socket socket;
        while ((socket = getSocket()) == null){
            System.out.println("尝试获取Socket资源");
        }
        while (true){
            System.out.println("客户端:"+socket.getInetAddress()+"已连接到服务器");
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            //读取客户端发送来的消息
            String mess = br.readLine();
            System.out.println("客户端："+mess);
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            bw.write(mess+"\n");
            bw.flush();
        }
    }
}
