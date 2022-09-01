package com.sy.java.collection_.set_;

import java.util.HashSet;

/**
 * HashSet扩容机制
 *
 * @author lfeiyang
 * @since 2022-09-01 23:18
 */
public class HashSetIncrement {
    @SuppressWarnings({"all"})
    public static void main(String[] args) {
        HashSet<Object> hashSet = new HashSet<>();
        for (int i = 0; i < 100; i++) {
            hashSet.add(i);
        }
    }
}
