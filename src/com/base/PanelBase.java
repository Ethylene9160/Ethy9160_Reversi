package com.base;
import com.ethy9160.Regret.Loader;
import com.ethy9160.Regret.Saver;
import com.ethy9160.move.*;

import javax.swing.*;
import java.awt.*;

/** 操作面板基础（用于被继承）
 * 这个类已经继承了JFrame类，可以自由自在地对JFrame发挥。
 * <p>
 * 1. 全局（成员）变量：</p>
 * 两个10*10的二维数组othe[][]、othePre[][];
 * int型x, y, xOld, yOld, score, whiteScore, blackScore, cou, participant, judge;
 * 布尔型isOver, isTap, isBlack, isPass;
 * Timer型timer（未初始化，需要手动初始化）
 * <p>
 * 2. 构造器：</p>
 * 构造器中仅包含了init()函数，没有添加监听事件。
 * <p>
 * <b>3. 方法：</b></p>
 * init():无参方法，返回值为空，对上述除了timer之外的变量进行了初始化。<p>
 * listenMouse():无参方法，返回值为空，用来记录鼠标点击的地方。
 * 详情见该方法定义，由于界面变化，这个方法或许需要被重写。</p>
 * passProcess():无参方法，返回值为空。如果出现了让手棋之后的游戏算法。详见其定义。<p>
 * judger():无参方法，返回值为空。用于判定参与人员的得分，对局状况。详见其定义。</p>
 * @Author Ethylene9160
 * @version 1.4
 * @since 1.4
 */
public class PanelBase extends JPanel{
    /***** 全局变量设定 *****/
    public int othe[][] = new int[10][10];
    public int othePre[][] = new int[10][10];
    public int participant, x, y, score, whiteScore, blackScore, cou;
    public int judge;//判断获胜情况，黑棋胜利为1，白棋胜利为2，平局为3
    public int xOld, yOld;
    public boolean isOver, isTap, isBlack, isPass, yourTimeOut, opponentTimeOut, isStart;
    public Timer timer;
    public JTextField timeCounter = new JTextField(5);
    public int timeCnt;
    public String timeCount;
    public boolean isRegret;
    public boolean isSaved;
    public boolean isLoaded;
    //滚动条
    public JScrollPane scrollPane;
    public JTextArea systemText = new JTextArea("系统消息：\n");//用来存储系统消息
    /** 初始化
     * 这个方法将除了timer之外的成员变量进行了初始化。
     * @return null
     */
    public void init(){
        timeCounter.setText("");
        //棋盘初始化
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                othe[i][j] = 0;
                othePre[i][j] = 0;
            }
        }
        //初始化棋盘上的四颗棋子
        othe[4][4] = 1;
        othe[4][5] = -1;
        othe[5][5] = 1;
        othe[5][4] = -1;
        othePre[3][4] = 1;
        othePre[4][3] = 1;
        othePre[5][6] = 1;
        othePre[6][5] = 1;

        //变量数据初始化
        score = 0;//分数归零
        whiteScore = 2; blackScore = 2;
        participant = -1;//黑棋先行
        x = 0; y = 0;
        xOld = -100; yOld = -100;
        judge = 0;
        cou = 4;
        //游戏状态初始化
        isOver = false;
        isStart = false;
        isTap = false;
        isBlack = true;
        isPass = false;
        yourTimeOut = false;
        opponentTimeOut = false;
        isRegret = false;
        isSaved = false;
        isLoaded = false;
    }

    /** 构造器
     * 空构造器。
     */
    public PanelBase() {
        init();
//        systemText.setBackground(new Color(40,233,233));
//        systemText.setEditable(false);
//        this.scrollPane = new JScrollPane(systemText);
    }

    /** 鼠标定位
     * <B>注意：</B>这个方法<B>没有监听鼠标！</B>
     * 需要自行将这个方法嵌套在鼠标监听中，
     * 并对x，y赋值（x = e.getX(); Y= e.getY();)）。
     * @return null
     */
    public void listenMouse(){
        if (x > 64 && x < 596 && y > 192 && y < 704) {
            x = x / 64;
            y = y / 64 - 2;
            System.out.println("check mouseListener");
            if (othePre[y][x] == 1) {
                isTap = true;
                isPass = false;
            }
        }
    }

    /** 让手棋
     * 无参方法，空返回值。
     * <b>这个方法自带判定othePre[9][9]是否为5的步骤。</b>
     * 如果场上出现让手棋，那么将会执行这个方法。
     * 这个方法会对isBlack取反，同时对isPass赋值为真。
     * 同时，这个方法<b>能够判定游戏是否结束</b>，
     * 因为如果出现让手棋的选手也没有棋子可以下，游戏结束，isOver为真。
     * @return null
     */
    public void passProcess(){
        //让手棋使用让手棋方法：
        if(isTap && cou <= 64 && othePre[9][9] == 5 &&!isOver){
            timeCnt = 0;
            isBlack = !isBlack;
            isPass = true;
            System.out.println(othePre[9][9]);

//            //监测点
//            for (int i = 0; i < 10; i++) {
//                for (int j = 0; j < 10; j++) {
//                    System.out.printf("%3d",othe[i][j]);
//                }
//                System.out.println();
//            }
//            System.out.println(participant);
//            for (int i = 0; i < 10; i++) {
//                for (int j = 0; j < 10; j++) {
//                    System.out.printf("%3d",othePre[i][j]);
//                }
//                System.out.println();
//            }

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
    }

    /** 判定得分、结束：
     * 返回值：空；
     * 获取场上总分值（score）；
     * 计算白棋得分（whiteScore）和黑棋得分（blackScore）；
     * 结束之后判断谁赢（judge）。
     * @return null
     */
    public void judger(){
        //获取得分状况
        score = 0;
        for (int i = 1; i < 9; i++) {
            for (int j = 1; j < 9; j++) {
                score += othe[i][j];
            }
        }
        if(isOver) {
            //结束判定
            if(!yourTimeOut && ! opponentTimeOut) {
                if (score < 0) {
                    judge = 1;
                } else if (score > 0) {
                    judge = 2;
                } else {
                    judge = 3;
                }
            }else {
                if (yourTimeOut) {
                    judge = 4;
                } else if (opponentTimeOut) {
                    judge = 5;
                }
            }
        }
        whiteScore = (cou + score)/2;
        blackScore = cou - whiteScore;
    }
}
