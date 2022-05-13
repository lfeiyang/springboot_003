package com.sy.controller.es.delete;

import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.support.replication.ReplicationResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * 删除索引
 *
 * @author lfeiyang
 * @since 2022-05-13 18:52
 */
@Slf4j
@RestController
@Scope(value = "singleton")
public class DeleteRequestController {
    @Autowired
    private RestHighLevelClient restHighLevelClient;

    /**
     * 同步执行
     *
     * @return int
     **/
    @GetMapping("/deleteSynchronousExecution")
    public int deleteSynchronousExecution() throws IOException {
        DeleteRequest request = new DeleteRequest("posts", "1");

        DeleteResponse deleteResponse = restHighLevelClient.delete(request, RequestOptions.DEFAULT);

        String index = deleteResponse.getIndex();
        String id = deleteResponse.getId();
        long version = deleteResponse.getVersion();
        ReplicationResponse.ShardInfo shardInfo = deleteResponse.getShardInfo();
        if (shardInfo.getTotal() != shardInfo.getSuccessful()) {
            return shardInfo.getTotal();
        }

        if (shardInfo.getFailed() > 0) {
            for (ReplicationResponse.ShardInfo.Failure failure : shardInfo.getFailures()) {
                String reason = failure.reason();

                System.out.println(reason);
            }
        }

        return 0;
    }

    /**
     * 异步执行
     **/
    @GetMapping("/deleteAsynchronousExecution")
    public void deleteAsynchronousExecution() throws IOException {
        DeleteRequest request = new DeleteRequest("posts", "2");

        ActionListener<DeleteResponse> listener = new ActionListener<DeleteResponse>() {
            @Override
            public void onResponse(DeleteResponse deleteResponse) {
                System.out.println("Success");
            }

            @Override
            public void onFailure(Exception e) {
                System.out.println("Failure");
            }
        };

        restHighLevelClient.deleteAsync(request, RequestOptions.DEFAULT, listener);
    }

    /**
     * 不存在
     *
     * @return java.lang.String
     **/
    @GetMapping("/deleteNotFound")
    public String deleteNotFound() throws IOException {
        DeleteRequest request = new DeleteRequest("posts", "does_not_exist");
        DeleteResponse deleteResponse = restHighLevelClient.delete(request, RequestOptions.DEFAULT);
        if (deleteResponse.getResult() == DocWriteResponse.Result.NOT_FOUND) {
            return "不存在";
        }

        return null;
    }
}
