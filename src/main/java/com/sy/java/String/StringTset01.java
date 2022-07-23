package com.sy.java.String;

/**
 * String的基本使用:体现String的不可变性
 *
 * @author lfeiyang
 * @since 2022-07-21 21:52
 */
public class StringTset01 {
    public static void main(String[] args) {
        // test1();

        // test2();

        test3();
    }

    public static void test1() {
        String s1 = "abc"; // 字面量定义的方式，"abc"存储在字符串常量池中
        String s2 = "abc";
        //s1 = "hello";

        System.out.println(s1 == s2); // false
        System.out.println(s1); // hello
        System.out.println(s2); // abc
    }

    public static void test2() {
        String s1 = "abc";
        String s2 = "abc";
        s2 += "def";
        System.out.println(s2); // abcdef
        System.out.println(s1); // abc
    }

    public static void test3() {
        String s1 = "abc";
        String s2 = s1.replace('a', 'm');
        System.out.println(s1); // abc
        System.out.println(s2); // mbc
    }
}
