# <font face=幼圆 color=white>[SpringBoot增删改查](https://pagehelper.github.io/)</font>

## <font face=幼圆 color=white>一、[Mybatis-Generator逆向工程](https://mapperhelper.github.io/docs/)</font>

### <font face=幼圆 color=white>1.1、Maven依赖</font>

```xml
<!--1.Java持久层框架Mybatis-->
<!--1.1.SpringBoot整合Mybatis包-->
<dependency>
    <groupId>org.mybatis.spring.boot</groupId>
    <artifactId>mybatis-spring-boot-starter</artifactId>
    <version>2.2.2</version>
</dependency>
<!--1.2.Mybatis包-->
<dependency>
    <groupId>org.mybatis</groupId>
    <artifactId>mybatis</artifactId>
    <version>3.5.9</version>
</dependency>
<!-- 1.3.SpringBoot整合通用Mapper（单表查询） -->
<dependency>
    <groupId>tk.mybatis</groupId>
    <artifactId>mapper-spring-boot-starter</artifactId>
    <version>4.2.1</version>
</dependency>
<!--1.4.Mapper包-->
<dependency>
    <groupId>tk.mybatis</groupId>
    <artifactId>mapper</artifactId>
    <version>4.2.1</version>
</dependency>
<!-- 1.5.generator插件核心包 -->
<dependency>
    <groupId>org.mybatis.generator</groupId>
    <artifactId>mybatis-generator-core</artifactId>
    <version>1.3.7</version>
</dependency>
```

### <font face=幼圆 color=white>1.2、Generator插件</font>

```xml
<plugin>
    <groupId>org.mybatis.generator</groupId>
    <artifactId>mybatis-generator-maven-plugin</artifactId>
    <version>1.3.7</version>
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

### <font face=幼圆 color=white>1.3、Generator配置属性</font>

```xml
<!-- MyBatis Generator  -->
<!-- Java接口和实体类  -->
<targetJavaProject>${basedir}/src/main/java</targetJavaProject>
<targetMapperPackage>com.sy.mapper</targetMapperPackage>
<targetModelPackage>com.sy.model</targetModelPackage>
<!-- XML生成路径  -->
<targetResourcesProject>${basedir}/src/main/resources</targetResourcesProject>
<targetXMLPackage>mapper</targetXMLPackage>
<!-- 依赖版本  -->
<mapper.version>4.2.1</mapper.version>
<mysql.version>8.0.28</mysql.version>
```

### <font face=幼圆 color=white>1.4、generatorConfig.xml配置文件</font>

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
        <javaClientGenerator targetPackage="${targetMapperPackage}" targetProject="${targetJavaProject}" type="XMLMAPPER">
            <!-- enableSubPackages:是否让schema作为包的后缀 -->
            <property name="enableSubPackages" value="true"/>
        </javaClientGenerator>

        <!-- 配置需要生成的表 -->
        <table tableName="frame_ou">
            <generatedKey column="id" sqlStatement="Mysql" identity="true"/>
        </table>
    </context>
</generatorConfiguration>
```

### <font face=幼圆 color=white>1.5、config.xml配置文件</font>

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

### <font face=幼圆 color=white>1.6、application.properties</font>

```properties
#mybatis
mybatis.configuration.log-impl=org.apache.ibatis.logging.log4j2.Log4j2Impl
#Mapper接口扫描生成别名
mybatis.type-aliases-package=com.sy.mapper
#扫描Mapper配置xml文件
mybatis.mapper-locations=classpath:mapper/*xml

#通用Mapper3
#自定义Mapper
#mapper.mappers=com.sfn.bms.common.config.MyMapper
#是否判断字符串!=''
mapper.not-empty=false
#主键策略
mapper.identity=MYSQL
```



## <font face=幼圆 color=white>二、Mapper接口层</font>

```java
package com.sy.mapper;

import com.sy.model.FrameOu;
import tk.mybatis.mapper.common.Mapper;

public interface FrameOuMapper extends Mapper<FrameOu> {
    
}
```

## <font face=幼圆 color=white>三、Service业务层</font>

### <font face=幼圆 color=white>3.1、通用Service业务接口层</font>

```java
package com.sy.service;

import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 通用Service业务接口层
 *
 * @author lfeiyang
 * @since 2022-04-30 21:55
 */
@Service
public interface IService<T> {
    T selectByKey(Object key);

    int save(T entity);

    int updateNotNull(T entity);

    int updateAll(T entity);

    int delete(Object key);

    int batchDelete(List<String> list, String property, Class<T> clazz);

    List<T> selectAll();

    List<T> selectByExample(Object example);
}
```

### <font face=幼圆 color=white>3.2、通用Service业务层</font>

