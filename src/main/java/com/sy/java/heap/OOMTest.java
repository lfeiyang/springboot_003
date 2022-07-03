package com.sy.java.heap;

import java.util.ArrayList;
import java.util.Random;

/**
 * OOM测试
 *
 * @author lfeiyang
 * @since 2022-07-03 18:11
 */
public class OOMTest {
    public static void main(String[] args) {
        ArrayList<Picture> list = new ArrayList<>();
        while (true) {
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();

                e.printStackTrace();
            }

            list.add(new Picture(new Random().nextInt(1024 * 1024)));
        }

    }
}

class Picture {
    private byte[] pixels;

    public Picture(int length) {
        this.pixels = new byte[length];
    }
}
