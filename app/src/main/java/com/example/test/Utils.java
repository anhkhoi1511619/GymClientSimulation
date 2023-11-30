package com.example.test;

public class Utils {
    /**
     * 概要：byte配列をStringに変換
     * @param input 変換したいbyte配列
     * @return 変換後String data
     */
    public static String format(byte[] input) {
        if(input == null) {
            return "";
        }
        StringBuilder ret = new StringBuilder();
        for (byte x: input) {
            ret.append(format(x));
        }
        return ret.toString();
    }
    /**
     * 概要：1byteをStringに変換
     * @param input 変換したいbyte
     * @return 変換後String data
     */
    public static String format(byte input){
        return String.format("%02X", input);
    }
}
