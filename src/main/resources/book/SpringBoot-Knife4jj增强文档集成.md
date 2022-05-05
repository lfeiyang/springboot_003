 

# <font face=幼圆 color=white>[SpringBoot集成knif4jj增强文档](http://www.quartz-scheduler.org/)</font>

# <font face=幼圆 color=white>FAQ</font>

## <font face=幼圆 color=white>1.找不到插件</font>

```java
SpringBoot集成swagger后出现: Failed to start bean ‘documentationPluginsBootstrapper‘的编译错误：


org.springframework.context.ApplicationContextException:Failed to start bean 'documentationPluginsBootstrapper'; nested exception is java.lang.NullPointerException

解决办法：在启动类加一个注解：@EnableWebMvc
```

```properties
#Spring Boot 2.6.X使用PathPatternMatcher匹配路径，Swagger引用的Springfox使用的路径匹配是基于AntPathMatcher的，添加配置
spring.mvc.pathmatch.matching-strategy=ANT_PATH_MATCHER
```

