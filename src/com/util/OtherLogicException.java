package com.util;

import com.ethy9160.move.P4_Othello;
/**
 * 请不要使用它！！！
 */
public class OtherLogicException extends Exception{
    /**
     * 用于检测棋盘的其他逻辑错误，如：没有使用cheat模式的时候的棋盘错误
     * <p>
     * （其实只有游戏已经结束的棋盘不能读取。）
     * @param board 棋盘（10*10）
     * @param isCheatInfo cheat模式的数字编码
     * @author Ethylene9160
     */
    public static void checkOtherLogic(int[][] board, int isCheatInfo) throws OtherLogicException{
       int p[][] = new int[10][10];
        if(isCheatInfo != 1){
            for (int i = 1; i < 9; i++) {
                for (int j = 1; j < 9; j++) {
                    P4_Othello.predict(board, p, 1);
                    if(p[9][9] == 5){
                        P4_Othello.predict(board, p, -1);
                        if(p[9][9] == 5){
                            throw new OtherLogicException();
                        }
                    }
                }
            }
        }
    }

    /**
     * 其中棋子不能单独存在！
     * 如果返回值是真，那么抛出异常。
     * @param
     * @param
     * @param
     * @return
     */
//    public static boolean findChess(int[][] board, int i, int j){
//        return (board[i][j] != 0 &&
//                board[i+1][j+1] == 0 &&
//                board[i][j+1] == 0 &&
//                board[i-1][j+1] == 0 &&
//                board[i+1][j] == 0 &&
//                board[i-1][j] == 0 &&
//                board[i+1][j-1] == 0 &&
//                board[i][j-1] == 0 &&
//                board[i-1][j-1] == 0);
//    }

    public static void main(String[] args) {
        int[][] a = new int[10][10];
        a[3][4] = 1;
        a[4][4] = -1;
        try {
            checkOtherLogic(a,0);
            System.out.println("continue");
            a[4][4] = 1;
            checkOtherLogic(a,0);
            System.out.println("over");
        } catch (OtherLogicException e) {
            System.out.println("error");
        }
    }

}
