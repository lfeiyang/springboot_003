package com.sy.java;

/**
 * 栈溢出StackOverflowError
 * 默认情况：count 9725
 * 默认栈的大小： -Xss256k count 2153
 *
 * @author lfeiyang
 * @since 2022-06-29 23:17
 */
public class StackErrorTest {
    private static int count = 0;

    public static void main(String[] args) {
        System.out.println(count++);

        main(args);
    }
}
