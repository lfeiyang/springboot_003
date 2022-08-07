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



### <font face=幼圆 color=white>4.4、强引用、软引用、弱引用和虚引用</font>

| 引用类型 | 被垃圾回收时间 | 用途               | 生存时间           |
| -------- | -------------- | ------------------ | ------------------ |
| 强引用   | 从来不会       | 对象的一般状态     | JVM 停止运行时终止 |
| 软引用   | 当内存不足时   | 对象缓存           | 内存不足时终止     |
| 弱引用   | 正常垃圾回收时 | 对象缓存           | 垃圾回收后终止     |
| 虚引用   | 正常垃圾回收时 | 跟踪对象的垃圾回收 | 垃圾回收后终止     |



## <font face=幼圆 color=white>五、垃圾收集器</font>

### <font face=幼圆 color=white>5.1、垃圾收集器组合关系</font>

![垃圾收集器组合关系](D:\project\springboot_003\src\main\resources\book\jvm\上篇：内存与垃圾回收篇\image\垃圾收集器组合关系.png)

1. <font face=幼圆 color=white>两个收集器间有连线，表明它们可以搭配使用（7中搭配）：Serial/Serial old、Serial/CMS、ParNew/Serial old、ParNew/CMS、Parallel Scavenge/Serial 0ld、Parallel Scavenge/Parallel 01d、G1</font>
2. <font face=幼圆 color=white>其中 Serial old 作为 CMS出现 "Concurrent Mode Failure" 失败的后备预案。</font>
3. <font face=幼圆 color=white>红色虚线：由于维护和兼容性测试的成本，在 JDK 8 时将 Serial + CMS、ParNew + Serial old 这两个组合声明为废弃（JEP173），并在 JDK9 中完全取消了这些组合的支持（JEP214），即：移除。</font>
4. <font face=幼圆 color=white>绿色虚线：JDK14 中：弃用 Parallel Scavenge 和 Serialold GC 组合（JEP366）。</font>
5. <font face=幼圆 color=white>青色虚线：JDK14 中：删除 CMS 垃圾回收器（JEP363）。</font>

​		<font face=幼圆 color=white>为什么要有如此之多的收集器，一个不够吗？因为 Java 的使用场景很多，移动端，服务器等。所以就需要针对不同的场景，提供不同的垃圾收集器，提高垃圾收集的性能</font>

​		<font face=幼圆 color=white>对各个收集器进行比较，并没有一种放之四海皆准、任何场景下都适用的完美收集器存在，更加没有万能的收集器。所以我们选择的只是对具体应用最合适的收集器</font>



### <font face=幼圆 color=white>5.2、如何查看默认垃圾收集器</font>

<font face=幼圆 color=white>-XX:+PrintcommandLineFlags：查看命令行相关参数（包含使用的垃圾收集器）</font>

<font face=幼圆 color=white>使用命令行指令：jinfo -flag 相关垃圾回收器参数 进程ID</font>



### <font face=幼圆 color=white>5.3、垃圾回收器</font>

#### <font face=幼圆 color=white>5.3.1、G1</font>

##### <font face=幼圆 color=white>5.3.1.1、什么是G1垃圾收集器?</font>

1. <font face=幼圆 color=white>G1(Garbage-First)是一款面向服务端应用的垃圾收集器,主要针对配备多核CPU及大容量内存的机器,**以极高概率满足GC停顿时间的同时**,还**兼具高吞吐量的性能特征**</font>
2. <font face=幼圆 color=white>在JDK1.7版本正式启用,是**JDK 9以后的默认垃圾收集器,取代了CMS 回收器**</font>

##### <font face=幼圆 color=white>5.3.1.2、为什么名字叫Garbage First?</font>

1.  <font face=幼圆 color=white>G1是一个并行回收器,它**把堆内存分割为很多不相关的区域(region物理上不连续),把堆分为2048个区域**,每一个region的大小是1 - 32M不等,必须是**2的整数次幂**。使用不同的region可以来表示Eden、幸存者0区、幸存者1区、老年代等</font>
2. <font face=幼圆 color=white>每次根据允许的收集时间,**优先回收价值最大的Region**
   (每次回收完以后都有一个空闲的region,在后台维护一个优先列表)</font>
3. <font face=幼圆 color=white>由于这种方式的侧重点在于回收垃圾最大量的区间(Region),所以我们给G1一个名字:垃圾优先(Garbage First) </font>
4. <font face=幼圆 color=white>下面说一个问题:**既然我们已经有了前面几个强大的GC,为什么还要发布Garbage First(G1)GC？**</font>
   - <font face=幼圆 color=white>**官方给G1设定的目标是在延迟可控的情况下获得尽可能高的吞吐量,所以才担当起"全功能收集器"的重任与期望。**</font>

