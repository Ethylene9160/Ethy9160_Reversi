package com.ethy9160.webmove;

import com.base.SendBase;

import javax.swing.*;
import java.net.Socket;

public class Sender extends SendBase implements Runnable{
//详见父类定义。
    public Sender(Socket client){
        super(client);
    }

    @Override
    public void run() {
        //发送数据
        while(flag){
            try {
                Thread.sleep(75);//让线程暂停从而减少系统资源占用
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(isSend){
                send(sendData);
                isSend = false;
                System.out.println("发送成功！");
            }
        }
    }
}
