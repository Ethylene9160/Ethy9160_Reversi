package com.ethy9160.main;

import com.ethy9160.Regret.*;
import com.ethy9160.move.P4_Othello;
import com.ethy9160.move.P5_UltOthello;
import com.sun.java.swing.plaf.windows.WindowsInternalFrameTitlePane;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * 本地对战框：用于实现四大基本功能。继承了JPanel类，
 * 由于框架的bug，这个类没有继承PanelBase。
 * @author 马齐晨、Ethylene9160
 */
public class CommonPanel extends JPanel implements ActionListener, MouseListener{

    /*************  全局变量设定  ****************/
    private int othe[][] = new int[10][10];//实际操作对这个进行
    private int othePre[][] = new int[10][10];//这个用作预测棋子
    int max;
    ArrayList<Step> stepList;
    ArrayList<Step> steplistSave;
    private int participant,
            x, y,
            xOld, yOld,
            score, whiteScore, blackScore,
            cou,
            cheatInfo,//默认为0（false），与isCheat绑定。
            judge;//判断获胜情况，黑棋胜利为1，白棋胜利为2，平局为3
    boolean isOver,//游戏是否结束
            isTap,//是否点击了鼠标
            isBlack,//是否是黑棋
            isWild,//心跳模式
            isCheat,//是否是cheat模式
            isRegret, isSaved, isLoaded;
    private boolean isPass;//是否是让手棋
    JButton restartButton = new JButton();//自行设定
    JButton regretButton = new JButton();//自行设定
    JButton saveButton = new JButton();//自行设定
    JButton loadButton = new JButton();//自行设定
    JButton normalButton, //回到正常模式的button
            switchButton, // 切换对手的button
            wildButton,//心跳模式
            cheatButton;//设置作弊按钮

