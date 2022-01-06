package com.ethy9160.webmove;
import com.ethy9160.web.*;

import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

/**
 * 游戏操作端的server
 * 通过构建channel数组，实现多线程转发消息。
 */
public class MoveServer {
    //创建集合对象，从而实现“群发
    //public static List<MoveChannel> list = new ArrayList<MoveChannel>();
    public static Map<Integer, MoveChannel> listMap = new HashMap<>();
    public static int webID;
    public static void main(String[] args) throws IOException {
        webID  = 100;//从100开始，避免和错误信息代码重合
        System.out.println("开服！");
        ServerSocket moveServer = new ServerSocket(8888);
        while(true){
            webID ++;
            Socket socket = moveServer.accept();
            //System.out.println(socket.getRemoteSocketAddress());
            //创建线程对象，此时说明有客户端进来了
            MoveChannel moveChannel = new MoveChannel(socket);
            moveChannel.ownID = webID;
            System.out.println("来客户了，当前在线："+(listMap.size()+1));
            //添加到集合
            new DataOutputStream(socket.getOutputStream()).writeUTF(Integer.toString(webID));
            listMap.put(webID, moveChannel);
            //list.add(moveChannel);
            //启动线程
            new Thread(moveChannel).start();
        }
    }
}
