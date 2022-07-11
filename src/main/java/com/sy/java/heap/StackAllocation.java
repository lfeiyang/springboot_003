package com.sy.java.heap;

/**
 * 栈上分配
 *
 * @author lfeiyang
 * @since 2022-07-06 21:47
 */
public class StackAllocation {
    public static void main(String[] args) throws InterruptedException {
        long start = System.currentTimeMillis();
        for (int i = 0; i < 100000000; i++) {
            alloc();
        }
        long end = System.currentTimeMillis();
        System.out.println("花费的时间为：" + (end - start) + " ms");

        // 为了方便查看堆内存中对象个数，线程sleep
        Thread.sleep(10000000);
    }

    private static void alloc() {
        UserHeap userHeap = new UserHeap();
    }

    static class UserHeap {
        private String name;
        private String age;
        private String gender;
        private String phone;
    }
}

