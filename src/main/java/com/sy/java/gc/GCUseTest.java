package com.sy.java.gc;

import java.util.ArrayList;
import java.util.List;

/**
 * 查看默认垃圾收集器
 *
 * @author lfeiyang
 * @since 2022-07-27 23:08
 */
public class GCUseTest {
    public static void main(String[] args) {
        List<byte[]> list = new ArrayList<>();
        while (true) {
            byte[] arr = new byte[100];
            list.add(arr);
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
