package com.ethy9160.Regret;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;

import java.util.ArrayList;

/**
 * 修改时间：12月19日15：53
 *
 * @author 马齐晨
 */
public class RegretStep {

    private int cou;
    private int[][] formerBoard = new int[10][10];
    private int color;
    private int cheatInfo;
    private ArrayList<Step> stepList;

    public RegretStep(ArrayList stepList){
        this.stepList = new ArrayList<>();
        this.stepList.addAll(stepList);
    }

    public int getCheatInfo() {
        return stepList.get(stepList.size()-1).getCheatInfo();
    }
    public  int getX(){
        return stepList.get(stepList.size()-1).getX_former();
    }
    public int getY(){
        return stepList.get(stepList.size()-1).getY_former();
    }
    public int getColor(){
        color = stepList.get(stepList.size()-1).getColor();
        return color;
    }//获取上一个棋盘谁在下棋
    public int[][] getBoard(){
        formerBoard = stepList.get(stepList.size()-1).getBoard();
        return formerBoard;
    }//提取第n-1个棋盘
    public int getCou(){
        cou = stepList.get(stepList.size()-1).getCou();
        return cou;
    }
    public void initR(ArrayList<Step> stepList){
        stepList.remove(stepList.size()-1);
    }//移除最后一项
}
