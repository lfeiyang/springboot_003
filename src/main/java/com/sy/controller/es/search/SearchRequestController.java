package com.sy.controller.es.search;

import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.core.TimeValue;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.ScoreSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * 搜索
 *
 * @author lfeiyang
 * @since 2022-05-14 19:07
 */
@Slf4j
@RestController
@Scope(value = "singleton")
public class SearchRequestController {
    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @GetMapping("/searchMatchAll")
    public SearchResponse searchMatchAll() throws IOException {
        SearchRequest searchRequest = new SearchRequest("posts");

        // 搜索设置器
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        searchRequest.source(searchSourceBuilder);

        return restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
    }

    /**
     * 分页
     *
     * @return org.elasticsearch.action.search.SearchResponse
     **/
    @GetMapping("/searchLimit")
    public SearchResponse searchLimit() throws IOException {
        SearchRequest searchRequest = new SearchRequest("posts");

        // 搜索设置器
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        // 查询条件
        QueryBuilder matchQueryBuilder = QueryBuilders.matchQuery("user", "lFeiYan")
                .fuzziness(Fuzziness.AUTO) // 启用模糊匹配
                .prefixLength(3) // 前缀长度选择
                .maxExpansions(10); // 扩展后缀模糊长度
        searchSourceBuilder.query(matchQueryBuilder);

        // 分页
        searchSourceBuilder.from(0);
        searchSourceBuilder.size(10);
        searchSourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));

        // 排序
        searchSourceBuilder.sort(new ScoreSortBuilder().order(SortOrder.DESC));
        searchSourceBuilder.sort(new FieldSortBuilder("_id").order(SortOrder.ASC));

        // _source检索
        String[] includeFields = new String[]{"message", "innerObject.*"};
        String[] excludeFields = new String[]{"user"};
        searchSourceBuilder.fetchSource(includeFields, excludeFields);

        // 高亮
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        HighlightBuilder.Field highlightUser = new HighlightBuilder.Field("user");
        highlightBuilder.field(highlightUser);
        searchSourceBuilder.highlighter(highlightBuilder);

        searchRequest.source(searchSourceBuilder);
        return restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
    }
}
