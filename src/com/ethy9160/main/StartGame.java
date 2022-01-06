package com.ethy9160.main;

import com.ethy9160.AI.AIPanel;
import com.ethy9160.web.Client;
import com.ethy9160.webmove.WebPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class StartGame {
    public static void main(String[] args) {
        JButton AIBlack = new JButton();
        AIBlack.setIcon(Data.AIBlack);
        JButton AIWhite = new JButton();
        AIWhite.setIcon(Data.AIWhite);
        JButton local = new JButton();
        local.setIcon(Data.local);
        JButton webBlack = new JButton();
        webBlack.setIcon(Data.webBlack);
        JButton webWhite = new JButton();
        webWhite.setIcon(Data.webWhite);
        JButton webChat = new JButton();
        webChat.setIcon(Data.webChat);

        JPanel p1 = new JPanel(null);
        设置边界范围(0, 0, 100, 300, p1);
       // p1.setBackground(Color.pink);
        JFrame frame = new JFrame("奥赛罗之星际争霸");
        frame.setBounds(200,200,733,583);
//        设置边界范围(200, 200, 250, 400, frame);
        frame.setDefaultCloseOperation(3);
        frame.setResizable(false);
//        设置大小是否可调整("否", frame);
        frame.setBackground(Color.white);
        frame.add(p1);
        JLabel label = new JLabel(Data.backgroundMenu);
        label.setBounds(0,0,Data.backgroundMenu.getIconWidth(),Data.backgroundMenu.getIconHeight());
        AIBlack.setBounds(542, 60, 150, 50);
        AIWhite.setBounds(522, 140, 150, 50);
        local.setBounds(502, 220, 150, 50);
        webBlack.setBounds(482, 300, 150, 50);
        webWhite.setBounds(462, 380, 150, 50);
        webChat.setBounds(442, 460, 150, 50);

        p1.add(AIBlack);
        p1.add(AIWhite);
        p1.add(local);
        p1.add(webBlack);
        p1.add(webWhite);
        p1.add(webChat);
        p1.add(label);
        frame.setVisible(true);
        AIBlack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {//监听器
                //弹窗
                JFrame frame1 = new JFrame("大战CPU星-你是黑棋");
                setFrame(frame1);
                AIPanel.setParticipant((byte) -1);
                AIPanel panel = new AIPanel();
                frame1.add(panel);;
            }
        });
        AIWhite.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {//监听器
                //弹窗
                JFrame frame1 = new JFrame("大战CPU星-你是白棋");
                setFrame(frame1);
                AIPanel.setParticipant((byte) 1);
                AIPanel panel = new AIPanel();
                frame1.add(panel);
            }
        });
        local.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //弹窗
                JFrame frame1 = new JFrame("独立星球-单机模式");
                setFrame(frame1);
                frame1.add((new CommonPanel()));
            }
        });
        webBlack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JDialog dialog = new JDialog();
                setDialog(dialog);
                dialog.setLayout(new GridLayout(3,1));
                JTextField IPField = new JTextField("");
                JTextField portField = new JTextField("");
                JPanel IPPanel = new JPanel(new GridLayout(1,2));
                JPanel portPanel = new JPanel(new GridLayout(1,2));
                JPanel finalPanel = new JPanel(new GridLayout(1,2));
                IPPanel.add(new JLabel("输入服务器IP"));
                IPPanel.add(IPField);
                portPanel.add(new JLabel("输入服务端口"));
                portPanel.add(portField);
                JButton button = new JButton("确定");
                JButton button2 = new JButton("默认端口");
                finalPanel.add(button2);
                finalPanel.add(button);
                dialog.add(IPPanel);
                dialog.add(portPanel);
                dialog.add(finalPanel);
                button2.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        dialog.dispose();
                        //弹窗
                        JFrame frame1 = new JFrame("宇宙决战-联机模式（黑）");
                        frame1.setResizable(false);
                        frame1.setBounds(200, 200, 1024, 768);
                        frame1.setVisible(true);
                        frame.setResizable(false);
                        WebPanel.setParticipant((byte) -1);
                        try {
                            frame1.add(new WebPanel("127.0.0.1", 8888));
                        } catch (IOException ex) {
                            //新增报错窗口，12/20/17：27
                            JOptionPane.showMessageDialog(null,"连接失败！","错误",0);
                            frame1.dispose();
                        }
                    }
                });

                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        dialog.dispose();
                        //弹窗
                        JFrame frame1 = new JFrame("宇宙决战-联机模式（黑）");
                        frame1.setResizable(false);
                        frame1.setBounds(200, 200, 1024, 768);
                        frame1.setVisible(true);
                        frame.setResizable(false);
                        WebPanel.setParticipant((byte) -1);
//                WebPanel webPanel = null;
                        try {
                            frame1.add(new WebPanel(IPField.getText(), Integer.parseInt(portField.getText())));
                        } catch (IOException ex) {
                            //新增报错窗口，12/20/17：27
                            JOptionPane.showMessageDialog(null,"连接失败！","错误",0);
                            frame1.dispose();
                        }
                    }
                });
            }
        });
        webWhite.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JDialog dialog = new JDialog();
                setDialog(dialog);
                dialog.setLayout(new GridLayout(3,1));
                JTextField IPField = new JTextField("");
                JTextField portField = new JTextField("");
                JPanel IPPanel = new JPanel(new GridLayout(1,2));
                JPanel portPanel = new JPanel(new GridLayout(1,2));
                JPanel finalPanel = new JPanel(new GridLayout(1,2));
                IPPanel.add(new JLabel("输入服务器IP"));
                IPPanel.add(IPField);
                portPanel.add(new JLabel("输入服务端口"));
                portPanel.add(portField);
                JButton button = new JButton("确定");
                JButton button2 = new JButton("默认端口");
                finalPanel.add(button2);
                finalPanel.add(button);
                dialog.add(IPPanel);
                dialog.add(portPanel);
                dialog.add(finalPanel);
                button2.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        dialog.dispose();
                        //弹窗
                        JFrame frame1 = new JFrame("宇宙决战-联机模式（白）");
                        frame1.setResizable(false);
                        frame1.setBounds(200, 200, 1024, 768);
                        frame1.setVisible(true);
                        frame.setResizable(false);
                        WebPanel.setParticipant((byte) 1);