##### <font face=幼圆 color=white>5.3.1.3、G1垃圾回收的优势与不足</font>

1. <font face=幼圆 color=white>**并行和并发**</font>

   - <font face=幼圆 color=white>并行性: G1在回收期间,可以有多个GC线程同时工作,有效利用多核计算能力。此时用户线程STW</font>

   - <font face=幼圆 color=white>并发性: G1拥有与应用程序交替执行的能力,部分工作可以和应用程序同时执行,因此,一般来说,不会在整个回收阶段发生完全阻塞应用程序的情况</font>

2. <font face=幼圆 color=white>**分代收集**</font>

   - <font face=幼圆 color=white>从分代上看,**G1依然属于分代型垃圾回收器**,它会区分年轻代和老年代,年轻代依然有Eden区和Survivor区。**但从堆的结构上看,它不要求整个Eden区、年轻代或者老年代都是连续的**,也**不再坚持固定大小和固定数量**</font>

   - <font face=幼圆 color=white>将堆空间分为若干个区域(Region),这些区域中包含了逻辑上的年轻代和老年代。</font>

   - <font face=幼圆 color=white>和之前的各类回收器不同,它**同时兼顾年轻代和老年代**。对比其他回收器,或者工作在年轻代,或者工作在老年代</font>

![G1分代收集](D:\project\springboot_003\src\main\resources\book\jvm\上篇：内存与垃圾回收篇\image\G1分代收集.png)

3. <font face=幼圆 color=white>**空间整合**</font>
   - <font face=幼圆 color=white>G1将内存划分为一个个的region。 内存的回收是**以region作为基本单位的**。Region之间是复制算法,但整体上实际可看作是**标记一压缩(Mark一Compact)算法**,两种算法都可以避免内存碎片。这种特性有利于程序长时间运行,分配大对象时不会因为无法找到连续内存空间而提前触发下一次GC。尤其是当Java堆非常大的时候,G1的优势更加明显)</font>

4. <font face=幼圆 color=white>**可预测的停顿时间模型(即:软实时soft real一time)**</font>

​		<font face=幼圆 color=white>这是 G1 相对于 CMS 的另一大优势,G1除了追求低停顿外,还能建立**可预测的停顿时间模型**,能让使用者明确**指定在一个长度为 M 毫秒的时间片段内,消耗在垃圾收集上的时间不得超过 N 毫秒**、可以通过参数**-XX:MaxGCPauseMillis**进行设置)</font>

- <font face=幼圆 color=white>由于分区的原因,G1可以只选取部分区域进行内存回收,这样缩小了回收的范围,因此对于全局停顿情况的发生也能得到较好的控制</font>
- <font face=幼圆 color=white>G1 跟踪各个 Region 里面的垃圾堆积的价值大小(回收所获得的空间大小以及回收所需时间的经验值),在后台维护一个优先列表,**每次根据允许的收集时间,优先回收价值最大的Region。保证了G1收集器在有限的时间内可以获取尽可能高的收集效率**</font>
- <font face=幼圆 color=white>相比于CMS GC,G1未必能做到CMS在最好情况下的延时停顿,但是最差情况要好很多</font>

##### <font face=幼圆 color=white>5.3.1.4、G1参数设置</font>

1. <font face=幼圆 color=white>**-XX:+UseG1GC:**手动指定使用G1收集器执行内存回收任务（JDK9后不用设置，默认就是G1）</font>
2. <font face=幼圆 color=white>-XX:G1HeapRegionSize:设置每个Region的大小。值是2的幂,范围是1MB到32MB之间,目标是根据最小的Java堆大小划分出约2048个区域。默认是堆内存的1/2000</font>
3. <font face=幼圆 color=white>-XX:MaxGCPauseMillis:设置期望达到的最大GC停顿时间指标(JVM会尽力实现,但不保证达到)。默认值是200ms（如果这个值设置很小,如20ms,那么它收集的region会少,这样长时间后,堆内存会满。产生FullGC,FullGC会出现STW,反而影响用户体验)</font>
4. <font face=幼圆 color=white>-XX:ParallelGCThread:设置STW时GC线程数的值。最多设置为8(垃圾回收线程)</font>
5. <font face=幼圆 color=white>-XX:ConcGCThreads:设置并发标记的线程数。将n设置为并行垃圾回收线程数(ParallelGCThreads)的1/4左右</font>
6. <font face=幼圆 color=white> **-XX:InitiatingHeapOccupancyPercent:**设置触发并发GC周期的Java堆占用率阈值。**超过此值,就触发GC**。默认值是45</font>

