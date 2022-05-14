package com.sy.controller.es.doc.update;

import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.script.Script;
import org.elasticsearch.script.ScriptType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 修改索引
 *
 * @author lfeiyang
 * @since 2022-05-13 20:46
 */
@Slf4j
@RestController
@Scope(value = "singleton")
public class UpdateRequestController {
    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @GetMapping("/updateScript")
    public UpdateResponse updateScript() throws IOException {
        UpdateRequest request = new UpdateRequest("posts", "1");

        Map<String, Object> parameters = Collections.singletonMap("count", 4);

        Script inline = new Script(ScriptType.INLINE, "painless", "ctx._source.field += params.count", parameters);

        request.script(inline);
        request.scriptedUpsert(true);

        return restHighLevelClient.update(request, RequestOptions.DEFAULT);
    }

    /**
     * 部分文档更新
     *
     * @return org.elasticsearch.action.update.UpdateResponse
     **/
    @GetMapping("/updatePartialDocument")
    public UpdateResponse updatePartialDocument() throws IOException {
        UpdateRequest request = new UpdateRequest("posts", "1");

        String jsonString = "{" +
                "\"updated\":\"2017-01-01\"," +
                "\"reason\":\"daily update\"" +
                "}";

        request.doc(jsonString, XContentType.JSON);

        return restHighLevelClient.update(request, RequestOptions.DEFAULT);
    }

    /**
     * Map部分文档更新
     *
     * @return org.elasticsearch.action.update.UpdateResponse
     **/
    @GetMapping("/updateMapDocument")
    public UpdateResponse updateMapDocument() throws IOException {
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("updated", new Date());
        jsonMap.put("reason", "daily update");
        UpdateRequest request = new UpdateRequest("posts", "1").doc(jsonMap);

        return restHighLevelClient.update(request, RequestOptions.DEFAULT);
    }

    /**
     * 不存在会创建文档
     *
     * @return org.elasticsearch.action.update.UpdateResponse
     **/
    @GetMapping("/upserts")
    public UpdateResponse upserts() throws IOException {
        UpdateRequest request = new UpdateRequest("posts", "1");

        String jsonString = "{\"doc\":{\"created\":\"2017-01-01\"}}";
        request.doc(jsonString, XContentType.JSON); // 存在执行
        request.upsert(jsonString, XContentType.JSON); // 不存在执行

        return restHighLevelClient.update(request, RequestOptions.DEFAULT);
    }
}
