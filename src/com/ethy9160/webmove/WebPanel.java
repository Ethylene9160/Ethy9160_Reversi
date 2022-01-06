package com.ethy9160.webmove;

import com.base.*;
import com.ethy9160.main.Data;
import com.ethy9160.move.P4_Othello;
import com.ethy9160.move.P5_UltOthello;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.Socket;
/**
 * 重点Panel类
 * 继承自父类，本质上是panel。
 * <p>
 *     1.如何解决各类无法描述的bug
 *     2.如何保证出现bug的时候最大限度保证面板能够正常运行
 *
 * </p>
 * 网络报错序号对应一览表：
 * <p>
 * 7：对方已经初始化。 645，651
 * <p>
 * 8：对方退出游戏 520，566
 * <p>
 * 9：服务器即将关闭 556
 * <p>
 * 89：认输 325，474
 * <p>
 * 90：准备请求【高频bug】 640
 * <p>
 * 91：对方正在游戏中
 * 583
 * <p>
 * 92：发出连接请求
 * 268，605
 * <p>
 * 93：发送接收连接的请求
 *
 * <p>
 * 94：未知错误原因，如内存异常，数组越界
 * <p>
 * 95：颜色不相符合
 * <p>
 * 96：对方掉线（不是通过按钮退出游戏）
 * <p>
 * 98：对手不存在
 * <p>
 * 99：超时
 * <p>
 * 其他：我方断网（在receiver中弹出）
 * <p>
 * 使用花生壳的公共服务器。
 * @author Ethylene9160、马齐晨
 */
public class WebPanel extends PanelBase implements ActionListener, MouseListener, PanelInter{
    JButton restartButton,//重新启动按钮
            prepareButton,//准备按钮
            connectButton,//连接按钮
            cancelConnectButton,//断开连接按钮
            giveUpButton,//认输按钮
            errorConfirmButton = new JButton("确定"),
            button1 = new JButton("确定");//连接对话框和报错对话框中的按钮

    JTextField positionIn = new JTextField(2);//用于展示接收到的坐标
    JTextField positionOut = new JTextField(2);//由于展示要发送的坐标
    JTextField setID = new JTextField(3);
    JTextField connectText = new JTextField();
    JTextArea errorText = new JTextArea("");
    JTextArea invitedText = new JTextArea();
    /* ******** 设置全局变量 ******/
    public static byte participant0;//需要额外设定！
    public final byte _INIT_PARTICIPANT_ = participant0;
    public int giveUpInfo;
    private int IPPort;
    public Sender sender;//自设
    public Receiver receiver;//自设
    public String yourID, opponentID, IPAddress;//用来接收你的ID
    public String sendPosition, receivePosition, message;//需要自行设定
    public static void setParticipant(byte p){//需要自行设定
        participant0 = p;
        System.out.println(participant0);
    }
    boolean isYou,
            isConnect,
            isPrepare,
            isGiveUp,
            isOpponentInit,//对手是否初始化
            isOpponentPrepared,
            hasReceiveID = false;

    JDialog connectDialog,
            invitedDialog,
            systemDialog,
            errorDialog;


    @Override
    public void initForThis(){
        //设定开始是谁
        isYou = (_INIT_PARTICIPANT_ != 1);
        isPrepare = false;
        isGiveUp = false;
        opponentTimeOut = false;
        yourTimeOut = false;
        isStart = false;
        isOver = false;
        isOpponentPrepared = false;
        giveUpInfo = 0;//1:白胜；2：黑胜
        timeCnt = 0;
        timeCount = "";
        positionIn.setText("");
        positionOut.setText("");
    }



