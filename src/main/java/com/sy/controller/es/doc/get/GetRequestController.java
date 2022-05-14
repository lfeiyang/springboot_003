package com.sy.controller.es.doc.get;

import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.Strings;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
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
        GetRequest request = new GetRequest("posts", "1");

        GetResponse getResponse = restHighLevelClient.get(request, RequestOptions.DEFAULT);

        return getResponse.getSourceAsMap();
    }

    /**
     * 不包含数据源
     *
     * @return java.util.Map<java.lang.String, java.lang.Object>
     **/
    @GetMapping("/getNotSourceRequest")
    public Map<String, Object> getNotSourceRequest() throws IOException {
        GetRequest request = new GetRequest("posts", "1");

        request.fetchSourceContext(FetchSourceContext.DO_NOT_FETCH_SOURCE);

        GetResponse getResponse = restHighLevelClient.get(request, RequestOptions.DEFAULT);

        return getResponse.getSourceAsMap();
    }

    /**
     * 返回包含指定字段数据
     *
     * @return java.util.Map<java.lang.String, java.lang.Object>
     **/
    @GetMapping("/getIncludesRequest")
    public Map<String, Object> getIncludesRequest() throws IOException {
        GetRequest request = new GetRequest("posts", "1");

        String[] includes = new String[]{"message", "*Date"};
        String[] excludes = Strings.EMPTY_ARRAY;
        FetchSourceContext fetchSourceContext = new FetchSourceContext(true, includes, excludes);
        request.fetchSourceContext(fetchSourceContext);

        GetResponse getResponse = restHighLevelClient.get(request, RequestOptions.DEFAULT);

        return getResponse.getSourceAsMap();
    }

    /**
     * 返回不包含指定字段数据
     *
     * @return java.util.Map<java.lang.String, java.lang.Object>
     **/
    @GetMapping("/getExcludesRequest")
    public Map<String, Object> getExcludesRequest() throws IOException {
        GetRequest request = new GetRequest("posts", "1");

        String[] includes = Strings.EMPTY_ARRAY;
        String[] excludes = new String[]{"message"};
        FetchSourceContext fetchSourceContext = new FetchSourceContext(true, includes, excludes);
        request.fetchSourceContext(fetchSourceContext);

        GetResponse getResponse = restHighLevelClient.get(request, RequestOptions.DEFAULT);

        return getResponse.getSourceAsMap();
    }

    /**
     * 这个要设置store为true，暂时不会
     *
     * @return java.util.Map<java.lang.String, java.lang.Object>
     **/
    @GetMapping("/getStoreFieldRequest")
    public Map<String, Object> getStoreFieldRequest() throws IOException {
        GetRequest request = new GetRequest("posts", "1");

        request.storedFields("message");
        GetResponse getResponse = restHighLevelClient.get(request, RequestOptions.DEFAULT);

        return getResponse.getField("message").getValue();
    }

}
