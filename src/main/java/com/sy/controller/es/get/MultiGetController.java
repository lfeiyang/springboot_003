package com.sy.controller.es.get;

import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.get.MultiGetRequest;
import org.elasticsearch.action.get.MultiGetResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * 复查询
 *
 * @author lfeiyang
 * @since 2022-05-14 14:55
 */
@Slf4j
@RestController
@Scope(value = "singleton")
public class MultiGetController {
    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @GetMapping("/mGet")
    public MultiGetResponse mGet() throws IOException {
        MultiGetRequest request = new MultiGetRequest();
        request.add(new MultiGetRequest.Item("posts", "1"));
        request.add(new MultiGetRequest.Item("posts", "2"));

        return restHighLevelClient.mget(request, RequestOptions.DEFAULT);
    }
}