    public WebPanel(String IPAddress0, int IP0) throws IOException {
        super();
        initForThis();
        IPAddress = IPAddress0;
        IPPort = IP0;
        restartButton = new JButton();
        prepareButton = new JButton();
        connectButton = new JButton();
        giveUpButton = new JButton();
        cancelConnectButton = new JButton();
        //对话框设置
        this.connectDialog = new JDialog();
        this.errorDialog = new JDialog();
        this.invitedDialog = new JDialog();
        this.systemDialog = new JDialog();
        timeCounter.setFont(new Font("微软雅黑",Font.BOLD,18));
        timeCounter.setBackground(new Color(255,180,128));
        scrollPane = new JScrollPane(systemText);
        systemText.setEditable(false);
        systemText.setBackground(new Color(44,222,225));

        setDialogs();
//        Socket clientSocket = new Socket(IPAddress, 37856);
        Socket clientSocket = new Socket(IPAddress, IPPort);
        //Socket clientSocket = new Socket("10.26.139.25", 8888);
        //发送线程启动，由于是引用传递，可以随便使用。
        //Socket clientSocket = new Socket("127.0.0.1", 8888);
        sender = new Sender(clientSocket);//输出信息
        receiver = new Receiver(clientSocket, positionIn);
        //启动线程
        new Thread(sender).start();
        new Thread(receiver).start();
        this.setFocusable(true);
        this.addMouseListener(this);
        //开始计时
        timer = new Timer(329,this);
        timer.start();
    }
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);//清屏
        setButtons();
        errorText.setEditable(false);
        this.setBackground(new Color(20,230,230));//设置面板颜色
        this.setBounds(0,-46 , 1024, 768);

        Data.background.paintIcon(this,g,0,0);
        Data.board.paintIcon(this,g,0,128);
        Data.timeClock.paintIcon(this,g,795,100);
        Data.playerYou.paintIcon(this,g,650,75);
        Data.playerOpponent.paintIcon(this,g,650,120);
        Data.counterPanel.paintIcon(this,g,240,75);
        Data.webButtons.paintIcon(this,g,635,165);
        systemText.setFont(new Font("等线",Font.BOLD,16));
        scrollPane.setBounds(656,426,258,268);
        this.add(scrollPane);
        Data.chat.paintIcon(this,g,650,420);
        timeCounter.setBounds(830,110,45,30);
        timeCounter.setEditable(false);


        //绘制个人ID
        g.setColor(Color.white);
        g.setFont(new Font("微软雅黑", Font.BOLD, 17));//设置字体
        g.drawString(("你的ID:" + yourID), 670, 100);
        g.drawString(("对方ID:" + opponentID), 670, 145);
        //绘制对局
        for (int i = 1; i < 9; i++){
            for (int j = 1; j < 9; j++) {
                if(othe[i][j] == -1){
                    Data.blackChess.paintIcon(this,g,64*j+2, 64*i+130);
                }
                else if(othe[i][j] == 1){
                    Data.whiteChess.paintIcon(this,g,64*j+2,64*i+130);
                }
                else if(othePre[i][j] == 1 && isStart){
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
        g.setColor(Color.green);
        g.setFont(new Font("微软雅黑", Font.BOLD, 100));
        if((judge == 1 && !isGiveUp )|| giveUpInfo == 1){//设置字体
            Data.blackWin.paintIcon(this,g,70,198);
        }else if((judge == 2 && !isGiveUp) || giveUpInfo == 2){
            Data.whiteWin.paintIcon(this,g,70,198);
        }else if(judge == 3){
            Data.drawHint.paintIcon(this,g,70,198);
        }else if(judge == 4){
            Data.youOut.paintIcon(this,g,70,198);
        }else if(judge == 5){
            Data.opponentOut.paintIcon(this,g,70,198);
        }

        //让手棋界面提示
        if(isPass && cou != 64){
            Data.passHint.paintIcon(this,g,220,395);
        }

        //添加按钮组件
        this.add(restartButton);
        this.add(connectButton);
        this.add(prepareButton);
        this.add(cancelConnectButton);
        this.add(giveUpButton);
        this.add(giveUpButton);
//        this.add(positionOut);
//        this.add(positionIn);
        this.add(timeCounter);
        //测试网络ID
        setID.setBounds(590,0,60,40);
        setID.setEditable(false);
        this.add(setID);
    }

    //进行走棋
    @Override
    public void actionPerformed(ActionEvent e) {
        //获取positionIn的消息
        if(!positionIn.getText().equals("")){
            this.message = positionIn.getText();
            positionIn.setText("");
        }
        beforeError();

        //获取你的专属ID。
        if (!hasReceiveID) {
            if (this.message != null) {
                this.yourID = this.message;
                this.message = "";
                System.out.println(this.yourID);
                hasReceiveID = true;
                repaint();
            }
        }
        if(isConnect) {
            if(this.message.length() > 3){//检测有无在游戏中中途被连接
                String temp = message;
                message = "";
                int p1 = temp.indexOf("#");
                int p2 = temp.indexOf("#",p1+2);
                if((temp.substring(0,p1)).equals("92")){
                    sender.sendData = temp.substring(p1+1, p2) + "#91";
                    sender.isSend = true;
                }
            }else {
                connectError();
                if (!isOver && isStart) {
                    timeLimit();
                    moveChess();
                    //让手棋使用让手棋方法：
                    passProcess();
                    judger();
                    timer.start();
                    //计时器
                    timeCounter.setText(timeCount);
                    repaint();
                }
            }
        }else{
            beforeConnect();
        }
        this.message = "";
    }

    //鼠标监听
    @Override
    public void mouseClicked(MouseEvent e) {
        //你的回合：进行监听
        if(isYou && isStart && !isOver) {
            x = e.getX();
            y = e.getY();
            listenMouse();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

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
    public void moveChess() {
        if(message.equals("89")){
            isGiveUp = true;
            this.errorText.setText("游戏结束：对方认输\n");
            systemText.append("对方认输了\n");
            isOver = true;
            this.errorDialog.setVisible(true);
            if(_INIT_PARTICIPANT_ == 1){
                giveUpInfo = 2;
            }else{
                giveUpInfo = 1;
            }
        }
        //对手走棋
        if(!isYou){
            timeCnt= 0;
            receivePosition = message;
            if ("".equals(receivePosition)) {
            } else {
                int res = Integer.parseInt(receivePosition);
                if(res > 88){
                    /* 错误信息统计-游戏过程中
                     * 91：对方切换了网络
                     * 94：未知原因网络故障
                     * 99：对方timeOut
                     */
                    if(res == 99) {
                        opponentTimeOut = true;
                        isOver = true;
                        // isStart = false; 修改时间 12月19日01：33
                    }
                }else {
                    x = res / 10;
                    System.out.println(x);
                    y = res % 10;
                    System.out.println(y);
                    System.out.println(res);
                    this.message = "";
                    isTap = true;
                    isPass = false;
                    System.out.println("接收！");
                }
            }
        }
        //得分初始化

        score = 0;
        if(isTap && cou <= 64 && othePre[9][9] != 5 && !isOver){
            isBlack = !isBlack;
            if(isYou){
                //将sendPosition转换为 字符串，从而能够发送过去
                sendPosition = Integer.toString((x*10+y));
                positionOut.setText("你的坐标："+sendPosition);
                //向对手发送信息
                sender.sendData = (opponentID + "#" + sendPosition);
                sender.isSend = true;
                System.out.println("发送！");
            }
            System.out.println(x+" ck1 "+y);
            xOld = x; yOld = y;
            cou++;
            System.out.println(participant+" "+othePre[0][0]+"\n"+x+" "+y);
            //获取棋盘
            P5_UltOthello.position(y, x, othe, participant);
            //预测棋子
            P4_Othello.predict(othe, othePre, participant);
            judgePass();
        }
    }

    @Override
    public void judgePass() {
        //判定让手棋
        if(othePre[9][9] != 5 ){
            participant = -participant;
            isYou = !isYou;//切换对手
            isTap = false;
        }
    }

    @Override
    public void timeLimit() {
        if(isYou && isStart){
            timeCnt ++;
            timeCount = Integer.toString(30-timeCnt/3);
            if(timeCnt > 90){//每步限定用时30秒
                sender.sendData =opponentID + "#99";
                sender.isSend = true;
                yourTimeOut = true;
                isOver = true;
            }
        }
    }

    @Override
    public void setButtons() {
        //初始化按钮组
        restartButton.setVisible(true);
        prepareButton.setVisible(true);
        cancelConnectButton.setVisible(true);
        connectButton.setVisible(true);
        giveUpButton.setVisible(true);

        //连接按钮
        {
            connectButton.setIcon(Data.connectHint);
            connectButton.setBounds(725,180,120,40);
            connectButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(/*(!isStart || isOver) &&*/ !isConnect) {
                        connectDialog.setVisible(true);
                    }else{
                        errorText.setText("不满足连接的条件：\n正在游戏中或未断开连接。");
                    }
                }
            });
        }

        //准备按钮
        {
            prepareButton.setIcon(Data.prepare);
            prepareButton.setBounds(650, 260, 120, 40);
            prepareButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(!isStart && isConnect && !isGiveUp) {
                        isPrepare = true;
                        sender.sendData = (opponentID + "#90");
                        sender.isSend = true;
                        repaint();
                    }
                }
            });
        }



        //认输按钮
        {
            giveUpButton.setIcon(Data.surrender);
            giveUpButton.setBounds(800,260,120,40);
            giveUpButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(isStart && !isOver) {
                        isGiveUp = true;
                        isOver = true;
                        sender.sendData = opponentID + "#89";
                        sender.isSend = true;
                        if (_INIT_PARTICIPANT_ == 1) {
                            giveUpInfo = 1;
                        } else {
                            giveUpInfo = 2;
                        }
                        systemText.append("你认输了\n");
                        repaint();
                    }
                }
            });
        }

        //重新开始按钮
        {
            restartButton.setIcon(Data.restartHint2);
            restartButton.setBounds(650,340,120,40);
            restartButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(/*!isStart ||*/ isOver || isGiveUp) {
                        initForThis();
                        init();
                        repaint();
                    }
                }
            });
        }

        //取消连接按钮
        {
            cancelConnectButton.setIcon(Data.disconnect);
            cancelConnectButton.setBounds(800,340,120,40);
            cancelConnectButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(isConnect) {
                        if(isStart && !isOver) {
                            errorText.setText("您还在游戏中，不能断开。");
                            errorDialog.setVisible(true);
                        }else{
                            sender.sendData = opponentID + "#8";
                            sender.isSend = true;
                            errorText.setText("您已断开连接。");
                            errorDialog.setVisible(true);
                            isConnect = false;
                            isStart = false;
                            opponentID = null;
//                            for(byte i = 1; i < 9; i ++){
//                                for(byte j = 1; j < 9; j++){
//                                    othePre[i][j] = 0;
//                                }
//                            }
                            repaint();
                        }
                    }else{
                        errorText.setText("您已断开连接。");
                        errorDialog.setVisible(true);
                    }
                }
            });
        }
    }

    /**
     * 无论何时都有的错误类型，包含：
     * <p>98：没有连接到对手
     * <p>94：未知原因的错误
     * <p>
     * 8：对手离开房间
     * <p>9：服务器即将断开
     */
    public void beforeError(){
        if(this.message.equals("98")){
            this.message = "";
            JOptionPane.showMessageDialog(null,"网络错误：没有找到对手。","错误",0);
            systemText.append("网络错误：没有连接到对手。\n");
        }else if(this.message.equals("94")){
            this.message = "";
            JOptionPane.showMessageDialog(null,"很抱歉，我们遇到了未知错误，请检查操作是否正确。\n如果不能成功，请您退出后重新进入。","错误",0);
            systemText.append("遇到了未知错误，请检查操作。\n");
        }else if(this.message.equals("8")){
            this.errorText.setText("对手离开了房间。");
            systemText.append("对手离开了房间。\n");
            this.errorDialog.setVisible(true);
            isConnect = false;
            opponentID = null;
            isYou = false;
            repaint();
        }else if(this.message.equals("9")){
            this.message = "";
            JOptionPane.showMessageDialog(null,"注意：服务器即将关闭。\n请尽快结束并退出游戏。","警告", JOptionPane.WARNING_MESSAGE);
            systemText.append("警告：服务器即将关闭。\n");
        }
    }

    /**
     * 建立连接前，包含：
     * <p>
     * 93：接收对方的邀请信息
     * <p>
     * 95：检测棋子颜色不符报错
     * <p>
     * 92：发出邀请
     */
    public void beforeConnect(){
        if(message.equals("91")){
            this.message = "";
            this.errorText.setText("连接失败，原因：\n对方正在游戏中。");
            systemText.append("连接失败，原因：对方正在游戏中。\n");
            this.errorDialog.setVisible(true);
        }else if (message.equals("93")) {//接受对方的邀请
            this.message = "";
            this.errorText.setText("成功连接到ID："+ opponentID);
            systemText.append("连接成功！\n");
            //重连后进行初始化
            init();
            initForThis();

            this.errorDialog.setVisible(true);
            isConnect = true;
            repaint();
        }else if(message.equals("95")){//棋子颜色不相符合
            this.message = "";
            errorText.setText("连接失败：对方棋子与你相同。\n请重新选择颜色再尝试连接。");
            systemText.append("连接失败，请切换棋子颜色。\n");
            errorDialog.setVisible(true);
        } else if(this.message.length() > 2){
            String temp = message;
            message = "";
            int p1 = temp.indexOf("#");
            int p2 = temp.indexOf("#",p1+2);
            if((temp.substring(0,p1)).equals("92")){
                if(Integer.parseInt(temp.substring(p2+1)) == _INIT_PARTICIPANT_){
                    System.out.println(temp.substring(p2+1));
                    sender.sendData = temp.substring(p1+1, p2) + "#95";
                    sender.isSend = true;
                }else {
                    this.opponentID = temp.substring(p1 + 1, p2);
                    invitedText.setText("ID为" + opponentID + " 的客户希望与你建\n立连接，是否同意？");
                    invitedDialog.setVisible(true);
                }
            };
        }
    }

    /**
     * 连接成功后的错误
     * <p>
     * 96：对方掉线
     * <p>
     * 90：对手已准备
     * <p>
     * 7:对方还未初始化
     */
    public void connectError(){
        if(this.message.equals("96")){//对方掉线
//            this.errorText.setText("连接中断：对方断开了连接");
            systemText.append("连接中断：对方断开了连接\n");
            JOptionPane.showMessageDialog(null,"连接中断：对方断开了连接！","错误",0);
            this.message = "";
            opponentID = null;
            isConnect = false;
            isOver = false;
            isStart = false;
            repaint();
//            this.errorDialog.setVisible(true);
        }else if(this.message.equals("90")){
            /********这个地方十分危险！！
             * ******
             * ********/
            if(isPrepare && (isOver || isGiveUp)) {
                sender.sendData = opponentID + "#7";
                sender.isSend = true;
                this.errorText.setText("对方正在等待重新开始，请尽\n快复位棋盘。");
                systemText.append("请尽快复位棋盘，重新开始游戏。\n");
                this.errorDialog.setVisible(true);
            }
        }else if(this.message.equals("7")){
            isPrepare = false;
            this.message = "";
            this.errorText.setText("对方还未将棋盘复位，请等待。");
            systemText.append("对方还未将棋盘复位，请等待。\n");
            this.errorDialog.setVisible(true);
        }
        if (!isStart && hasReceiveID) {
            if (this.message.equals("90")) {
                isOpponentPrepared = true;
                this.message = "";
                if(!isPrepare) {
                    this.errorText.setText("对手已准备，\n请尽快准备以开始游戏。");
                    systemText.append("对手已准备。\n");
                    this.errorDialog.setVisible(true);
                }
            }
            if (isOpponentPrepared && isPrepare) {
                isStart = true;
            }
        }
    }

    public void setDialogs(){
        dialogConfig(this.connectDialog);
        dialogConfig(this.errorDialog);
        dialogConfig(this.invitedDialog);
        dialogConfig(systemDialog);
        //连接对话框
        {
            this.connectDialog.setLayout(new GridLayout(3, 1));
            this.connectDialog.add(new JLabel("请输入对方ID："));
            this.connectDialog.add(connectText);
            this.connectDialog.add(button1);
            //连接按钮
            button1.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    opponentID = connectText.getText();
                    setID.setText(opponentID);
                    sender.sendData = opponentID + "#92#" + yourID + "#" + _INIT_PARTICIPANT_;
                    sender.isSend = true;
                    connectDialog.setVisible(false);
                }
            });
        }

        //被邀请对话框
        {
            JButton confirmButton = new JButton("同意");
            JButton cancelButton = new JButton("不同意");

            invitedDialog.setVisible(false);
            invitedDialog.setLayout(new GridLayout(2,1));
            JPanel panel = new JPanel();
            panel.setLayout(new GridLayout(1,2));
            invitedDialog.add(invitedText);
            invitedDialog.add(panel);
            panel.add(confirmButton);
            panel.add(cancelButton);

            confirmButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    sender.sendData = opponentID + "#93";
                    systemText.append("连接成功！\n");
                    sender.isSend = true;
                    //重连后初始化
                    isConnect = true;
                    init();
                    initForThis();
                    invitedDialog.setVisible(false);
                   repaint();
                }
            });
            cancelButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    invitedDialog.setVisible(false);
                }
            });
        }

        //报错对话框
        {
            errorText.setBackground(new Color(90,255,255));
            errorDialog.setVisible(false);
            errorDialog.add(errorText, BorderLayout.CENTER);
            errorDialog.add(errorConfirmButton, BorderLayout.SOUTH);
            errorConfirmButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    errorDialog.setVisible(false);
                }
            });
        }
    }

    public void dialogConfig(JDialog dialog){
        dialog.setResizable(false);
        dialog.setBounds(420,350,180,120);
    }
}
