package com.sy.java;

/**
 * 栈帧测试
 *
 * @author lfeiyang
 * @since 2022-06-30 21:57
 */
public class StackFrameTest {
    public static void main(String[] args) {
        StackFrameTest stackFrameTest = new StackFrameTest();
        stackFrameTest.method1();
    }

    public void method1(){
        System.out.println("method1开始执行");
        method2();
        System.out.println("method1结束执行");
    }

    public int method2(){
        System.out.println("method1开始执行");
        int i = 10;
        int m = (int) method3();
        System.out.println("method1即将结束");
        return i + m;
    }

    public double method3(){
        System.out.println("method1开始执行");
        double j = 20.0;
        System.out.println("method1即将结束");
        return j;
    }
}
