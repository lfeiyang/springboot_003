package com.sy.controller.es.get;

import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.rest.RestStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;

/**
 * Get响应
 *
 * @author lfeiyang
 * @since 2022-05-12 22:30
 */
@Slf4j
@RestController
@Scope(value = "singleton")
public class GetRequestResponseController {
    @Autowired
    private RestHighLevelClient restHighLevelClient;

    /**
     * Map形式
     *
     * @return java.util.Map<java.lang.String, java.lang.Object>
     **/
    @GetMapping("/getMapResponse")
    public Map<String, Object> getMapResponse() throws IOException {
        GetRequest request = new GetRequest("posts", "1");

        GetResponse getResponse = restHighLevelClient.get(request, RequestOptions.DEFAULT);

        return getResponse.getSourceAsMap();
    }

    /**
     * 字符串形式
     *
     * @return java.lang.String
     **/
    @GetMapping("/getStringResponse")
    public String getStringResponse() throws IOException {
        GetRequest request = new GetRequest("posts", "1");

        GetResponse getResponse = restHighLevelClient.get(request, RequestOptions.DEFAULT);

        return getResponse.getSourceAsString();
    }

    /**
     * 字节数组形式
     *
     * @return byte[]
     **/
    @GetMapping("/getByteResponse")
    public byte[] getByteResponse() throws IOException {
        GetRequest request = new GetRequest("posts", "1");

        GetResponse getResponse = restHighLevelClient.get(request, RequestOptions.DEFAULT);

        return getResponse.getSourceAsBytes();
    }

    /**
     * 没生成的索引
     **/
    @GetMapping("/getNotExistResponse")
    public void getNotExistResponse() throws IOException {
        GetRequest request = new GetRequest("does_not_exist", "1");
        try {
            GetResponse getResponse = restHighLevelClient.get(request, RequestOptions.DEFAULT);
        } catch (ElasticsearchException e) {
            if (e.status() == RestStatus.NOT_FOUND) {
                System.out.println("不存在");
            }
        }
    }

    /**
     * 版本冲突
     **/
    @GetMapping("/getConflictResponse")
    public void getConflictResponse() throws IOException {
        try {
            GetRequest request = new GetRequest("posts", "1").version(2);
            GetResponse getResponse = restHighLevelClient.get(request, RequestOptions.DEFAULT);
        } catch (ElasticsearchException exception) {
            if (exception.status() == RestStatus.CONFLICT) {
                System.out.println("版本冲突");
            }
        }
    }
}
