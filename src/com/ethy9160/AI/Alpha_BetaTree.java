package com.ethy9160.AI;

import static com.ethy9160.move.P4_Othello.predict;
import static com.ethy9160.move.P5_UltOthello.position;

public class Alpha_BetaTree {
    public int height = 4;
    public int highest = 0;
    public int lowest = height;
    public int Alpha_BetaOthePre[][] = new int[10][10];
    public int othe[][] = new int[10][10];
    public int nutCnt;
    public byte alpha, beta;
    public byte treeRes[] = new byte[2];
    public byte powerOthe[][] = {
            {0,   0,   0,   0,   0,   0,   0,   0,   0,0},
            {0, 100,   2,   5,   3,   3,   5,   2, 100,0},
            {0,   2,   1,  10,   9,   9,  10,   1,   2,0},
            {0,   5,  10,   8,   4,   4,   8,  10,   5,0},
            {0,   3,   9,   4,   1,   1,   4,   9,   3,0},
            {0,   3,   9,   4,   1,   1,   4,   9,   3,0},
            {0,   5,  10,   8,   4,   4,   8,  10,   5,0},
            {0,   2,   1,  10,   9,   9,  10,   1,   2,0},
            {0, 100,   2,   5,   3,   3,   5,   2, 100,0},
            {0,   0,   0,   0,   0,   0,   0,   0,   0,0},
    };
    //拷贝一份当前的棋盘
    public void setOthe(int[][] othe){
        for (int i = 1; i < 9; i++) {
            for (int j = 1; j < 9; j++) {
                this.othe[i][j] = othe[i][j];
            }
        }
    }
    //指针传递
    public byte deepSearch(int[][] othe){
        byte res = 0;
        //复制一份棋盘，用避免修改了对局棋盘
        int otheAI[][] = new int[10][10];
        //使用值传递，拷贝数组
        for (int i = 1; i < 9; i++) {
            for (int j = 1; j < 9; j++) {
                otheAI[i][j] = othe[i][j];
            }
        }
        //白棋是1；
         predict(otheAI, Alpha_BetaOthePre, 1);
        //记录结点数
        for (int i = 1; i < 9; i++) {
            for (int j = 1; j < 9; j++) {
                if(Alpha_BetaOthePre[i][j] == 1){
                    nutCnt ++;
                }
            }
        }

        //调用权值表



        //仍然是指针传递
        return res;
    }


    int calTime = 4;//创建递归次数记录
    public byte[] calculate(int participant, int pre[][]){
        /******寻找最大最小：*****
         * 对于每一步的白棋，需要找到其最大权值以赋值，
         * 因为对手也会寻找最优质的地方进行下棋，
         * 因此需要这样进行计算。
         * ****/

        if(participant == 1) {
            alpha = findMax(Alpha_BetaOthePre);
        }else {
            //寻找最小
            beta = findMin(Alpha_BetaOthePre);
        }

        //将棋盘传入
        int[][] thisPre = new int[10][10];
        //拷贝棋盘
        for (int i = 0; i < 10; i++){
            for (int j = 0; j < 10; j++) {
                thisPre[i][j] = pre[i][j];
            }
        }
        calTime--;
        participant = -participant;
        if(calTime > 0) {//遍历棋盘，仅遍历1-9这九行
            for (int i = 1; i < 8; i++) {
                for (int j = 1; j < 10; j++) {
                    //检测到thisPre是1时
                    if (thisPre[i][j] == 1) {
                        //落子
                        //othe[i][j] = participant;
                        position(i, j, othe, participant);
                        predict(othe,thisPre, participant);
                        //递归
                        calculate(participant, thisPre);
                    }
                }
            }
        }
        calTime += 2;
        treeRes[0] = alpha;
        treeRes[1] = beta;
        return treeRes;
    }

    /**
     *
     * @param participant 参与方的棋子，黑棋为-1，白棋为1.
     * @param othePre 用于预测的棋盘。这里使用的是指针传递！
     * @param othe 奥赛罗棋盘，这里使用的是<b>引用</b>（指针）传递！
     * @param calTime 用于计算递归次数。递归的出口是calTime = 0.
     * @return
     */
    public byte[] bestPosition(int participant, int othePre[][], int othe[][], int calTime){
        if(calTime == 0){//递归出口
            return treeRes;
        }else{
            if(participant== 1){
                beta = findMin(othePre);

            }else{
                alpha = findMax(othePre);
            }
            participant = -participant;
            //递归体
            bestPosition(participant, othePre, othe, calTime-1);
        }
        byte res[] = {1,1};
        return res;
    }

    //寻找最大值
    public byte findMax(int[][] Alpha_BetaOthePre){
        byte inf = 0, max = 0;
        //寻找最佳下棋位置
        for (byte i = 1; i < 9; i++) {
            for (byte j = 1; j < 9; j++) {
                //只检测能下棋的地方
                if(Alpha_BetaOthePre[i][j] == 1){
                    if(max < powerOthe[i][j]){
                        //获取权值最大位置
                        max = powerOthe[i][j];
                        //inf的赋值应当遵循传入的10*10数组
                        //一旦出现更大的权，立刻更新位置数据inf
                        inf = (byte) (10*i + j);
                    }
                }
            }
        }
        return inf;
    }

    //寻找最小值
    public byte findMin(int[][] Alpha_BetaOthePre){
        byte inf = 0, min = 0;
        //寻找最差下棋位置
        for (byte i = 1; i < 9; i++) {
            for (byte j = 1; j < 9; j++) {
                //只检测能下棋的地方
                if(Alpha_BetaOthePre[i][j] == 1){
                    if(min > powerOthe[i][j]){
                        //获取权值最小位置
                        min = powerOthe[i][j];
                        //inf的赋值应当遵循传入的10*10数组
                        //一旦出现更小的权，立刻更新位置数据inf
                        inf = (byte) (10*i + j);
                    }
                }
            }
        }
        return inf;
    }
}
