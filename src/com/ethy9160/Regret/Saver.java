package com.ethy9160.Regret;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;

/**
 * 存档。
 * @author 马齐晨
 */
public class Saver {
//    private static int cnt = 1;
    private List<String> saveInfo = new ArrayList<>();

    public Saver(ArrayList<Step> stepList){
        for(Step a: stepList){
            this.saveInfo.add(a.toString());
        }
    }

    /**
     * 输入当前为你俺家的文档数目，他会写一个存档文件。
     * @param currentMax
     */
    public void saveC(int currentMax) {
        String fileName = "D:\\resource\\Library\\Document"+(++currentMax)+".txt";

        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
            writer.write(saveInfo.toString());
            writer.close();
        }catch(Exception e){
            e.printStackTrace();
        }
//        count();
    }
}
