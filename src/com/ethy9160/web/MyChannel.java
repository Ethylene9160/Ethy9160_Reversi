package com.ethy9160.web;


import com.util.CloseUtil;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.List;
/**
 * 每一个客户都是一条路
 * 输入流
 * 输出流
 * 接收数据
 * 发送数据
 */
public class MyChannel implements Runnable{
    protected DataInputStream inputStream;
    protected DataOutputStream outputStream;
    protected boolean flag = true, isGetName = false;
//    protected Client client;
    protected Socket clientSocekt;
    private String userName;


    public MyChannel(Socket clientSocket) {
        this.clientSocekt = clientSocket;
        try{
            inputStream = new DataInputStream(clientSocket.getInputStream());
            outputStream = new DataOutputStream(clientSocket.getOutputStream());

        }catch (IOException e){
            flag = false;
            CloseUtil.closeAll(inputStream, outputStream);
        }
    }

    //接收数据
    protected String receive(){
        String str = "";
        try{
            str = inputStream.readUTF();
            if(!isGetName){
                int p1 = str.indexOf(':');
                this.userName = str.substring(0,p1);
                isGetName = true;
            }
            System.out.println("接收到："+str);
        } catch (IOException e) {
            flag = false;
            CloseUtil.closeAll();
            Server.list.remove(this);
            List<MyChannel> list = Server.list;
            Server.list.remove(this);
            for(MyChannel a : list){
                a.send(this.userName + "离开了聊天室");
            }
        }
        return str;
    }

    //发送数据
    protected void send(String str){
        if(str != null && str.length() != 0){
            try{
                outputStream.writeUTF(str);
                outputStream.flush();
            }catch (IOException e){
                flag = false;
                CloseUtil.closeAll();
                List<MyChannel> list = Server.list;
                Server.list.remove(this);
                for(MyChannel a : list){
                    a.send("有人离开了聊天室");
                }
            }
        }
    }
    //转发
    protected void transmit(){
        String str = receive();
        //遍历集合
        List<MyChannel> list = Server.list;
        for(MyChannel a : list){
            if(a == this){
                continue;
            }
            a.send(str);
        }
    }


    @Override
    public void run() {
        while(flag){
            transmit();
            //transmit("192.168.159.1");
        }
    }
}
