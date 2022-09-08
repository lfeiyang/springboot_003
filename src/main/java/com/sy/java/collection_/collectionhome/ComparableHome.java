package com.sy.java.collection_.collectionhome;

import java.util.TreeSet;

/**
 * TreeSet比较器测试
 *
 * @author lfeiyang
 * @since 2022-09-08 22:19
 */
@SuppressWarnings({"all"})
public class ComparableHome {
    public static void main(String[] args) {
        TreeSet<Object> treeSet = new TreeSet<>();

        // 需要实现Comparable接口
        treeSet.add(new Dog());
    }
}

@SuppressWarnings({"all"})
class Dog {

}
