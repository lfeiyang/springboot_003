package com.sy.java.collection_.set_;

import lombok.extern.slf4j.Slf4j;

import java.util.Comparator;
import java.util.TreeSet;

/**
 * TreeSet底层结构和源码分析
 *
 * @author lfeiyang
 * @since 2022-09-06 23:04
 */
@Slf4j
@SuppressWarnings({"all"})
public class TreeSet_ {
    public static void main(String[] args) {
        // TreeSet treeSet = new TreeSet();
        TreeSet treeSet = new TreeSet(new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                //下面 调用String的 compareTo方法进行字符串大小比较
                //如果老韩要求加入的元素，按照长度大小排序
                //return ((String) o2).compareTo((String) o1);
                return ((String) o1).length() - ((String) o2).length();
            }
        });

        //添加数据.
        treeSet.add("jack");
        treeSet.add("tom");//3
        treeSet.add("sp");
        treeSet.add("a");
        treeSet.add("abc");//3
        System.out.println("treeSet=" + treeSet);
    }
}
