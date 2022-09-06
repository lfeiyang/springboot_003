package com.sy.java.collection_.map_;

import java.util.HashMap;

/**
 * HashMap树化扩容机制
 *
 * @author lfeiyang
 * @since 2022-09-04 14:56
 */
@SuppressWarnings({"all"})
public class HashMapSourceTree {
    public static void main(String[] args) {
        HashMap<Object, Object> hashMap = new HashMap<>();
        for (int i = 0; i < 12; i++) {
            hashMap.put(new A(i), "Hello");
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
