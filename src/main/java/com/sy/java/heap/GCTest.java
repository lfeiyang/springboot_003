package com.sy.java.heap;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;

/**
 * 测试Minor GC、Major GC、Full GC
 *
 * @author lfeiyang
 * @since 2022-07-04 0:58
 */
@Slf4j
public class GCTest {
    public static void main(String[] args) {
        int i = 0;

        try {
            ArrayList<String> list = new ArrayList<>();
            String a = "atguigu.com";
            while (true) {
                list.add(a);
                a = a + a;
                i++;
            }
        } catch (Exception e) {
            System.out.println("遍历次数为：" + i);

            e.printStackTrace();
        }
    }
}
