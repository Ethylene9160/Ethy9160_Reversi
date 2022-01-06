package com.ethy9160.AI;

import com.ethy9160.move.*;
import com.sun.org.apache.xpath.internal.jaxp.XPathImpl;

import java.util.ArrayList;

public class AIStepJudge {
    public int othe[][] = new int[10][10];
    private final int MAX_SEARCH = 5;
    public int realOpponent, realParticipant;
    int a;
    public final short MAX = 390;
    public final short MIN = -275;
    public ArrayList<ResRoute> resList;

    public AIStepJudge(int[][] othe, int realParticipant) {
        this.realParticipant = realParticipant;
        this.realOpponent = -realParticipant;
        this.resList = new ArrayList<ResRoute>();
        //拷贝一份棋盘
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                this.othe[i][j] = othe[i][j];
            }
        }
    }

    /**
     * 移动棋子
     * @param participant 参与者的棋子颜色，黑棋-1，白棋1
     * @param othe        已存在的<b>AI计算</b>棋盘（不是panel中的棋盘，而是这里拷贝的棋盘。
     * @param othePre     othePre棋盘，也是拷贝之后的棋盘。
     * @param x           x坐标
     * @param y           y坐标
     */
    public void move(int participant, int[][] othe, int othePre[][], int y, int x) {
        //opponent = -participant;
        //获取棋盘
        P5_UltOthello.position(y, x, othe, participant);
        //预测棋子
        P4_Othello.predict(othe, othePre, participant);
    }

    int counter = 0;

    /**
     * 核心算法
     * @param participant 房前落子方
     * @param othe 棋盘
     * @param y 行坐标
     * @param x 列坐标
     * @param calTime 递归层数遍历
     * @return 祖先节点权值
     */
    public int findHighestScore(int participant, int[][] othe, int y, int x, int calTime) {
        int opponent = -participant;
//        int score = 0;
        int myOthe[][] = new int[10][10];
        for (int i = 1; i < 9; i++) {
            for (int j = 1; j < 9; j++) {
                myOthe[i][j] = othe[i][j];
            }
        }
        int myPre[][] = new int[10][10];
        short myPower[][] = {
                {0,   0,   0,   0,   0,   0,   0,   0,   0,   0},
                {0, MAX, MIN,  66,  49,  49,  66, MIN, MAX,   0},
                {0, MIN, MIN,  15,  13,  13,  15, MIN, MIN,   0},
                {0,  66,  15,   8,   0,   0,   8,  15,  66,   0},
                {0,  49,  11,   0,  -1,  -1,   0,  11,  49,   0},
                {0,  59,  13,   0,  -1,  -1,   0,  11,  59,   0},
                {0,  80,  18,   8,   0,   0,   8,  15,  80,   0},
                {0, MIN, MIN,  25,  23,  13,  15, MIN, MIN,   0},
                {0, MAX, MIN,  76,  55,  49,  66, MIN, MAX,   0},
                {0,   0,   0,   0,   0,   0,   0,   0,   0,   0},
        };
        if(othe[1][1] == participant){
            myPower[1][2] = 168;
            myPower[2][2] = 168;
            myPower[2][1] = 168;
            myPower[1][3] = 120;
            myPower[1][6] = 120;
            myPower[8][3] = 120;
            myPower[8][4] = 120;
        }
        if(othe[1][8] == participant){
            myPower[1][7] = 168;
            myPower[2][8] = 168;
            myPower[1][6] = 168;
            myPower[1][5] = 120;
            myPower[1][4] = 120;
            myPower[1][3] = 120;
            myPower[2][7] = 120;
        }
        if(othe[8][1] == participant){
            myPower[7][2] = 168;
            myPower[8][2] = 168;
            myPower[7][1] = 168;
            myPower[6][1] = 120;
            myPower[5][1] = 120;
            myPower[8][3] = 120;
            myPower[8][4] = 120;
        }
        if(othe[8][8] == participant){
            myPower[7][8] = 168;
            myPower[8][7] = 168;
            myPower[7][7] = 168;
            myPower[8][6] = 120;
            myPower[8][5] = 120;
            myPower[8][3] = 120;
            myPower[3][8] = 120;
            myPower[5][8] = 120;
            myPower[6][8] = 120;
        }
        if(myOthe[1][1] == opponent || myOthe[1][8] == opponent){
            for(byte i = 3; i < 7; i++){
                myOthe[1][i] = -110;
            }
        }
        if(myOthe[1][1] == opponent || myOthe[8][1] == opponent){
            for(byte i = 3; i < 7; i++){
                myOthe[i][1] = -110;
            }
        }
        if(myOthe[8][8] == opponent || myOthe[1][8] == opponent){
            for(byte i = 3; i < 7; i++){
                myOthe[i][8] = -110;
            }
        }
        if(myOthe[8][8] == opponent || myOthe[8][1] == opponent){
            for(byte i = 3; i < 7; i++){
                myOthe[8][i] = -110;
            }
        }

        //递归出口
        this.counter ++ ;

        if(myPre[9][9] != 5) {
            if (calTime == MAX_SEARCH) {
//                if(participant == this.realOpponent){
                    return -findMax(myPre, myPower);
//                    return findMax(myPre, myPower);
//                }else {
//                    return -findMax(myPre, myPower);
//                    return findMax(myPre, myPower);
                //}
            } else {
                move(participant, myOthe, myPre, y, x);//向后走一步
                //遍历othePre棋盘
                //short temp = Short.MIN_VALUE;//剪枝操作中间存储器，修改时间：12/23/08：53
                for (int i = 1; i < 9; i++) {
                    for (int j = 1; j < 9; j++) {
                        if (myPre[i][j] == 1 /*&& temp < myPower[i][j]*/) {//进行剪枝操作，如果
                            myPower[i][j] += findHighestScore(opponent, myOthe, i, j, calTime + 1);//刚
                           // temp = myPower[i][j];//更新temp的大小。注意，这个方法会使运算结果变得更快，但不见得效果会很好。
                        }
                    }
                }

                //System.out.println(" ========= " + counter + " ======= ");
//                if (participant == this.realOpponent) {
                    return -findMax(myPre, myPower);
//                } else {
//                    return -findMax(myPre, myPower);
//                }
            }
        }else{
            return 0;
        }
    }

