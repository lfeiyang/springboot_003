# <font face=幼圆 color=white>Spring-Boot-Maven环境搭建</font>

## <font face=幼圆 color=white>一、声明SpringBoot工程</font>

```xml
<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>2.1.8.RELEASE</version>
    <relativePath/>
</parent>
```



## <font face=幼圆 color=white>二、添加web依赖</font>

```xml
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-web</artifactId>
</dependency>
```



## <font face=幼圆 color=white>三、SpringBoot插件</font>

```xml
<plugins>
  <plugin>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-maven-plugin</artifactId>
  </plugin>
</plugins>
```



## <font face=幼圆 color=white>四、SpringBoot启动类</font>

### <font face=幼圆 color=white>4.1、SpringConfig配置类</font>

```java
// 扫描包（不然要和其它包在同层级，否则扫描不到）
@SpringBootApplication(scanBasePackages = "com.sy")
public class SpringConfig {
}
```



### <font face=幼圆 color=white>4.2、SpringConfig启动类</font>

```java
public class SpringBootStarter {
    public static void main(String[] args) {
        SpringApplication.run(SpringConfig.class, args);
    }
}
```

