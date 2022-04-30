# <font face=幼圆 color=white>[SpringBoot分页插件](https://pagehelper.github.io/)</font>

## <font face=幼圆 color=white>一、引用分页插件</font>

```xml
<!-- 8.分页插件-->
<dependency>
    <groupId>com.github.pagehelper</groupId>
    <artifactId>pagehelper-spring-boot-starter</artifactId>
    <version>1.4.2</version>
</dependency>
```

## 

## <font face=幼圆 color=white>二、application.properties配置文件</font>

```properties
# 指定数据库，可以不配置，pagehelper 插件会自动检测数据库的类型
pagehelper.helperDialect=mysql

# 分页合理化参数默认 false，当该参数设置为 true 时
# pageNum <= 0 时，默认显示第一页
# pageNum 超过 pageSize 时，显示最后一页
pagehelper.reasonable=true

# 用于从对象中根据属性名取值，可以配置 pageNum，pageSize，count 不用配置映射的默认值
pagehelper.supportMethodsArguments=true

# 分页插件会根据查询方法的参数中，自动根据 params 配置的字段中取值，找到合适的值会自动分页
pagehelper.params=count=countSql
```

## <font face=幼圆 color=white>三、Service层</font>

```java
@Override
public List<FrameUser> getSimpleFrameUserList(int first, int pageSize) {
    // 紧跟着的第一个select方法会被分页
    PageHelper.startPage(first, pageSize);

    return userMapper.getFrameUserList();
}

@Override
public List<FrameUser> getFrameUserList(int first, int pageSize) {
    PageHelper.startPage(first, pageSize);

    List<FrameUser> frameUserList = userMapper.getFrameUserList();

    PageInfo<FrameUser> frameUserPageInfo = new PageInfo<>(frameUserList);

    return frameUserPageInfo.getList();
}
```

## <font face=幼圆 color=white>四、Controller层</font>

```java
@RequestMapping("/getSimpleFrameUserList")
public List<FrameUser> getSimpleFrameUserList(@RequestParam(defaultValue = "1") int first, @RequestParam(defaultValue = "10") int pageSize) {
    return userService.getSimpleFrameUserList(first, pageSize);
}

@RequestMapping("/getFrameUserList")
public List<FrameUser> getFrameUserList(@RequestParam(defaultValue = "1") int first, @RequestParam(defaultValue = "10") int pageSize) {
    return userService.getFrameUserList(first, pageSize);
}
```

