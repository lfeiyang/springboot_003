package com.sy.java.String;

/**
 * StringTable的垃圾回收测试
 * -Xms15m -Xmx15m -XX:+PrintStringTableStatistics -XX:+PrintGcDetails
 *
 * @author lfeiyang
 * @since 2022-07-24 11:00
 */
public class StringGcTest {
    private static final int COUNT = 100000;

    public static void main(String[] args) {
        for (int i = 0; i < COUNT; i++) {
            String.valueOf(i).intern();
        }
    }
}
