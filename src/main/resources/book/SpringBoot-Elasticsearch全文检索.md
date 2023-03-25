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

##### <font face=幼圆 color=white>1.1.3.4.Elasticsearch创建安全账户</font>

```shell
# 1.在es节点上启用安全功能
# 在es配置路径/elasticsearch-7.17.1/config下的elasticsearch.yml添加如下两行
[sandwich@centos-elk config]$ tail -n 2 elasticsearch.yml
xpack.security.enabled: true
discovery.type: single-node

# 2.为内置用户创建密码
# es内置用户用于特定的管理目的：apm_system，beats_system，elastic，kibana，logstash_system 和 remote_monitoring_user。
# 在使用它们之前，我们必须为它们设置密码。在 Elasticsearch 的目录里安装打入如下的命令：
[sandwich@centos-elk bin]$ ./elasticsearch-setup-passwords interactive
Initiating the setup of passwords for reserved users elastic,apm_system,kibana,kibana_system,logstash_system,beats_system,remote_monitoring_user.
You will be prompted to enter passwords as the process progresses.
Please confirm that you would like to continue [y/N]y


Enter password for [elastic]: 
Reenter password for [elastic]: 
Enter password for [apm_system]: 
Reenter password for [apm_system]: 
Enter password for [kibana_system]: 
Reenter password for [kibana_system]: 
Enter password for [logstash_system]: 
Reenter password for [logstash_system]: 
Enter password for [beats_system]: 
Reenter password for [beats_system]: 
Enter password for [remote_monitoring_user]: 
Reenter password for [remote_monitoring_user]: 
Changed password for user [apm_system]
Changed password for user [kibana_system]
Changed password for user [kibana]
Changed password for user [logstash_system]
Changed password for user [beats_system]
Changed password for user [remote_monitoring_user]
Changed password for user [elastic]

# 3.查看内置用户和权限
{{host}}/_security/user
```



#### <font face=幼圆 color=white>1.1.4.配置JDK</font>

```shell
# elasticsearch-env配置文件
# use elasticsearch jdk
ES_JAVA_HOME="/usr/lib/jvm/java-11-openjdk-11.0.18.0.10-1.el7_9.x86_64"

# 多JDK安装
# 安装jdk11
sudo yum install java-11-openjdk -y

# alternatives命令显示已安装jdk版本列表
alternatives --config java

# 若提示alternatives找不到则执行以下命令
update-alternatives --config java

# 若仍然没有变化则可能是因为原本就配置了环境变量的原因
# 前往 /etc/profile 中 找到配置jdk环境变量的地方，全部注释
```

