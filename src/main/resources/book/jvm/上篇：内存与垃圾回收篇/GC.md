# <font face=幼圆 color=white>GC</font>

## <font face=幼圆 color=white>一、什么是垃圾？</font>

1. <font face=幼圆 color=white>垃圾是指在运行程序中没有任何指针指向的对象，这个对象就是需要被回收的垃圾</font>
2. <font face=幼圆 color=white>如果不及时对内存中的垃圾进行清理，那么，这些垃圾对象所占的内存空间会一直保留到应用程序结束，被保留的空间无法被其他对象使用。甚至可能导致内存溢出</font>



## <font face=幼圆 color=white>二、[Java垃圾回收](https://docs.oracle.com/javase/8/docs/technotes/guides/vm/gctuning/toc.html)</font>

- [x] <font face=幼圆 color=white>方法区</font>
- [x] <font face=幼圆 color=white>堆区</font>
  1. <font face=幼圆 color=white>Java堆是垃圾收集的重点位置</font>
  2. <font face=幼圆 color=white>频繁收集年轻代</font>
  3. <font face=幼圆 color=white>较少收集老年代</font>
  4. <font face=幼圆 color=white>基本不懂动永久代</font>



## <font face=幼圆 color=white>三、垃圾回收算法</font>

### <font face=幼圆 color=white>3.1、垃圾标记阶段</font>

#### <font face=幼圆 color=white>3.1.1、引用计数算法</font>

1. <font face=幼圆 color=white>引用计数法（Reference Counting）比较简单，对每一个对象保存一个整型的引用计数器属性。用于记录对象被引用的情况</font>
   - <font face=幼圆 color=white>对于一个对象A，只要有任何一个对象引用了A，则A的引用计数器就加1；当引用失效时，引用计数器就减1。只要对象的引用计数器的值为0，即表示对象A不能在被使用，可进行回收</font>


2. <font face=幼圆 color=white>优点：实现简单，垃圾对象便于辨识；判定效率高，回收没有延迟性</font>

3. <font face=幼圆 color=white>缺点：</font>

   - <font face=幼圆 color=white>他需要单独的字段存储计数器，这样的做法增加了存储空间的开销</font>

   - <font face=幼圆 color=white>每次赋值都需要更新计数器，伴随着加法和减法操作，这增加了时间开销</font>

   - <font face=幼圆 color=white>引用计数器还有一个严重的问题，即无法处理循环引用的问题，这是一条致命的缺陷，导致在Java回收的垃圾回收器</font>


![循环引用](D:\project\springboot_003\src\main\resources\book\jvm\上篇：内存与垃圾回收篇\image\循环引用.png)



#### <font face=幼圆 color=white>3.1.2、可达性分析算法</font>

​		<font face=幼圆 color=white>当前主流的商用程序语言（Java、C#，上溯至前面提到的古老的Lisp）的内存管理子系统，都是通过可达性分析（Reachability Analysis）算法来判定对象是否存活的</font>	

![可达性分析](D:\project\springboot_003\src\main\resources\book\jvm\上篇：内存与垃圾回收篇\image\可达性分析.png)

##### <font face=幼圆 color=white>3.1.2.1、GC Roots元素</font>

1. <font face=幼圆 color=white>在`虚拟机栈`（栈帧中的本地变量表）中引用的对象，譬如各个线程被调用的方法堆栈中使用到的参数、局部变量、临时变量等关联记忆：例如局部变量存在栈中，当方法执行完后，所有的局部变量就会弹出栈，就会进行回收。反之，在栈内的话，就不可回收</font>

2. <font face=幼圆 color=white>在方法区中类`静态属性`引用的对象，譬如Java类的引用类型静态变量。关联记忆：静态属性生命期和jvm一致，等价于永远存在</font>

3. <font face=幼圆 color=white>在方法区中`常量`引用的对象，譬如字符串常量池（String Table）里的引用</font>

4. <font face=幼圆 color=white>在本地方法栈中JNI（即通常所说的Native方法）引用的对象</font>

5. <font face=幼圆 color=white>Java虚拟机内部的引用，如基本数据类型对应的Class对象，一些常驻的异常对象（比如NullPointExcepiton、OutOfMemoryError）等，还有系统类加载器</font>

6. <font face=幼圆 color=white>所有被`同步锁`（synchronized关键字）持有的对象</font>

7. <font face=幼圆 color=white>反映Java虚拟机内部情况的JMXBean、JVMTI中注册的回调、本地代码缓存等</font>



### <font face=幼圆 color=white>3.2、对象Finalization机制</font>

