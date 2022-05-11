package com.sy.controller.es.index;

import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.support.replication.ReplicationResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.rest.RestStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * [一句话注释]
 *
 * @author lfeiyang
 * @since 2022-05-11 22:10
 */
@Slf4j
@RestController
@Scope(value = "singleton")
public class IndexRequestResponseController {
    @Autowired
    private RestHighLevelClient restHighLevelClient;

    /**
     * Index响应
     **/
    @GetMapping("/indexResponse")
    public void indexResponse() throws IOException {
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("user", "lFeiYang7");
        jsonMap.put("postDate", new Date());
        jsonMap.put("message", "trying out Elasticsearch");

        IndexRequest request = new IndexRequest("posts").id("7").source(jsonMap);

        IndexResponse indexResponse = restHighLevelClient.index(request, RequestOptions.DEFAULT);

        String index = indexResponse.getIndex();
        String id = indexResponse.getId();

        // 1.处理（如果需要）第一次创建文档的情况
        if (indexResponse.getResult() == DocWriteResponse.Result.CREATED) {
            System.out.println(indexResponse.getResult());
        }
        // 2.处理（如果需要）文档已经存在而被重写的情况
        else if (indexResponse.getResult() == DocWriteResponse.Result.UPDATED) {
            System.out.println(indexResponse.getResult());
        }

        ReplicationResponse.ShardInfo shardInfo = indexResponse.getShardInfo();

        // 3.处理成功分片数小于总分片数的情况
        if (shardInfo.getTotal() != shardInfo.getSuccessful()) {
            System.out.println(shardInfo.getTotal());
        }

        // 4.处理潜在的故障
        if (shardInfo.getFailed() > 0) {
            for (ReplicationResponse.ShardInfo.Failure failure : shardInfo.getFailures()) {
                String reason = failure.reason();

                System.out.println(reason);
            }
        }
    }

    /**
     * 版本冲突
     **/
    @GetMapping("/conflict")
    public void conflict() throws IOException {
        IndexRequest request = new IndexRequest("posts")
                .id("1")
                .source("field", "value")
                .setIfSeqNo(10L)
                .setIfPrimaryTerm(20);
        try {
            IndexResponse response = restHighLevelClient.index(request, RequestOptions.DEFAULT);
        } catch (ElasticsearchException e) {
            if (e.status() == RestStatus.CONFLICT) {
                System.out.println("版本冲突");
            }
        }
    }
}
