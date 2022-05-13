package com.sy.controller.es.index;

import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 索引
 *
 * @author lfeiyang
 * @since 2022-05-11 21:04
 */
@Slf4j
@RestController
@Scope(value = "singleton")
public class IndexRequestController {
    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @GetMapping("/firstIndex")
    public void firstIndex() throws IOException {
        // 1.Index索引名
        IndexRequest request = new IndexRequest("posts");

        // 2.请求文档的ID
        request.id("1");

        // 3.文档来源
        String jsonString = "{" +
                "\"user\":\"lFeiYang\"," +
                "\"postDate\":\"2013-01-30\"," +
                "\"count\":\"1\"," +
                "\"message\":\"trying out Elasticsearch\"" +
                "}";

        request.source(jsonString, XContentType.JSON);

        // 4.同步执行
        restHighLevelClient.index(request, RequestOptions.DEFAULT);
    }

    /**
     * 提供的文档源Map自动转换为 JSON 格式
     **/
    @GetMapping("/secondIndex")
    public void secondIndex() throws IOException {
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("user", "lFeiYang2");
        jsonMap.put("postDate", new Date());
        jsonMap.put("message", "trying out Elasticsearch");

        IndexRequest request = new IndexRequest("posts").id("2").source(jsonMap);

        restHighLevelClient.index(request, RequestOptions.DEFAULT);
    }

    /**
     * 文档源作为XContentBuilder对象提供，Elasticsearch 内置帮助器生成 JSON 内容
     **/
    @GetMapping("/thirdIndex")
    public void thirdIndex() throws IOException {
        XContentBuilder builder = XContentFactory.jsonBuilder();
        builder.startObject();
        {
            builder.field("user", "lFeiYang3");
            builder.timeField("postDate", new Date());
            builder.field("message", "trying out Elasticsearch");
        }
        builder.endObject();

        IndexRequest request = new IndexRequest("posts").id("3").source(builder);

        restHighLevelClient.index(request, RequestOptions.DEFAULT);
    }

    /**
     * 以密钥对形式提供的文档源Object，将其转换为 JSON 格式
     **/
    @GetMapping("/fourIndex")
    public void fourIndex() throws IOException {
        IndexRequest request = new IndexRequest("posts")
                .id("4")
                .source("user", "lFeiYang4",
                        "postDate", new Date(),
                        "message", "trying out Elasticsearch");

        restHighLevelClient.index(request, RequestOptions.DEFAULT);
    }
}