1. <font face=幼圆 color=white>Java 语言提供了对象终止（finalization）机制来允许开发人员提供对象被销毁之前的自定义处理逻辑</font>
2. <font face=幼圆 color=white>当垃圾回收器发现没有引用指向一个对象，即：垃圾回收此对象之前，总会先调用这个对象的 finalize() 方法</font>
3. <font face=幼圆 color=white>finalize() 方法允许在子类中被重写，用于在对象被回收时进行资源释放。通常在这个方法中进行一些资源释放和清理的工作，比如关闭文件、套接字和数据库连接等</font>
4. <font face=幼圆 color=white>从功能上来说，finalize() 方法与 c++ 中的析构函数比较相似，但是 Java 采用的是基于垃圾回收器的自动内存管理机制，所以 finalize() 方法在本质上不同于C++中的析构函数</font>

#### <font face=幼圆 color=white>3.2.1、注意</font>

<font face=幼圆 color=white>永远不要主动调用某个对象的 finalize()方法，应该交给垃圾回收机制调用。理由包括下面三点：</font>

1. <font face=幼圆 color=white>在 finalize() 时可能会导致对象复活</font>
2. <font face=幼圆 color=white>finalize() 方法的执行时间是没有保障的，它完全由 GC线程决定，极端情况下，若不发生 GC，则 finalize() 方法将没有执行机会。因为优先级比较低，即使主动调用该方法，也不会因此就直接进行回收</font>
3. <font face=幼圆 color=white>一个糟糕的 finalize() 会严重影响 GC 的性能</font>



#### <font face=幼圆 color=white>3.2.2、对象是生存还是死亡？</font>

<font face=幼圆 color=white>由于 finalize() 方法的存在，虚拟机中的对象一般处于三种可能的状态</font></font>

<font face=幼圆 color=white>如果从所有的根节点都无法访问到某个对象，说明对象己经不再使用了。一般来说，此对象需要被回收。但事实上，也并非是“非死不可”的，这时候它们暂时处于“缓刑”阶段。一个无法触及的对象有可能在某一个条件下“复活”自己，如果这样，那么对它的回收就是不合理的，为此，定义虚拟机中的对象可能的三种状态</font>

1. <font face=幼圆 color=white>可触及的：从根节点开始，可以到达这个对象</font>
2. <font face=幼圆 color=white>可复活的：对象的所有引用都被释放，但是对象有可能在 finalize() 中复活</font>
3. <font face=幼圆 color=white>不可触及的：对象的 finalize() 被调用，并且没有复活，那么就会进入不可触及状态。不可触及的对象不可能被复活，因为 finalize() 只会被调用一次</font>

<font face=幼圆 color=white>以上3种状态中，是由于 finalize() 方法的存在而进行的区分。只有在对象不可触及时才可以被回收</font>



#### <font face=幼圆 color=white>3.2.3、具体过程</font>

<font face=幼圆 color=white>判定一个对象 objA 是否可回收，至少要经历两次标记过程：</font>

1. <font face=幼圆 color=white>如果对象 objA 到 GC Roots 没有引用链，则进行第一次标记</font>

2. <font face=幼圆 color=white>进行筛选，判断此对象是否有必要执行 finalize() 方法</font>

   

- [x]  <font face=幼圆 color=white>如果对象 objA 没有重写 finalize() 方法，或者 finalize() 方法已经被虚拟机调用过，则虚拟机视为“没有必要执行”，objA 被判定为不可触及的</font>
- [x]  <font face=幼圆 color=white>如果对象 objA 重写了 finalize() 方法，且还未执行过，那么 objA 会被插入到 F-Queue 队列中，由一个虚拟机自动创建的、低优先级的 Finalizer 线程触发其 finalize() 方法执行</font>
- [x]  <font face=幼圆 color=white>finalize() 方法是对象逃脱死亡的最后机会，稍后 GC 会对 F-Queue 队列中的对象进行第二次标记。如果 objA 在 finalize() 方法中与引用链上的任何一个对象建立了联系，那么在第二次标记时，objA 会被移出“即将回收”集合。之后，对象会再次出现没有引用存在的情况。在这个情况下，finalize() 方法不会被再次调用，对象会直接变成不可触及的状态，也就是说，一个对象的 finalize() 方法只会被调用一次</font>

![Finalization](D:\project\springboot_003\src\main\resources\book\jvm\上篇：内存与垃圾回收篇\image\Finalization.png)

<font face=幼圆 color=yellow>JVisual VM记得以管理员身份运行,否则不支持JVM</font>



### <font face=幼圆 color=white>3.3、标记清除算法、标记整理算法和复制算法比较</font>

