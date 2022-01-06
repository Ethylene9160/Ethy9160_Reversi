package com.ethy9160.AI;
import com.base.PanelBase;
import com.base.PanelInter;
import com.ethy9160.Regret.RegretStep;
import com.ethy9160.Regret.Step;
import com.ethy9160.main.Data;
import com.ethy9160.move.*;
//import org.omg.CORBA.INITIALIZE;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class AIPanel extends PanelBase implements MouseListener, KeyListener, ActionListener, PanelInter {

    /*************  全局变量设定  ****************/

    public static byte participant0;
    private final int _INIT_PARTICIPANT_ = participant0;
    ArrayList<Step> stepList;
    public static void setParticipant(byte p){
        participant0 = p;
        System.out.println(participant0);
    }
    private boolean isYou;
    JButton normalButton; //回到正常模式的button
             // 切换对手的button
            //cheatButton;//设置作弊按钮
    JButton restartButton = new JButton();//自行设定
    JButton regretButton = new JButton();//自行设定
    private int[] AIjudge = new int[2];
    int regretCount;
    /**************  初始化  ***************/
    @Override
    public void initForThis() {
        stepList = new ArrayList<>();
        //如果初始参与者是1，那么你是后手。反之。
        isYou = (_INIT_PARTICIPANT_ == -1);
        regretCount = 0;
        //机器走棋子初始化
        AIjudge[1] = 0;
        AIjudge[0] = 0;
//
//
//
//        othe = new int[][]{
//                {0,0,0,0,0,0,0,0,0,0},
//                {0, 0, 0, 0, 0, -1, -1, -1, 0, 0},
//                {0, -1, 0, 0, 0, 0, 1, 0, 0, 0},
//                {0, -1, -1, 0, 1, 0, 1, -1, -1, 0},
//                {0,-1,-1,1,1,1,1,-1,-1,0},
//                {0, -1, -1, -1, -1, -1, -1, -1, -1, 0},
//                {0, -1, 1, -1, -1, 1, 1, 1, -1, 0},
//                {0, -1, 0, 1, 1, 1, 1, 0, -1, 0},
//                {0, 0, -1, -1, -1, -1, -1, 0, 0, 0},
//                {0,0,0,0,0,0,0,0,0,0}
//        };
//        xOld = 6;yOld = 7;
//        cou = 46;
//        whiteScore = 15;
//        blackScore = 31;
//        P4_Othello.predict(othe, othePre,1);
//        String str = "经过5步搜索走了：";
//        systemText.append(str + "4, 7\n");
//        systemText.append(str + "6, 2\n");
//        systemText.append(str + "7, 6\n");
//        systemText.append(str + "3, 6\n");
//        systemText.append(str + "3, 7\n");
//        systemText.append(str + "4, 8\n");
//        systemText.append(str + "1, 3\n");
//        systemText.append(str + "2, 2\n");
//        systemText.append(str + "5, 5\n");
//        systemText.append(str + "1, 5\n");
//        systemText.append(str + "3, 8\n");


    }

    @Override
    public void timeLimit() {

    }

    @Override
    public void setButtons() {
        //悔棋
        {
            regretButton.setIcon(Data.regret);
            regretButton.setBounds(800,345,120,40);
            regretButton.setVisible(true);
            this.add(regretButton);
            regretButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(!isRegret && !isOver){
                        isRegret = true;
                        System.out.println("悔棋");
                    }
                }
            });
        }

        //重新开始按钮
        {
            restartButton.setIcon(Data.restartHint);
            restartButton.setBounds(640, 345, 120, 40);
            restartButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    init();
                    initForThis();
                    repaint();
                }
            });
        }


    }

    /*************** 添加监听事件 ******************/
    public AIPanel(){
        super();
        initForThis();
        this.setFocusable(true);
        this.addMouseListener(this);
        this.addKeyListener(this);

        scrollPane = new JScrollPane(systemText);
        systemText.setEditable(false);
        systemText.setBackground(new Color(44,222,225));
        //switchButton = new JButton("切换棋手");
        //normalButton = new JButton("回到正常");
        //cheatButton = new JButton("狂野模式");
        timer = new Timer(250,this);
        timer.start();
    }

    /***********  绘制操作界面 *************/
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);//清屏
        //this.setBackground(new Color(20,230,230));//设置面板颜色
        Data.background.paintIcon(this,g,0,0);
        this.setBounds(0,-46 , 1024, 768);
        Data.board.paintIcon(this,g,0,128);
        Data.counterPanel.paintIcon(this,g,240,75);
        systemText.setFont(new Font("等线",Font.BOLD,16));
        scrollPane.setBounds(656,426,258,268);
        this.add(scrollPane);
        Data.chat.paintIcon(this,g,650,420);
        //绘制对局
        for (int i = 1; i < 9; i++){
            for (int j = 1; j < 9; j++) {
                if(othe[i][j] == -1){
                    Data.blackChess.paintIcon(this,g,64*j+2, 64*i+130);
                }
                else if(othe[i][j] == 1){
                    Data.whiteChess.paintIcon(this,g,64*j+2,64*i+130);
                }
                else if(othePre[i][j] == 1){
                    g.setColor(Color.red);
                    g.fillOval(64*j+22,64*i+150,20,20);
                }
            }
        }
        g.setColor(Color.gray);
        g.fillOval(64*xOld+22,64*yOld+150,20,20);


        //绘制提示
        if(!isBlack){
            Data.whiteMark.paintIcon(this,g,400,70);
        }else{
            Data.blackMark.paintIcon(this,g,170,70);
        }
        g.setColor(Color.black);
        g.setFont(new Font("黑体", Font.BOLD, 30));//设置字体
        g.drawString(String.format("%02d",blackScore), 265, 122);
        g.drawString(String.format("%02d",whiteScore), 321, 122);
        Data.computer.paintIcon(this, g, 650, 76);


        //绘制结束界面
        //绘制结束界面
        if(judge == 1){
            Data.blackWin.paintIcon(this,g,70,198);
        }else if(judge == 2){
            Data.whiteWin.paintIcon(this,g,70,198);
        }else if(judge == 3){
            Data.drawHint.paintIcon(this,g,70,198);
        }
        //让手棋界面提示
        if(isPass && cou != 64){
            Data.passHint.paintIcon(this,g,220,395);
        }
        //在面板上添加按钮
        setButtons();//【重要】 这个方法中设置按钮的基本信息！！！
        this.add(restartButton);
        //this.add(switchButton);
        //this.add(cheatButton);
        //this.add(normalButton);
    }





    //监听鼠标按键
    @Override
    public void mouseClicked(MouseEvent e) {
        //你的回合：进行监听
        if(isYou) {
            x = e.getX();
            y = e.getY();
            listenMouse();
        }
    }

    //action
    @Override
    public void actionPerformed(ActionEvent e) {
        if(!isOver) {
            regret();//如果出现错误，请将这个部分删去
            moveChess();
            passProcess();
            judger();
        }
        timer.start();
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

    @Override
    public void mousePressed(MouseEvent e){

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void moveChess() {
        //机器走棋
        if(!isYou){
            System.out.println("checkPointAI  1");
            //AIjudge = IntelligentOpponent.IntelligentAuthorise(othePre, othe[1][1], othe[1][8], othe[8][1], othe[8][8], (byte)participant);
            if(8 < cou && 60 >= cou) {//递归+权值
                AIjudge = new AIStepJudge(othe, participant).findBestPosition(othePre);
                System.out.println("checkPointAI  2");
                if (AIjudge[0] != 0 && AIjudge[1] != 0) {
                    y = AIjudge[0];
                    x = AIjudge[1];
                    systemText.append("经过5步搜索走了："+x+", " + y + "\n");
                }
            }else if(cou > 60){//贪婪+递归
                AIjudge = new AIStepJudge(othe, participant).reverseMorePieces(othePre, cou);
                //AIjudge = new AIStepJudge(othe, participant).findBestPosition(othePre);
                System.out.println("checkPointAI  2");
                if (AIjudge[0] != 0 && AIjudge[1] != 0) {
                    y = AIjudge[0];
                    x = AIjudge[1];
                    systemText.append("经过贪婪搜索走了："+x+", " + y + "\n");
                }
            } else{//权值表直接下
                AIjudge = IntelligentOpponent.IntelligentAuthorise(othePre, othe[1][1], othe[1][8], othe[8][1], othe[8][8], (byte)participant);
                y = AIjudge[0];
                x = AIjudge[1];
                systemText.append("按照权值表走了："+x+", " + y + "\n");
            }
            System.out.println(x + "" + y);
            System.out.println(cou);
            isTap = true;
            isPass = false;
        }
        else{
//            AIjudge = IntelligentOpponent.IntelligentAuthorise(othePre, othe[1][1], othe[1][8], othe[8][1], othe[8][8], (byte)participant);
//            y = AIjudge[0];
//            x = AIjudge[1];
//            if(othePre[y][x] == 1) {
//                System.out.println("check鼠标监听");
//                isTap = true;
//                isPass = false;
//            }
        }
        if(isTap && cou <= 64 && othePre[9][9] != 5 && !isOver){
            //AIjudge = IntelligentOpponent.IntelligentAuthorise(othePre, othe[1][1], othe[1][8], othe[8][1], othe[8][8], (byte)participant);

            isBlack = !isBlack;
            //12/23/17:29
            if(isYou) {
                if(isPass){
                    regretCount = 1;
                }else{
                    regretCount = 0;
                }
                Step s = new Step(participant, othe, xOld, yOld, cou, regretCount);//导入step
                stepList.add(s);
            }
            xOld = x; yOld = y;
            cou++;
            System.out.println(participant+" "+othePre[0][0]+"\n"+x+" "+y);
            //获取棋盘
            P5_UltOthello.position(y, x, othe, participant);
            //预测棋子
            P4_Othello.predict(othe, othePre, participant);
            //判定让手棋
            judgePass();
            repaint();
        }
    }

    @Override
    public void judgePass() {
        if(othePre[9][9] != 5 ){
            participant = -participant;
            //切换对手
            isYou = !isYou;
            isTap = false;
        }
    }


    //悔棋
    private void regret(){
        if(isYou && isRegret){
            isPass = false;
            RegretStep regret = new RegretStep(stepList);
            try {
                for (int i = 1; i < 9; i++) {
                    for (int j = 1; j < 9; j++) {
                        othe[i][j] = regret.getBoard()[i][j];
                    }
                }
//                int oldCou = this.cou;
                isTap = false;
                xOld = regret.getX();
                yOld = regret.getY();
                cou = regret.getCou();
                participant = regret.getColor();
                isBlack = (participant == -1);
                regretCount = regret.getCheatInfo();
                regret.initR(stepList);
                P4_Othello.predict(othe, othePre, -participant);
                System.out.println(participant);
                //if(oldCou == this.cou+1) isPass = true;
                //12/24/16:54
                if(othe[yOld][xOld] == _INIT_PARTICIPANT_) isPass = true;
            } catch (IndexOutOfBoundsException e1) {
                System.out.println("越界");
                systemText.append("出现了一些问题：超过悔棋记忆上下限\n");
            }
            repaint();
            isRegret = false;
        }
    }
}