    //滚动条
    JScrollPane scrollPane;
    JTextArea systemText = new JTextArea("系统消息：\n");//用来存储系统消息
    Timer timer = new Timer(200,this);//200ms刷新一次，请自行设置
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
        stepList = new ArrayList<Step>();
        steplistSave = new ArrayList<Step>();
        {
            score = 0;//用来判断谁赢了，如果数组每个元素的值的和小于零，那么黑棋获胜；大于零，白棋获胜；否则平局。
            participant = -1;
            cheatInfo = 0;
            x = 0;
            y = 0;
            judge = 0;
            xOld = -100;
            yOld = -100;
            //游戏状态
            isOver = false;
            isTap = false;
            isBlack = true;
            isPass = false;
            isCheat = false;
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
            whiteScore = 2;
            blackScore = 2;
            switchButton.setVisible(false);
        }
    }

    /*************** 添加监听事件 ******************/
    public CommonPanel(){
        switchButton = new JButton();
        normalButton = new JButton();
        cheatButton = new JButton();
        init();
        scrollPane = new JScrollPane(systemText);
        systemText.setEditable(false);
        systemText.setBackground(new Color(40,233,233));
        this.setFocusable(true);
        this.addMouseListener(this);//自行在构造器中添加！
        timer.start();
    }

    /***********  绘制操作界面 *************/
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);//清屏
        this.setBackground(new Color(20,230,230));//设置面板颜色
        Data.background.paintIcon(this,g,0,0);
        this.setBounds(0,-46 , 1024, 768);
        Data.chat.paintIcon(this,g,650,420);
        Data.board.paintIcon(this,g,0,128);
        Data.counterPanel.paintIcon(this,g,240,75);
        Data.localButtonPanel.paintIcon(this,g,625,80);
        systemText.setFont(new Font("等线",Font.BOLD,16));
        scrollPane.setBounds(656,426,258,268);
        this.add(scrollPane);
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
        this.add(switchButton);
        this.add(cheatButton);
        this.add(normalButton);
    }

    public void moveChess(){
        //得分初始化
        score = 0;
        if(isTap && cou <= 64 && othePre[9][9] != 5 && !isOver){
            isBlack = !isBlack;

            Step s = new Step(participant, othe, xOld, yOld, cou, cheatInfo);//导入step
            stepList.add(s);

            xOld = x; yOld = y;
            cou++;
            System.out.println(participant+" "+othePre[0][0]+"\n"+x+" "+y);
            //获取棋盘
            P5_UltOthello.position(y, x, othe, participant);
            //预测棋子
            if(!isCheat) {
                //预测棋子
                P4_Othello.predict(othe, othePre, participant);
            }else{
                //无论如何都要落子
                othe[y][x] = participant;
            }
            //判定让手棋
            if(othePre[9][9] != 5 ){
                participant = -participant;
                isTap = false;
            }
            Step s2 = new Step(participant, othe, xOld, yOld, cou, cheatInfo);//导入step
            System.out.println("cou储存值：" + cou);
            steplistSave.add(s2);
            //
            if(isCheat){
                for (int i = 0; i < 9; i++) {
                    for (int j = 0; j < 9; j++) {
                        if(othe[i][j] == 0) {
                            othePre[i][j] = 1;
                        }else{
                            othePre[i][j] = 0;
                        }
                    }
                }
            }
            repaint();
        }
    }

    private void load(){
        if(isLoaded){
            isLoaded = false;
//            isPass = false;
            try {
                File file = new File("D:\\resource\\Library");
                max = file.list().length;
                int c = 0;
                String [] options = new String[max];
                for(String name : file.list()){
                    options[c] = name;
                    c++;
                }
                String str1 =  (String)JOptionPane.showInputDialog(null,"当前共有" + max +"项存档，请选择：","提示",JOptionPane.QUESTION_MESSAGE,null,options,options[0]);
                //System.out.println(info);
                System.out.println("Str1:" + str1);

                //dialog.dispose();//关闭弹窗
//                int index = Integer.parseInt(str1);
                if(str1 != null) {//修改：12/21/11：05
                    systemText.append("正在读取【" + str1 + "】中的存档\n");
                    Loader ld = new Loader();
                    ld.loadFile(str1);
                    participant = ld.getColor();
                    isBlack = (participant == -1);
                    cheatInfo = ld.getCheatInfoCur();
                    isCheat = (cheatInfo == 1);
                    for (int i = 1; i < 9; i++) {
                        for (int j = 1; j < 9; j++) {
                            othe[i][j] = ld.getCurrentBoard()[i][j];
                        }
                    }
                    steplistSave = ld.getStepListSave();
                    stepList = ld.getStepList();
                    isTap = false;
                    xOld = ld.getxCur();
                    yOld = ld.getyCur();
                    this.cou = ld.getCouCur();
                    System.out.println("cou: " + cou);
                    if (isCheat) {
                        for (int i = 0; i < 9; i++) {
                            for (int j = 0; j < 9; j++) {
                                this.othePre[i][j] = 1;
                            }
                        }
                        switchButton.setVisible(true);
                    } else {
                        P4_Othello.predict(othe, othePre, -participant);
                        int p = steplistSave.size();
                        switchButton.setVisible(false);
//                        if (p > 2 && this.participant == steplistSave.get(p - 2).getColor()) {
//                            isPass = true;
//                        }
                        //如果悔棋之后，再上一步的相对应的participant无路可走，那么isPass 为真。
//                        int newPre[][] = new int[10][10];
//                        P4_Othello.predict(stepList.get(p-2).getBoard(), newPre, stepList.get(p-2).getColor());
//                        if(newPre[9][9] == 5){
//                            isPass = true;
//                        }else{
//                            isPass = false;
//                        }
                        //延用AIPanel中的方法
                        if(p > 2) {
                            if (othe[yOld][xOld] == participant) {
                                isPass = true;
                            } else {
                                isPass = false;
                            }
                        }
                    }
                    isOver = false;
                    judge =0;
                    repaint();
                    systemText.append("读取成功！\n");
                }else{
                    systemText.append("您取消了存档的读取。\n");//修改：12/21/11:11
                }
            } catch(Exception e){
                //JOptionPane.showMessageDialog(null,"发生了一些错误，我们无法按照您的需求读取存档信息。","错误",JOptionPane.ERROR_MESSAGE);	//消息对话框
                System.out.println("Error");
                systemText.append("读取失败，存档格式非法。\n");
            }
            System.out.println(participant);
            repaint();
        }

    }

    private void save() {
        if(isSaved){
            int currentMax;
            try {
                File file = new File("D:\\resource\\Library");
                currentMax = file.list().length;
                systemText.append("保存成功！档案为：Document" +(currentMax+1) +".txt\n");
                Saver sn = new Saver(steplistSave);
                sn.saveC(currentMax);
            }catch(Exception E){
                currentMax = 0;
                systemText.append("保存失败！\n");
            }
            isSaved = false;
            max = currentMax;
            repaint();
        }
    }

    private void regret(){
        if(isRegret){
            try {
//                isPass = false;
                RegretStep regret = new RegretStep(stepList);
                try {
                    for (int i = 1; i < 9; i++) {
                        for (int j = 1; j < 9; j++) {
                            othe[i][j] = regret.getBoard()[i][j];
                        }
                    }
                    isTap = false;
                    xOld = regret.getX();
                    yOld = regret.getY();
                    cou = regret.getCou();
                    cheatInfo = regret.getCheatInfo();
                    participant = regret.getColor();
                    isBlack = (participant == -1);
                    isCheat = (cheatInfo == 1);
                    if(isCheat){
                        isPass = false;
                        switchButton.setVisible(true);
                        for (int i = 0; i < 9; i++) {
                            for (int j = 0; j < 9; j++) {
                                this.othePre[i][j] = 1;
                            }
                        }
                    }else {
                        int p =stepList.size();
//                        P4_Othello.predict(othe, othePre, stepList.get(p-2).getBoard()[stepList.get(p-2).getY_former()][stepList.get(p-2).getY_former()]);
//                        if(othePre[9][9] == 5){
//                            isPass = true;
//                        }else{
//                            isPass = false;
//                        }
                        switchButton.setVisible(false);
//                        P4_Othello.predict(stepList.get(p-2).getBoard(), othePre, stepList.get(p-2).getColor());
                        if(othe[yOld][xOld] == participant) {
                            isPass = true;
                        }else{
                            isPass = false;
                        }
//                        if(othePre[9][9] == 5){
//                            isPass = true;
//                        }else{
//                            isPass = false;
//                        }
                        P4_Othello.predict(othe, othePre, -participant);
                        //如果悔棋之后，再上一步的相对应的participant无路可走，那么isPass 为真。
//                        int newPre[][] = new int[10][10];

//                        if(p > 2 && this.participant == stepList.get(p-2).getColor()){
//                            isPass = true;
//                        }
                    }
                    repaint();
                } catch (IndexOutOfBoundsException e1) {
                    System.out.println("越界");
                    systemText.append("出现了一些问题：超过悔棋记忆上下限\n");
                    init();
                }
                regret.initR(stepList);
                System.out.println(participant);
                repaint();
                //悔棋的时候也应当把steplistSave移走最后一项。12月19日23：00
                steplistSave.remove(steplistSave.remove(steplistSave.size()-1));
            }catch(IndexOutOfBoundsException e2){
                System.out.println("OutOfBounds");
                systemText.append("悔到头啦，不如重新开始吧~\n");
                repaint();
            }finally {
                isRegret = false;
            }
        }
    }

    public void passProcess(){
        //让手棋使用让手棋方法：
        if(isTap && cou <= 64 && othePre[9][9] == 5 &&!isOver){
            isBlack = !isBlack;
            isPass = true;
            System.out.println(othePre[9][9]);

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

    public void judger(){
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
    }


    //action
    @Override
    public void actionPerformed(ActionEvent e) {
        load();
        save();
        regret();
        moveChess();
        passProcess();
        judger();
        timer.start();//自己添加
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(!isOver) {
            x = e.getX();
            y = e.getY();
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
    //int i = 0;
    /**
     * 请在这个地方写下对button的<b>除了设置 <code>this.add(button);</code> 之外的操作</b>，以减少绘图中的代码行数。
     * @author Ethylene9160
     */
    public void setButtons(){//最近修改：12/21/10：46
        //重新开始按钮
        {
            restartButton.setIcon(Data.restartHint2);
            restartButton.setBounds(650, 130, 120, 40);
            restartButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    init();
                    repaint();
                }
            });
        }

        //悔棋按钮
        {
            regretButton.setIcon(Data.regret);
            regretButton.setBounds(800,130,120,40);
            regretButton.setVisible(true);
            this.add(regretButton);
            regretButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(!isRegret && !isOver){
                        isRegret = true;
                    }
                }
            });
        }

        //存档按钮
        {
            saveButton.setIcon(Data.save);
            saveButton.setBounds(650,200,120,40);
            saveButton.setVisible(true);
            this.add(saveButton);
            saveButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(!isSaved && cou > 4 && !isOver){
                        isSaved = true;
                    }
                }
            });
        }

        //读档按钮
        {
            loadButton.setIcon(Data.load);
            loadButton.setBounds(800,200,120,40);
            loadButton.setVisible(true);
            this.add(loadButton);
            loadButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(!isLoaded){
                        isLoaded = true;
                    }
                }
            });
        }

        //跳转棋按钮
        {
            switchButton.setIcon(Data.shift);
            switchButton.setBounds(650, 340, 120, 40);
            switchButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    //if (!isPass) {
                    if(isCheat) {
                        if (!isCheat) P4_Othello.predict(othe, othePre, participant);
                        if (othePre[9][9] == 5) {
                            System.out.println("让手棋区间，无法交换双方");
                        } else {
                            isBlack = !isBlack;
                            participant = -participant;
                        }
                        repaint();
                    }else{
                        System.out.println("只有狂野模式才能使用。");
                    }
                    //} else {
                       // JOptionPane.showMessageDialog(null,"让手棋期间，无法进行这项操作。","提示",JOptionPane.WARNING_MESSAGE);	//消息对话框

                   // }
                }
            });
        }

        //狂野模式按钮
        {
            cheatButton.setIcon(Data.wild);
            cheatButton.setBounds(800, 270, 120, 40);
            cheatButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    for (int i = 0; i < 9; i++) {
                        for (int j = 0; j < 9; j++) {
                            if (othe[i][j] == 0)
                                othePre[i][j] = 1;
                        }
                    }
                    switchButton.setVisible(true);
                    isCheat = true;
                    cheatInfo = 1;
                    repaint();
                }
            });
        }

//        //心跳模式，添加事件：12/24/20：50
//        {
//            wildButton.setBounds(650, 340, 120, 40);
//            wildButton.addActionListener(new ActionListener() {
//                @Override
//                public void actionPerformed(ActionEvent e) {
//                    if(!isCheat){
//                        isCheat = true;
//                        systemText.append("狂野模式启动。\n");
//                    }
//                }
//            });
//        }

        //回到正常模式的按钮：
        {
            normalButton.setIcon(Data.normal);
            normalButton.setBounds(650, 270, 120, 40);
            normalButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    isCheat = false;
                    switchButton.setVisible(false);
                    cheatInfo = 0;
                    P4_Othello.predict(othe, othePre, -participant);
                    if (othePre[9][9] == 5) {
                        P4_Othello.predict(othe, othePre, participant);
                        if (othePre[9][9] == 5) {
                            isOver = true;
                            //i++;
                            System.out.println("结束了");
                            if (score < 0) {
                                judge = 1;
                            } else if (score > 0) {
                                judge = 2;
                            } else {
                                judge = 3;
                            }
                        } else {
                            isPass = true;
                            isBlack = !isBlack;
                            participant = -participant;
                        }
                    }
                    repaint();
                }
            });
        }
    }

}