##### <font face=幼圆 color=white>5.3.1.4、G1应用场景</font>

1. <font face=幼圆 color=white><font face=幼圆 color=red>**面向服务端应用，针对具有大内存、多处理器的机器**。</font>(在普通大小的堆里表现并不惊喜)，最主要的应用是需要低GC延迟，并具有大堆的应用程序提供解决方如:在堆大小约6GB或更大时，可预测的暂停时间可以低于0.5秒; ( G1通过每次只清理一 部分而不是全部的Region的增量式清理来保证每次GC停顿时间不会过长)</font>

2. <font face=幼圆 color=yellow>**用来替换掉JDK1.5中的CMS收集器**</font>

    <font face=幼圆 color=white>在下面的情况时，使用G1可能比CMS好:</font>
      <font face=幼圆 color=white> ①超过50%的Java堆被活动数据占用;</font>
        <font face=幼圆 color=white>②对象分配频率或年代提升频率变化很大;</font>
      <font face=幼圆 color=white> ③GC停顿时间过长(长于0.5至1秒)。</font>

3. <font face=幼圆 color=white>HotSpot垃圾收集器里，除了G1以外，其他的垃圾收集器使用内置的JVM线程执行GC的多线程操作，而G1 GC可以采用应用线程承担后台运行的GC工作，即**当JVM的GC线程处理速度慢时，系统会调用应用程序线程帮助加速垃圾回收过程**</font>

##### <font face=幼圆 color=white>5.3.1.5、G1的主要回收环节</font>

<font face=幼圆 color=white>年轻代GC (Young GC)</font>

<font face=幼圆 color=white>老年代并发标记过程 (Concurrent Marking)</font>

<font face=幼圆 color=white>混合回收(Mixed GC)</font>

<font face=幼圆 color=orange>(如果需要，单线程、独占式、高强度的Full GC还是继续存在的。它
针对GC的评估失败提供了-种失败保护机制，即强力回收。)</font>

![G1垃圾回收过程](D:\project\springboot_003\src\main\resources\book\jvm\上篇：内存与垃圾回收篇\image\G1垃圾回收过程.png)

1. <font face=幼圆 color=white> 应用程序分配内存**,<font face=幼圆 color=red>当年轻代的Eden区用尽时开始年轻代回收过程；</font>**G1的年轻代收集阶段是一个<font face=幼圆 color=red>并行(多个垃圾线程)的独占式收集器</font>。在年轻代回收期,G1 GC暂停所有应用程序线程,启动多线程执行年轻代回收。然后<font face=幼圆 color=orange>从年轻代区间移动存活对象到Survivor区间或者老年区间,也有可能是两个区间都会涉及</font></font>
2. <font face=幼圆 color=white>**当堆内存使用达到一定值(默认45%)时,开始老年代并发标记过程**</font>
3. <font face=幼圆 color=white>  标记完成马上开始混合回收过程。对于一个混合回收期,G1 GC从老年区间移动存活对象到空闲区间,这些空闲区间也就成为了老年代的一部分。和年轻代不同,老年代的G1回收器和其他GC不同,<font face=幼圆 color=green>G1的老年代回收器不需要整个老年代被回收,一次只需要扫描/回收一小部分老年代的Region就可以了</font>。同时,这个<font face=幼圆 color=red>老年代Region是和年轻代一起被回收的。</font></font>
4. <font face=幼圆 color=white> 举个例子：一个Web服务器,Java进程最大堆内存为4G,每分钟响应1500个请求,每45秒钟会新分配大约2G的内存。G1会每45秒钟进行一次年轻代回收,每31个小时整个堆的使用率会达到45%,会开始老年代并发标记过程,标记完成后开始四到五次的混合回收</font>

##### <font face=幼圆 color=white>5.3.1.6、G1回收详解</font>

###### <font face=幼圆 color=white>5.3.1.6.1、回收过程一：Young GC</font>

<font face=幼圆 color=red>回收时机：</font>

1. <font face=幼圆 color=red>当Eden空间耗尽时,G1会启动一次年轻代垃圾回收过程</font>

2. <font face=幼圆 color=white>年轻代垃圾回收只会回收Eden区和Survivor区</font>

3. <font face=幼圆 color=white>回收前:</font>

   ![年轻代GC前](D:\project\springboot_003\src\main\resources\book\jvm\上篇：内存与垃圾回收篇\image\年轻代GC前.png)

