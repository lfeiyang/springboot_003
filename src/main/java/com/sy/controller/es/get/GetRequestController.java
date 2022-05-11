package com.sy.controller.es.get;

import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;

/**
 * 获取索引
 *
 * @author lfeiyang
 * @since 2022-05-11 22:42
 */
@Slf4j
@RestController
@Scope(value = "singleton")
public class GetRequestController {
    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @GetMapping("/getFirstRequest")
    public Map<String, Object> getFirstRequest() throws IOException {
        GetRequest getRequest = new GetRequest("posts", "1");

        GetResponse getResponse = restHighLevelClient.get(getRequest, RequestOptions.DEFAULT);

        return getResponse.getSourceAsMap();
    }
}
