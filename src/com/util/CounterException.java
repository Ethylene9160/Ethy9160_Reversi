package com.util;

public class CounterException extends Exception {
    /**
     * 检测counter：
     * 将cou的值传到第一位，数组的值（int[][]）传再第二位。
     * @param cou cou的接收值
     * @param board 数组棋盘，需要时10*10！！
     * @throws CounterException
     */
    public static void checkCounter(int cou, int[][] board) throws CounterException {
        int p = 0;
        for (int i = 1; i < 9; i++) {
            for (int j = 1; j < 9; j++) {
                if(board[i][j] != 0){
                    p++;
                }
            }
        }
        if(p != cou || cou >= 64){
            throw new CounterException();
        }
    }
    //样例
    public static void main(String[] args) {
        int[][] a = new int[9][9];
        try {
            checkCounter(0,a);
            System.out.println("continue");
            checkCounter(1,a);
            System.out.println("over");
        } catch (CounterException e) {
            System.out.println("error!");
        }
    }
}