4. <font face=幼圆 color=white>回收后:</font>

   ![年轻代GC后](D:\project\springboot_003\src\main\resources\book\jvm\上篇：内存与垃圾回收篇\image\年轻代GC后.png)

<font face=幼圆 color=red>回收机制：</font>

- [x] <font face=幼圆 color=white>**第一阶段，根扫描:**</font>

  - <font face=幼圆 color=white>一定要考虑**remembered Set**,看是否有老年代中的对象引用了新生代对象</font>
  - <font face=幼圆 color=white>根是指static变量指向的对象,正在执行的方法调用链条上的局部变量等。根引用连同RSet记录的外部引用作为扫描存活对象的入口)</font>

- [x] <font face=幼圆 color=white>**第二阶段，更新RSet:**</font>

  - <font face=幼圆 color=white>处理dirty card queue(见备注)中的card,更新RSet。 此阶段完成后,<font face=幼圆 color=orange>**RSet可以准确的反映老年代对所在的内存分段中对象的引用**</font></font>

  ​        <font face=幼圆 color=white><font face=幼圆 color=yellow>**dirty card queue**</font>: 对于应用程序的**引用赋值语句object.field=object,JVM会在之前和之     后执行特殊的操作以在dirty card queue中入队一个保存了对象引用信息的card**。在年轻代回     收的时候,G1会对Dirty CardQueue中所有的card进行处理,以更新RSet,保证RSet实时准确的     反映引用关系。那为什么不在引用赋值语句处直接更新RSet呢？这是为了性能的需要,**RSet的     处理需要线程同步,开销会很大,使用队列性能会好很多**</font>

- [x] <font face=幼圆 color=white>**第三阶段，处理RSet:**</font>

  - <font face=幼圆 color=white>识别<font face=幼圆 color=red>**被老年代对象指向的Eden中的对象**</font>,这些**被指向的Eden中的对象被认为是存活的对象**</font>

- [x] <font face=幼圆 color=white>**第四阶段，复制对象:**</font>

  - <font face=幼圆 color=white>此阶段,对象树被遍历,Eden区 内存段中<font face=幼圆 color=red>**存活的对象**会被**复制**到**Survivor区中空的内存分段**</font>,Survivor区内存段中<font face=幼圆 color=orange>**存活的对象如果年龄未达阈值**</font>,年龄会加1,<font face=幼圆 color=yellow>**达到阀值会被会被复制到old区中空的内存分段**</font>。如果<font face=幼圆 color=green>**Survivor空间不够,Eden空间的部分数据会直接晋升到老年代空间**</font></font>

- [x] <font face=幼圆 color=white>**第五阶段，处理引用:**</font>

  - <font face=幼圆 color=white>处理Soft,Weak, Phantom, Final, JNI Weak等引用。**最终Eden空间的数据为空,GC停止工作,而目标内存中的对象都是连续存储的,没有碎片,所以复制过程可以达到内存整理的效果,减少碎片**</font>



###### <font face=幼圆 color=white>5.3.1.6.2、回收过程二：老年代并发标记过程</font>

- [x] <font face=幼圆 color=white>**一、初始标记阶段:**</font>

  - <font face=幼圆 color=white>标记从根节点直接可达的对象。这个阶段是STW的,并且会触发一次年轻代GC</font>
- [x] <font face=幼圆 color=white>**二、根区域扫描(Root Region Scanning):** </font>

  - <font face=幼圆 color=white>G1 GC扫描Survivor区**直接可达的老年代区域对象,**并标记被引用的对象。**这一过程必须在young GC之前完成**(YoungGC时,会动Survivor区,所以这一过程必须在young GC之前完成)</font>
- [x] <font face=幼圆 color=white>**三、并发标记(Concurrent Marking):**</font>

  - <font face=幼圆 color=white>在整个堆中进行并发标记(和应用程序并发执行),此过程可能被young GC中断。在并发标记阶段,<font face=幼圆 color=red>**若发现区域对象中的所有对象都是垃圾,那这个区域会被立即回收**</font>。同时,<font face=幼圆 color=orange>**并发标记过程中,会计算每个区域的对象活性**</font>(区域中**存活对象的比例**)。</font>
- [x] <font face=幼圆 color=white>**四、再次标记(Remark):**</font>
- <font face=幼圆 color=white>由于应用程序持续进行,需要修正上一次的标记结果。**是STW的**。G1中采用了比CMS更快的初始快照算法:snapshot一at一the一beginning (SATB).</font>
- [x] <font face=幼圆 color=white>**五、独占清理(cleanup,STW):**</font>

  - <font face=幼圆 color=white>计算各个区域的存活对象和GC回收比例,并进行排序,识别可以混合回收的区域。为下阶段做铺垫。是STW的。(这个阶段并不会实际上去做垃圾的收集)</font>
