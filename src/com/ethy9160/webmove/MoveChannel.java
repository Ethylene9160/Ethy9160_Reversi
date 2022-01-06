package com.ethy9160.webmove;

import com.util.CloseUtil;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.List;
import java.util.Map;

/**
 * 每一个客户都是一条路
 * 输入流
 * 输出流
 * 接收数据
 * 发送数据
 * <p>
 *     通过“#”来分割消息内容。
 */
public class MoveChannel implements Runnable{
    protected DataInputStream inputStream;
    protected DataOutputStream outputStream;
    protected boolean flag = true;
    protected Socket clientSocekt;
    private String IPAddress;
    public int ownID, targetID;

    public MoveChannel(Socket clientSocket) {
        this.clientSocekt = clientSocket;
        this.IPAddress = clientSocket.getInetAddress().toString();
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
            System.out.println("接收到："+str);
        } catch (IOException e) {
            flag = false;
            CloseUtil.closeAll(outputStream,inputStream);
            try {
                MoveServer.listMap.get(targetID).send("96");
            }catch (Exception e1){
                System.out.println("中转服务器断开");
            }
            MoveServer.listMap.remove(ownID);
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
                CloseUtil.closeAll(outputStream);
                MoveServer.listMap.remove(ownID);
            }
        }
    }
    protected void sendSingle(String str, int webID){
        if(str != null && str.length() != 0){
            try{
                outputStream.writeUTF(str);
                outputStream.flush();
            }catch (IOException e){
                flag = false;
                CloseUtil.closeAll();try {
                    MoveServer.listMap.get(webID).send("96");
                }catch (Exception e1){
                    System.out.println("中转服务器断开");
                }
                MoveServer.listMap.remove(ownID);
            }
        }
    }

    /** 转发（单独发送）
     * 最近修改时间：2021年12月18日0：30
     * @author Ethylene9160
     */
    protected void transmitSingle(){
        try {
            String str = receive();
            int p1 = str.indexOf("#");
//            int p2 = str.indexOf("#",p1+1);
//            ownID = Integer.parseInt(str.substring(0, p1));
            targetID = Integer.parseInt(str.substring(0, p1));
            String position = str.substring(p1 + 1);
            //遍历集合
            Map<Integer, MoveChannel> listMap = MoveServer.listMap;
            listMap.get(targetID).send(position);
        }catch(IndexOutOfBoundsException e){
            System.out.println("ERROR!");
            try {
                MoveServer.listMap.get(ownID).send("94");
            }catch (Exception e1){
                System.out.println("中转服务器断开");
            }
            //MoveServer.listMap.get(targetID).send("94");
        }catch(NullPointerException e){
            System.out.println("Error!");
            try {
                MoveServer.listMap.get(ownID).send("98");
            }catch (Exception e1){
                System.out.println("中转服务器断开");
            }
        }catch(Exception e){
            System.out.println("Error");
            try {
                MoveServer.listMap.get(ownID).send("94");
            }catch (Exception e1){
                System.out.println("中转服务器断开");
            }
        }
    }


    @Override
    public void run() {
        while(flag){
            transmitSingle();
        }
    }

    public static void main(String[] args) {
        for(int a = 101; a < 200; a++){
            if(MoveServer.listMap.containsKey(a)){
                try{
                    MoveServer.listMap.get(a).send("9");
                }catch(Exception e){
                    System.out.println("error!");
                }
            }
        }
    }
}