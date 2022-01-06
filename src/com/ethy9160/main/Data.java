package com.ethy9160.main;

import javax.swing.*;
import java.net.URL;

public class Data {



    /* 相对路径 */
    //黑方提示
    public static URL blackMarkURL = Data.class.getResource("/statics\\BlackMark.png");
    public static ImageIcon blackMark = new ImageIcon(blackMarkURL);

    public static void main(String[] args) {
        System.out.println(blackMarkURL);
    }
    //白方提示
    public static URL whiteMarkURL = Data.class.getResource("/statics\\WhiteMark.png");
    public static ImageIcon whiteMark = new ImageIcon(whiteMarkURL);
    //按钮
    public static URL buttonURL = Data.class.getResource("/statics\\Button.png");
    public static ImageIcon button = new ImageIcon(buttonURL);
    //计分板
    public static URL CounterPanelURL = Data.class.getResource("/statics\\CounterPanel.png");
    public static ImageIcon counterPanel = new ImageIcon(CounterPanelURL);
    //棋盘
    public static URL boardURL = Data.class.getResource("/statics\\Board.png");
    public static ImageIcon board = new ImageIcon(boardURL);
    //聊天框
    public static URL chatURL = Data.class.getResource("/statics\\chat.png");
    public static ImageIcon chat = new ImageIcon(chatURL);
    //对方timeOut
    public static URL opponentOutURL = Data.class.getResource("/statics\\OpponentTimeOut.png");
    public static ImageIcon opponentOut = new ImageIcon(opponentOutURL);
    //你timeOut
    public static URL youOutURL = Data.class.getResource("/statics\\yourTimeOut.png");
    public static ImageIcon youOut = new ImageIcon(youOutURL);
    //白棋
    public static URL whiteChessURL = Data.class.getResource("/statics\\WhiteChess.png");
    public static ImageIcon whiteChess = new ImageIcon(whiteChessURL);
    //黑祺
    public static URL blackChessURL = Data.class.getResource("/statics\\BlackChess.png");
    public static ImageIcon blackChess = new ImageIcon(blackChessURL);
    //白棋胜利提示
    public static URL whiteWinUrl = Data.class.getResource("/statics\\WhiteWin.png");
    public static ImageIcon whiteWin = new ImageIcon(whiteWinUrl);
    //黑棋胜利提示
    public static URL blackWinUrl = Data.class.getResource("/statics\\BlackWin.png");
    public static ImageIcon blackWin = new ImageIcon(blackWinUrl);
    //平局
    public static URL drawHintUrl = Data.class.getResource("/statics\\Draw.png");
    public static ImageIcon drawHint = new ImageIcon(drawHintUrl);
    //游戏背景
    public static URL backGroundUrl = Data.class.getResource("/statics\\BackGroundPlay.png");
    public static ImageIcon background = new ImageIcon(backGroundUrl);
    //主界面背景
    public static URL backGroundMenuUrl = Data.class.getResource("/statics\\Background.png");
    public static ImageIcon backgroundMenu = new ImageIcon(backGroundMenuUrl);
    //本地模式按钮面板
    public static URL localButtonPanelUrl = Data.class.getResource("/statics\\buttonPanel.png");
    public static ImageIcon localButtonPanel = new ImageIcon(localButtonPanelUrl);
    //联机模式按钮面板
    public static URL webButtonPanelUrl = Data.class.getResource("/statics\\buttonPanel2.png");
    public static ImageIcon webButtonPanel = new ImageIcon(webButtonPanelUrl);


    //让手提示
    public static URL passHintUrl = Data.class.getResource("/statics\\Pass.png");
    public static ImageIcon passHint = new ImageIcon(passHintUrl);

    /***** Button 类 ***/