- [x] <font face=幼圆 color=white>**六、并发清理阶段:**</font>
  - <font face=幼圆 color=white>识别并清理完全空闲的区域</font>



###### <font face=幼圆 color=white>5.3.1.6.3、回收过程三：混合回收 Mixed GC</font>

​		<font face=幼圆 color=white>**Mixed GC并不是FullGC**,<font face=幼圆 color=red>**老年代的堆占有率达到参数(-XX:InitiatingHeapOccupancyPercent)设定的值则触发**</font>,回收所有的Young和部分Old(根据期望的GC停顿时间确定old区垃圾收集的优先顺序)以及大对象区,正常情况G1的垃圾收集是先做MixedGC,**主要使用复制算法**,需要把各个region中存活的对象拷贝到别的region里去,<font face=幼圆 color=orange>**拷贝过程中如果发现没有足够的空region能够承载拷贝对象就会触发一次Full GC**</font></font>

![Mixed GC](D:\project\springboot_003\src\main\resources\book\jvm\上篇：内存与垃圾回收篇\image\Mixed GC.png)

- <font face=幼圆 color=white>并发标记结束以后，老年代中百分百为垃圾的内存分段被回收了，部分为垃圾的内存分段被计算了出来。默认情况下，这些老年代的内存分段会分8次(可以通过-XX:G1MixedGCCountTarget设置)被回收。</font>

- <font face=幼圆 color=white>混合回收的回收集(Collection Set)包括八分之一的老年代内存分段，Eden区 内存分段，Survivor区内存分段。 混合回收的算法和年轻代回收的算法完全一样，只是回收集多了老年代的内存分段。具体过程请参考上面的年轻代回收过程。</font>
- <font face=幼圆 color=white>由于老年代中的内存分段默认分8次回收，<font face=幼圆 color=red>**G1会优先回收垃圾多的内存分段**</font>。垃圾占内存分段比例越高的，越会被先回收。并且有一个**阈值**会决定内存分段是否被回收，<font face=幼圆 color=orange>**-XX:G1MixedGCLiveThresholdPercent，默认为65%**</font>，意思是垃圾占内存分段比例要达到65%才会被回收。如果垃圾占比太低，意味着存活的对象占比高，在复制的时候会花费更多的时间。</font>
- <font face=幼圆 color=white>混合回收并不一定 要进行8次。有一个阈值<font face=幼圆 color=yellow>**-XX :G1HeapWastePercent**,默认值为10%</font>，意思是允许整个堆内存中有10%的空间被浪费，意味着如果发现可以回收的垃圾占堆内存的比例低于10%，则不再进行混合回收。因为GC会花费很多的时间但是回收到的内存却很少。</font>



###### <font face=幼圆 color=white>5.3.1.6.4、回收过程四：Full GC</font>

- <font face=幼圆 color=white>G1的初衷就是要避免<font face=幼圆 color=red>**Fu1l GC**</font>的出现。但是如果上述方式不能正常工作，<font face=幼圆 color=red>**G1会停止应用程序的执行(Stop-The-World)** </font>，使用单线程的内存回收算法进行垃圾回收，性能会非常差，应用程序停顿时间会很长</font>
- <font face=幼圆 color=white>要避免Full GC的发生，一旦发生需要进行调整。什么时候会发生Full GC呢? <font face=幼圆 color=orange>比如**堆内存太小**，**当G1在复制存活对象的时候没有空的内存分段可用，则会回退到full gc**</font>， 这种情况可以通过增大内存解决</font>

- <font face=幼圆 color=red>**导致G1Full GC的原因可能有两个:** .</font>

   <font face=幼圆 color=#38d8f0> **1. 回收的时候没有足够的to-space来存放晋升的对象**</font>
   <font face=幼圆 color=#38d8f0> **2. 并发处理过程没完成空间就耗尽了**</font>



##### <font face=幼圆 color=white>5.3.1.7、G1回收器优化建议</font>

<font face=幼圆 color=white>**①.年轻代大小**</font>

- <font face=幼圆 color=white>避免使用-Xmn或-Xx :NeyvRatio等相关选项显式设置年轻代大小</font>
- <font face=幼圆 color=white>固定年轻代的大小会覆盖暂停时间目标</font>