```java
package com.sy.service.impl;

import com.sy.service.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * 通用Service业务层层实现
 *
 * @author lfeiyang
 * @since 2022-04-30 21:59
 */
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public abstract class BaseService<T> implements IService<T> {
    @Autowired
    protected Mapper<T> mapper;

    public Mapper<T> getMapper() {
        return mapper;
    }

    @Override
    public T selectByKey(Object key) {
        return mapper.selectByPrimaryKey(key);
    }

    @Override
    @Transactional
    public int save(T entity) {
        return mapper.insert(entity);
    }

    @Override
    @Transactional
    public int updateNotNull(T entity) {
        return mapper.updateByPrimaryKeySelective(entity);
    }

    @Override
    @Transactional
    public int updateAll(T entity) {
        return mapper.updateByPrimaryKey(entity);
    }

    @Override
    @Transactional
    public int delete(Object key) {
        return mapper.deleteByPrimaryKey(key);
    }

    @Override
    @Transactional
    public int batchDelete(List<String> list, String property, Class<T> clazz) {
        Example example = new Example(clazz);
        example.createCriteria().andIn(property, list);
        return mapper.deleteByExample(example);
    }

    @Override
    public List<T> selectAll() {
        return mapper.selectAll();
    }

    @Override
    public List<T> selectByExample(Object example) {
        return mapper.selectByExample(example);
    }
}
```

### <font face=幼圆 color=white>3.3、Service业务接口层</font>

```java
package com.sy.service;

import com.sy.model.FrameOu;
import org.springframework.stereotype.Service;

/**
 * 部门Service业务接口层
 *
 * @author lfeiyang
 * @since 2022-04-30 21:47
 */
public interface IFrameOuService extends IService<FrameOu> {

}
```

### <font face=幼圆 color=white>3.4、Service业务层</font>

```java
package com.sy.service.impl;

import com.sy.model.FrameOu;
import com.sy.service.IFrameOuService;
import org.springframework.stereotype.Service;

/**
 * 部门Service业务实现层
 *
 * @author lfeiyang
 * @since 2022-04-30 22:32
 */
@Service
public class FrameOuService extends BaseService<FrameOu> implements IFrameOuService {

}
```

## <font face=幼圆 color=white>四、Controller控制层</font>

```java
package com.sy.controller;

import com.github.pagehelper.PageHelper;
import com.sy.model.FrameOu;
import com.sy.service.IFrameOuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * 部门控制层
 *
 * @author lfeiyang
 * @since 2022-04-30 23:26
 */
@RestController
@Scope(value = "singleton")
public class FrameOuController {
    @Autowired
    private IFrameOuService ouService;

    @RequestMapping("/selectByKey")
    public FrameOu selectByKey(@RequestParam String key) {
        return ouService.selectByKey(key);
    }

    @RequestMapping("/save")
    public int save(FrameOu frameOu) {
        return ouService.save(frameOu);
    }

    @RequestMapping("/updateNotNull")
    public int updateNotNull(FrameOu frameOu) {
        return ouService.updateNotNull(frameOu);
    }

    @RequestMapping("/updateAll")
    public int updateAll(FrameOu frameOu) {
        return ouService.updateAll(frameOu);
    }

    @RequestMapping("/delete")
    public int delete(String key) {
        return ouService.delete(key);
    }

    @RequestMapping("/batchDelete")
    public int batchDelete(@RequestParam("list") List<String> list, String property) {
        return ouService.batchDelete(list, property, FrameOu.class);
    }

    @RequestMapping("/getFrameOuList")
    public List<FrameOu> getFrameOuList(@RequestParam(defaultValue = "1") int first, @RequestParam(defaultValue = "10") int pageSize) {
        PageHelper.startPage(first, pageSize);

        return ouService.selectAll();
    }

    @RequestMapping("/selectByExample")
    public List<FrameOu> selectByExample() {
        Example example = new Example(FrameOu.class);
        example.createCriteria().andLike("ouCode", "370212%");

        return ouService.selectByExample(example);
    }
}
```

## <font face=幼圆 color=white>FAQ</font>

### <font face=幼圆 color=white>1.插件版本过高</font>

```text
FAQ:realm =    plugin>org.mybatis.generator:mybatis-generator-maven-plugin:1.4.0

# 降低该插件版本
mybatis-generator-maven-plugin 1.4.1 -> 1.3.7
```

### <font face=幼圆 color=white>2.MapperScan引错包</font>

```text
# 引错包了
java.lang.NoSuchMethodException: tk.mybatis.mapper.provider.base.BaseSelectProvider.

# 解决方案：将红框里的换成  
import tk.mybatis.spring.annotation.MapperScan;
```

### <font face=幼圆 color=white>3.驼峰命名查询自动转下划线问题</font>

#### <font face=幼圆 color=white>3.1.application.properties关闭自动驼峰命名</font>

```properties
#不开启开启驼峰命名
mybatis.configuration.map-underscore-to-camel-case=false
```

#### <font face=幼圆 color=white>3.2.Mapper的xml配置文件</font>

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.sy.mapper.FrameUserMapper">
    <resultMap id="BaseResultMap" type="com.sy.model.FrameUser">
        <!--
          WARNING - @mbg.generated
        -->
        <id column="userGuid" jdbcType="VARCHAR" property="userGuid"/>
        <result column="loginId" jdbcType="VARCHAR" property="loginId"/>
        <result column="displayName" jdbcType="VARCHAR" property="displayName"/>
    </resultMap>
</mapper>
```