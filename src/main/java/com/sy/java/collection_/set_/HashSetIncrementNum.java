package com.sy.java.collection_.set_;

import java.util.HashSet;

/**
 * hashSet扩容计数（一个Node就算一个，不是必须在一个链表上）
 *
 * @author lfeiyang
 * @since 2022-09-01 23:44
 */
@SuppressWarnings({"all"})
public class HashSetIncrementNum {
    public static void main(String[] args) {
        HashSet<Object> hashSet = new HashSet<>();
        for (int i = 0; i < 7; i++) {
            hashSet.add(new B(i));
        }

        for (int i = 0; i < 7; i++) {
            hashSet.add(new C(i));
        }
    }
}

@SuppressWarnings({"all"})
class C {
    private int n;

    public C(int n) {
        this.n = n;
    }

    @Override
    public int hashCode() {
        return 100;
    }
}

@SuppressWarnings({"all"})
class B {
    private int n;

    public B(int n) {
        this.n = n;
    }

    @Override
    public int hashCode() {
        return 200;
    }
}