//    public int findHighestScore(int participant, int[][] othe, int y, int x, int calTime) {
//        int opponent = -participant;
//        int myOthe[][] = new int[10][10];
//        //拷贝一份棋盘
//        for (int i = 1; i < 9; i++) {
//            for (int j = 1; j < 9; j++) {
//                myOthe[i][j] = othe[i][j];
//            }
//        }
//        int myPre[][] = new int[10][10];
//        short myPower[][] = {
//                {0,   0,   0,   0,   0,   0,   0,   0,   0,   0},
//                {0, MAX, MIN,  66,  49,  49,  66, MIN, MAX,   0},
//                {0, MIN, MIN,  15,  13,  13,  15, MIN, MIN,   0},
//                {0,  66,  15,   8,   0,   0,   8,  15,  66,   0},
//                {0,  49,  11,   0,  -1,  -1,   0,  11,  49,   0},
//                {0,  59,  13,   0,  -1,  -1,   0,  11,  59,   0},
//                {0,  80,  18,   8,   0,   0,   8,  15,  80,   0},
//                {0, MIN, MIN,  25,  23,  13,  15, MIN, MIN,   0},
//                {0, MAX, MIN,  76,  55,  49,  66, MIN, MAX,   0},
//                {0,   0,   0,   0,   0,   0,   0,   0,   0,   0},
//        };
//        if(othe[1][1] == participant){
//            myPower[1][2] = 108;
//            myPower[2][2] = 108;
//            myPower[2][1] = 88;
//            myPower[1][3] = 80;
//            myPower[1][6] = 80;
//            myPower[8][3] = 80;
//            myPower[8][4] = 80;
//        }
//        if(othe[1][8] == participant){
//            myPower[1][7] = 118;
//            myPower[2][8] = 118;
//            myPower[1][6] = 118;
//            myPower[1][5] = 80;
//            myPower[1][4] = 80;
//            myPower[1][3] = 80;
//            myPower[2][7] = 80;
//        }
//        if(othe[8][1] == participant){
//            myPower[7][2] = 138;
//            myPower[8][2] = 138;
//            myPower[7][1] = 138;
//            myPower[6][1] = 100;
//            myPower[5][1] = 100;
//            myPower[8][3] = 100;
//            myPower[8][4] = 100;
//        }
//        if(othe[8][8] == participant){
//            myPower[7][8] = 138;
//            myPower[8][7] = 138;
//            myPower[7][7] = 138;
//            myPower[8][6] = 100;
//            myPower[8][5] = 100;
//            myPower[8][3] = 100;
//            myPower[3][8] = 100;
//            myPower[5][8] = 100;
//            myPower[6][8] = 100;
//        }
//        if(myOthe[1][1] == opponent || myOthe[1][8] == opponent){
//            for(byte i = 3; i < 7; i++){
//                myOthe[1][i] = -80;
//            }
//        }
//        if(myOthe[1][1] == opponent || myOthe[8][1] == opponent){
//            for(byte i = 3; i < 7; i++){
//                myOthe[i][1] = -80;
//            }
//        }
//        if(myOthe[8][8] == opponent || myOthe[1][8] == opponent){
//            for(byte i = 3; i < 7; i++){
//                myOthe[i][8] = -80;
//            }
//        }
//        if(myOthe[8][8] == opponent || myOthe[8][1] == opponent){
//            for(byte i = 3; i < 7; i++){
//                myOthe[8][i] = -80;
//            }
//        }
//
//        //递归出口
//        move(participant, myOthe, myPre, y, x);//向后走一步
//        this.counter ++ ;
//        if(myPre[9][9] != 5) {
//            if (calTime == MAX_SEARCH) {
//                if(participant == this.realParticipant) {
//                    return myPower[y][x];
//                }else{
//                    return myPower[y][x];
//                }
//            } else {
//                //遍历othePre棋盘
//                for (int i = 1; i < 9; i++) {
//                    for (int j = 1; j < 9; j++) {
//                        if (myPre[i][j] == 1) {
//                            myPower[i][j] -= findHighestScore(opponent, myOthe, i, j, calTime + 1);//刚
//                        }
//                    }
//                }
//                if(participant == this.realParticipant) {
//                    return (findMax(myPre, myPower));
//                }else{
//                    return (findMax(myPre, myPower));
//                }
//            }
//        }else{
//            //System.out.println(" 本轮结束 ");
//            return 0;
//        }
//    }


    /**
     * 寻找可以下的棋子当中的最大值以及所在的位置，所在的位置用<b>10*x+y</b>表示。
     *
     * @param othePre 预测棋盘
     * @return 10*x + y
     */
    public short findMax(int[][] othePre, short[][] myPower) {
        short score = 0;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (othePre[i][j] == 1 && score < myPower[i][j]) {
                    score = myPower[i][j];
                }
            }
        }
        return score;
    }

    /**
     * 寻找可以下棋的位置中，权值最小的点。
     *
     * @param othePre
     * @return 10*x+y
     */
    public int findMin(int[][] othePre, short[][] myPower) {
        short score = 0;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (othePre[i][j] == 1 && score > myPower[i][j]) {
                    score = myPower[i][j];
                }
            }
        }
        return score;
    }

    /**
     * 抢占最高权值点的方法。
     * @param othePre predict棋盘
     * @return
     */
    public int[] findBestPosition(int[][]othePre){
        int inf[] = new int[2];
//        int finalPower[][] =  {
//                {0,   0,   0,   0,   0,   0,   0,   0,   0,   0},
//                {0, MAX, MIN,  66,  49,  49,  66, MIN, MAX,   0},
//                {0, MIN, MIN,  15,  13,  13,  15, MIN, MIN,   0},
//                {0,  66,  15,   8,   0,   0,   8,  15,  10,   0},
//                {0,  49,  11,   0,  -1,  -1,   0,  11,   9,   0},
//                {0,  59,  13,   0,  -1,  -1,   0,  11,   9,   0},
//                {0,  80,  18,   8,   0,   0,   8,  15,  10,   0},
//                {0, MIN, MIN,  25,  23,  13,  15, MIN, MIN,   0},
//                {0, MAX, MIN,  76,  55,  49,  66, MIN, MAX,   0},
//                {0,   0,   0,   0,   0,   0,   0,   0,   0,   0},
//        };
        int finalPower[][] = {
                {0,   0,   0,   0,   0,   0,   0,   0,   0,   0},
                {0, MAX, MIN,  66,  49,  49,  66, MIN, MAX,   0},
                {0, MIN, MIN,  15,  13,  13,  15, MIN, MIN,   0},
                {0,  66,  15,   8,   0,   0,   8,  15,  66,   0},
                {0,  49,  11,   0,  -1,  -1,   0,  11,  49,   0},
                {0,  59,  13,   0,  -1,  -1,   0,  11,  59,   0},
                {0,  80,  18,   8,   0,   0,   8,  15,  80,   0},
                {0, MIN, MIN,  25,  23,  13,  15, MIN, MIN,   0},
                {0, MAX, MIN,  76,  55,  49,  66, MIN, MAX,   0},
                {0,   0,   0,   0,   0,   0,   0,   0,   0,   0},
        };
        if(othe[1][1] == realParticipant){
            finalPower[1][2] = 100;
            finalPower[2][2] = 100;
            finalPower[2][1] = 100;
        }
        if(othe[1][8] == realParticipant){
            finalPower[1][7] = 100;
            finalPower[2][8] = 100;
            finalPower[2][7] = 100;
        }
        if(othe[8][1] == realParticipant){
            finalPower[7][2] = 100;
            finalPower[8][2] = 100;
            finalPower[7][1] = 100;
        }
        if(othe[8][8] == realParticipant){
            finalPower[7][8] = 100;
            finalPower[8][7] = 100;
            finalPower[7][7] = 100;
        }
        if(othe[1][1] == this.realOpponent || othe[1][8] == realOpponent){
            for(byte i = 3; i < 7; i++){
                othe[1][i] = -110;
            }
        }
        if(othe[1][1] == realOpponent || othe[8][1] == realOpponent){
            for(byte i = 3; i < 7; i++){
                othe[i][1] = -110;
            }
        }
        if(othe[8][8] == realOpponent || othe[1][8] == realOpponent){
            for(byte i = 3; i < 7; i++){
                othe[i][8] = -110;
            }
        }
        if(othe[8][8] == realOpponent || othe[8][1] == realOpponent){
            for(byte i = 3; i < 7; i++){
                othe[8][i] = -110;
            }
        }
        int x = Integer.MIN_VALUE;
//        int temp = 0;
        for (byte i = 1; i < 9; i++) {
            for (byte j = 1; j < 9; j++) {
                if(othePre[i][j] == 1){
                    finalPower[i][j] += findHighestScore(realParticipant, othe, i, j, 0);
                    if(x < finalPower[i][j]){
                        x = finalPower[i][j];
                        inf[0] = i;
                        inf[1] = j;
                    }
                    System.out.printf("%5d", finalPower[i][j]);
                }else{
                    System.out.printf("%5d", 0);
                }
            }
            System.out.println();
        }
        System.out.println("递归次数 " + this.counter);
        return inf;
    }

    /**
     * 最后几步，尽可能多地吃掉对手的棋子。
     * @param othePre 预测棋盘
     * @return
     */
    public int[] reverseMorePieces(int[][] othePre, int cou){
        int inf[] = new int[2];
        int temp = Short.MIN_VALUE;

        for (byte i = 1; i < 9; i++) {
            for (byte j = 1; j < 9; j++) {
                if(othePre[i][j] == 1){
                    finalReversePieces(this.othe, this.realParticipant, cou, i, j);
                    if(this.realParticipant == -1){
                        this.a = -this.a;
                    }
                    if(temp < this.a){
                        temp = a;
                        inf[0] = i;
                        inf[1] = j;
                    }
                    //temp =

//                    if(temp > finalTemp){
//                        finalTemp = temp;
//                        inf[0] = i;
//                        inf[1] = j;
//                    }
                }
            }
        }
        return inf;
    }
    
    public void finalReversePieces(int[][] othe, int participant, int cou, int y, int x){
        cou++;
        int opponent = - participant;
        //if(cou <= 64)
        int myBoard[][] = new int[10][10];
        int myPre[][] = new int[10][10];
        int finalScore = 0;
        for (int i = 1; i < 9; i++) {
            for(int j = 1; j < 9; j++){
                finalScore += othe[i][j];
                myBoard[i][j] = othe[i][j];
            }
        }
        //往后走一步
        move(participant, myBoard, myPre, y, x);
        if(cou >= 64){
            this.a = 0;
            for (byte i = 1; i < 9; i++) {
                for (byte j = 1; j < 9; j++) {
                    a += this.othe[i][j];
                }
            }
            //递归出口
            return;
        }else {
            if (myPre[9][9] == 5) {
                P4_Othello.predict(myBoard, myPre, opponent);
                for (byte i = 1; i < 9; i++) {
                    for (byte j = 1; j < 9; j++) {
                        if (myPre[i][j] == 1) {
                            finalReversePieces(myBoard, participant, cou + 1, i, j);
                        }
                    }
                }
            } else {
                for (byte i = 1; i < 9; i++) {
                    for (byte j = 1; j < 9; j++) {
                        if (myPre[i][j] == 1) {
                            finalReversePieces(myBoard, opponent, cou + 1, i, j);
                        }
                    }
                }
            }
        }
    }
}
