package com.sy.java.gc;

import java.util.ArrayList;

/**
 * 内存溢出排查
 * -Xms8m -Xmx8m -XX:+HeapDumpOnOutOfMemoryError
 *
 * @author lfeiyang
 * @since 2022-07-24 23:46
 */
public class HeapOOM {
    byte[] buffer = new byte[1 * 1024 * 1024]; // 1MB


    public static void main(String[] args) {
        ArrayList<HeapOOM> list = new ArrayList<>();
        int count = 0;
        try {
            while (true) {
                list.add(new HeapOOM());
                count++;
            }
        } catch (Throwable e) {
            System.out.println("count = " + count);
            e.printStackTrace();
        }
    }
}
