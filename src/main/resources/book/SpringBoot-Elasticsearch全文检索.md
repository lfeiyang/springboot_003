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



## <font face=幼圆 color=white>三、 [Java High Level REST Client](https://www.elastic.co/guide/en/elasticsearch/client/java-rest/7.4/java-rest-high.html)</font>

### <font face=幼圆 color=white>3.1.实体</font>

<font face=幼圆 color=white>Kibana提供的`Dev Tools`里边执行</font>

```java
PUT _template/hero_template
{
    "index_patterns":[
        "hero*"
    ],
    "mappings":{
        "properties":{
            "@timestamp":{
                "type":"date"
            },
            "id":{
                "type":"integer"
            },
            "name":{
                "type":"keyword"
            },
            "country":{
                "type":"keyword"
            },
            "birthday":{
                "type":"keyword"
            },
            "longevity":{
                "type":"integer"
            }
        }
    }
}
```



### <font face=幼圆 color=white>3.2.实例</font>

```java
package com.sy.controller;

import com.alibaba.fastjson.JSON;
import com.sy.model.Hero;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.DeleteByQueryRequest;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.*;

/**
 * [一句话注释]
 *
 * @author lfeiyang
 * @since 2022-05-09 1:04
 */
@Slf4j
@RestController
@Scope(value = "singleton")
public class EsController {
    @Autowired
    private RestHighLevelClient restHighLevelClient;

    /**
     * 创建索引
     *
     * @return java.lang.String
     **/
    @GetMapping("/createIndex")
    public Long createIndex() throws IOException {
        IndexRequest request = new IndexRequest("hero");
        request.id("1");
        Map<String, String> map = new HashMap<>();
        map.put("id", "1");
        map.put("name", "曹操");
        map.put("country", "魏");
        map.put("birthday", "公元155年");
        map.put("longevity", "65");
        request.source(map);
        IndexResponse indexResponse = restHighLevelClient.index(request, RequestOptions.DEFAULT);

        return indexResponse.getVersion();
    }

    /**
     * 批量插入
     *
     * @return org.elasticsearch.action.bulk.BulkResponse
     **/
    @GetMapping("/bulkRequestTest")
    public BulkResponse bulkRequestTest() throws IOException {
        BulkRequest request = new BulkRequest();
        request.add(new IndexRequest("hero").id("2")
                .source(XContentType.JSON, "id", "2", "name", "刘备", "country", "蜀", "birthday", "公元161年", "longevity", "61"));
        request.add(new IndexRequest("hero").id("3")
                .source(XContentType.JSON, "id", "3", "name", "孙权", "country", "吴", "birthday", "公元182年", "longevity", "61"));
        request.add(new IndexRequest("hero").id("4")
                .source(XContentType.JSON, "id", "4", "name", "诸葛亮", "country", "蜀", "birthday", "公元181年", "longevity", "53"));
        request.add(new IndexRequest("hero").id("5")
                .source(XContentType.JSON, "id", "5", "name", "司马懿", "country", "魏", "birthday", "公元179年", "longevity", "72"));
        request.add(new IndexRequest("hero").id("6")
                .source(XContentType.JSON, "id", "6", "name", "荀彧", "country", "魏", "birthday", "公元163年", "longevity", "49"));
        request.add(new IndexRequest("hero").id("7")
                .source(XContentType.JSON, "id", "7", "name", "关羽", "country", "蜀", "birthday", "公元160年", "longevity", "60"));
        request.add(new IndexRequest("hero").id("8")
                .source(XContentType.JSON, "id", "8", "name", "周瑜", "country", "吴", "birthday", "公元175年", "longevity", "35"));

        return restHighLevelClient.bulk(request, RequestOptions.DEFAULT);
    }

    @GetMapping("/updateUser")
    public DocWriteResponse.Result updateTest() throws IOException {
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("country", "魏");

        UpdateRequest request = new UpdateRequest("hero", "7").doc(jsonMap);
        UpdateResponse updateResponse = restHighLevelClient.update(request, RequestOptions.DEFAULT);

        return updateResponse.getResult();
    }

    /**
     * 插入/更新数据
     *
     * @return org.elasticsearch.action.index.IndexResponse
     **/
    @GetMapping("/insertOrUpdateOne")
    public IndexResponse insertOrUpdateOne() {
        Hero hero = new Hero();
        hero.setId(5);
        hero.setName("曹丕");
        hero.setCountry("魏");
        hero.setBirthday("公元187年");
        hero.setLongevity(39);
        IndexRequest request = new IndexRequest("hero");
        request.id(hero.getId().toString());
        request.source(JSON.toJSONString(hero), XContentType.JSON);
        try {
            return restHighLevelClient.index(request, RequestOptions.DEFAULT);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 删除数据
     *
     * @return org.elasticsearch.action.delete.DeleteResponse
     **/
    @GetMapping("/deleteById")
    public DeleteResponse deleteById() throws IOException {
        DeleteRequest deleteRequest = new DeleteRequest("hero");
        deleteRequest.id("1");

        return restHighLevelClient.delete(deleteRequest, RequestOptions.DEFAULT);
    }

    /**
     * 删除数据
     *
     * @return int
     **/
    @GetMapping("/deleteByQueryRequest")
    public BulkByScrollResponse deleteByQueryRequest() throws IOException {
        DeleteByQueryRequest request = new DeleteByQueryRequest("hero");
        request.setConflicts("proceed");
        request.setQuery(new TermQueryBuilder("country", "吴"));

        return restHighLevelClient.deleteByQuery(request, RequestOptions.DEFAULT);
    }

    /**
     * 复合操作
     *
     * @return boolean
     **/
    @GetMapping("/bulkDiffRequest")
    public boolean bulkDiffRequest() throws IOException {
        BulkRequest request = new BulkRequest();
        request.add(new DeleteRequest("hero", "3"));
        request.add(new UpdateRequest("hero", "7").doc(XContentType.JSON, "longevity", "70"));

        BulkResponse bulkResponse = restHighLevelClient.bulk(request, RequestOptions.DEFAULT);
        BulkItemResponse[] bulkItemResponses = bulkResponse.getItems();
        for (BulkItemResponse item : bulkItemResponses) {
            DocWriteResponse itemResponse = item.getResponse();
            switch (item.getOpType()) {
                case UPDATE:
                    UpdateResponse updateResponse = (UpdateResponse) itemResponse;
                    break;
                case DELETE:
                    DeleteResponse deleteResponse = (DeleteResponse) itemResponse;
            }

            return RestStatus.OK.equals(item.status());
        }

        return false;
    }

    /**
     * 但条件查询 + limit
     *
     * @return java.lang.String
     **/
    @GetMapping("/selectByUser")
    public String selectByUser() {
        SearchRequest request = new SearchRequest("hero");
        SearchSourceBuilder builder = new SearchSourceBuilder();
        builder.query(new TermQueryBuilder("country", "魏"));
        // 相当于mysql里边的limit 1；
        builder.size(3);
        request.source(builder);
        try {
            SearchResponse response = restHighLevelClient.search(request, RequestOptions.DEFAULT);
            SearchHit[] hits = response.getHits().getHits();

            return Arrays.toString(hits);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 多条件查询 + 排序 + 分页
     **/
    @GetMapping("/boolQuery")
    public void boolQuery() {
        SearchRequest request = new SearchRequest("hero");
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        boolQueryBuilder.must(QueryBuilders.termQuery("country", "魏"));
        boolQueryBuilder.must(QueryBuilders.rangeQuery("longevity").gte(50));
        sourceBuilder.query(boolQueryBuilder);
        sourceBuilder.from(0).size(2);
        sourceBuilder.query(boolQueryBuilder);
        sourceBuilder.sort("longevity", SortOrder.DESC);
        request.source(sourceBuilder);
        SearchResponse response = null;
        try {
            response = restHighLevelClient.search(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            log.error("Query by Condition execution failed: {}", e.getMessage(), e);
        }
        assert response != null;
        SearchHit[] hits = response.getHits().getHits();
        List<Hero> herosList = new ArrayList<>(hits.length);
        for (SearchHit hit : hits) {
            herosList.add(JSON.parseObject(hit.getSourceAsString(), Hero.class));
        }

        log.info("print info: {}, size: {}", herosList.toString(), herosList.size());
    }
}
```



## <font face=幼圆 color=white>四、 [ElasticsearchRestTemplate](https://blog.csdn.net/qq_43692950/article/details/122285770)</font>

