package com.sy.java.String;

import lombok.extern.slf4j.Slf4j;

/**
 * intern() 的空间效率测试
 *
 * @author lfeiyang
 * @since 2022-07-23 17:42
 */
@Slf4j
public class StringIntern2 {
    static final int MAX_COUNT = 1000 * 10000;
    static final String[] arr = new String[MAX_COUNT];

    public static void main(String[] args) {
        Integer[] data = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

        long start = System.currentTimeMillis();
        for (int i = 0; i < MAX_COUNT; i++) {
             arr[i] = new String(String.valueOf(data[i % data.length]));
            //arr[i] = new String(String.valueOf(data[i % data.length])).intern();

        }
        long end = System.currentTimeMillis();
        log.warn("花费的时间为：" + (end - start));

        try {
            Thread.sleep(1000000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();

            e.printStackTrace();
        }
        System.gc();
    }
}
