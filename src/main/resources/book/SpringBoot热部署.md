# <font face=幼圆 color=white>[JRebel热部署](https://manuals.jrebel.com/jrebel/ide/intellij.html)</font>

## <font face=幼圆 color=white>一、安装</font>

### <font face=幼圆 color=white>1.1.从插件存储库安装</font>

<font face=幼圆 color=white>1.1.1.Open the IntelliJ IDEA  **Settings**… dialog. Select **Plugins** from the sections menu.</font>

<font face=幼圆 color=white>1.1.2.Use the **Marketplace** tab to browse and locate the JRebel plugin</font>

[![../_images/intellij-plugins-repository-list.png](https://manuals.jrebel.com/jrebel/_images/intellij-plugins-repository-list.png)](https://manuals.jrebel.com/jrebel/_images/intellij-plugins-repository-list.png)

<font face=幼圆 color=white>1.1.3.Click **Install** to download and install the JRebel plugin for IntelliJ IDEA</font>

<font face=幼圆 color=white>1.1.4.IntelliJ might ask you to restart the IDE to complete the plugin installation</font>

## <font face=幼圆 color=white>二、激活</font>

### <font face=幼圆 color=white>2.1.团队评估或商业许可</font>

<font face=幼圆 color=white>Open this tab if you already have a JRebel license. You can select one of three activation options:</font>

[![../_images/team-eval-comm-lic.png](https://manuals.jrebel.com/jrebel/_images/team-eval-comm-lic.png)](https://manuals.jrebel.com/jrebel/_images/team-eval-comm-lic.png)

<font face=幼圆 color=white>2.1.1.**Team URL (connect to online licensing service)** </font>

```text
	如果您的网络管理员为您提供了团队 URL，请选择此选项。团队 URL 是由许可证服务器地址和唯一密钥组
成的唯一 URL。在第一个字段中指定团队 URL，在第二个字段中输入您的电子邮件地址。单击激活 JRebel。
JRebel 6 需要 License Server 3 或更新版本才能激活
```

## <font face=幼圆 color=white>三、项目配置</font>

### <font face=幼圆 color=white>3.1.唯一配置文件[rebel.xml](https://manuals.jrebel.com/jrebel/standalone/config.html#rebel-xml)</font>

<font face=幼圆 color=white>右键单击**Project**视图中的项目节点并选择**JRebel > Enable JRebel**</font>

[![../_images/proj-conf.png](https://manuals.jrebel.com/jrebel/_images/proj-conf.png)](https://manuals.jrebel.com/jrebel/_images/proj-conf.png)



### <font face=幼圆 color=white>3.2.[Maven 插件](https://manuals.jrebel.com/jrebel/standalone/maven.html#maven-rebel-xml)</font>

## <font face=幼圆 color=white>四、工具窗口</font>

![../_images/intellij-jrebel-tool-window.png](https://manuals.jrebel.com/jrebel/_images/intellij-jrebel-tool-window.png)

## <font face=幼圆 color=white>五、服务器启动</font>

[![../_images/launch-buttons.png](https://manuals.jrebel.com/jrebel/_images/launch-buttons.png)](https://manuals.jrebel.com/jrebel/_images/launch-buttons.png)

### <font face=幼圆 color=white>5.1.禁用自动应用程序部署</font>

​		<font face=幼圆 color=white>**通过Run > Edit Configurations**打开相应应用程序服务器的**运行/调试**配置。在**Server**选项卡上，将**On frame deactivation**设置为**Do nothing**</font>

[![../_images/intellij-rundebugconf.png](https://manuals.jrebel.com/jrebel/_images/intellij-rundebugconf.png)](https://manuals.jrebel.com/jrebel/_images/intellij-rundebugconf.png)

<font face=幼圆 color=red>**现在，您可以使用**Run with JRebel**或**Debug with JRebel**启动按钮来使用 JRebel 启动选定的配置**（通过SpringBootStarter中run启动热部署才有效）</font>

## <font face=幼圆 color=white>六、IDE 日志级别配置</font>

<font face=幼圆 color=white>此属性的默认值为`info`。JRebel IDE 插件日志文件存储在`{user.home}/.jrebel/`</font>

```properties
rebel.ide.log=[off|error|warn|info|debug|trace]
```

## <font face=幼圆 color=white>七、JRebel破解</font>

### <font face=幼圆 color=white>7.1.[下载激活JRebel的插件](https://manuals.jrebel.com/jrebel/standalone/config.html#rebel-xml)</font>

<font face=幼圆 color=white>运行ReverseProxy_windows_amd64.exe</font>

[![img](https://img2020.cnblogs.com/i-beta/1829785/202003/1829785-20200306154033215-1783763193.png)](https://img2020.cnblogs.com/i-beta/1829785/202003/1829785-20200306154033215-1783763193.png)

### <font face=幼圆 color=white>7.2.获取Team Url</font>

```http
# GUID生成链接https://www.guidgen.com/
http://127.0.0.1:8888/GUID
```

### <font face=幼圆 color=white>7.3.配置离线模式</font>

<font face=幼圆 color=white>这样就可以关闭</font>ReverseProxy_windows_amd64.exe程序运行

![在这里插入图片描述](https://img-blog.csdnimg.cn/20190320141720873.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3pjeGJk,size_16,color_FFFFFF,t_70)
