package com.sy.java.String;

import java.util.HashSet;

/**
 * 字符串OOM测试
 * JDK6:-XX:PermSize=6m -XX:MaxPermsize=6m -Xms6m -Xmx6m
 * JDK8:-XX:MetaspaceSize=6m -XX:MaxMetaSpacesize=6m -Xms6m -Xmx6m
 *
 * @author lfeiyang
 * @since 2022-07-23 10:52
 */
public class StringTest04 {
    public static void main(String[] args) {
        // 用set保持着常量池引用，避免full gc回收常量池行为
        HashSet<String> set = new HashSet<>();

        //在short可以取值的范围内足以让6m发生OOM
        short i = 0;
        while (true) {
            set.add(String.valueOf(i).intern());
        }
    }
}
