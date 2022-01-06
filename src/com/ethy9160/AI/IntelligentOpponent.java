package com.ethy9160.AI;

public class IntelligentOpponent {
    public static int[] IntelligentAuthorise(int AIOthe[][], int a1, int a2, int a3, int a4, byte participant){
        int inf[] = {0, 0};
        byte max = Byte.MIN_VALUE;
//        byte powerOthe[][] = {
//                {0,   0,   0,   0,   0,   0,   0,   0,   0, 0},
//                {0, 100,   2,  10,   9,   9,  10,   2, 100, 0},
//                {0,   2,   1,   5,   3,   3,   5,   1,   2, 0},
//                {0,  10,   5,   8,   4,   4,   8,  10,   5, 0},
//                {0,   9,   3,   4,   1,   1,   4,   9,   3, 0},
//                {0,   9,   3,   4,   1,   1,   4,   9,   3, 0},
//                {0,  10,   5,   8,   4,   4,   8,  10,   5, 0},
//                {0,   2,   1,  10,   9,   9,  10,   1,   2, 0},
//                {0, 100,   2,   5,   3,   3,   5,   2, 100, 0},
//                {0,   0,   0,   0,   0,   0,   0,   0,   0, 0},
//        };//注意，这是一个8*8数组！
        byte powerOthe[][] ={
                {0,   0,   0,   0,   0,   0,   0,   0,   0,   0},
                {0,  90, -45,  10,   9,   9,  10, -45,  90,   0},
                {0, -45, -40,   5,   3,   3,   5, -40, -45,   0},
                {0,  10,   5,   8,   0,   0,   8,   5,  10,   0},
                {0,   9,   1,   0,  -1,  -1,   0,   1,   9,   0},
                {0,   9,   1,   0,  -1,  -1,   0,   1,   9,   0},
                {0,  10,   5,   8,   0,   0,   8,   5,  10,   0},
                {0, -45, -40,   5,   3,   3,   5, -40, -45,   0},
                {0,  90, -45,  10,   9,   9,  10, -45,  90,   0},
                {0,   0,   0,   0,   0,   0,   0,   0,   0,   0},
        };
        //边界权值优化
        if(a1 == participant){
            powerOthe[1][2] = 12;
            powerOthe[2][2] = 12;
            powerOthe[2][1] = 12;
        }
        if(a2 == participant){
            powerOthe[1][7] = 12;
            powerOthe[2][8] = 12;
            powerOthe[2][7] = 12;
        }
        if(a3 == participant){
            powerOthe[7][2] = 12;
            powerOthe[8][2] = 12;
            powerOthe[7][1] = 12;
        }
        if(a4 == participant){
            powerOthe[7][8] = 12;
            powerOthe[8][7] = 12;
            powerOthe[7][7] = 12;
        }
        //寻找最佳下棋位置
        for (int i = 1; i < 9; i++) {
            for (int j = 1; j < 9; j++) {
                //只检测能下棋的地方
                if(AIOthe[i][j] == 1){
                    if(max < powerOthe[i][j]){
                        //获取权值最大位置
                        max = powerOthe[i][j];
                        //inf的赋值应当遵循传入的10*10数组
                        //一旦出现更大的权，立刻更新位置数据inf
                        inf[0] = i;
                        inf[1] = j;
                    }
                }
            }
        }
        return inf;
    }
}