    //狂野模式
    public static URL wildUrl = Data.class.getResource("/statics\\Icon\\Wild.png");
    public static ImageIcon wild = new ImageIcon(wildUrl);
    //准备
    public static URL prepareUrl = Data.class.getResource("/statics\\Icon\\Prepare.png");
    public static ImageIcon prepare = new ImageIcon(prepareUrl);
    //悔棋
    public static URL regretUrl = Data.class.getResource("/statics\\Icon\\Regret.png");
    public static ImageIcon regret = new ImageIcon(regretUrl);
    //重新开始
    public static URL restartHintUrl = Data.class.getResource("/statics\\Icon\\Restart.png");
    public static ImageIcon restartHint = new ImageIcon(restartHintUrl);
    //重新开始2
    public static URL restartHintUrl2 = Data.class.getResource("/statics\\Icon\\Restart2.png");
    public static ImageIcon restartHint2 = new ImageIcon(restartHintUrl2);
    //连接
    public static URL connectHintUrl = Data.class.getResource("/statics\\Icon\\Connect.png");
    public static ImageIcon connectHint = new ImageIcon(connectHintUrl);
    //断开连接
    public static URL diaConnectUrl = Data.class.getResource("/statics\\Icon\\Disconnect.png");
    public static ImageIcon disconnect = new ImageIcon(diaConnectUrl);
    //认输
    public static URL surrenderUrl = Data.class.getResource("/statics\\Icon\\Surrender.png");
    public static ImageIcon surrender = new ImageIcon(surrenderUrl);

    //人机白模式
    public static URL AIWhiteUrl = Data.class.getResource("/statics\\Icon\\AIWhite.png");
    public static ImageIcon AIWhite = new ImageIcon(AIWhiteUrl);
    //人机黑
    public static URL AIBlackUrl = Data.class.getResource("/statics\\Icon\\AIBlack.png");
    public static ImageIcon AIBlack = new ImageIcon(AIBlackUrl);
    //本地模式
    public static URL localUrl = Data.class.getResource("/statics\\Icon\\Local.png");
    public static ImageIcon local = new ImageIcon(localUrl);
    //联机白
    public static URL webWhiteUrl = Data.class.getResource("/statics\\Icon\\WebWhite.png");
    public static ImageIcon webWhite = new ImageIcon(webWhiteUrl);
    //联机黑
    public static URL webBlackUrl = Data.class.getResource("/statics\\Icon\\WebBlack.png");
    public static ImageIcon webBlack = new ImageIcon(webBlackUrl);
    //联机聊天
    public static URL webChatUrl = Data.class.getResource("/statics\\Icon\\WebChat.png");
    public static ImageIcon webChat = new ImageIcon(webChatUrl);

    //存档
    public static URL saveUrl = Data.class.getResource("/statics\\Icon\\save.png");
    public static ImageIcon save = new ImageIcon(saveUrl);
    //读档
    public static URL loadUrl = Data.class.getResource("/statics\\Icon\\load.png");
    public static ImageIcon load = new ImageIcon(loadUrl);
    //切换棋手
    public static URL shiftUrl = Data.class.getResource("/statics\\Icon\\shift.png");
    public static ImageIcon shift = new ImageIcon(shiftUrl);
    //回到正常
    public static URL normalUrl = Data.class.getResource("/statics\\Icon\\normal.png");
    public static ImageIcon normal = new ImageIcon(normalUrl);
    //

    //Web专属部分：
    //你的ID
    public static URL playerYouUrl = Data.class.getResource("/statics\\Player.png");
    public static ImageIcon playerYou = new ImageIcon(playerYouUrl);

    //对手ID
    public static URL playerOpponentUrl = Data.class.getResource("/statics\\Player.png");
    public static ImageIcon playerOpponent = new ImageIcon(playerOpponentUrl);

    //网络按钮面板
    public static URL webButtonsUrl = Data.class.getResource("/statics\\buttonPanel2.png");
    public static ImageIcon webButtons = new ImageIcon(webButtonsUrl);

    //计时板
    public static URL timeClockUrl = Data.class.getResource("/statics\\TimeClock.png");
    public static ImageIcon timeClock = new ImageIcon(timeClockUrl);

    //计算机图标
    public static URL computerUrl = Data.class.getResource("/statics\\Computer.png");
    public static ImageIcon computer = new ImageIcon(computerUrl);

    //思考图标
    public static URL thinkUrl = Data.class.getResource("/statics\\Thinking.png");
    public static ImageIcon think = new ImageIcon(thinkUrl);

}