//                WebPanel webPanel = null;
                        try {
                            frame1.add(new WebPanel("127.0.0.1", 8888));
                        } catch (IOException ex) {
                            //新增报错窗口，12/20/17：27
                            JOptionPane.showMessageDialog(null,"连接失败！","错误",0);
                            frame1.dispose();
                        }
                    }
                });

                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        dialog.dispose();
                        //弹窗
                        JFrame frame1 = new JFrame("宇宙决战-联机模式（白）");
                        frame1.setResizable(false);
                        frame1.setBounds(200, 200, 1024, 768);
                        frame1.setVisible(true);
                        frame.setResizable(false);
                        WebPanel.setParticipant((byte) 1);
//                WebPanel webPanel = null;
                        try {
                            frame1.add(new WebPanel(IPField.getText(), Integer.parseInt(portField.getText())));
                        } catch (IOException ex) {
                            //新增报错窗口，12/20/17：27
                            JOptionPane.showMessageDialog(null,"连接失败！","错误",0);
                            frame1.dispose();
                        }
                    }
                });
            }
        });
        webChat.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JDialog dialog = new JDialog();
                setDialog(dialog);
                JButton button = new JButton("确定");
                JButton button2 = new JButton("默认");
                dialog.setLayout(new GridLayout(3,1));
                JTextField IPField = new JTextField("");
                JTextField portField = new JTextField("");
                JTextField nameField = new JTextField("未命名", 12);
                JPanel IPPanel = new JPanel(new GridLayout(1,2));
                JPanel portPanel = new JPanel(new GridLayout(1,3));
                JPanel infoPanel = new JPanel(new GridLayout(1,3));
                IPPanel.add(new JLabel("输入服务器IP"));
                IPPanel.add(IPField);
                portPanel.add(new JLabel("输入服务端口"));
                portPanel.add(portField);
                portPanel.add(button2);
                infoPanel.add(new JLabel("昵称："));
                infoPanel.add(nameField);
                infoPanel.add(button);
                dialog.add(IPPanel);
                dialog.add(portPanel);
                dialog.add(infoPanel);

//                JDialog dialog = new JDialog();
                button2.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {

                        try {
                            dialog.dispose();
                            Client client = new Client("127.0.0.1", 9999);
                            client.nameText.setText(nameField.getText());
                        } catch (IOException ex) {
                            //新增报错窗口，12/20/17：27
                            JOptionPane.showMessageDialog(null,"连接失败！","错误",0);	//消息对话框
                            System.out.println("error");
                            dialog.dispose();
                        }
                    }
                });
                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {

                        try {
                            dialog.dispose();
                            Client client = new Client(IPField.getText(), Integer.parseInt(portField.getText()));
                            client.nameText.setText(nameField.getText());
                        } catch (IOException ex) {
                            //新增报错窗口，12/20/17：27
                            JOptionPane.showMessageDialog(null,"连接失败！","错误",0);	//消息对话框
                            System.out.println("error");
                            dialog.dispose();
                        }
                    }
                });
            }
        });
    }

    public static void 设置是否可编辑(String 是否可编辑, TextArea text) {
        if (是否可编辑.equals("是")) {
            text.setEditable(true);
        } else {
            text.setEditable(false);
        }
    }

    public static void 设置边界范围(int 横坐标, int 纵坐标, int 长, int 宽, JDialog dialog) {
        dialog.setBounds(横坐标, 纵坐标, 长, 宽);
    }

    public static void 设置是否可见(String 是否可见, Dialog dialog) {
        boolean is = false;
        if (是否可见.equals("是")) {
            is = true;
        }
        dialog.setVisible(is);
    }

    public static void 设置边界范围(int 横坐标, int 纵坐标, int 长, int 宽, JFrame frame) {
        frame.setBounds(横坐标, 纵坐标, 长, 宽);
    }

    public static void 设置是否可见(String 是否可见, JFrame dialog) {
        boolean is = false;
        if (是否可见.equals("是")) {
            is = true;
        }
        dialog.setVisible(is);
    }

    public static void 设置大小是否可调整(String 大小是否可调, JFrame frame) {
        if (大小是否可调.equals("是")) {
            frame.setResizable(true);
        } else {
            frame.setResizable(false);
        }
    }

    public static void 设置边界范围(int 横坐标, int 纵坐标, int 长, int 宽, JPanel frame) {
        frame.setBounds(横坐标, 纵坐标, 长, 宽);
    }

    public static void setFrame(JFrame frame){
        frame.setVisible(true);
        frame.setBounds(150,100,1024,768);
        frame.setResizable(false);
    }

    public static void setDialog(JDialog frame){
        frame.setVisible(true);
        frame.setBounds(300,250,198,141);
        frame.setResizable(false);
    }
}
