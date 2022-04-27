# <font face=幼圆 color=white>SpringBoot日志</font>

## <font face=幼圆 color=white>一、CommonsLogging日志系统</font>

### <font face=幼圆 color=white>1.1、依赖项</font>

```text
	Spring Boot项目一般都会引用spring-boot-starter或者spring-boot-starter-web，而这两个起步依赖中都已经包含了对于spring-boot-starter-logging的依赖，所以，无需额外添加依赖
```



```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-logging</artifactId>
</dependency>
```



### <font face=幼圆 color=white>1.2、快速定位日志</font>

```java
// 通过xx.class来实现的
private static final Logger LOG = LoggerFactory.getLogger(xx.class);
```



### <font face=幼圆 color=white>1.3、日志信息存储到文件</font>

```properties
// 优先级name > path，默认spring.log
logging.file.path = 
logging.file.name = 
```



### <font face=幼圆 color=white>1.4、日志级别</font>

```properties
// TRACE < DEBUG < INFO < WARN < ERROR < FATAL
logging.level.root=warn
```



### <font face=幼圆 color=white>1.5、日志格式</font>

```properties
// 控制台
logging.pattern.console=%d{yyyy/MM/dd-HH:mm:ss} [%thread] %-5level %logger- %msg%n 

// 文件
logging.pattern.file=%d{yyyy/MM/dd-HH:mm} [%thread] %-5level %logger- %msg%n
```

```text
%d{HH:mm:ss.SSS}——日志输出时间

%thread——输出日志的进程名字，这在Web应用以及异步任务处理中很有用

%-5level——日志级别，并且使用5个字符靠左对齐

%logger- ——日志输出者的名字

%msg——日志消息

%n——平台的换行符
```

## 

### <font face=幼圆 color=white>1.6、使用xml扩展</font>

```text
在resources目录下创建logback-spring.xml配置文件
logback-spring.xml, logback-spring.groovy, logback.xml, logback.groovy
```



### <font face=幼圆 color=white>1.7、 自定义日志配置</font>

```properties
logging.config=classpath:log4j2.xml
```

| Logging System          | Customization                                                |
| :---------------------- | ------------------------------------------------------------ |
| Logback                 | `logback-spring.xml`, `logback-spring.groovy`, `logback.xml`,  `logback.groovy` |
| Log4j2                  | log4j2-spring.xml  log4j2.xml                                |
| JDK (Java Util Logging) | `logging.properties`                                         |



## <font face=幼圆 color=white>二、Log4j2日志系统</font>

### <font face=幼圆 color=white>2.1、 移除CommonsLogging原生日志</font>

```xml
<!--1..添加web依赖-->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
    <exclusions>
        <exclusion>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-logging</artifactId>
        </exclusion>
    </exclusions>
</dependency>
```



### <font face=幼圆 color=white>2.2、 Log4j日志</font>

```xml
<!-- 5.log4j2依赖 -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-log4j2</artifactId>
</dependency>
<!--异步,使用 log4j2 的 AsyncLogger 时需要包含 disruptor 并发框架 -->
<dependency>
    <groupId>com.lmax</groupId>
    <artifactId>disruptor</artifactId>
    <version>3.4.2</version>
</dependency>
```



### <font face=幼圆 color=white>2.3、 Log4j配置文件</font>

```xml
Filters决定日志事件能否被输出。过滤条件有三个值：ACCEPT(接受)，DENY(拒绝)，NEUTRAL(中立)。

onMatch="ACCEPT"匹配该级别及以上级别;
onMatch="DENY"不匹配该级别及以上级别;

onMismatch="ACCEPT" 表示匹配该级别以下的级别;
onMismatch="DENY" 表示不匹配该级别以下的级别;
```

