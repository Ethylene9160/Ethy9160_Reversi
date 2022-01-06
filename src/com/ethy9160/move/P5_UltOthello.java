package com.ethy9160.move;

public class P5_UltOthello {

    public static void reverse(int row, int col,int[][] othe,boolean[][] isReverse, int participant){
        int[][] otheOld = new int[10][10];
        for(int i = 0; i < 10; i ++){
            for (int j = 0; j < 10; j++) {
                otheOld[i][j] = othe[i][j];
            }
        }
        position(row,col,othe,participant);
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if(otheOld[i][j] != othe[i][j]){
                    isReverse[i][j] = true;
                }
            }
        }
    }

    /**
     * 下棋。
     * @param row 行
     * @param col 列
     * @param othe 当前棋盘（会被改变）
     * @param participant 当前玩家
     */
    public static void position(int row, int col,int[][] othe, int participant) {
        /*********************** 初始化 ****************************/
        int opponent, transfer;//创建：对手, 交换器, 输入的行数,输入的列数
        //设置对手
        if (participant != 1) {
            opponent = 1;
        } else {
            opponent = -1;
        }

        //创建初始数组：10*10，这样创建可以忽略边界。
        boolean outJudge;



        /****输入样例：****
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


        /****                     主控区                        ****/


/*************** 判定 *******************/


        /****  检测旁边是否有敌方的棋子，没有则掰掰，有则继续判定 ****/
        if(othe[row][col] == 0) {
            //向右边
            if (othe[row][col + 1] == opponent) {
                for (int i = col + 2; i < 9; i++) {
                    if (othe[row][i] == participant) {
                        //如果出现participant，则赋值
                        for (int k = col; k < i; k++) {
                            othe[row][k] = participant;
                        }
                        //赋值结束，跳出循环
                        outJudge = true;
                        break;
                    }
                    //出现0则break
                    if (othe[row][i] == 0) break;
                }
            }
            //down:
            if (othe[row + 1][col] == opponent) {
                for (int i = row + 2; i < 9; i++) {
                    if (othe[i][col] == participant) {
                        //如果出现participant，则赋值
                        for (int k = row; k < i; k++) {
                            othe[k][col] = participant;
                        }
                        //赋值结束，跳出循环
                        outJudge = true;
                        break;
                    }
                    //出现0则break
                    if (othe[i][col] == 0) break;
                }
            }
            //left:
            if (othe[row][col - 1] == opponent) {
                for (int i = col - 2; i > 0; i--) {
                    if (othe[row][i] == participant) {
                        //如果出现participant，则赋值
                        for (int k = col; k > i; k--) {
                            othe[row][k] = participant;
                        }
                        //赋值结束，跳出循环
                        outJudge = true;
                        break;
                    }
                    //出现0则break
                    if (othe[row][i] == 0) break;
                }
            }
            //up:
            if (othe[row - 1][col] == opponent) {
                for (int i = row - 2; i > 0; i--) {
                    if (othe[i][col] == participant) {
                        //如果出现participant，则赋值
                        for (int k = row; k > i; k--) {
                            othe[k][col] = participant;
                        }
                        //赋值结束，跳出循环
                        outJudge = true;
                        break;
                    }
                    //出现0则break
                    if (othe[i][col] == 0) break;
                }
            }
            //down and right
            if (othe[row + 1][col + 1] == opponent) {
                for (int i = row + 2, j = col + 2; i < 9 && j < 9; i++, j++) {
                    if (othe[i][j] == participant) {
                        //如果出现participant，则赋值
                        for (int r = row, k = col; r < i && k < j; r++, k++) {
                            othe[r][k] = participant;
                        }
                        //赋值结束，跳出循环
                        outJudge = true;
                        break;
                    }
                    //出现0则break
                    if (othe[i][j] == 0) break;
                }
            }
            //up and left
            if (othe[row - 1][col - 1] == opponent) {
                for (int i = row - 2, j = col - 2; i > 0 && j > 0; i--, j--) {
                    if (othe[i][j] == participant) {
                        //如果出现participant，则赋值
                        for (int r = row, k = col; r > i && k > j; r--, k--) {
                            othe[r][k] = participant;
                        }
                        //赋值结束，跳出循环
                        outJudge = true;
                        break;
                    }
                    //出现0则break
                    if (othe[i][j] == 0) break;
                }
            }
            //down and left
            if (othe[row + 1][col - 1] == opponent) {
                for (int i = row + 2, j = col - 2; i < 9 && j > 0; i++, j--) {
                    if (othe[i][j] == participant) {
                        //如果出现participant，则赋值
                        for (int r = row, k = col; r < i && k > j; r++, k--) {
                            othe[r][k] = participant;
                        }
                        //赋值结束，跳出循环
                        outJudge = true;
                        break;
                    }
                    //出现0则break
                    if (othe[i][j] == 0) break;
                }
            }
            //up and right
            if (othe[row - 1][col + 1] == opponent) {
                for (int i = row - 2, j = col + 2; i > 0 && j < 9; i--, j++) {
                    if (othe[i][j] == participant) {
                        //如果出现participant，则赋值
                        for (int r = row, k = col; r > i && k < j; r--, k++) {
                            othe[r][k] = participant;
                        }
                        //赋值结束，跳出循环
                        outJudge = true;
                        break;
                    }
                    //出现0则break
                    if (othe[i][j] == 0) break;
                }
            }

        }
        /************** 结束判定 ******************/

        /***************** 输出 *******************/

        //return othe;
    }
}

