package com.ysxsoft.common_base.socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.net.SocketFactory;

/**
 * create by Sincerly on 2019/3/2 0002
 **/
public class SocketTestServer {
    public static void main(){
        try {
            Socket socket=SocketFactory.getDefault().createSocket("192.168.1.188",5555);
            socket.setKeepAlive(true);
            socket.setSoTimeout(60);
            socket.setReuseAddress(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
