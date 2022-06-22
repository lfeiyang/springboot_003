package com.sy.java;

import sun.misc.Launcher;
import sun.security.ec.CurveDB;

import java.net.URL;
import java.security.Provider;

/**
 * 类加载器演示
 *
 * @author lfeiyang
 * @since 2022-06-22 21:30
 */
public class ClassLoaderTest {
    public static void main(String[] args) {
        System.out.println("===========启动类加载器=============");
        URL[] urLs = Launcher.getBootstrapClassPath().getURLs();
        for(URL element : urLs){
            System.out.println(element.toExternalForm());
        }

        // 从上面的路劲中随意找一个类，看看他的类加载器是什么
        ClassLoader classLoader = Provider.class.getClassLoader();
        System.out.println("加载器======>" + classLoader);


        System.out.println("===========扩展类加载器=============");
        String extDirs = System.getProperty("java.ext.dirs");
        for(String path : extDirs.split(";")){
            System.out.println(path);
        }

        // 从上面的路劲中随意找一个类，看看他的类加载器是什么
        ClassLoader classLoader1 = CurveDB.class.getClassLoader();
        System.out.println("加载器======>" + classLoader1);
    }
}
