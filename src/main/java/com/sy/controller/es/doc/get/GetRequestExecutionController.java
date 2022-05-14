package com.sy.controller.es.doc.get;

import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.ActionListener;
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
 * 查询执行方式
 *
 * @author lfeiyang
 * @since 2022-05-12 22:30
 */
@Slf4j
@RestController
@Scope(value = "singleton")
public class GetRequestExecutionController {
    @Autowired
    private RestHighLevelClient restHighLevelClient;

    /**
     * 同步执行
     *
     * @return java.util.Map<java.lang.String, java.lang.Object>
     **/
    @GetMapping("/getSynchronousExecution")
    public Map<String, Object> getSynchronousExecution() throws IOException {
        GetRequest request = new GetRequest("posts", "1");

        GetResponse getResponse = restHighLevelClient.get(request, RequestOptions.DEFAULT);

        return getResponse.getSourceAsMap();
    }

    /**
     * 异步执行
     **/
    @GetMapping("/getAsynchronousExecution")
    public void getAsynchronousExecution() throws IOException {
        GetRequest request = new GetRequest("posts", "1");

        ActionListener<GetResponse> listener = new ActionListener<GetResponse>() {
            @Override
            public void onResponse(GetResponse getResponse) {
                System.out.println("Success");
            }

            @Override
            public void onFailure(Exception e) {
                System.out.println("Failure");
            }
        };

        restHighLevelClient.getAsync(request, RequestOptions.DEFAULT, listener);
    }
}
