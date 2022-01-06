package com.ethy9160.Regret;

/**
 * 这是一个用来存储Step的类。
 * 包含：
 * <p>
 * x, y, color(participant), board[][], cou（合计68个元素）
 * @author 马齐晨
 */
public class Step {
    private int x_former;
    private int y_former;
    private int color;
    private int[][]board = new int[10][10];
    private String boardStr = null;
    private int cou;
    private int cheatInfo;

    public Step(int color,int[][] board,int x_former,int y_former,int cou,int cheatInfo){
        this.cou = cou;
        this.x_former = x_former;
        this.y_former = y_former;
        this.color = color;
        this.cheatInfo = cheatInfo;
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i < 9; i++) {
            for (int i1 = 1; i1 < 9; i1++) {
                this.board[i][i1] = board[i][i1];
                sb.append(board[i][i1]).append(";");
            }
        }
        sb.setLength(sb.length()-1);
        this.boardStr = sb.toString();
    }
    public int getX_former(){
        return x_former;
    }
    public int getY_former(){
        return y_former;
    }
    public int getColor(){
        return color;
    }
    public int[][] getBoard(){
        return board;
    }
    public int getCou(){return cou;}

    public int getCheatInfo() {
        return cheatInfo;
    }

    /**
     * 储存格式：行棋方， x坐标， y坐标， 下子数， 是否为cheat模式， 棋盘
     * @return 没有
     */
    @Override
    public String toString() {
        return String.format("%d;%d;%d;%d;%d;%s",color,x_former,y_former,cou,cheatInfo,boardStr);
    }
}
