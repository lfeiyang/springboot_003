package com.sy.java.collection_.set_;

import java.util.HashSet;

/**
 * HashSet扩容机制（树化）
 *
 * @author lfeiyang
 * @since 2022-09-01 23:31
 */
public class HashSetIncrementTree {
    @SuppressWarnings({"all"})
    public static void main(String[] args) {
        HashSet<Object> hashSet = new HashSet<>();
        for (int i = 0; i < 12; i++) {
            hashSet.add(new A(i));
        }
    }
}

@SuppressWarnings({"all"})
class A {
    private int n;

    public A(int n) {
        this.n = n;
    }

    @Override
    public int hashCode() {
        return 100;
    }
}