<font face=幼圆 color=white>**②.暂停时间目标不要太过严苛**</font>

- <font face=幼圆 color=white>G1 GC的吞吐量目标是90%的应用程序时间和10%的垃圾回收时间</font>
- <font face=幼圆 color=white>评估G1 GC的吞吐量时，暂停时间目标不要太严苛。目标太过严苛表示你愿意承受更多的垃圾回收开销，而这些会直接影响到吞吐量。</font>



#### <font face=幼圆 color=white>5.3.2、CMS</font>

##### <font face=幼圆 color=white>5.3.2.1、什么是CMS?</font>

1. <font face=幼圆 color=white>**在JDK 1.5时期，HotSpot推出了一款在强交互应用中几乎可认为有划时代意义的垃圾收集器: CMS (Concurrent -Mark- Sweep)收集器，这款收集器是HotSpot虚拟机中第一款真正意义. 上的并发收集器，它第一次实现了让垃圾收集线程与用户线程同时工作。**</font>
2. <font face=幼圆 color=white>**CMS收集器的关注点是尽可能缩短垃圾收集时用户线程的停顿时间。停顿时间越短(低延迟)就越适合与用户交互的程序，良好的响应速度能提升用户体验。**目前很大一部分的Java应用集中在互联网站或者B/S系统的服务端上，这类应用尤其重视服务的响应速度，希望系统停顿时间最短，以给用户带来较好的体验。CMS收集器就非常符合这类应用的需求。 </font>
3. <font face=幼圆 color=white>**CMS的垃圾收集算法采用标记-清除算法，并且也会"Stop-the-world"**</font>

##### <font face=幼圆 color=white>5.3.2.2、回收阶段</font>

1. <font face=幼圆 color=white>初始标记(Initial-Mark) 阶段:在这个阶段中，程序中所有的工作线程都将会因为“Stop-the-World”机制而出现短暂的暂停，这个阶段的主要任务<font face=幼圆 color=red>**仅仅只是标记出GCRoots能直接关联到的对象。一旦标记完成之后就会恢复之前被暂停的所有应用线程。由于直接关联对象比较小，所以这里的速度非常快。**</font></font>
2. <font face=幼圆 color=white>并发标记(Concurrent-Mark) 阶段:<font face=幼圆 color=orange>从GC Roots的直接关联对象开始遍历整个对象图的过程</font>，这个过程耗时较长但是不需要停顿用户线程，可以与垃圾收集线程一起并发运行。</font>
3. <font face=幼圆 color=white>重新标记(Remark)阶段:由于在并发标记阶段中，程序的工作线程会和垃圾收集线程同时运行或者交叉运行，因此<font face=幼圆 color=yellow>为了修正并发标记期间，因用户程序继续运作而导致标记产生变动的那-部分对象的标记记录</font>，这个阶段的停顿时间通常会比初始标记阶段稍长一些，但也远比并发标记阶段的时间短</font>
4. <font face=幼圆 color=white>并发清除(Concurrent-Sweep)阶段:<font face=幼圆 color=green>**此阶段清理删除掉标记阶段判断的已经死亡的对象，释放内存空间**</font>。由于不需要移动存活对象，所以这个阶段也是可以与用户线程同时并发的</font>

![CMS](D:\project\springboot_003\src\main\resources\book\jvm\上篇：内存与垃圾回收篇\image\CMS.png)

##### <font face=幼圆 color=white>5.3.2.3、**CMS的优点**</font>

1. <font face=幼圆 color=white>并发收集</font>
2. <font face=幼圆 color=white>低延迟</font>

##### <font face=幼圆 color=white>5.3.2.4、**CMS的弊端**</font>

1. <font face=幼圆 color=white>**会产生内存碎片，导致并发清除后，用户线程可用的空间不足。在无法分配大对象的情况下，不得不提前触发Full GC**</font>
2. <font face=幼圆 color=white>**CMS收集器对CPU资源非常敏感。在并发阶段，它虽然不会导致用户停顿，但是会因为占用了一部分线程而导致应用程序变慢，总吞吐量会降低**</font>
3. <font face=幼圆 color=white>**CMS收集器无法处理浮动垃圾。可能出现“Concurrent Mode Failure"失败而导致另一次Full GC的产生。在并发标记阶段由于程序的工作线程和垃圾收集线程是同时运行或者交叉运行的，那么在并发标记阶段如果产生新的垃圾对象，CMS将无法对这些垃圾对象进行标记，最终会导致这些新产生的垃圾对象没有被及时回收，从而只能在下一次执行GC时释放这些之前未被回收的内存空间**</font>

