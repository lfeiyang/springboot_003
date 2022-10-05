# <font face=幼圆 color=white>SpringBoot-多数据源配置</font>

## <font face=幼圆 color=white>一、Mybatis多数据源</font>

### <font face=幼圆 color=white>1.1、创建数据库</font>

```mysql
drop database if exists mybatisone;
create database mybatisone;
use mybatisone;
drop table if exists book;
create table book(
id int(11) not null auto_increment comment 'id',
name varchar(128) default null comment '名称',
author varchar(64) default null comment '作者',
primary key(id)
)ENGINE=INNODB default charset=utf8;
insert into book(id,name,author)values(1,"西游记","吴承恩");

drop database if exists mybatistwo;
create database mybatistwo;
use mybatistwo;
drop table if exists book;
create table book(
id int(11) not null auto_increment comment 'id',
name varchar(128) default null comment '名称',
author varchar(64) default null comment '作者',
primary key(id)
)ENGINE=INNODB default charset=utf8;
insert into book(id,name,author)values(1,"红楼梦","曹雪芹");
```



### <font face=幼圆 color=white>1.2、添加依赖</font>

```java
<!--1.Java持久层框架Mybatis-->
<!--1.1.SpringBoot整合Mybatis包-->
<dependency>
    <groupId>org.mybatis.spring.boot</groupId>
    <artifactId>mybatis-spring-boot-starter</artifactId>
    <version>${mybatis.version}</version>
</dependency>
<!-- 1.2.SpringBoot整合通用Mapper（单表查询） -->
<dependency>
    <groupId>tk.mybatis</groupId>
    <artifactId>mapper-spring-boot-starter</artifactId>
    <version>${mapper.version}</version>
</dependency>
<!-- 1.3.generator插件核心包 -->
<dependency>
    <groupId>org.mybatis.generator</groupId>
    <artifactId>mybatis-generator-core</artifactId>
    <version>${mybatis.generator.version}</version>
</dependency>

<!--2.Mysql驱动-->
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <version>${mysql.version}</version>
</dependency>

<!--3.Druid连接池-->
<dependency>
    <groupId>com.alibaba</groupId>
    <artifactId>druid-spring-boot-starter</artifactId>
    <version>${druid.version}</version>
</dependency>

<!--4.分页插件-->
<dependency>
    <groupId>com.github.pagehelper</groupId>
    <artifactId>pagehelper-spring-boot-starter</artifactId>
    <version>${pagehelper.version}</version>
</dependency>
```





### <font face=幼圆 color=white>1.3、配置数据库链接</font>

```properties
#数据源配置一
spring.datasource.one.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.one.url=jdbc:mysql:///qdfj
spring.datasource.one.username=root
spring.datasource.one.password=123456
spring.datasource.one.type=com.alibaba.druid.pool.DruidDataSource
```



### <font face=幼圆 color=white>1.4、配置数据源</font>

```java
package com.lfeiyang.config;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.sql.DataSource;

/**
 * 配置多数据源
 *
 * @author lfeiyang
 * @since 2022-10-04 21:18
 */
@Configuration
@PropertySource("classpath:jdbc.properties")
public class DataSourceConfig
{
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.one")
    public DataSource dsOne() {
        return DruidDataSourceBuilder.create().build();
    }
}
```



### <font face=幼圆 color=white>1.5、创建Mybatis配置</font>

```java
package com.lfeiyang.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tk.mybatis.spring.annotation.MapperScan;

import javax.sql.DataSource;

/**
 * 数据源一Mybatis配置
 *
 * @author lfeiyang
 * @since 2022-10-04 22:21
 */
@Configuration
@MapperScan(value = "com.lfeiyang.mapper", sqlSessionFactoryRef = "sqlSessionFactoryBeanOne")
public class MybatisConfigOne
{
    @Autowired
    @Qualifier("dsOne")
    DataSource dsOne;

    @Bean
    SqlSessionFactory sqlSessionFactoryBeanOne() throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dsOne);
        return factoryBean.getObject();
    }

    @Bean
    SqlSessionTemplate sqlSessionTemplateOne() throws Exception {
        return new SqlSessionTemplate(sqlSessionFactoryBeanOne());
    }
}
```
