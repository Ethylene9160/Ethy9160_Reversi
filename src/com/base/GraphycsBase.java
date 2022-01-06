package com.base;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/**
 * 聊天室客户机的父类，作用是被继承（减少client的代码量）。
 * 包含：
 * <p>
 * <code>JScrollPane scrollPane</code> (包含<code>mainTextArea</code>)
 * <p>
 * <code>JTextField sendText, nameText</code>(要发出去的消息框，名字消息框)
 * <p>
 * <code>JButton sendButton</code> 发送端的按钮
 * <p>以及一些必要组件。【<b>继承了JFrame类。</b>】
 */
public class GraphycsBase extends JFrame{
    //文本域，用来显示传输的消息
    public JTextArea mainTextArea;
    //滚动条
    public JScrollPane scrollPane;
    //文本框，用于发送消息的框框
    public JTextField sendText;
    //文本框，设置ID名称
    public JTextField nameText;
    //面板
    public JPanel mainPanel;
    //按钮
    public JButton sendButton;

    //指针初始化
    public void setPointer(){
        this.setTitle("宇宙奥赛罗交流群");
        mainTextArea = new JTextArea();
        scrollPane = new JScrollPane(mainTextArea);
        mainPanel = new JPanel();
        sendButton = new JButton("发送");
        nameText = new JTextField(10);
        sendText = new JTextField(12);//这样发送框就会有十列
    }

    //属性初始化
    public void setConfig(){
        mainPanel.add(new JLabel("ID: ")) ;
        mainPanel.add(nameText);
        mainTextArea.setEditable(false);//文本框设置不可编辑
        //文本框，按钮添加入
        mainPanel.add(sendText);
        mainPanel.add(sendButton);
        nameText.setEditable(false);
        //滚动条，panel加入frame
        this.setResizable(false);
        this.add(scrollPane, BorderLayout.CENTER);
        this.add(mainPanel,BorderLayout.SOUTH);
        //设置标题大小bounds关闭
        this.setBounds(300,300,400,400);
        this.setVisible(true);
        //frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public void init(){
        //setPosition();
        setPointer();
        setConfig();
    }
}