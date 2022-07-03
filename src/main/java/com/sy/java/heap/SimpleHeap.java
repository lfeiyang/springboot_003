package com.sy.java.heap;

/**
 * 简单堆
 *
 * @author lfeiyang
 * @since 2022-07-03 16:21
 */
public class SimpleHeap {
    // 属性、成员变量
    private int id;

    public SimpleHeap(int id){
        this.id = id;
    }

    public void show(){
        System.out.println("My ID is " + id);
    }

    public static void main(String[] args) {
        SimpleHeap s1 = new SimpleHeap(1);
        SimpleHeap s2 = new SimpleHeap(1);

        int[] arr = new int[10];

        Object[] arr1 = new Object[10];
    }
}
