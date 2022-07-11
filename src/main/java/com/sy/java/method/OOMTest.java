package com.sy.java.method;


import jdk.internal.org.objectweb.asm.ClassWriter;
import jdk.internal.org.objectweb.asm.Opcodes;

/**
 * OOM举例
 *
 * @author lfeiyang
 * @since 2022-07-07 22:43
 */
public class OOMTest extends ClassLoader {
    public static void main(String[] args) {
        int j = 0;
        OOMTest test = new OOMTest();


        try {
            for (int i = 0; i < 100000; i++) {
                ClassWriter classWriter = new ClassWriter(0);
                //指明版本号，修饰符，类型，包名，父类，接口
                classWriter.visit(Opcodes.V1_8, Opcodes.ACC_PUBLIC, "Class" + i, "com.lydon.test", "java/lang/Object", null);
                byte[] code = classWriter.toByteArray();
                test.defineClass("Class" + i, code, 0, code.length);
                j++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println(j);
        }
    }
}
