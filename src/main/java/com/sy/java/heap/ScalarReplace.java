package com.sy.java.heap;

/**
 * 标量替换测试
 * -Xmx100m -Xms100m -XX:+DoEscapeAnalysis -XX:+PrintGC
 * -XX:-EliminateAllocations//是否允许标量替换，测试的时候主要改变这里
 */
public class ScalarReplace {
    public static class User {
        public int id;//标量（无法再分解成更小的数据）
        public String name;//聚合量（String还可以分解为char数组）
    }

    public static void alloc() {
        User u = new User();//未发生逃逸
        u.id = 5;
        u.name = "www.atguigu.com";
    }

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        for (int i = 0; i < 10000000; i++) {
            alloc();
        }
        long end = System.currentTimeMillis();
        System.out.println("花费的时间为： " + (end - start) + " ms");
    }
}
