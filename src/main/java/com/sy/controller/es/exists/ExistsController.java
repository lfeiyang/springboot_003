package com.sy.controller.es.exists;

import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * 存在请求
 *
 * @author lfeiyang
 * @since 2022-05-12 23:04
 */
@Slf4j
@RestController
@Scope(value = "singleton")
public class ExistsController {
    @Autowired
    private RestHighLevelClient restHighLevelClient;

    /**
     * 同步执行
     *
     * @return boolean
     **/
    @GetMapping("/existsSynchronousExecution")
    public boolean existsSynchronousExecution() throws IOException {
        GetRequest getRequest = new GetRequest("posts", "1");
        getRequest.fetchSourceContext(new FetchSourceContext(false));
        getRequest.storedFields("_none_");

        return restHighLevelClient.exists(getRequest, RequestOptions.DEFAULT);
    }

    /**
     * 异步执行
     **/
    @GetMapping("/existsAsynchronousExecution")
    public void existsAsynchronousExecution() throws IOException {
        GetRequest getRequest = new GetRequest("posts", "1");
        getRequest.fetchSourceContext(new FetchSourceContext(false));
        getRequest.storedFields("_none_");

        ActionListener<Boolean> listener = new ActionListener<Boolean>() {
            @Override
            public void onResponse(Boolean exists) {
                System.out.println("Success");
            }

            @Override
            public void onFailure(Exception e) {
                System.out.println("Failure");
            }
        };

        restHighLevelClient.existsAsync(getRequest, RequestOptions.DEFAULT, listener);
    }
}
