package com.sy.java.collection_.set_;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

/**
 * 模拟个HashSet的底层(HashMap的底层结构)
 *
 * @author lfeiyang
 * @since 2022-08-31 0:04
 */
@Slf4j
public class HashSetStructure {
    public static void main(String[] args) {
        //1. 创建一个数组，数组的类型是Node[]
        //2. 有些人，直接把Node[]数组称为 表
        Node[] table = new Node[16];
        log.warn("table=" + Arrays.toString(table));

        //3.创建节点
        Node john = new Node("john", null);

        table[2] = john;
        Node jack = new Node("jack", null);
        //将jack 结点挂载到john
        john.next = jack;
        //将rose 结点挂载到jack
        jack.next = new Node("Rose", null);

        Node lucy = new Node("lucy", null);
        //把Lucy放到table表的索引为3的位置。
        table[3] = lucy;
        log.warn("table=" + Arrays.toString(table));
    }
}

/**
 * 节结点，存储数据，可以指向下一个结点，从而形成链表
 *
 * @author lfeiyang
 * @since 2022-08-31 0:04
 */
class Node {
    Object item;
    Node next;

    public Node(Object item, Node next) {
        this.item = item;
        this.next = next;
    }

    @Override
    public String toString() {
        return "Node{" +
                "item=" + item +
                '}';
    }
}
