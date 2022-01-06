package com.util;

public class ModeException extends Exception{
    /**
     * 传入check模式信息，检测模式格式合法性。
     * @param checkInfo
     * @throws ModeException
     */
    public static void checkMode(int checkInfo) throws ModeException {
        if(checkInfo != 0 && checkInfo != 1){
            throw new ModeException();
        }
    }

    public static void main(String[] args) {
        try{
            checkMode(1);
            System.out.println("continue");
            checkMode(2);
            System.out.println("Over");
        }catch (ModeException e){
            System.out.println("error");
        }
    }
}
