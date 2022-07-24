package com.sy.java.String;

/**
 * 字符串唯一性
 *
 * @author lfeiyang
 * @since 2022-07-23 11:34
 */
public class StringTest05 {
    public static void main(String[] args) {
        System.out.println(); //3791
        System.out.println("1"); //3791
        System.out.println("2");
        System.out.println("3");
        System.out.println("4");
        System.out.println("5");
        System.out.println("6");
        System.out.println("7");
        System.out.println("8");
        System.out.println("9");
        System.out.println("10");
        // 这里的“1” 到“10”就不会被加载
        System.out.println("1"); //3802
        System.out.println("2"); //3802
        System.out.println("3");
        System.out.println("4");
        System.out.println("5");
        System.out.println("6");
        System.out.println("7");
        System.out.println("8");
        System.out.println("9");
        System.out.println("10"); //3802


    }
}
