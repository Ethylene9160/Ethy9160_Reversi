package com.util;

/**
 * 用于检测participant和xOld，yOld是否在一起。
 */
public class PositionException extends Exception{
    /**
     * 检测位置是否符合
     * @param board 棋盘（即：board）
     * @param yOld
     * @param xOld
     * @throws PositionException
     */
    public static void checkPosition(int[][] board, int yOld, int xOld) throws PositionException {
        if(yOld > 8 || xOld > 8 || yOld < 1 || xOld < 1 || board[yOld][xOld] == 0){
            throw new PositionException();
        }
    }

    public static void main(String[] args) {
        int board[][] = new int[10][10];
        board[4][4] = 1;
        int i = 4, j = 4;
        int par = 1;
        try{
            checkPosition(board, i, j);
            System.out.println("continue");
            checkPosition(board,i,j);
            System.out.println("end");
        }catch (PositionException e) {
            System.out.println("error");
        }
    }
}
/*
-1;-1;-1;-1;-1;-1;-1;-1;
0;1;1;1;1;1;1;-1;
0;1;1;1;1;1;1;-1;
0;1;1;1;1;1;1;-1;
0;1;1;1;1;1;1;-1;
0;1;1;1;1;1;1;0;
0;0;0;0;0;0;0;0;
0;0;0;0;0;0;0;0;

 */
