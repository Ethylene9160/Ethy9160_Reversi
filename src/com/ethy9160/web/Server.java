package com.ethy9160.web;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
public class Server {
    //创建集合对象，从而实现“群发”
    public static List<MyChannel> list = new ArrayList<MyChannel>();
//    public static List<Client> clientList = new ArrayList<Client>();
    public static void main(String[] args) throws IOException{
        System.out.println("开服！");
        ServerSocket server = new ServerSocket(9999);
        while(true){
            Socket socket = server.accept();
            System.out.println("来客户了，当前在线："+(list.size()+1));

            /* 测试机 */
//            System.out.println("尝试1：" + socket.getLocalAddress().getHostAddress().toString());
//            System.out.println("尝试2：" + socket.getInetAddress().getHostAddress().toString());
//            System.out.println("----- 分割线------");
            /* 测试结果：
            * 根据结果，在同一个局域网内，测试二是正确的！并且格式恰好是"xxx.xxx.xx.xxx"之类的。
            * 不在同一局域网内会挂。
            * 公网测出来是公网的IP。
            *  */
            //创建线程对象，此时说明有客户端进来了
            MyChannel channel = new MyChannel(socket);
            //添加到集合
            list.add(channel);
            //启动线程
            new Thread(channel).start();
        }
    }
}
