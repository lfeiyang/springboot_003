package com.sy.java.buffer;

import java.nio.ByteBuffer;
import java.util.ArrayList;

/**
 * 直接内存溢出
 *
 * @author lfeiyang
 * @since 2022-07-16 12:11
 */
public class BufferTest2 {
    private static final int BUFFER = 1024 * 1024 * 20; //20MB

    public static void main(String[] args) {
        ArrayList<ByteBuffer> list = new ArrayList<>();

        int count = 0;
        try {
            while (true) {
                ByteBuffer byteBuffer = ByteBuffer.allocateDirect(BUFFER);
                list.add(byteBuffer);
                count++;
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } finally {
            System.out.println(count);
        }

    }
}
