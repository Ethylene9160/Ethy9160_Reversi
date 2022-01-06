package com.ethy9160.main;
import com.base.PanelBase;
import com.ethy9160.move.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class CompetePanel_Old extends JPanel implements MouseListener, ActionListener {

    /*************  全局变量设定  ****************/
    private int othe[][] = new int[10][10];//实际操作对这个进行
    private int othePre[][] = new int[10][10];//这个用作预测棋子
    private int participant, x, y, score, cou, whiteScore, blackScore;
    private int judge;//判断获胜情况，黑棋胜利为1，白棋胜利为2，平局为3
    private int xOld, yOld;
    boolean isOver, isTap, isBlack;
    private boolean isPass;
    JButton b1 = new JButton("重新开始");//自行设定
    Timer timer = new Timer(180,this);//250ms刷新一次，请自行设置
    /************** 添加设置isPass的方法 **************/

    /**************  初始化  ***************/
    public void init(){
        //初始化棋盘数组
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                othe[i][j] = 0;
                othePre[i][j] = 0;
            }
        }

        score = 0;//用来判断谁赢了，如果数组每个元素的值的和小于零，那么黑棋获胜；大于零，白棋获胜；否则平局。
        participant = -1;
        x = 0; y = 0;
        judge = 0;
        xOld = 0; yOld = 0;
        //游戏状态
        isOver = false;
        isTap = false;
        isBlack = true;
        isPass = false;
        //初始化棋盘上的四颗棋子
        othe[4][4] = 1;
        othe[4][5] = -1;
        othe[5][5] = 1;
        othe[5][4] = -1;
        othePre[3][4] = 1;
        othePre[4][3] = 1;
        othePre[5][6] = 1;
        othePre[6][5] = 1;
        cou = 4;
        whiteScore = 2; blackScore = 2;
    }

    /*************** 添加监听事件 ******************/
    public CompetePanel_Old(){
        init();
        this.setFocusable(true);
        this.addMouseListener(this);//自行在构造器中添加！
        timer.start();
    }

    /***********  绘制操作界面 *************/
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);//清屏
        this.setBackground(new Color(250, 125, 185));//设置面板颜色
        this.setBounds(25, 25, 900, 800);
        //画出棋盘线
        for (int i = 0; i < 9; i++) {
            g.drawLine(0, 100 * i, 800, 100 * i);
            g.drawLine(100 * i, 0, 100 * i, 800);
        }
        //绘制得分状况
        g.setColor(Color.black);
        g.setFont(new Font("宋体", Font.BOLD, 25));//设置字体
        g.drawString("黑: " + blackScore, 810, 255);
        g.setColor(Color.white);
        g.drawString("白: " + whiteScore, 810, 545);
        //绘制对局
        for (int i = 1; i < 9; i++){
            for (int j = 1; j < 9; j++) {
                if(othe[i][j] == -1){
                    g.setColor(Color.black);
                    g.fillOval(100*j-95,100*i-95,90,90);
                }
                else if(othe[i][j] == 1){
                    g.setColor(Color.white);
                    g.fillOval(j*100-95,i*100-95,90,90);
                }
                else if(othePre[i][j] == 1){
                    g.setColor(Color.orange);
                    g.fillOval(j*100-65,i*100-65,30,30);
                }
            }
        }
        g.setColor(Color.gray);
        g.fillOval(100*xOld-65,100*yOld-65,30,30);

        //绘制提示棋子
        if(!isBlack){
            g.setColor(Color.white);
            g.fillOval(810,610,80,80);
            g.setColor(Color.black);
            g.setFont(new Font("微软雅黑", Font.BOLD, 40));//设置字体
            g.drawString("白", 830, 665);
        }else{
            g.setColor(Color.black);
            g.fillOval(810,110,80,80);
            g.setColor(Color.white);
            g.setFont(new Font("微软雅黑", Font.BOLD, 40));//设置字体
            g.drawString("黑", 830, 165);
        }

        //绘制结束界面
        if(judge == 1){
            g.setColor(Color.green);
            g.setFont(new Font("微软雅黑", Font.BOLD, 100));//设置字体
            g.drawString("黑 胜", 350, 350);
        }else if(judge == 2){
            g.setColor(Color.green);
            g.setFont(new Font("微软雅黑", Font.BOLD, 100));//设置字体
            g.drawString("白 胜", 350, 350);
        }else if(judge == 3){
            g.setColor(Color.green);
            g.setFont(new Font("微软雅黑", Font.BOLD, 100));//设置字体
            g.drawString("平 局", 350, 350);
        }
        //让手棋界面提示
        if(isPass && cou != 64){
            g.setColor(Color.red);
            g.setFont(new Font("微软雅黑", Font.BOLD, 100));//设置字体
            g.drawString("让 手", 350, 350);
        }
        b1.setBounds(800,700,100,100);
        b1.setVisible(true);
        this.add(b1);
        b1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                init();
                repaint();
            }
        });


    }




    //action
    @Override
    public void actionPerformed(ActionEvent e) {
        //得分初始化
        score = 0;
        if(isTap && cou <= 64 && othePre[9][9] != 5 && !isOver){
            isBlack = !isBlack;
            xOld = x; yOld = y;
            cou++;
            System.out.println(participant+" "+othePre[0][0]+"\n"+x+" "+y);
            //获取棋盘
            P5_UltOthello.position(y, x, othe, participant);
            //预测棋子
            P4_Othello.predict(othe, othePre, participant);

            //判定让手棋
            if(othePre[9][9] != 5 ){
                participant = -participant;
                isTap = false;
            }
            repaint();
        }
        //让手棋使用让手棋方法：
        if(isTap && cou <= 64 && othePre[9][9] == 5 &&!isOver){
            isBlack = !isBlack;
            isPass = true;
            System.out.println(othePre[9][9]);

            //监测点
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 10; j++) {
                    System.out.printf("%3d",othe[i][j]);
                }
                System.out.println();
            }
            System.out.println(participant);
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 10; j++) {
                    System.out.printf("%3d",othePre[i][j]);
                }
                System.out.println();
            }

            //获取棋盘
            P5_UltOthello.position(y, x, othe, participant);
            //预测棋子
            P4_Othello.predict(othe, othePre, -participant);
            //判断另一方能否走棋，如果另一方也不能走，那么游戏结束。
            if(othePre[9][9] == 5){
                isPass = false;
                isOver = true;
            }
            repaint();
            isTap = false;
        }

        //获取得分状况
        for (int i = 1; i < 9; i++) {
            for (int j = 1; j < 9; j++) {
                score += othe[i][j];
            }
        }
        /************** 结束判定 ***************/
        if(isOver) {
            //结束判定
            if (score < 0) {
                judge = 1;
            } else if (score > 0) {
                judge = 2;
            } else{
                judge = 3;
            }
        }
        whiteScore = (cou + score)/2;
        blackScore = cou - whiteScore;
        timer.start();//自己添加
    }

    @Override
    public void mousePressed(MouseEvent e) {

        x = e.getX();
        y = e.getY();
        if (x > 0 && x <= 800 && y > 0 && y <= 800) {
            x = x / 100 + 1;
            y = y / 100 + 1;
            if(othePre[y][x] == 1) {
                //System.out.println("check鼠标监听");
                isTap = true;
                isPass = false;
            }
        }

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}


/*****************backup***************/