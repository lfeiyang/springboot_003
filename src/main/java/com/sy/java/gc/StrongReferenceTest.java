package com.sy.java.gc;

/**
 * 强引用
 *
 * @author lfeiyang
 * @since 2022-07-27 21:46
 */
public class StrongReferenceTest {
    public static void main(String[] args) {
        StringBuffer str = new StringBuffer("hello,lfeiyang!");
        StringBuffer str1 = str;

        str = null;
        System.gc();

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            
            e.printStackTrace();
        }


        System.out.println(str1);
    }
}