|              | Mark-Sweep | Mark-Compact | Copying |
| ------------ | ---------- | ------------ | ------- |
| 内存是否整齐 | 否         | 整齐         | 整齐    |
| 空间开销     | 小         | 小           | 大      |
| 速度         | 中等       | 慢           | 快      |



### <font face=幼圆 color=white>3.4、分代收集算法 -- 最优算法</font>

#### <font face=幼圆 color=white>3.4.1、对象分类</font>

- <font face=幼圆 color=white>新生代：朝生夕灭的对象（例如：方法的局部变量引用的对象等）</font>
- <font face=幼圆 color=white>老年代：存活得比较久，但还是要死的对象（例如：缓存对象、单例对象等）</font>
- <font face=幼圆 color=white>永久代：对象生成后几乎不灭的对象（例如：加载过的类信息）</font>



#### <font face=幼圆 color=white>3.4.2、内存区域</font>

​		<font face=幼圆 color=white>**新生代和老年代都在java堆，永久代在方法区**</font>

![新生代和老年代的内存比例](D:\project\springboot_003\src\main\resources\book\jvm\上篇：内存与垃圾回收篇\image\新生代和老年代的内存比例.png)



#### <font face=幼圆 color=white>3.4.3、分代收集算法</font>

- <font face=幼圆 color=white>年轻代（Young Gen）采用复制算法</font>
- <font face=幼圆 color=white>老年代（Tenured Gen）采用标记-清除和标记-整理的混合实现</font>
  1. <font face=幼圆 color=white>Mark阶段的开销与存活对象的数量成正比</font>
  2. <font face=幼圆 color=white>Sweep阶段的开销与所管理区域的大小成正比</font>
  3. <font face=幼圆 color=white>Compact阶段的开销与存活对象的数据成正比</font>



### <font face=幼圆 color=white>3.5 增量收集算法</font>

1. <font face=幼圆 color=white>在现有的收集算法中，每次垃圾回收，应用程序都会处于一种Stop the World的状态，这种状态下，应用程序会被挂起，暂停一切正常的工作。这样一来，将严重影响用户体验或者系统稳定性</font>
2. <font face=幼圆 color=white>基本思想：如果一次性将所有的垃圾进行处理，需要造成系统长时间的停顿，那么就可以让垃圾收集线程和应用程序线程交替进行。每次，垃圾回收线程只收集一小片区域的内存空间，接着切换到用户线程继续执行。依次反复，知道垃圾收集完成</font>

<font face=幼圆 color=red>造成系统吞吐量的下降</font>



## <font face=幼圆 color=white>四、垃圾回收相关概念</font>

### <font face=幼圆 color=white>4.1、stop the world</font>

1. <font face=幼圆 color=white>分析工作必须在一个能确保一致性的快照中进行</font>
2. <font face=幼圆 color=white>一致性指整个分析期间整个执行系统看起来像被冻结在某个时间点上</font>
3. <font face=幼圆 color=white>如果出现分析过程中对象引用关系还在不断变化，则分析结果的准确性无法保证</font>
4. <font face=幼圆 color=white>被STW中断的应用程序线程会在完成GC之后恢复，频繁中断会让用户感觉像是网速不快造成卡带一样，所以我们需要减少STW的发生</font>
5. <font face=幼圆 color=white>STW和采用哪款GC无关，所有的GC都有这个事件</font>
6. <font face=幼圆 color=white>哪怕是G1也不能完全避免Stop-The-World情况的发生，只能说垃圾回收器越来越优秀，回收效率越来越高，尽可能地缩短了暂停时间</font>
7. <font face=幼圆 color=white>STW是JVM在后台自动发起和自动完成的。在用户不可见的情况下，把用户正常工作的线程全部停掉</font>
8. <font face=幼圆 color=white>开发中不要用System.gc(),会导致Stop-The-World的发生</font>



### <font face=幼圆 color=white>4.2、并行与并发</font>

1. <font face=幼圆 color=white>并发：指的是多个事情，在同一时间段内同时发生了</font>
2. <font face=幼圆 color=white>并行：指的是多个事情，在同一时间点上同时发生了</font>
3. <font face=幼圆 color=white>并发的多个任务之间是互相抢占资源的</font>
4. <font face=幼圆 color=white>并行的多个任务之间是不互相抢占资源的</font>
5. <font face=幼圆 color=white>只有在多 CPU 或者一个 CPU 多核的情况中，才会发生并行</font>
6. <font face=幼圆 color=white>否则，看似同时发生的事情，其实都是并发执行的</font>



### <font face=幼圆 color=white>4.3、安全点与安全区域</font>
