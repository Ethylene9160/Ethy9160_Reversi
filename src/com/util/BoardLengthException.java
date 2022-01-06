package com.util;

public class BoardLengthException extends Exception{

    /**
     *
     * @param boardElement 刚刚分离出来的东西
     * @param <T> 数组
     * @throws BoardLengthException
     * @author 马齐晨、Ethylene9160
     */
    public static <T> void checkBoardLength(T[] boardElement) throws BoardLengthException {
        if(boardElement.length != 69){
            throw new BoardLengthException();
        }
    }

    public static void main(String[] args) {
        Integer a[] = {1,2,3,4,5};
        Integer b[] = new Integer[69];
        try {
            checkBoardLength(b);
            System.out.println("continue");
            checkBoardLength(a);
        } catch (BoardLengthException e) {
            System.out.println("Wrong Answer");
        }
    }

}
