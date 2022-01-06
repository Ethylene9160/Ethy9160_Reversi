package com.ethy9160.web;

import com.base.GraphycsBase;

import java.awt.event.*;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.Socket;

import static java.awt.event.KeyEvent.VK_ENTER;
import static java.awt.event.KeyEvent.VK_SPACE;

public class Client extends GraphycsBase implements ActionListener, KeyListener{
    public String opponent = "";
    public String participant = "";
    public static int playerCnt = 1;
    private int playerID, KCode;
    boolean isType;
    public String data;
    GraphicSend graphicSend;
    GraphycReceive graphycReceive;
    public int getPlayerID(){
        return playerID;
    }
    //读取文本框中的内容
    public String getSendText(){
        return sendText.getText();
    }
    //设置文本框内容
    public void setSendText(String str){
        this.sendText.setText(str);
    }
    public Inet4Address IP;

    /**构造器
     * 这是一个<b>JFrame</b>类！
     * @throws IOException
     */
    public Client(String IPAddress, int port) throws IOException {
        //界面初始化
        init();
        sendText.addKeyListener(this);
        data = "";
        playerID = playerCnt;
        playerCnt++;
        Socket clientSocket = new Socket(IPAddress, port);
        //发送线程启动，由于是引用传递，可以随便使用。
        graphicSend = new GraphicSend(clientSocket);
        //接收线程启动
        graphycReceive = new GraphycReceive(clientSocket, mainTextArea);
        //启动线程
        Thread thread = new Thread(graphicSend);
        thread.start();
        new Thread(graphycReceive).start();
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                data = nameText.getText() +": "+sendText.getText();
                graphicSend.sendData = data;
                graphicSend.isSend = true;
                mainTextArea.append("【我】" + data +"\n");
                sendText.setText("");
            }
        });
        if(isType){
            isType = false;
            graphicSend.sendData = nameText.getText() + ": " + sendText.getText();
            graphicSend.isSend = true;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override//12/20/15：00,新增回车键发送文本的功能。
    public void keyPressed(KeyEvent e) {
        KCode = e.getKeyCode();
        if(KCode == VK_ENTER) {
            data = nameText.getText() + ": " + sendText.getText();
            graphicSend.sendData = data;
            graphicSend.isSend = true;
            mainTextArea.append("【我】" + data + "\n");
            sendText.setText("");
            KCode = 10086;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

}
