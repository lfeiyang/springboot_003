package com.sy.java.String;

/**
 * 性能比较
 *
 * @author lfeiyang
 * @since 2022-07-23 12:33
 */
public class StringTest07 {
    public static void main(String[] args) {
        int times = 40000;

        long start = System.currentTimeMillis();

        // testString(times);    // String  6963ms
        testStringBuilder(times); // StringBuilder    2ms

        long end = System.currentTimeMillis();
        System.out.println("String: " + (end - start) + "ms");


    }

    public static void testString(int times) {
        String str = "";
        for (int i = 0; i < times; i++) {
            str += "test";
        }
    }

    public static void testStringBuilder(int times) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < times; i++) {
            sb.append("test");
        }
    }
}
