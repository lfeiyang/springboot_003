# <font face=幼圆 color=white>[SpringBoot默认Json解析方案](https://github.com/FasterXML/jackson)</font>

## <font face=幼圆 color=white>一、HttpMessageConverter</font>

<font face=幼圆 color=white>HttpMessageConverter是一个消息转换工具，有两方面的功能：</font>

```text
1.将服务端返回的对象序列化成json字符串

2.将前端传过来的json字符串反序列化成java对象
```

 

## <font face=幼圆 color=white>二、默认json解析方案</font>

```text
所有的json生成都离不开相关的HttpMessageConverter。
SpringMVC自动配置了Jackson和Gson的HttpMessageConverter，SpringBoot对此做了自动化配置。

```

