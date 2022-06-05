# <font face=幼圆 color=white>[SpringBoot-Vue2.X前后端不分离整合](https://blog.csdn.net/wenxingchen/article/details/119721008)</font>

## <font face=幼圆 color=white>一、结构目录</font>

![image-20220605100836879](D:\project\springboot_003\src\main\resources\book\vue2.x\image\整合vue目录.png)



## <font face=幼圆 color=white>二、[vue.js](https://cdn.jsdelivr.net/npm/vue@2/dist/vue.js)</font>



## <font face=幼圆 color=white>三、[element-ui.js](https://unpkg.com/element-ui/lib/index.js)</font>



## <font face=幼圆 color=white>四、[element-ui.css](https://unpkg.com/element-ui@2.15.9/lib/theme-chalk/index.css)</font>



## <font face=幼圆 color=white>五、首页</font>

```vue
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>首页</title>
  <link rel="icon" href="lfy/favicon.ico">
  <script src="lfy/common/common-js.js" type="application/javascript"></script>
</head>
<body>
<div id="app">
  {{ message }}
</div>

<script>
  new Vue({
    el: '#app',
    data: {
      message: 'Hello Vue!'
    }
  })
</script>
</body>
</html>
```

