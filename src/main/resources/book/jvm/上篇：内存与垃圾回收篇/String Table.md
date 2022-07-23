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
