package com.sy.java;

import java.util.Date;

/**
 * 局部变量表
 *
 * @author lfeiyang
 * @since 2022-06-30 22:50
 */
public class LocalVariablesTest {
    private int count = 0;

    public static void main(String[] args) {
        LocalVariablesTest localVariablesTest = new LocalVariablesTest();
        int num = 10;
        localVariablesTest.test1();
    }

    public void test1() {
        // 该对象引用this将会存放在index为0的slot处
        Date date = new Date();
        String name1 = "Hello,Lfeiyang";
        String info = test2(date, name1);
    }

    public String test2(Date datep, String name2) {
        datep = null;
        name2 = "hahaha";
        double weight = 130.5;
        char gender = '男';
        return datep + name2;
    }

    public void test3() {
        count++;
    }

    /***  
     * 
     * Slot栈帧中的局部变量表中的槽位是重复利用是重复利用的
     **/
    public void test4() {
        int a = 0;
        {
            int b = 0;
            b = +1;
        }

        // 此时的b就会复用a的槽位
        int c = a + 1;
    }
}
