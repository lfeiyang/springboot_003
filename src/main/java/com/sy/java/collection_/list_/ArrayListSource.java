package com.sy.java.collection_.list_;

import java.util.ArrayList;

/**
 * ArrayList底层结构和源码分析
 *
 * @author lfeiyang
 * @since 2022-08-29 22:01
 */
public class ArrayListSource {
    @SuppressWarnings({"all"})
    public static void main(String[] args) {
        // 使用无参构造器创建ArrayList
        ArrayList<Object> list = new ArrayList<>();

        // 使用有参构造器创建ArrayList
        // ArrayList<Object> list = new ArrayList<>(8);

        // 向集合添加10条数据
        for (int i = 1; i <= 10; i++) {
            list.add(i);
        }

        // 再向集合添加5条数据
        for (int i = 11; i <= 15; i++) {
            list.add(i);
        }

        list.add(100);
        list.add(200);
    }
}
