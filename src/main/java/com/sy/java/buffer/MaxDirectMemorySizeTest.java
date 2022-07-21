package com.sy.java.buffer;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * 直接通过 Unsafe 类申请本地内存
 * -Xmx20m -XX:MaxDirectMemorySize=10m
 *
 * @author lfeiyang
 * @since 2022-07-16 12:14
 */
public class MaxDirectMemorySizeTest {
    private static final long _1MB = 1024 * 1024;

    public static void main(String[] args) throws IllegalAccessException {
        Field unsafeField = Unsafe.class.getDeclaredFields()[0];
        unsafeField.setAccessible(true);
        Unsafe unsafe = (Unsafe) unsafeField.get(null);
        while (true) {
            unsafe.allocateMemory(_1MB);
        }

    }
}
