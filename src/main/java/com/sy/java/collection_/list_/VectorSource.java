package com.sy.java.collection_.list_;

import java.util.Vector;

/**
 * Vector底层结构和源码分析
 *
 * @author lfeiyang
 * @since 2022-08-30 20:35
 */
public class VectorSource {
    @SuppressWarnings({"all"})
    public static void main(String[] args) {
        Vector vec = new Vector();
        for (int i = 0; i < 10; i++) {
            vec.add(i);
        }

        vec.add(100);
    }
}