##### <font face=幼圆 color=white>5.3.2.5、**CMS参数设置**</font>

1. <font face=幼圆 color=white>**- XX: +UseConcMarkSweepGC** **手动指定使用CMS收集器执行内存回收任务**</font>
   - <font face=幼圆 color=red>**开启该参数后会自动将-XX: +UseParNewGC打开。即: ParNew (Young区用) +CMS (0ld区用) +Serial 0ld的组合。**</font>

2. <font face=幼圆 color=white>**-XX:CMSlnitiatingOccupanyFraction设置堆内存使用率的阈值，一旦达到该阈值，便开始进行回收**</font>
   - <font face=幼圆 color=red>**JDK5及以前版本的默认值为68,即当老年代的空间使用率达到68%时，会执行一次CMS回收。JDK6及以上版本默认值为92%**</font>
   - <font face=幼圆 color=white>如果内存增长缓慢，则可以设置一个稍大的值，大的阈值可以有效降低CMS的触发频率，减少老年代回收的次数可以较为明显地改善应用程序性能。反之，如果应用程序内存使用率增长很快，则应该降低这个阈值，以避免频繁触发老年代串行收集器（Serial）。<font face=幼圆 color=orange>**因此通过该选项便可以有效降低Full GC的执行次数**</font></font>

3. <font face=幼圆 color=white>**-xx: +UseCMSCompactAtFullCollection** 用于指定在执行完Full GC后对内存空间进行压缩整理，以此避免内存碎片的产生。不过由于内存压缩整理过程无法并发执行，所带来的问题就是停顿时间变得更长了</font>

4. <font face=幼圆 color=white>**-XX: CMSFullGCsBeforeCompaction 设置在执行多少次Full GC后对内存空间进行压缩整理**</font>

5. <font face=幼圆 color=white>**-xx: ParallelCMSThreads 设置CMS的线程数量**</font>

   ​		<font face=幼圆 color=white>CMS默认启动的线程数是(ParallelGCThreads+3) /4,ParallelGCThreads是年轻代并行收集器的线程数。当CPU资源比较紧张时，受到CMS收集器线程的影响，应用程序的性能在垃圾回收阶段可能会非常糟糕</font>



#### <font face=幼圆 color=white>5.3.3、Parallel Scavenge</font>

1. <font face=幼圆 color=white> **HotSpot的年轻代中除了拥有ParNew收集器是基于<font face=幼圆 color=yellow>并行</font>回收的以外，Parallel Scavenge收集器同样也采用了<font face=幼圆 color=red>复制算法</font>、并行回收和"<font face=幼圆 color=orange>Stopthe World</font>"机制。**</font>

2. <font face=幼圆 color=white>**那么Parallel收集器的出现是否多此一举?**</font>

   -    <font face=幼圆 color=white>**和ParNew收集器不同，Parallel Scavenge收集器的目标则是达到一个可控制的吞吐量(Throughput) ，它也被称为<font face=幼圆 color=purple>吞吐量优先</font>的垃圾收集器**</font>


   -    <font face=幼圆 color=white>**自适应调节策略也是Parallel Scavenge与ParNew一个重要区别。**</font>



3. <font face=幼圆 color=white>**高吞吐量则可以高效率地利用CPU时间尽快完成程序的运算任务，主要适合在后台运算而不需要太多交互的任务。因此，常见在服务器环境中使用。例如，那些执行批量处理、订单处理、工资支付、科学计算的应用程序。**</font>

4. <font face=幼圆 color=white>**Parallel收集器在JDK1.6时提供了用于执行老年代垃圾收集的Parallel 0ld收集器，用来代替老年代的Serial 0ld收集器。**</font>

5. <font face=幼圆 color=white>**Parallel 0ld收集器采用了标记-压缩算法，但同样也是基于并行回收和"Stop- the-world"机制。**</font>

6. <font face=幼圆 color=white>在程序吞吐量优先的应用场景中，Parallel 收集器和Parallel 0ld收集器的组合，在Server模式下的内存回收性能很不错。<font face=幼圆 color=green>在Java8中，默认是此垃圾收集器。</font></font>

##### <font face=幼圆 color=white>5.3.3.1、**Parallel参数设置**</font>

1. <font face=幼圆 color=white>**-xx:+UseParallelGC手动指定年轻代使用Parallel并行收集器执行内存回收任务**</font>

