# <font face=幼圆 color=white>SpringBoot-多环境部署</font>

## <font face=幼圆 color=white>一、使用spring.profiles.active来分区配置</font>

​		<font face=幼圆 color=white>很多时候，我们项目在开发环境和生成环境的环境配置是不一样的，例如，数据库配置，在开发的时候，我们一般用测试数据库，而在生产环境的时候，我们是用正式的数据，这时候，我们可以利用profile在不同的环境下配置用不同的配置文件或者不同的配置</font>

​		<font face=幼圆 color=white>spring boot允许你通过命名约定按照一定的格式(application-{profile}.[properties](https://so.csdn.net/so/search?q=properties&spm=1001.2101.3001.7020))来定义多个配置文件，然后通过在application.properyies通过spring.profiles.active来具体激活一个或者多个配置文件，如果没有没有指定任何profile的配置文件的话，spring boot默认会启动application-default.properties。</font>

<font face=幼圆 color=white>profile的配置文件可以按照application.properyies的放置位置一样，放于以下四个位置</font>

> 1. <font face=幼圆 color=white>当前目录的 "/config"的子目录下</font>
> 2. <font face=幼圆 color=white>当前目录下</font>
> 3. <font face=幼圆 color=white>classpath根目录的"/config"包下</font>
> 4. <font face=幼圆 color=white>classpath的根目录下</font>

### <font face=幼圆 color=white>1.1、POM配置</font>

```java
<profiles>
    <profile>
        <!-- 本地开发环境 -->
        <id>dev</id>
        <properties>
            <!-- profiles.active 可以改为其他非关键字名字,env也可以,但是注意上下几个的标签要一致,本次统一使用的profiles.active-->
            <profiles.active>dev</profiles.active>
            <!--也可以配置其他属性,供使用;比如下面这个属性可以配置在maven打包跳过测试位置赋值 使用${skiptestconf}-->
            <skiptestconf>false</skiptestconf>
        </properties>
        <activation>
            <!-- 设置默认激活这个配置 -->
            <activeByDefault>true</activeByDefault>
        </activation>
    </profile>
    <profile>
        <!-- 发布环境 -->
        <id>prod</id>
        <properties>
            <profiles.active>prod</profiles.active>
            <skiptestconf>true</skiptestconf>
        </properties>
    </profile>
    <profile>
        <!-- 测试环境 -->
        <id>test</id>
        <properties>
            <profiles.active>test</profiles.active>
            <skiptestconf>true</skiptestconf>
        </properties>
    </profile>
</profiles>
```



### <font face=幼圆 color=white>1.2、Application.properties配置</font>

```properties
#多环境配置
spring.profiles.active=@profiles.active@
```



### <font face=幼圆 color=white>1.3、资源过滤</font>

```java
<!--根据激活条件引入打包所需的配置和文件-->
<resource>
	    <directory>src/main/resources</directory>
	    <!--引入所需环境的配置文件-->
	    <filtering>true</filtering>
	    <includes>
	        <include>application.properties</include>
	        <!--根据maven选择环境导入配置文件-->
	        <include>application-${profiles.active}.properties</include>
	    </includes>
	</resource>
</resources>
```



### <font face=幼圆 color=white>1.4、IDEA切换</font>

- <font face=幼圆 color=white>点击lifecycle中的package即可正常打包，相当于一下命令</font>

```java
mvn clean install package -P dev
```

