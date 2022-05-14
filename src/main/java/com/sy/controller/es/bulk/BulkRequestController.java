package com.sy.controller.es.bulk;

import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.bulk.BackoffPolicy;
import org.elasticsearch.action.bulk.BulkProcessor;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.ByteSizeUnit;
import org.elasticsearch.common.unit.ByteSizeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.core.TimeValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * 批处理
 *
 * @author lfeiyang
 * @since 2022-05-14 12:49
 */
@Slf4j
@RestController
@Scope(value = "singleton")
public class BulkRequestController {
    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @GetMapping("/firstBulk")
    public void firstBulk() throws IOException {
        BulkRequest request = new BulkRequest();
        request.add(new IndexRequest("posts").id("1").source(XContentType.JSON, "field", "foo"));
        request.add(new IndexRequest("posts").id("2").source(XContentType.JSON, "field", "bar"));
        request.add(new IndexRequest("posts").id("3").source(XContentType.JSON, "field", "baz"));
        request.add(new IndexRequest("posts").id("4").source(XContentType.JSON, "field", "baz"));

        restHighLevelClient.bulk(request, RequestOptions.DEFAULT);
    }

    @GetMapping("/secondBulk")
    public void secondBulk() throws IOException {
        BulkRequest request = new BulkRequest();
        request.add(new DeleteRequest("posts", "3"));
        request.add(new UpdateRequest("posts", "2").doc(XContentType.JSON, "other", "test"));
        request.add(new IndexRequest("posts").id("4").source(XContentType.JSON, "field", "baz"));

        restHighLevelClient.bulk(request, RequestOptions.DEFAULT);
    }

    /**
     * 批处理过程
     *
     * @return boolean
     **/
    @GetMapping("/bulkProcessor")
    public boolean bulkProcessor() throws InterruptedException {
        BulkProcessor.Listener listener = new BulkProcessor.Listener() {
            @Override
            public void beforeBulk(long executionId, BulkRequest request) {
                int numberOfActions = request.numberOfActions();
                log.debug("Executing bulk [{}] with {} requests", executionId, numberOfActions);
            }

            @Override
            public void afterBulk(long executionId, BulkRequest request, BulkResponse response) {
                if (response.hasFailures()) {
                    log.warn("Bulk [{}] executed with failures", executionId);
                } else {
                    log.debug("Bulk [{}] completed in {} milliseconds", executionId, response.getTook().getMillis());
                }
            }

            @Override
            public void afterBulk(long executionId, BulkRequest request, Throwable failure) {
                log.error("Failed to execute bulk", failure);
            }
        };

        BulkProcessor.Builder builder = BulkProcessor.builder((request, bulkListener) -> restHighLevelClient.bulkAsync(request, RequestOptions.DEFAULT, bulkListener), listener);

        builder.setBulkActions(500);
        builder.setBulkSize(new ByteSizeValue(1L, ByteSizeUnit.MB));
        builder.setConcurrentRequests(0);
        builder.setFlushInterval(TimeValue.timeValueSeconds(10L));
        builder.setBackoffPolicy(BackoffPolicy.constantBackoff(TimeValue.timeValueSeconds(1L), 3));

        IndexRequest one = new IndexRequest("posts").id("1").source(XContentType.JSON, "title", "In which order are my Elasticsearch queries executed?");
        IndexRequest two = new IndexRequest("posts").id("2").source(XContentType.JSON, "title", "Current status and upcoming changes in Elasticsearch");
        IndexRequest three = new IndexRequest("posts").id("3").source(XContentType.JSON, "title", "The Future of Federated Search in Elasticsearch");

        BulkProcessor bulkProcessor = builder.build();
        bulkProcessor.add(one);
        bulkProcessor.add(two);
        bulkProcessor.add(three);

        return bulkProcessor.awaitClose(30L, TimeUnit.SECONDS);
    }
}
