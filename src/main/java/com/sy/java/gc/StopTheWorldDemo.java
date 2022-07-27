package com.sy.java.gc;

import java.util.ArrayList;
import java.util.List;

/**
 * 测试 Stop The World
 *
 * @author lfeiyang
 * @since 2022-07-26 23:24
 */
public class StopTheWorldDemo {
    public static class WorkThread extends Thread {
        List<byte[]> list = new ArrayList<byte[]>();

        // 主要任务是制造垃圾，进而触发 full gc
        public void run() {
            try {
                while (true) {
                    for (int i = 0; i < 1000; i++) {
                        byte[] buffer = new byte[1024];
                        list.add(buffer);
                    }

                    if (list.size() > 10000) {
                        list.clear();
                        System.gc(); // 会触发 full gc，进而会出现 STW 事件
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    // 1秒大于1次
    public static class PrintThread extends Thread {
        public final long startTime = System.currentTimeMillis();

        public void run() {
            try {
                while (true) {
                    // 每秒打印时间信息
                    long t = System.currentTimeMillis() - startTime;
                    System.out.println(t / 1000 + "." + t % 1000);
                    Thread.sleep(1000);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        WorkThread w = new WorkThread();
        PrintThread p = new PrintThread();
        // w.start();
        p.start();
    }
}