![在这里插入图片描述](https://img-blog.csdnimg.cn/4fec0073387e4b60a23e9a8d1de42f85.png)

#### <font face=幼圆 color=white>1.1.5.启动</font>

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

### <font face=幼圆 color=white>2.3.账户</font>

```shell
# 为Kibana添加内置用户
# 明文
[sandwich@centos-elk config]$ tail -n 2 kibana.yml 
elasticsearch.username: "kibana"
elasticsearch.password: "123456"

# 加密
[sandwich@centos-elk bin]$ ./kibana-keystore create
Created Kibana keystore in /home/sandwich/app/elk/kibana-7.17.1-linux-x86_64/config/kibana.keystore
[sandwich@centos-elk bin]$ ./kibana-keystore add elasticsearch.username
Enter value for elasticsearch.username: ******
[sandwich@centos-elk bin]$ ./kibana-keystore add elasticsearch.password
Enter value for elasticsearch.password: ******

# url添加
./bin/kibana --elasticsearch.hosts="http://localhost:9200" --elasticsearch.username=kibana --elasticsearch.password=123456
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
    
    /**
     * 高亮查询（https://blog.csdn.net/qq_43750656/article/details/106752154）
     *
     * @param keyWords 关键词
     * @return java.util.List<com.sy.model.Item>
     **/
    @GetMapping("/highlightQuery")
    public List<Item> highlightQuery(String keyWords) throws IOException {
        List<Item> items = new ArrayList<>();

        //1.构建检索条件
        SearchRequest searchRequest = new SearchRequest();

        //2.指定要检索的索引库
        searchRequest.indices("item");

        //3.指定检索条件
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
        sourceBuilder.query(QueryBuilders.multiMatchQuery(keyWords, "title"));

        //4.结果高亮
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.requireFieldMatch(true); //如果该属性中有多个关键字 则都高亮
        highlightBuilder.field("title");
        highlightBuilder.preTags("<span style='color:red'>");
        highlightBuilder.postTags("</span>");
        sourceBuilder.highlighter(highlightBuilder);

        searchRequest.source(sourceBuilder);
        SearchResponse response = restHighLevelClient.search(searchRequest, EsConfig.COMMON_OPTIONS);
        SearchHit[] hits = response.getHits().getHits();
        for (SearchHit hit : hits) {
            // 如果不做高亮，则可以直接转为json，然后转为对象
            // String value = hit.getSourceAsString();
            // ESProductTO esProductTO = JSON.parseObject(value, ESProductTO.class);
            //解析高亮字段
            //获取当前命中的对象的高亮的字段
            Map<String, HighlightField> highlightFields = hit.getHighlightFields();
            HighlightField title = highlightFields.get("title");
            StringBuilder newName = new StringBuilder();
            if (title != null) {
                //获取该高亮字段的高亮信息
                Text[] fragments = title.getFragments();

                //将前缀、关键词、后缀进行拼接
                for (Text fragment : fragments) {
                    newName.append(fragment);
                }
            }

            //将高亮后的值替换掉旧值
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
            sourceAsMap.put("title", newName.toString());
            String json = JSON.toJSONString(sourceAsMap);
            Item item = JSON.parseObject(json, Item.class);
            items.add(item);
        }

        return items;
    }
}
```



## <font face=幼圆 color=white>四、 [ElasticsearchRestTemplate](https://blog.csdn.net/qq_43692950/article/details/122285770)</font>

```java
package com.sy.controller.es;

import com.sy.model.Item;
import com.sy.repository.ItemRepository;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.document.Document;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.ByQueryResponse;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * SpringBoot集成es
 *
 * @author lfeiyang
 * @since 2022-05-15 18:56
 */
@Slf4j
@RestController
public class ElasticsearchRestTemplateController {
    // 对索引操作
    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    // 对索引库数据操作
    @Autowired
    private ItemRepository itemRepository;

    /**
     * 自动生成，这块其实不需要了
     **/
    @GetMapping("/createIndexTemplate")
    public void createIndexTemplate() {
        // 创建索引库
        elasticsearchRestTemplate.indexOps(IndexCoordinates.of("item")).create();

        // 设置映射
        Document mapping = elasticsearchRestTemplate.indexOps(IndexCoordinates.of("item")).createMapping(Item.class);
        elasticsearchRestTemplate.indexOps(IndexCoordinates.of("item")).putMapping(mapping);
    }

    /**
     * 删除索引库
     *
     * @return java.lang.String
     **/
    @GetMapping("/deleteIndex")
    public boolean deleteIndex() {
        return elasticsearchRestTemplate.indexOps(IndexCoordinates.of("item")).delete();
    }

    /**
     * 保存文档
     *
     * @return com.sy.model.Item
     **/
    @GetMapping("/saveIndex")
    public Item saveIndex() {
        Item item = new Item(1L, "小米8", "手机", "小米", 3499.00, "https://image.baidu.com/13123.jpg");

        return itemRepository.save(item);
    }

    /**
     * 批量保存文档
     *
     * @return java.util.List<com.sy.model.Item>
     **/
    @GetMapping("/saveAllIndex")
    public List<Item> saveAllIndex() {
        List<Item> list = new ArrayList<>();

        list.add(new Item(2L, "小米8", "手机", "小米", 3499.00, "https://image.baidu.com/13123.jpg"));
        list.add(new Item(3L, "小米8", "手机", "小米", 3499.00, "https://image.baidu.com/13123.jpg"));
        list.add(new Item(4L, "小米8", "手机", "小米", 3499.00, "https://image.baidu.com/13123.jpg"));
        list.add(new Item(5L, "小米8", "手机", "小米", 3499.00, "https://image.baidu.com/13123.jpg"));

        return (List<Item>) itemRepository.saveAll(list);
    }

    /**
     * 主键删除
     *
     * @param id 文档主键
     * @return java.lang.String
     **/
    @GetMapping("/deleteDocumentById")
    public String deleteDocumentById(String id) {
        return elasticsearchRestTemplate.delete(id, IndexCoordinates.of("item"));
    }

    /**
     * 根据条件删除
     *
     * @return org.springframework.data.elasticsearch.core.query.ByQueryResponse
     **/
    @GetMapping("/deleteByQuery")
    public ByQueryResponse deleteByQuery() {
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();

        // 并且 and
        queryBuilder.must(QueryBuilders.termQuery("category", "手机"));
        queryBuilder.must(QueryBuilders.termQuery("id", 2));
        Query query = new NativeSearchQuery(queryBuilder);

        return elasticsearchRestTemplate.delete(query, Item.class, IndexCoordinates.of("item"));
    }

    /**
     * 分页查询
     *
     * @return java.util.List<com.sy.model.Item>
     **/
    @GetMapping("/getPage")
    public List<Item> getPage() {
        QueryBuilder queryBuilder = QueryBuilders.termQuery("title", "lisi");

        // 构建分页，page 从0开始
        Pageable pageable = PageRequest.of(0, 2);

        Query query = new NativeSearchQueryBuilder()
                .withQuery(queryBuilder)
                .withPageable(pageable)
                //排序
                .withSorts(SortBuilders.fieldSort("id").order(SortOrder.ASC))
                .build();

        SearchHits<Item> search = elasticsearchRestTemplate.search(query, Item.class);
        Stream<SearchHit<Item>> searchHitStream = search.get();
        return searchHitStream.map(SearchHit::getContent).collect(Collectors.toList());
    }

    /**
     * 精确单查询
     *
     * @param id 主键
     * @return java.util.List<com.sy.model.Item>
     **/
    @GetMapping("/termItem")
    public List<Item> termItem(String id) {
        QueryBuilder queryBuilder = QueryBuilders.termQuery("id", id);

        return getResult(queryBuilder, Item.class);
    }

    /**
     * 精确多查询
     *
     * @param ids 主键集
     * @return java.util.List<com.sy.model.Item>
     **/
    @GetMapping("/termsItem")
    public List<Item> termsItem(@RequestParam List<String> ids) {
        QueryBuilder queryBuilder = QueryBuilders.termsQuery("id", ids);

        return getResult(queryBuilder, Item.class);
    }

    /**
     * 分词单查询
     *
     * @param title 标题
     * @return java.util.List<com.sy.model.Item>
     **/
    @GetMapping("/matchItem")
    public List<Item> matchItem(String title) {
        QueryBuilder queryBuilder = QueryBuilders.matchQuery("title", title).analyzer("ik_max_word");

        return getResult(queryBuilder, Item.class);
    }

    /**
     * 分词多查询
     *
     * @param title 标题
     * @return java.util.List<com.sy.model.Item>
     **/
    @GetMapping("/multiMatchItem")
    public List<Item> multiMatchItem(String title) {
        QueryBuilder queryBuilder = QueryBuilders.multiMatchQuery(title, "title", "brand").analyzer("ik_max_word");

        return getResult(queryBuilder, Item.class);
    }

    /**
     * 模糊查询
     *
     * @param title 标题
     * @return java.util.List<com.sy.model.Item>
     **/
    @GetMapping("/fuzzyItem")
    public List<Item> fuzzyItem(String title) {
        FuzzyQueryBuilder queryBuilder = QueryBuilders.fuzzyQuery("title", title).fuzziness(Fuzziness.TWO);

        return getResult(queryBuilder, Item.class);
    }

    /**
     * 前缀查询
     *
     * @param prefix 前缀
     * @return java.util.List<com.sy.model.Item>
     **/
    @GetMapping("/prefixItem")
    public List<Item> prefixItem(String prefix) {
        PrefixQueryBuilder queryBuilder = QueryBuilders.prefixQuery("title", prefix);

        return getResult(queryBuilder, Item.class);
    }

    /**
     * 通配符查询
     *
     * @param wildcard 通配符
     * @return java.util.List<com.sy.model.Item>
     **/
    @GetMapping("/wildcardItem")
    public List<Item> wildcardItem(String wildcard) {
        WildcardQueryBuilder queryBuilder = QueryBuilders.wildcardQuery("title", wildcard);

        return getResult(queryBuilder, Item.class);
    }

    /**
     * 正则查询
     *
     * @param regex 正则
     * @return java.util.List<com.sy.model.Item>
     **/
    @GetMapping("/regexItem")
    public List<Item> regexItem(String regex) {
        RegexpQueryBuilder queryBuilder = QueryBuilders.regexpQuery("title", regex);
        queryBuilder.boost(2);

        return getResult(queryBuilder, Item.class);
    }

    /**
     * 多条件查询 -- and
     *
     * @return java.util.List<com.sy.model.Item>
     **/
    @GetMapping("/boolItem")
    public List<Item> boolItem() {
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();

        boolQueryBuilder.must(QueryBuilders.termQuery("title", "小米"));
        boolQueryBuilder.must(QueryBuilders.termQuery("brand", "小米"));

        return getResult(boolQueryBuilder, Item.class);
    }

    /**
     * 多条件查询 -- or
     *
     * @return java.util.List<com.sy.model.Item>
     **/
    @GetMapping("/shouldItem")
    public List<Item> shouldItem() {
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();

        boolQueryBuilder.should(QueryBuilders.termQuery("title", "小米"));
        boolQueryBuilder.should(QueryBuilders.termQuery("brand", "小米"));

        return getResult(boolQueryBuilder, Item.class);
    }

    /**
     * 多条件查询 -- 不等于
     *
     * @return java.util.List<com.sy.model.Item>
     **/
    @GetMapping("/mustNotItem")
    public List<Item> mustNotItem() {
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();

        boolQueryBuilder.mustNot(QueryBuilders.termQuery("title", "大米"));

        return getResult(boolQueryBuilder, Item.class);
    }

    /**
     * 多条件查询 -- filter
     *
     * @return java.util.List<com.sy.model.Item>
     **/
    @GetMapping("/filterItem")
    public List<Item> filterItem() {
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();

        boolQueryBuilder.filter(QueryBuilders.termQuery("title", "小米"));

        return getResult(boolQueryBuilder, Item.class);
    }

    /**
     * 范围查询
     *
     * @return java.util.List<com.sy.model.Item>
     **/
    @GetMapping("/rangeItem")
    public List<Item> rangeItem() {
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();

        boolQueryBuilder.filter(QueryBuilders.rangeQuery("price").gte("2000").lte("4000"));

        return getResult(boolQueryBuilder, Item.class);
    }

    public <T> List<T> getResult(QueryBuilder queryBuilder, Class<T> clazz) {
        Pageable pageable = PageRequest.of(0, 10);

        NativeSearchQuery query = new NativeSearchQueryBuilder()
                .withQuery(queryBuilder)
                .withPageable(pageable)
                .withSorts(SortBuilders.fieldSort("id").order(SortOrder.ASC))
                .build();

        SearchHits<T> search = elasticsearchRestTemplate.search(query, clazz);
        Stream<SearchHit<T>> searchHitStream = search.get();
        return searchHitStream.map(SearchHit::getContent).collect(Collectors.toList());
    }

}
```



## <font face=幼圆 color=white>五、 [分词器](https://github.com/medcl/elasticsearch-analysis-ik)</font>

```Java
把IK分词插件集成到Elasticsearch后,在SpringBoot中集成了Elasticsearch,就有了分词功能
```