2. <font face=幼圆 color=white>**-xx: +UseParallelolaGC手动指定老年代都是使用并行回收收集器**</font>

   - 
        <font face=幼圆 color=white>分别适用于新生代和老年代。默认jdk8是开启的</font>


   -    <font face=幼圆 color=white>上面两个参数，默认开启一个，另一个也会被开启。(<font face=幼圆 color=cherry>互相激活</font>)</font>

3. <font face=幼圆 color=white>**-xx: ParallelGCThreads设置年轻代并行收集器的线程数。一般地，最好CPU数量相等，以避免过多的线程数影响垃圾收集性能**</font>

   - <font face=幼圆 color=white>在默认情况下，当CPU数量小于8个， ParallelGCThreads 的值等于CPU数量</font>

   - <font face=幼圆 color=white>当CPU数量大于8个，ParallelGCThreads 的值等于3+ [5*CPU_ Count] /8]</font>


4. <font face=幼圆 color=white>**-xx: +UseAdaptiveSizePolicy设置Parallel Scavenge收集器具有自适应调节策略**</font>

   -  <font face=幼圆 color=white>**在这种模式下，年轻代的大小、Eden和Survivor的比例、晋升老年代的对象年龄等参数会被自动调整，已达到在堆大小、吞吐量和停顿时间之间的平衡点。**</font>


   -  <font face=幼圆 color=white>**在手动调优比较困难的场合，可以直接使用这种自适应的方式，仅指定虚拟机的最大堆、目标的吞吐量(GCTimeRatio)和停顿时(MaxGCPauseMills)，让虚拟机自己完成调优工作。**</font>




#### <font face=幼圆 color=white>5.3.4、ParalNew</font>

1. <font face=幼圆 color=white>**如果说Serial GC是年轻代中的单线程垃圾收集器，那么ParNew收集器则是Serial收集器的多线程版本。Par是Parallel的缩写，New:只能处理的是新生代。**</font>
2. <font face=幼圆 color=white>**ParNew收集器除了采用并行回收的方式执行内存回收外，与Serial垃圾收集器之间几乎没有任何区别。ParNew收集器在年轻代中同样也是采用复制算法，"Stop-the-World"机制。**</font>
3. <font face=幼圆 color=white>**ParNew是很多JVM运行在Server模式下新生代的默认垃圾收集器。**</font>

![ParalNew](D:\project\springboot_003\src\main\resources\book\jvm\上篇：内存与垃圾回收篇\image\ParalNew.png)



#### <font face=幼圆 color=white>5.3.5、Serial</font>

1. <font face=幼圆 color=white>**Serial收集器是最基本、历史最悠久的垃圾收集器了。JDK1.3之前回收新生代唯一的选择。**</font>

2. <font face=幼圆 color=white>**Serial收集器作为Hotspot中Client模式下的默认新生代垃圾收集器。**</font>

3. <font face=幼圆 color=white>**Serial收集器采用复制算法、串行回收和"Stop-the-World"机制的方式执行内存回收。**</font>

   - <font face=幼圆 color=white>**Serial 0ld是运行在Client模式下默认的老年代的垃圾回收器**</font>

   - <font face=幼圆 color=white>**Serial 0ld在Server模式下主要有两个用途:①与新生代的Parallel Scavenge配合使用； ②作为老年代CMS收集器的后备垃圾收集方案**</font>

   ![Serial](D:\project\springboot_003\src\main\resources\book\jvm\上篇：内存与垃圾回收篇\image\Serial.png)

4. <font face=幼圆 color=white>**优势:简单而高效(与其他收集器的单线程比)**</font>

   ​		<font face=幼圆 color=white>对于限定单个CPU的环境来说，Serial收集器由于没有线程交互的开销，专心做垃圾收集自然可以获得最高的单线程收集效率。运行在Client模式下的虚拟机是个不错的选择</font>

5. <font face=幼圆 color=white>**在HotSpot虚拟机中，使用-XX:+UseSerialGC 参数可以指定年轻代和老年代都使用串行收集器**。**等价于新生代用Serial GC，且老年代用Serial 0ld GC**</font>

6. <font face=幼圆 color=white>**总结**</font>

​		<font face=幼圆 color=white>**这种垃圾收集器大家了解，现在已经不用串行的了。而且在限定单核cpu才可以用。现在都不是单核的了。对于交互较强的应用而言，这种垃圾收集器是不能接受的。一般在Java web应用程序中是不会采用串行垃圾收集器的。**</font>



### <font face=幼圆 color=white>5.4、7种经典垃圾回收器总结</font>

### ![7种经典的垃圾回收器](D:\project\springboot_003\src\main\resources\book\jvm\上篇：内存与垃圾回收篇\image\7种经典的垃圾回收器.png)
