package com.ethy9160.Regret;
import com.util.*;
import com.util.CounterException;
import com.ethy9160.main.CommonPanel;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static com.util.BoardElementsException.checkBoardElements;
import static com.util.CounterException.checkCounter;

/**
 * 档案读取。
 * 包含util中的计数器、棋盘、参与者、对弈模式的查错。
 * @author 马齐晨
 */
public class Loader {
    ArrayList<Step> stepListSave = new ArrayList<>();
    ArrayList<Step> stepList = new ArrayList<>();
    private int[][] currentBoard = new int[10][10];
    private String loadFileName;

    public Loader(){

    }

    /**
     * 传入<c>文件名</c>，获取文件。
     * @param index 文件名（由于旧版使用的是index记录下标，所以这里没有更名为“documentName”）
     */
    public void loadFile(String index){
        StringBuilder sb = new StringBuilder();
        sb.setLength(0);
        try {
            ArrayList<Step> stepContainer = new ArrayList<>();
            ArrayList<Step> stepListCheck = new ArrayList<>();
            this.loadFileName = "D:\\resource\\Library\\"+index;
            Scanner input = new Scanner(new File(loadFileName));
            while(input.hasNext()){
                sb.append(input.next());
            }
            sb.setLength(sb.length()-1);
            sb.deleteCharAt(0);
            String[] tokens = sb.toString().split(",");
            for(String a:tokens){
                String[] tokens2 = a.split(";");
                try {
                    BoardLengthException.checkBoardLength(tokens2);
                }catch (BoardLengthException e){
                    JOptionPane.showMessageDialog(null,"读取失败：棋盘长度非法。","错误",0);	//消息对话框
                    System.out.println("读取到的字符串长度不合理");
                    return;
                }
                int color = Integer.parseInt(tokens2[0]);
                int xCur = Integer.parseInt(tokens2[1]);
                int yCur = Integer.parseInt(tokens2[2]);
                int couCur = Integer.parseInt(tokens2[3]);
                int cheatInfoCur = Integer.parseInt(tokens2[4]);
                int[][] currentBoard = new int[10][10];
                int n = 5;
                while(n < 69){
                    for (int i = 1; i < 9; i++) {
                        for (int i1 = 1; i1 < 9; i1++) {
                            currentBoard[i][i1] = Integer.parseInt(tokens2[n]);
                            n++;
                        }
                    }
                }
                try {
                    checkBoardElements(currentBoard);
                    checkCounter(couCur,currentBoard);//添加检测counter，12/19/13：53
                    OtherLogicException.checkOtherLogic(currentBoard, cheatInfoCur);
                    ParticipantException.checkParticipant(color);
                    ModeException.checkMode(cheatInfoCur);
                    PositionException.checkPosition(currentBoard,yCur,xCur);
                    Step step = new Step(color,currentBoard,xCur,yCur,couCur,cheatInfoCur);
                    stepListCheck.add(step);
                    stepContainer.add(step);//添加检查participant，12/20/08：10
                } catch (BoardElementsException e1) {
                    JOptionPane.showMessageDialog(null,"读取失败：棋盘非法。","错误", JOptionPane.ERROR_MESSAGE);	//消息对话框
                    System.out.println("数组元素非法！");
                    return;
                }catch(CounterException e2){//添加检测counter，12/19/13：53
                    JOptionPane.showMessageDialog(null,"读取失败：计数器存在错误。","错误",0);	//消息对话框
                    System.out.println("计数器非法。");
                    return;
                }catch(ParticipantException e3){//添加检查participant，12/20/08：10
                    JOptionPane.showMessageDialog(null,"读取失败：对弈双方信息错误。","错误",0);	//消息对话框
                    System.out.println("参与者信息非法。");
                    return;
                }catch (ModeException e4){
                    JOptionPane.showMessageDialog(null,"读取失败：检测到模式错误。","错误",0);	//消息对话框
                    System.out.println("模式格式非法。");
                    return;
                }catch(PositionException e6){
                    JOptionPane.showMessageDialog(null,"读取失败：落子坐标错误。","错误",0);	//消息对话框
                    System.out.println("positionError.");
                    return;
                }catch (OtherLogicException e5){
                    JOptionPane.showMessageDialog(null,"读取失败：棋盘已经结束。","错误",0);	//消息对话框
                    System.out.println("otherLogidError.");
                    return;
                }

            }
            this.stepListSave = stepListCheck;
            stepContainer.remove(stepContainer.size()-1);
            this.stepList = stepContainer;
            /*this.color = Integer.parseInt(tokens[0]);
            this.xCur = Integer.parseInt(tokens[1]);
            this.yCur = Integer.parseInt(tokens[2]);
            int n = 3;
            while(n < 67){
                for (int i = 1; i < 9; i++) {
                    for (int i1 = 1; i1 < 9; i1++) {
                        this.currentBoard[i][i1] = Integer.parseInt(tokens[n]);
                        n++;
                    }

                }
            }*/
        }catch (IOException e){
            e.printStackTrace();
        }
        System.out.println("loader cou : " + getCouCur());
    }



    public int getCheatInfoCur(){
        return stepListSave.get(stepListSave.size()-1).getCheatInfo();
    }
    public int getColor(){
        return stepListSave.get(stepListSave.size()-1).getColor();
    }
    public int getxCur(){
        return stepListSave.get(stepListSave.size()-1).getX_former();
    }
    public int getyCur(){
        return stepListSave.get(stepListSave.size()-1).getY_former();
    }
    public int[][] getCurrentBoard(){
        return stepListSave.get(stepListSave.size()-1).getBoard();
    }
    public ArrayList<Step> getStepListSave(){
        return stepListSave;
    }
    public ArrayList<Step> getStepList(){
        return stepList;
    }
    public int getCouCur(){
        return stepListSave.get(stepListSave.size()-1).getCou();
    }
}
