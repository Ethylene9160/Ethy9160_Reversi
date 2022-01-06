package com.ethy9160.move;

public class P4_Othello {

    /**
     *
     * @param othe 当前棋盘（不会被改变）
     * @param othePre 待预测的predict区（会被改变）
     * @param participant 当前游戏者
     */
    public static void predict(int[][] othe, int[][] othePre, int participant) {
        participant = -participant;
        int opponent;//创建对手

        /* ********************** 初始化 ****************************/
        //将othePre初始化
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                othePre[i][j] = 0;
            }
        }

        boolean outJudge = false;//true输出棋盘，false输出-1。出现可以放的棋子位置时，赋值true。
        //设置对手
        if(participant == 1){opponent = -1;}else {opponent = 1;}

        /* 输入样例：****
         *  0  1 -1  1 -1  0  0  0
         *  0  1  1  0  1  0  1  0
         *  0  1  0  0  1  0  0  0
         *  0  0  0  0  0  0  0  0
         *  0  1  1  0  0  1  0  0
         *  0  1  1  0  0  0  0  1
         *  0 -1  1  1 -1  0  0 -1
         *  0  0 -1  0  1  0  0  0
         *  储存的othe棋盘：
         *  0  0  0  0  0  0  0  0  0  0
         *  0  0  1 -1  1 -1  0  0  0  0
         *  0  0  1  1  0  1  0  1  0  0
         *  0  0  1  0  0  1  0  0  0  0
         *  0  0  0  0  0  0  0  0  0  0
         *  0  0  1  1  0  0  1  0  0  0
         *  0  0  1  1  0  0  0  0  1  0
         *  0  0 -1  1  1 -1  0  0 -1  0
         *  0  0  0 -1  0  1  0  0  0  0
         *  0  0  0  0  0  0  0  0  0  0
         */

        /*************** 判定 *******************/

        //横排,检测己方：
        for (int i = 1; i < 9; i++) {
            //从左往右，检测到己方：
            for (int j = 1; j < 9; j++) {
                if(othe[i][j] == participant && othe[i][j+1] == opponent){
                    //如果下一个是对方，判定；否则跳到下一个
                    for (int k = j + 2; k < 9; k++) {
                        if(othe[i][k] == 0) {
                            othePre[i][k] = 1;
                            if(i > 0 && i < 9 && k > 0 && k < 9){outJudge = true;}
                            j = k;
                            break;
                        }
                        if(othe[i][k] == participant){
                            j = k-1;
                            break;
                        }
                    }
                }
            }


            //从右往左，检测到己方：
            for (int j = 8; j > 0; j--) {
                if(othe[i][j] == participant && othe[i][j-1] == opponent){//如果下一个是对方，判定；己方或者零则跳到下一个（往前一个）
                    for (int k = j-2; k > 0; k--) {
                        //检测到0，如
                        //  k  j-1  j
                        //  0   1  -1，
                        // j-2是0，则j回到这个k的下一位，重新判定开始
                        if(othe[i][k] == 0) {
                            othePre[i][k] = 1;
                            if(i > 0 && i < 9 && k > 0 && k < 9){outJudge = true;}
                            j = k;
                            break;
                        }
                        //检测到自己的棋子，如
                        //  k j-1 j
                        // -1  1 -1，
                        // j-2是-1，则j回到这个k（即j-2），重新判定开始
                        if(othe[i][k] == participant){
                            j = k+1;
                            break;
                        }
                    }
                }
            }

        }

        //竖列
        for (int i = 1; i < 9; i++) {
            //从上往下，检测到己方：
            for (int j = 1; j < 9; j++) {
                if(othe[j][i] == participant && othe[j+1][i] == opponent){
                    //如果下一个是对方，判定；否则跳到下一个
                    for (int k = j + 2; k < 9; k++) {
                        if(othe[k][i] == 0) {
                            othePre[k][i] = 1;
                            if(i > 0 && i < 9 && k > 0 && k < 9){outJudge = true;}
                            j = k;
                            break;
                        }
                        if(othe[k][i] == participant){
                            j = k-1;
                            break;
                        }
                    }
                }
            }


            //从下往上，检测到己方：
            for (int j = 8; j > 0; j--) {
                if(othe[j][i] == participant && othe[j-1][i] == opponent){//如果下一个是对方，判定；己方或者零则跳到下一个（往前一个）
                    for (int k = j-2; k > 0; k--) {
                        //检测到0，如
                        //  k  j-1  j
                        //  0   1  -1，
                        // j-2是0，则j回到这个k的下一位，重新判定开始
                        if(othe[k][i] == 0) {
                            othePre[k][i] = 1;
                            if(i > 0 && i < 9 && k > 0 && k < 9){outJudge = true;}
                            j = k;
                            break;
                        }
                        //检测到自己的棋子，如
                        //  k j-1 j
                        // -1  1 -1，
                        // j-2是-1，则j回到这个k（即j-2），重新判定开始
                        if(othe[k][i] == participant){
                            j = k+1;
                            break;
                        }
                    }
                }
            }

        }

        //对角线1类-上往下类
        for (int i = 1; i < 9; i++) {
            //从左上往右下，检测到己方：
            for (int j = 1; j < 9; j++) {
                if(othe[i][j] == participant && othe[i+1][j+1] == opponent){
                    //如果下一个是对方，判定；否则跳到下一个
                    for (int r = i + 2,k = j + 2; k < 9 && r < 9; r++, k++) {
                        if(othe[r][k] == 0) {
                            othePre[r][k] = 1;
                            if(r > 0 && r < 9 && k > 0 && k < 9){outJudge = true;}
                            break;
                        }
                        if(othe[r][k] == participant){
                            break;
                        }
                    }
                }
            }
            //右上往左下，只需要更改j，k的方向
            for (int j = 8; j > 0; j--) {
                if(othe[i][j] == participant && othe[i+1][j-1] == opponent){
                    //如果下一个是对方，判定；否则跳到下一个
                    for (int r = i + 2,k = j - 2; k > 0 && r < 9; r++, k--) {
                        if(othe[r][k] == 0) {
                            othePre[r][k] = 1;
                            if(r > 0 && r < 9 && k > 0 && k < 9){outJudge = true;}
                            break;
                        }
                        if(othe[r][k] == participant){
                            break;
                        }
                    }
                }
            }




        }

        //对角线2类-下往上类
        for (int i = 8; i > 0; i--) {
            //从右下往左上，检测到己方：
            for (int j = 8; j > 0; j--) {
                if(othe[i][j] == participant && othe[i-1][j-1] == opponent){//如果下一个是对方，判定；己方或者零则跳到下一个（往前一个）
                    for (int r = i - 2, k = j-2; k > 0 && r > 0; r--, k--) {
                        //检测到0，如
                        //  k  j-1  j
                        //  0   1  -1，
                        // j-2是0，则j回到这个k的下一位，重新判定开始
                        if(othe[r][k] == 0) {
                            othePre[r][k] = 1;
                            if(r > 0 && r < 9 && k > 0 && k < 9){outJudge = true;}
                            break;
                        }
                        if(othe[r][k] == participant){
                            break;
                        }
                    }
                }
            }
            //从左下往右上，只需要更改j的方向：
            for (int j = 1; j < 9; j++) {
                if(othe[i][j] == participant && othe[i-1][j+1] == opponent){//如果下一个是对方，判定；己方或者零则跳到下一个（往前一个）
                    for (int r = i - 2, k = j+2; k > 0 && r > 0; r--, k++) {
                        //检测到0，如
                        //  k  j-1  j
                        //  0   1  -1，
                        // j-2是0，则j回到这个k的下一位，重新判定开始
                        if(othe[r][k] == 0) {
                            othePre[r][k] = 1;
                            if(r > 0 && r < 9 && k > 0 && k < 9){outJudge = true;}
                            break;
                        }
                        if(othe[r][k] == participant){
                            break;
                        }
                    }
                }
            }
        }




        /* **************** 输出 *******************/

        if(!outJudge){
            othePre[9][9] = 5;//让otheOut[9][9]等于-5以判定让手
            //System.out.println("checkP4让手");
        }
    }
}


