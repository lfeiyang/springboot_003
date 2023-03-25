# <font face=幼圆 color=white>[SpringBoot-Generator便捷插件](https://mapperhelper.github.io/docs/3.usembg/)</font>

## <font face=幼圆 color=white>一、依赖文件</font>

​		<font face=幼圆 color=orange>在项目pom文件中，引入Mybatis-Generator插件，并且引入Mybatis和Mysql依赖</font>

```java
<!-- MyBatis Generator  -->
<!-- Java接口和实体类  -->
<targetJavaProject>${basedir}/src/main/java</targetJavaProject>
<targetMapperPackage>com.lfeiyang.mapper1</targetMapperPackage>
<targetModelPackage>com.lfeiyang.model</targetModelPackage>
<!-- XML生成路径  -->
<targetResourcesProject>${basedir}/src/main/resources</targetResourcesProject>
<targetXMLPackage>mapper</targetXMLPackage>
            
<plugin>
    <groupId>org.mybatis.generator</groupId>
    <artifactId>mybatis-generator-maven-plugin</artifactId>
    <version>${mybatis.generator.version}</version>
    <configuration>
        <configurationFile>${basedir}/src/main/resources/generator/generatorConfig.xml</configurationFile>
        <overwrite>true</overwrite>
        <verbose>true</verbose>
    </configuration>
    <dependencies>
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>${mysql.version}</version>
        </dependency>
        <dependency>
            <groupId>tk.mybatis</groupId>
            <artifactId>mapper</artifactId>
            <version>${mapper.version}</version>
        </dependency>
    </dependencies>
</plugin>
```

### 

## <font face=幼圆 color=white>二、配置Mybatis-Generator配置</font>

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE generatorConfiguration
        PUBLIC "-//mybatis.org//DTD MyBatis Generator Configuration 1.0//EN"
        "http://mybatis.org/dtd/mybatis-generator-config_1_0.dtd">

<generatorConfiguration>
    <properties resource="generator/config.properties"/>

    <context id="Mysql" targetRuntime="MyBatis3Simple" defaultModelType="flat">
        <property name="beginningDelimiter" value="`"/>
        <property name="endingDelimiter" value="`"/>

        <plugin type="${mapper.plugin}">
            <!-- 该配置会使生产的Mapper自动继承Mapper3 -->
            <property name="mappers" value="${mapper.Mapper}"/>
        </plugin>

        <!-- 阻止生成自动注释 -->
        <commentGenerator>
            <property name="javaFileEncoding" value="UTF-8"/>
            <property name="suppressDate" value="true"/>
            <property name="suppressAllComments" value="true"/>
        </commentGenerator>

        <!-- 数据库链接地址账号密码 -->
        <jdbcConnection driverClass="${jdbc.driverClass}"
                        connectionURL="${jdbc.url}"
                        userId="${jdbc.user}"
                        password="${jdbc.password}">
        </jdbcConnection>

        <!-- 生成Model类存放位置 -->
        <javaModelGenerator targetPackage="${targetModelPackage}" targetProject="${targetJavaProject}">
            <!-- enableSubPackages:是否让schema作为包的后缀 -->
            <property name="enableSubPackages" value="true"/>
            <!-- 从数据库返回的值被清理前后的空格 -->
            <property name="trimStrings" value="true"/>
        </javaModelGenerator>

        <!-- 生成映射文件存放位置 -->
        <sqlMapGenerator targetPackage="${targetXMLPackage}" targetProject="${targetResourcesProject}">
            <!-- enableSubPackages:是否让schema作为包的后缀 -->
            <property name="enableSubPackages" value="true"/>
        </sqlMapGenerator>

        <!-- 生成Mapper类存放位置 -->
        <!-- 客户端代码，生成易于使用的针对Model对象和XML配置文件的代码
            type="ANNOTATEDMAPPER",生成Java Model 和基于注解的Mapper对象
            type="XMLMAPPER",生成SQLMap XML文件和独立的Mapper接口 -->
        <javaClientGenerator targetPackage="${targetMapperPackage}" targetProject="${targetJavaProject}"
                             type="XMLMAPPER">
            <!-- enableSubPackages:是否让schema作为包的后缀 -->
            <property name="enableSubPackages" value="true"/>
        </javaClientGenerator>

        <!-- 配置需要生成的表 -->
        <table tableName="frame_user">
            <generatedKey column="id" sqlStatement="Mysql" identity="true"/>
        </table>
    </context>
</generatorConfiguration>
```



## <font face=幼圆 color=white>三、配置Config.properties</font>

```properties
# 数据库配置
jdbc.driverClass = com.mysql.cj.jdbc.Driver
jdbc.url = jdbc:mysql:///qdfj?useSSL=true&serverTimezone=Asia/Shanghai
jdbc.user = root
jdbc.password = 123456

# 通用Mapper配置
mapper.plugin = tk.mybatis.mapper.generator.MapperPlugin
mapper.Mapper = tk.mybatis.mapper.common.Mapper
```



## <font face=幼圆 color=white>四、使用方法</font>

![generator](D:\project\springboot_003\src\main\resources\book\image\generator.png)



## <font face=幼圆 color=white>五、FAQ</font>
```text
1.别用高版本，不兼容，缺方法

2.
```
