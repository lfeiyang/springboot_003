package com.sy.java;

/**
 * 字节码修改
 *
 * @author lfeiyang
 * @since 2022-05-30 21:32
 */
public class CtPrimitiveType {
    public static void main(String[] args) {
        int a = 32;
        if (a > 100) {
            int b = 1000;
        }
    }
}
