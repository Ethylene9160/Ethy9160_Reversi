package com.ethy9160.web;
import com.base.SendBase;
import java.net.Socket;

public class GraphicSend extends SendBase implements Runnable{
    /** 父类SendBase包含：
     * 1. 成员（全局）变量:
     * DataOutputStream outputStream 输出数据流;
     * boolean isSend 判断信息是否已经发送;
     * String sendData 存储要发送的字符串;
     * String serverAddress 服务器的地址(已删除);
     * 2. 方法:
     * void send(String): 传入要发送的信息，即可实现发送，需要在run方法中重写并调用;
     * 3. 构造器：
     * 传入参数：Socket类型。
     * @param client 客户机的“插头”
     */
    public GraphicSend(Socket client){
        super(client);
    }

    @Override
    public void run() {
        //发送数据
        while(flag){
            try {
                Thread.sleep(333);//让线程暂停从而减少系统资源占用
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(isSend){
                send(sendData);
                isSend = false;
            }
        }
    }
}
