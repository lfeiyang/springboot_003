package com.sy.java.gc;

/**
 * 测试 Object 类中 finalize() 方法，即对象的 finalization 机制
 *
 * @author lfeiyang
 * @since 2022-07-24 13:49
 */
public class CanReliveObj {
    public static CanReliveObj OBJ; // 类变量，属于 GC Root

    // 此方法只能被调用一次
    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        System.out.println("调用当前类重写的finalize()方法");
        OBJ = this; // 当前待回收的对象在 finalize() 方法中与引用链上的一个对象 obj 建立了联系，对象复活了
    }

    public static void main(String[] args) {
        try {
            OBJ = new CanReliveObj();
            // 对象第一次成功拯救自己
            OBJ = null;
            System.gc(); // 调用垃圾回收器
            System.out.println("第1次 gc");
            // 因为 Finalizer 线程优先级很低，暂停 2 秒，以等待它
            Thread.sleep(2000);
            if (OBJ == null) {
                System.out.println("obj is dead");
            } else {
                System.out.println("obj is still alive");
            }
            System.out.println("第2次 gc");
            // 下面这段代码与上面的完全相同，但是这次自救却失败了，因为一个对象的 finalize() 方法只会被调用一次
            OBJ = null;
            System.gc();
            // 因为 Finalizer 线程优先级很低，暂停2秒，以等待它
            Thread.sleep(2000);
            if (OBJ == null) {
                System.out.println("obj is dead");
            } else {
                System.out.println("obj is still alive");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
