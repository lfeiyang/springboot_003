# <font face=幼圆 color=white>SpringBoot默认首页</font>

## <font face=幼圆 color=white>一、静态资源路径</font>

### <font face=幼圆 color=white>1.1、配置静态资源访问路径</font>

```properties
# 这表示只有静态资源的访问路径为/resources/**时，才会处理请求
spring.mvc.static-path-pattern=/resources/**，
```

### <font face=幼圆 color=white>1.2、配置静态资源目录</font>

```properties
# 从前往后一次匹配
# 配置文件中如果进行了静态资源路径的配置，默认的配置不会失效
spring.resources.static-locations=classpath:/META-INF/resources
spring.resources.static-locations=classpath:/resources
spring.resources.static-locations=classpath:/static,classpath:/public
```

### <font face=幼圆 color=white>1.3、自定义静态资源访问</font>

```java
@Configuration
public class IndexViewConfig implements WebMvcConfigurer {
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/").setViewName("forward:/index.html");
		registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
	}
}
```

### <font face=幼圆 color=white>1.4、静态资源访问关键源码</font>

<font face=幼圆 color=white>WebMvcAutoConfiguration</font>

```java
public void addResourceHandlers(ResourceHandlerRegistry registry) {
    if (!this.resourceProperties.isAddMappings()) {
        logger.debug("Default resource handling disabled");
    } else {
        Duration cachePeriod = this.resourceProperties.getCache().getPeriod();
        CacheControl cacheControl = this.resourceProperties.getCache().getCachecontrol().toHttpCacheControl();
        if (!registry.hasMappingForPattern("/webjars/**")) {
            this.customizeResourceHandlerRegistration(registry.addResourceHandler(new String[]{"/webjars/**"}).addResourceLocations(new String[]{"classpath:/META-INF/resources/webjars/"}).setCachePeriod(this.getSeconds(cachePeriod)).setCacheControl(cacheControl));
        }

        String staticPathPattern = this.mvcProperties.getStaticPathPattern();
        if (!registry.hasMappingForPattern(staticPathPattern)) {
            this.customizeResourceHandlerRegistration(registry.addResourceHandler(new String[]{staticPathPattern}).addResourceLocations(WebMvcAutoConfiguration.getResourceLocations(this.resourceProperties.getStaticLocations())).setCachePeriod(this.getSeconds(cachePeriod)).setCacheControl(cacheControl));
        }

    }
}
```

## <font face=幼圆 color=white>二、默认首页加载顺序</font>

```properties
# 默认加载index.html顺序
/META-INF/resources > /resources > /static > /public
```

