package com.sy.java.heap;

import java.util.ArrayList;
import java.util.Random;

/**
 * 对象分配过程
 *
 * @author lfeiyang
 * @since 2022-07-03 23:23
 */
public class HeapInstance {
    byte[] buffer = new byte[new Random().nextInt(1024 * 200)]; //为了让对象变大

    public static void main(String[] args) {
        ArrayList<Object> list = new ArrayList<>();
        while (true) {
            list.add(new HeapInstance());
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
