package com.github.concurrent.thread.demo;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * <p>Socker启动</p>
 *
 * @author <a href="mailto:7066450@qq.com">panxi</a>
 * @version 1.0.0
 * @since 1.0
 */
public class SocketServerStart {
    public static void main(String[] args) {try {
        Socket socket = new Socket("127.0.0.1", 8888);
        while (true){
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            bw.write("Hello"+"\n");
            bw.flush();
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
    }
}
