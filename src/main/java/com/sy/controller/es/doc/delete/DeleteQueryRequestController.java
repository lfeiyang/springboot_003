package com.sy.controller.es.doc.delete;

import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.DeleteByQueryRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * 查询删除
 *
 * @author lfeiyang
 * @since 2022-05-14 18:55
 */
@Slf4j
@RestController
@Scope(value = "singleton")
public class DeleteQueryRequestController {
    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @GetMapping("/deleteQueryRequest")
    public BulkByScrollResponse deleteQueryRequest() throws IOException {
        DeleteByQueryRequest request = new DeleteByQueryRequest("dest");
        request.setConflicts("proceed");
        request.setQuery(new TermQueryBuilder("country", "蜀"));

        return restHighLevelClient.deleteByQuery(request, RequestOptions.DEFAULT);
    }
}
