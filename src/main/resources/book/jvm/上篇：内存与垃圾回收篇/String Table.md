# <font face=幼圆 color=white>String Table</font>

## <font face=幼圆 color=white>一、String的基本特性</font>

1. <font face=幼圆 color=white>String：字符串，使用一对 "" 引起来表示</font>

   - <font face=幼圆 color=white>String s1 = "字符串定义方式1"; // 字面量的定义方式</font>

   - <font face=幼圆 color=white>String s2 = new String("字符串定义方式2"); // 对象方式定义</font>

2. <font face=幼圆 color=white>String 声明为 final 的，不可被继承</font>
   - <font face=幼圆 color=white>*public final class String implements java.io.Serializable, Comparable<String>, CharSequence*</font>
3. <font face=幼圆 color=white>String 实现了 Serializable 接口：表示字符串是支持序列化的</font>
4. <font face=幼圆 color=white>String 实现了 Comparable 接口：表示 String 可以比较大小</font>
5. <font face=幼圆 color=white>String 在 JDK8 及以前，内部定义为 final char[] value 用于存储字符串数据</font>
   - <font face=幼圆 color=white>*private final char value[];*</font>

6. <font face=幼圆 color=white>String 在 JDK9 时改为 byte[] 用于存储字符串数据</font>

```java
@Stable
private final byte[] value;
```



## <font face=幼圆 color=white>二、为什么JDK9改变了结构</font>

![String存储结构变更](D:\project\springboot_003\src\main\resources\book\jvm\上篇：内存与垃圾回收篇\image\String存储结构变更01.png)



![String存储结构变更](D:\project\springboot_003\src\main\resources\book\jvm\上篇：内存与垃圾回收篇\image\String存储结构变更02.png)



## <font face=幼圆 color=white>三、String的不可变性</font>

<font face=幼圆 color=white>String：代表不可变的字符序列。简称：不可变性</font>

1. <font face=幼圆 color=white>当对字符串重新赋值时，需要重写指定内存区域赋值，不能使用原有的 value 进行赋值</font>
2. <font face=幼圆 color=white>当对现有的字符串进行连接操作时，也需要重新指定内存区域赋值，不能使用原有的 value 进行赋值</font>
3. <font face=幼圆 color=white>当调用 String 的 replace() 方法修改指定字符或字符串时，也需要重新指定内存区域赋值，不能使用原有的 value 进行赋值</font>
4. <font face=幼圆 color=white>通过字面量的方式（区别于new）给一个字符串赋值，此时的字符串值声明在字符串常量池中</font>



## <font face=幼圆 color=white>四、String Pool 底层 Hashtable 结构的说明</font>

1. <font face=幼圆 color=white>字符串常量池是不会存储相同内容的字符串的</font>
2. <font face=幼圆 color=white>String 的 String Pool是一个固定大小的 Hashtable，默认值大小长度是1009。如果放进 String Pool 的 String非常多，就会造成 Hash 冲突严重，从而导致链表会很长，而链表长了后直接会造成的影响就是当调用string.intern时性能会大幅下降</font>
3. <font face=幼圆 color=white>使用 -XX:StringTableSize 可设置 StringTable 的长度</font>
4. <font face=幼圆 color=white>在 JDK6 中 StringTable是固定的，就是1009的长度，所以如果常量池中的字符串过多就会导致效率下降很快。StringTablesize 设置没有要求</font>
5. <font face=幼圆 color=white>在 JDK7 中，StringTable 的长度默认值是 60013</font>
6. <font face=幼圆 color=white>在 JDK8 中，StringTable 可以设置的最小值为1009</font>



## <font face=幼圆 color=white>五、String内存结构分配位置</font>

1. <font face=幼圆 color=white>在Java语言中有8种基本数据类型和一种比较特殊的类型String。这些类型为了使它们在运行过程中速度更快、更节省内存，都提供了一种常量池的概念</font>
2. <font face=幼圆 color=white>常量池就类似一个Java系统级别提供的缓存。8种基本数据类型的常量池都是系统协调的，String类型的常量池比较特殊，存储的主要方法有两种：</font>
- <font face=幼圆 color=white> 直接使用双引号声明出来的String对象会直接存储在常量池中。</font>
  
- <font face=幼圆 color=white>**如果不是用双引号声明的String对象，可以使用String提供的intern()方法。**</font>
3. <font face=幼圆 color=white>在JDK 7以后，内部字符串**不再分配在Java堆的永久代中（永久代垃圾回收频率低）**，**而是分配在Java堆的主要部分（年轻代和老年代）**，所有的字符串都保存在堆中，与应用程序创建的其他对象放在一起。这种变化将导致更多的数据驻留在主Java堆中，而更少的数据在永久代中，因此可能需要调整堆的大小</font>
3. <font face=幼圆 color=white>**在加载许多类或大量使用String.intern()方法的大型应用程序中可以看到这个改动的明显效果。**</font>

![String内存分配](D:\project\springboot_003\src\main\resources\book\jvm\上篇：内存与垃圾回收篇\image\String内存分配.png)

### <font face=幼圆 color=white>5.1、**[为什么`StringTable`要从永久代放到堆区？](https://blog.csdn.net/u011069294/article/details/107492791)**</font>

1. <font face=幼圆 color=white>permSize默认比较小</font>
2. <font face=幼圆 color=white>永久代回收频率低</font>



## <font face=幼圆 color=white>六、String字符串拼接</font>

1. <font face=幼圆 color=white>**常量与常量的拼接结果在常量池，原理是编译期优化**</font>
2. <font face=幼圆 color=white>**常量池中不会存在相同内容的变量**</font>
3. <font face=幼圆 color=white>**只要其中有一个是变量，结果就在堆中。变量拼接的原理是StringBuilder**</font>
4. <font face=幼圆 color=white>**如果拼接的结果调用intern()方法，则主动将常量池中还没有的字符串对象放入池中，并返回此对象地址**</font>



### <font face=幼圆 color=white>6.1、String拼接性能比较</font>

1. <font face=幼圆 color=white>**结果：** 通过StringBuilder的append()的方式添加字符串的效率要远高于使用Sting的字符串拼接方式</font>
2. <font face=幼圆 color=white>**详情：** StringBuilder的append()方式：自始至终只创建过一个StringBuilder的对象，使用String的字符串拼接方式，执行过程中会创建多个StringBuilder和String对象，内存占用较大，如果进行GC，还需要花费额外的时间</font>



## <font face=幼圆 color=white>七、intern()的作用</font>

​		<font face=幼圆 color=white>**将字符串对象尝试放入串池，首先判断字符串常量池中是否存在对应的字符串值，如果存在则返回常量池中字符串的地址，如果不存在，则在常量池中添加该字符串，并返回对应的地址**</font>

### <font face=幼圆 color=white>7.1、new String()创建了几个对象</font>

1. <font face=幼圆 color=white>创建了两个对象：堆空间中一个new对象 ，字符串常量池中一个字符串常量"ab"（如果此时字符串常量池中已有该常量则不会创建）</font>
2. <font face=幼圆 color=white>字节码指令ldc在常量池创建对象</font>
