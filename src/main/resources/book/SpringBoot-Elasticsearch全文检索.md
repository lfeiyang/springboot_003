# <font face=幼圆 color=white>[Elasticsearch全文检索](https://www.elastic.co/cn/downloads/past-releases#elasticsearch)</font>

## <font face=幼圆 color=white>一、安装教程</font>

### <font face=幼圆 color=white>1.1.Linux安装</font>

#### <font face=幼圆 color=white>1.1.1.Linux安装</font>

```shell
# 解压
tar -zxf elasticsearch-7.12.0-linux-x86_64.tar.gz

# 启动
elasticsearch-7.12.0/bin/elasticsearch
```

#### <font face=幼圆 color=white>1.1.2.配置文件</font>

##### <font face=幼圆 color=white>1.1.2.1.limits.conf </font>

<font face=幼圆 color=red>bootstrap check failure [1] of [3]: max file descriptors [4096] for elasticsearch process is too low, increase to at least [65535]</font>

　　<font face=幼圆 color=white>这个是说ElasticSearch进程的最大文件描述大小需要65535，而当前是4096，解决办法是修改 /etc/security/limits.conf 文件，在末尾加上（存在则修改，数值不能比要求的小）：</font>

```shell
* soft nofile 65535
* hard nofile 65535
* soft nproc 65535
* hard nproc 65535
```

##### <font face=幼圆 color=white>1.1.2.2.limits.conf </font>

<font face=幼圆 color=red>bootstrap check failure [2] of [3]: max virtual memory areas vm.max_map_count [65530] is too low, increase to at least [262144]</font>

　　<font face=幼圆 color=white>这是说最大虚拟内存太小（vm.max_map_count配置），至少需要262144，当前为65530，解决办法是修改 /etc/sysctl.conf 文件，在末尾加上（存在则修改，数值不能比要求的小）：</font>

```shell
vm.max_map_count=655360

# 重启
sysctl -p
```

##### <font face=幼圆 color=white>1.1.2.3.elasticsearch.yml</font>

```shell
# 单机安装请取消注释：node.name: node-1，否则无法正常启动
node.name: node-1
# 启动地址，如果不配置，只能本地访问
network.host: 0.0.0.0
# 对外提供服务的端口
http.port: 9200
# 内部服务端口
transport.port: 9300
# 取消注释master节点，单机只保留一个node
cluster.initial_master_nodes: ["node-1"]
```

#### <font face=幼圆 color=white>1.1.3.新建用户</font>

##### <font face=幼圆 color=white>1.1.3.1.创建elastic账号</font>

```
# 创建用户组
groupadd elastic

# 创建用户
useradd elastic
```

##### <font face=幼圆 color=white>1.1.3.2.设置elastic账号的密码</font>

```shell
# sa123456!@#
passwd elastic
```

##### <font face=幼圆 color=white>1.1.3.3.为账号赋予目录权限</font>

```shell
# chown -R elastic:elastic /opt/elasticsearch-8.2.0
chown -R elastic:elastic {{espath}} 或 chown -R elastic {{espath}}
chmod -R 777 {{espath}}
```

#### <font face=幼圆 color=white>1.1.4.启动</font>

```shell
# /opt/elasticsearch-8.2.0/bin
./elasticsearch
```



## <font face=幼圆 color=white>二、可视化工具</font>

<font size=6 face=幼圆 color=white>kibana</font>

### <font face=幼圆 color=white>2.1.文件权限</font>

```shell
chown -R es /opt/kibana/
chmod -R 777 /opt/kibana/
```

### <font face=幼圆 color=white>2.2.kibana.yml配置</font>

```shell
# 端口
server.port: 5601
# 监听所有的ip
server.host: "0.0.0.0" 
# The URLs of the Elasticsearch instances to use for all your queries.
elasticsearch.hosts: ["http://192.168.58.100:9200"]
```



