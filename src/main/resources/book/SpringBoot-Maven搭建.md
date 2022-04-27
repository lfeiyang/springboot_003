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
// 扫描Mybais的Mapper
@MapperScan(value = {"com.sy.mapper"})
public class SpringConfig {
}
```



### <font face=幼圆 color=white>4.2、SpringConfig启动类</font>

```java
@RestController
// 启用自动配置
@EnableAutoConfiguration
public class SpringBootStarter {
    @RequestMapping("lfy")
    String home() {

        return "Hello World!";
    }

    public static void main(String[] args) {

        SpringApplication.run(SpringConfig.class, args);
    }
}
```

## <font face=幼圆 color=white>五、全局配置</font>

```properties
#服务器
server.port=8888
server.tomcat.uri-encoding=UTF-8
#server.servlet.context-path=/lfy

#POST乱码
#spring.http.encoding.charset=UTF-8
#spring.http.encoding.enabled=true
#spring.http.encoding.force=true

#json日期格式(向前端响应日期)
spring.jackson.date-format=yyyy-MM-dd hh:mm:ss

#springmvc日期格式(前端向后台传递日期参数)
spring.mvc.date-format=yyyy-MM-dd hh:mm:ss
spring.mvc.static-path-pattern=/**
spring.mvc.view.prefix=/WEB-INF/jsp/
spring.mvc.view.suffix=.jsp
spring.mvc.servlet.path=/

#数据源配置
spring.datasource.driver-class-name=com.mysql.jdbc.Driver
spring.datasource.url=jdbc:mysql:///qdfj
spring.datasource.username=root
spring.datasource.password=123456
spring.datasource.type=com.alibaba.druid.pool.DruidDataSource

#mybatis
mybatis.configuration.log-impl=org.apache.ibatis.logging.log4j.Log4jImpl
mybatis.type-aliases-package=com.sy.mapper

#日志
logging.level.root=error
#logging.level.com.sy.mapper=debug
#logging.level.org.mybatis=debug
```

