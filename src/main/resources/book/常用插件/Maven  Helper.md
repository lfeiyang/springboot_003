# <font face=幼圆 color=white>[Maven Helper](https://plugins.jetbrains.com/plugin/7179-maven-helper/)</font>

## <font face=幼圆 color=white>一、安装</font>

​		<font face=幼圆 color=white>File-->setting--->Plugins--->在搜索框中填写Maven Helper然后搜索，单击Install按钮进行安装，装完重启IDE。</font>

![](D:\project\springboot_003\src\main\resources\book\常用插件\image\mavenHelper01.png)



## <font face=幼圆 color=white>二、使用</font>

​		<font face=幼圆 color=white>当Maven Helper 插件安装成功后，打开项目中的pom文件，下面就会多出一个视图</font>

![](D:\project\springboot_003\src\main\resources\book\常用插件\image\mavenHelper02.png)

​		<font face=幼圆 color=white>切换到此试图即可进行相应操作：</font>
<font face=幼圆 color=white>1.Conflicts（查看冲突）</font>
<font face=幼圆 color=white>2.All Dependencies as List（列表形式查看所有依赖）</font>
<font face=幼圆 color=white>3.All Dependencies as Tree（树形式查看所有依赖）

​		<font face=幼圆 color=white>当前界面上还提供搜索功能 方便使用</font>

![](D:\project\springboot_003\src\main\resources\book\常用插件\image\mavenHelper03.png)

​		<font face=幼圆 color=white>打开pom文件，并可以切换tab，简单使用，如下图</font>

![](D:\project\springboot_003\src\main\resources\book\常用插件\image\mavenHelper04.png)



## <font face=幼圆 color=white>三、冲突jar包的解决</font>

​		<font face=幼圆 color=white>切换到maven 依赖视图选择冲突选项，如果有冲突，在左下面区域会有红色显示。</font>

​		<font face=幼圆 color=white>解决冲突，右键单击红色区域，弹出菜单选择Exclude命令，对冲突进行排除。</font>

![](D:\project\springboot_003\src\main\resources\book\常用插件\image\mavenHelper05.png)