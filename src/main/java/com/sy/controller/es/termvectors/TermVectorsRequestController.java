package com.sy.controller.es.termvectors;

import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.core.TermVectorsRequest;
import org.elasticsearch.client.core.TermVectorsResponse;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * 术语向量
 *
 * @author lfeiyang
 * @since 2022-05-14 11:57
 */
@Slf4j
@RestController
@Scope(value = "singleton")
public class TermVectorsRequestController {
    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @GetMapping("/termVectorsRequest")
    public TermVectorsResponse termVectorsRequest() throws IOException {
        TermVectorsRequest request = new TermVectorsRequest("hero", "2");
        request.setFields("user");

        return restHighLevelClient.termvectors(request, RequestOptions.DEFAULT);
    }

    @GetMapping("/termVectorsRequestBuild")
    public TermVectorsResponse termVectorsRequestBuild() throws IOException {
        XContentBuilder docBuilder = XContentFactory.jsonBuilder();
        docBuilder.startObject().field("user", "guest-user").endObject();
        TermVectorsRequest request = new TermVectorsRequest("hero",docBuilder);

        return restHighLevelClient.termvectors(request, RequestOptions.DEFAULT);
    }
}
