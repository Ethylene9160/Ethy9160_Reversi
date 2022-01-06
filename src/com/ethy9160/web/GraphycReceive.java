package com.ethy9160.web;

import com.util.CloseUtil;

import javax.swing.*;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * 客户机获取消息的途径。如果没有连接到服务器，会有报错。
 */
public class GraphycReceive extends JFrame implements Runnable{
    protected DataInputStream inputStream;
    protected boolean flag = true;
    protected JTextArea jta;
    public GraphycReceive(Socket client, JTextArea jta) {
        this.jta = jta;
        try{
            inputStream = new DataInputStream(client.getInputStream());
        }catch (IOException e){
            flag = false;
            JOptionPane.showMessageDialog(null,"连接中断！","错误",0);	//消息对话框
            CloseUtil.closeAll(inputStream,client);
        }
    }

    public String getMessage(){
        String str = "";
        try{
            str = inputStream.readUTF();
        } catch (IOException e) {
            flag = false;
            JOptionPane.showMessageDialog(null,"连接中断！","错误",0);	//消息对话框
            CloseUtil.closeAll(inputStream);
            e.printStackTrace();
        }
        return str;
    }

    @Override
    public void run() {
        while (flag){
            jta.append(getMessage() + "\n");
        }
    }
}