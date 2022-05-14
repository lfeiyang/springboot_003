package com.sy.controller.es.doc.index;

import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 索引执行方式
 *
 * @author lfeiyang
 * @since 2022-05-11 21:50
 */
@Slf4j
@RestController
@Scope(value = "singleton")
public class IndexRequestExecutionController {
    @Autowired
    private RestHighLevelClient restHighLevelClient;

    /**
     * 同步执行
     **/
    @GetMapping("/synchronousExecution")
    public void synchronousExecution() throws IOException {
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("user", "lFeiYang5");
        jsonMap.put("postDate", new Date());
        jsonMap.put("message", "trying out Elasticsearch");

        IndexRequest request = new IndexRequest("posts").id("5").source(jsonMap);

        restHighLevelClient.index(request, RequestOptions.DEFAULT);
    }

    /**
     * 异步执行
     **/
    @GetMapping("/asynchronousExecution")
    public void asynchronousExecution() throws IOException {
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("user", "lFeiYang6");
        jsonMap.put("postDate", new Date());
        jsonMap.put("message", "trying out Elasticsearch");

        IndexRequest request = new IndexRequest("posts").id("6").source(jsonMap);

        // 异步监听器
        ActionListener<IndexResponse> listener = new ActionListener<IndexResponse>() {
            @Override
            public void onResponse(IndexResponse indexResponse) {
                System.out.println("Success");
            }

            @Override
            public void onFailure(Exception e) {
                System.out.println("Failure");
            }
        };

        restHighLevelClient.indexAsync(request, RequestOptions.DEFAULT, listener);
    }
}
