package com.ethy9160.webmove;
import com.util.CloseUtil;

import javax.swing.*;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * 专供网络对局使用。
 */
public class Receiver implements Runnable{
    protected DataInputStream inputStream;
    protected boolean flag = true;
    public String getData;
    protected JTextField positionOut;
    public Receiver(Socket client, JTextField positionOut) {
        this.positionOut = positionOut;
        try{
            inputStream = new DataInputStream(client.getInputStream());
        }catch (IOException e){
            flag = false;
            CloseUtil.closeAll(inputStream,client);
        }
    }

    public String getMessage(){
        String str = "";
        try{
            str = inputStream.readUTF();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null,"错误：没有连接到服务器。\n请退出游戏或者检查网络连接。","错误",0);
            flag = false;
            CloseUtil.closeAll(inputStream);
            e.printStackTrace();
        }
        return str;
    }

    @Override
    public void run() {
        while (flag){
            getData = getMessage();
            positionOut.setText(getData);
            System.out.println("获取到数据：" + getData);
        }
    }
}
