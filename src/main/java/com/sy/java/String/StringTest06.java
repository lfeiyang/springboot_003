package com.sy.java.String;

import lombok.extern.slf4j.Slf4j;

/**
 * 字符串拼接操作
 *
 * @author lfeiyang
 * @since 2022-07-23 12:01
 */
@Slf4j
public class StringTest06 {
    public static void main(String[] args) {
        test1();

        test2();


        test3();

        test4();

        test5();
    }

    public static void test1() {
        // 都是常量，前端编译期会进行代码优化
        String s1 = "a" + "b" + "c";
        String s2 = "abc";

        // true，有上述可知，s1和s2实际上指向字符串常量池中的同一个值
        log.warn("结果：" + (s1 == s2));  //true
    }

    public static void test2() {
        String s1 = "javaEE";
        String s2 = "hadoop";

        String s3 = "javaEEhadoop";
        String s4 = "javaEE" + "hadoop";
        String s5 = s1 + "hadoop";
        String s6 = "javaEE" + s2;
        String s7 = s1 + s2;

        // 如果拼接符号的前后出现了变量，则相当于在堆空间中new String()，具体的内容为拼接的结果：javaEEhadoop
        log.warn("结果：" + (s3 == s4));  // true 编译期优化
        log.warn("结果：" + (s3 == s5));  // false s1是变量，不能编译期优化
        log.warn("结果：" + (s3 == s6));  // false s2是变量，不能编译期优化
        log.warn("结果：" + (s3 == s7));  // false s1、s2都是变量
        log.warn("结果：" + (s5 == s6));  // false s5、s6 不同的对象实例
        log.warn("结果：" + (s5 == s7));  // false s5、s7 不同的对象实例
        log.warn("结果：" + (s6 == s7));  // false s6、s7 不同的对象实例

        //intern():判断字符串常量池中是否存在JavaEEHadoop值，如果存在，则返回常量池中JavaEEHadoop的地址；
        //如果字符串常量池中不存在JavaEEHadoop，则在常量池中加载一份JavaEEHadoop，并返回对象地址。
        String s8 = s6.intern();
        log.warn("结果：" + (s3 == s8)); // true intern之后，s8和s3一样，指向字符串常量池中的"javaEEhadoop"
    }

    public static void test3() {
        String s1 = "a";
        String s2 = "b";
        String s3 = "ab";
        /*
        如下的s1 + s2 的执行细节：(变量s是我临时定义的,底层使用的是StringBuilder，但不一定是s）
        ① StringBuilder s = new StringBuilder();
        ② s.append("a")
        ③ s.append("b")
        ④ s.toString()  --> 约等于 new String("ab")

        补充：在jdk5.0之后使用的是StringBuilder,在jdk5.0之前使用的是StringBuffer
         */
        String s4 = s1 + s2;//
        log.warn("结果：" + (s3 == s4));//false
    }

    /*
    1. 字符串拼接操作不一定使用的是StringBuilder!
       如果拼接符号左右两边都是字符串常量或常量引用，则仍然使用编译期优化，即非StringBuilder的方式。
    2. 针对于final修饰类、方法、基本数据类型、引用数据类型的量的结构时，能使用上final的时候建议使用上。
     */
    public static void test4() {
        final String s1 = "a"; // final 修饰，s1实际上已经成为了字符串常量
        final String s2 = "b";
        String s3 = "ab";
        String s4 = s1 + s2; // 这里相当于是常量和常量的拼接，所以结果是放在常量池中
        log.warn("结果：" + (s3 == s4));//true
    }

    //练习：
    public static void test5(){
        String s1 = "javaEEhadoop";
        String s2 = "javaEE";
        String s3 = s2 + "hadoop";
        log.warn("结果：" + (s1 == s3));//false

        final String s4 = "javaEE";//s4:常量
        String s5 = s4 + "hadoop";
        log.warn("结果：" + (s1 == s5));//true

    }
}
