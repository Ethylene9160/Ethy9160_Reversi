package com.util;

public class BoardElementsException extends Exception {
    /**
     * <b>使用时需要调用这个方法！可以看样例。</b>
     * @param board 棋盘，需要<b>10*10</b>数组！
     * @throws BoardElementsException 棋盘元素错误
     * @author Ethylene9160
     */
    public static void checkBoardElements(int[][] board) throws BoardElementsException {
        for (int i = 1; i < 9; i++) {
            for (int j = 1; j < 9; j++) {
                if((board[i][j] != 1) && (board[i][j] != 0) && (board[i][j] != -1)){
                    throw new BoardElementsException();
                }
            }
        }
    }

    /**
     * 样例：把<code> checkBoardElements()</code> 环绕在<code> try{...}catch(){...}</code> 即可。
     * @param args
     * @author Ethylene9160
     */
    public static void main(String[] args) {
        int[][] a = new int[10][10];
        a[4][7] = 3;
        int[][] b = new int[10][10];


        try {
            checkBoardElements(b);
            System.out.println("continue");
            checkBoardElements(a);
        } catch (BoardElementsException e) {
            System.out.println("Error");
        }

    }
}
