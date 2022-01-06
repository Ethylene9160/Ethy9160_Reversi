package com.base;
import com.util.CloseUtil;
import jdk.nashorn.internal.scripts.JD;

import javax.swing.*;
import java.io.*;
import java.net.Socket;
/** 父类SendBase包含：
 * <p>
 * 1. 成员（全局）变量:</p>
 * DataOutputStream outputStream 输出数据流;
 * <p>
 * boolean isSend 判断信息是否已经发送;
 * <p>
 * String sendData 存储要发送的字符串;
 * <p>
 * String serverAddress 服务器的地址(已删除);
 * <p>
 * <b>2. 方法:</b></p>
 * void send(String): 传入要发送的信息，即可实现发送，需要在run方法中重写并调用;
 * <p>
 * 3. 构造器：
 * 传入参数：Socket类型。
 * @Author  Ethylene9160
 */
public class SendBase {
    //发送数据
    public DataOutputStream outputStream;
    //检测是否连接成功
    public boolean flag = true;
    //检查是否需要发送消息
    public boolean isSend;
    public String sendData;

    //构造器
    public SendBase(Socket client) {
        try{
            outputStream = new DataOutputStream(client.getOutputStream());
        }catch(IOException e){
            flag = false;


            CloseUtil.closeAll(outputStream,client);
            e.printStackTrace();
        }
    }

    //获取数据
    public String getMessage(String text){
        return text;
    }

    //发送信息
    public void send(String str){
        try{
            outputStream.writeUTF(str);
            outputStream.flush();//清空缓存
        } catch (IOException e) {
            flag = false;
            CloseUtil.closeAll(outputStream);//关闭数据流
            e.printStackTrace();
        }
    }
}
