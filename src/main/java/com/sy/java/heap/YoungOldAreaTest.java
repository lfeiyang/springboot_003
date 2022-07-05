package com.sy.java.heap;

/**
 * 内存分配策略 测试：大对象直接进入老年代
 *
 * @author lfeiyang
 * @since 2022-07-05 23:37
 */
public class YoungOldAreaTest {
    public static void main(String[] args) {
        byte[] buffer = new byte[1024 * 1024 * 20]; // 20m
    }
}
