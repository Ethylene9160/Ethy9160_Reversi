package com.util;

public class ParticipantException extends Exception{
    /**
     * 输入participant以检查participant的合法性。
     * @param participant 游戏参与者
     * @throws ParticipantException 错误类型
     */
    public static void checkParticipant(int participant) throws ParticipantException {
        if(participant != 1 && participant != -1){
            throw new ParticipantException();
        }
    }

    public static void main(String[] args) {
        try{
            checkParticipant(1);
            System.out.println("continue");
            checkParticipant(-1);
            System.out.println("over");
            checkParticipant(0);
        }catch (ParticipantException e){
            System.out.println("error");
        }
    }
}
