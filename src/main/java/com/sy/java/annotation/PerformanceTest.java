package com.sy.java.annotation;

import com.sy.model.AnnotationUser;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 注解性能测试
 *
 * @author lfeiyang
 * @since 2022-08-21 23:04
 */
@Slf4j
public class PerformanceTest {
    private static final int NUM = 1000000000;

    public static void main(String[] args) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        test1();//5ms
        test2();//4114ms
        test3();//1483ms
    }

    /***
     * 原生java
     **/
    public static void test1() {
        AnnotationUser user = new AnnotationUser();
        long start = System.currentTimeMillis();
        for (int i = 0; i < NUM; i++) {
            user.getName();
        }
        long end = System.currentTimeMillis();
        log.warn("原生java：" + (end - start) + "ms");
    }

    /***
     * 使用注解，不使用 setAccessible
     *
     **/
    public static void test2() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        AnnotationUser user = new AnnotationUser();
        Class<? extends AnnotationUser> c1 = user.getClass();
        Method getName = c1.getDeclaredMethod("getName", null);

        long start = System.currentTimeMillis();
        for (int i = 0; i < NUM; i++) {
            getName.invoke(user, null);
        }
        long end = System.currentTimeMillis();

        log.warn("使用注解，不使用 setAccessible：" + (end - start) + "ms");
    }

    /***
     * 使用注解，使用 setAccessible
     *
     **/
    public static void test3() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        AnnotationUser user = new AnnotationUser();
        Class<? extends AnnotationUser> c1 = user.getClass();
        Method getName = c1.getDeclaredMethod("getName", null);
        getName.setAccessible(true);
        long start = System.currentTimeMillis();
        for (int i = 0; i < NUM; i++) {
            getName.invoke(user, null);
        }
        long end = System.currentTimeMillis();

        log.warn("使用注解，使用 setAccessible：" + (end - start) + "ms");
    }
}
