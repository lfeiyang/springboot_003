package com.sy.controller.es.update;

import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.UpdateByQueryRequest;
import org.elasticsearch.script.Script;
import org.elasticsearch.script.ScriptType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Collections;

/**
 * 查询更新
 *
 * @author lfeiyang
 * @since 2022-05-14 15:39
 */
@Slf4j
@RestController
@Scope(value = "singleton")
public class updateQueryRequestController {
    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @GetMapping("/updateQueryRequest")
    public BulkByScrollResponse updateQueryRequest() throws IOException {
        UpdateByQueryRequest request = new UpdateByQueryRequest("dest");

        request.setConflicts("proceed");
        request.setQuery(new TermQueryBuilder("name", "刘备"));
        request.setMaxDocs(10);

        request.setScript(new Script(ScriptType.INLINE, "painless",
                "if (ctx._source.name == '刘备') {ctx._source.country = '魏';}",
                Collections.emptyMap()));

        return restHighLevelClient.updateByQuery(request, RequestOptions.DEFAULT);
    }
}
