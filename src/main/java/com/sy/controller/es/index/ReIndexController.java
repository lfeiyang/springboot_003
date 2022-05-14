package com.sy.controller.es.index;

import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.ReindexRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * 复制索引
 *
 * @author lfeiyang
 * @since 2022-05-14 15:13
 */
@Slf4j
@RestController
@Scope(value = "singleton")
public class ReIndexController {
    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @GetMapping("/reIndex")
    public BulkByScrollResponse reIndex() throws IOException {
        ReindexRequest request = new ReindexRequest();
        // 源索引
        request.setSourceIndices("hero", "posts");
        // 目标索引
        request.setDestIndex("dest");

        return restHighLevelClient.reindex(request, RequestOptions.DEFAULT);
    }
}
