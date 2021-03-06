package com.sy.controller;

import com.alibaba.fastjson.JSON;
import com.sy.config.EsConfig;
import com.sy.model.Hero;
import com.sy.model.Item;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.core.TimeValue;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.DeleteByQueryRequest;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * es????????????
 *
 * @author lfeiyang
 * @since 2022-05-09 1:04
 */
@Slf4j
@RestController
@Scope(value = "singleton")
public class EsController {
    @Autowired
    private RestHighLevelClient restHighLevelClient;

    /**
     * ????????????
     *
     * @return java.lang.String
     **/
    @GetMapping("/createIndex")
    public Long createIndex() throws IOException {
        IndexRequest request = new IndexRequest("hero");
        request.id("1");
        Map<String, String> map = new HashMap<>(100);
        map.put("id", "1");
        map.put("name", "??????");
        map.put("country", "???");
        map.put("birthday", "??????155???");
        map.put("longevity", "65");
        request.source(map);
        IndexResponse indexResponse = restHighLevelClient.index(request, RequestOptions.DEFAULT);

        return indexResponse.getVersion();
    }

    /**
     * ????????????
     *
     * @return org.elasticsearch.action.bulk.BulkResponse
     **/
    @GetMapping("/bulkRequestTest")
    public BulkResponse bulkRequestTest() throws IOException {
        BulkRequest request = new BulkRequest();
        request.add(new IndexRequest("hero").id("2")
                .source(XContentType.JSON, "id", "2", "name", "??????", "country", "???", "birthday", "??????161???", "longevity", "61"));
        request.add(new IndexRequest("hero").id("3")
                .source(XContentType.JSON, "id", "3", "name", "??????", "country", "???", "birthday", "??????182???", "longevity", "61"));
        request.add(new IndexRequest("hero").id("4")
                .source(XContentType.JSON, "id", "4", "name", "?????????", "country", "???", "birthday", "??????181???", "longevity", "53"));
        request.add(new IndexRequest("hero").id("5")
                .source(XContentType.JSON, "id", "5", "name", "?????????", "country", "???", "birthday", "??????179???", "longevity", "72"));
        request.add(new IndexRequest("hero").id("6")
                .source(XContentType.JSON, "id", "6", "name", "??????", "country", "???", "birthday", "??????163???", "longevity", "49"));
        request.add(new IndexRequest("hero").id("7")
                .source(XContentType.JSON, "id", "7", "name", "??????", "country", "???", "birthday", "??????160???", "longevity", "60"));
        request.add(new IndexRequest("hero").id("8")
                .source(XContentType.JSON, "id", "8", "name", "??????", "country", "???", "birthday", "??????175???", "longevity", "35"));

        return restHighLevelClient.bulk(request, RequestOptions.DEFAULT);
    }

    @GetMapping("/updateUser")
    public DocWriteResponse.Result updateTest() throws IOException {
        Map<String, Object> jsonMap = new HashMap<>(100);
        jsonMap.put("country", "???");

        UpdateRequest request = new UpdateRequest("hero", "7").doc(jsonMap);
        UpdateResponse updateResponse = restHighLevelClient.update(request, RequestOptions.DEFAULT);

        return updateResponse.getResult();
    }

    /**
     * ??????/????????????
     *
     * @return org.elasticsearch.action.index.IndexResponse
     **/
    @GetMapping("/insertOrUpdateOne")
    public IndexResponse insertOrUpdateOne() {
        Hero hero = new Hero();
        hero.setId(5);
        hero.setName("??????");
        hero.setCountry("???");
        hero.setBirthday("??????187???");
        hero.setLongevity(39);
        IndexRequest request = new IndexRequest("hero");
        request.id(hero.getId().toString());
        request.source(JSON.toJSONString(hero), XContentType.JSON);
        try {
            return restHighLevelClient.index(request, RequestOptions.DEFAULT);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * ????????????
     *
     * @return org.elasticsearch.action.delete.DeleteResponse
     **/
    @GetMapping("/deleteById")
    public DeleteResponse deleteById() throws IOException {
        DeleteRequest deleteRequest = new DeleteRequest("hero");
        deleteRequest.id("1");

        return restHighLevelClient.delete(deleteRequest, RequestOptions.DEFAULT);
    }

    /**
     * ????????????
     *
     * @return int
     **/
    @GetMapping("/deleteByQueryRequest")
    public BulkByScrollResponse deleteByQueryRequest() throws IOException {
        DeleteByQueryRequest request = new DeleteByQueryRequest("hero");
        request.setConflicts("proceed");
        request.setQuery(new TermQueryBuilder("country", "???"));

        return restHighLevelClient.deleteByQuery(request, RequestOptions.DEFAULT);
    }

    /**
     * ????????????
     *
     * @return boolean
     **/
    @GetMapping("/bulkDiffRequest")
    public boolean bulkDiffRequest() throws IOException {
        BulkRequest request = new BulkRequest();
        request.add(new DeleteRequest("hero", "3"));
        request.add(new UpdateRequest("hero", "7").doc(XContentType.JSON, "longevity", "70"));

        BulkResponse bulkResponse = restHighLevelClient.bulk(request, RequestOptions.DEFAULT);
        BulkItemResponse[] bulkItemResponses = bulkResponse.getItems();
        for (BulkItemResponse item : bulkItemResponses) {
            DocWriteResponse itemResponse = item.getResponse();
            switch (item.getOpType()) {
                case UPDATE:
                    UpdateResponse updateResponse = (UpdateResponse) itemResponse;
                    break;
                case DELETE:
                    DeleteResponse deleteResponse = (DeleteResponse) itemResponse;
                default:
            }

            return RestStatus.OK.equals(item.status());
        }

        return false;
    }

    /**
     * ??????????????? + limit
     *
     * @return java.lang.String
     **/
    @GetMapping("/selectByUser")
    public String selectByUser() {
        SearchRequest request = new SearchRequest("hero");
        SearchSourceBuilder builder = new SearchSourceBuilder();
        builder.query(new TermQueryBuilder("country", "???"));
        // ?????????mysql?????????limit 1???
        builder.size(3);
        request.source(builder);
        try {
            SearchResponse response = restHighLevelClient.search(request, RequestOptions.DEFAULT);
            SearchHit[] hits = response.getHits().getHits();

            return Arrays.toString(hits);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * ??????????????? + ?????? + ??????
     **/
    @GetMapping("/boolQuery")
    public void boolQuery() {
        SearchRequest request = new SearchRequest("hero");
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        boolQueryBuilder.must(QueryBuilders.termQuery("country", "???"));
        boolQueryBuilder.must(QueryBuilders.rangeQuery("longevity").gte(50));
        sourceBuilder.query(boolQueryBuilder);
        sourceBuilder.from(0).size(2);
        sourceBuilder.query(boolQueryBuilder);
        sourceBuilder.sort("longevity", SortOrder.DESC);
        request.source(sourceBuilder);
        SearchResponse response = null;
        try {
            response = restHighLevelClient.search(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            log.error("Query by Condition execution failed: {}", e.getMessage(), e);
        }
        assert response != null;
        SearchHit[] hits = response.getHits().getHits();
        List<Hero> herosList = new ArrayList<>(hits.length);
        for (SearchHit hit : hits) {
            herosList.add(JSON.parseObject(hit.getSourceAsString(), Hero.class));
        }

        log.info("print info: {}, size: {}", herosList.toString(), herosList.size());
    }

    /**
     * ????????????
     *
     * @param keyWords ?????????
     * @return java.util.List<com.sy.model.Item>
     **/
    @GetMapping("/highlightQuery")
    public List<Item> highlightQuery(String keyWords) throws IOException {
        List<Item> items = new ArrayList<>();

        //1.??????????????????
        SearchRequest searchRequest = new SearchRequest();

        //2.???????????????????????????
        searchRequest.indices("item");

        //3.??????????????????
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
        sourceBuilder.query(QueryBuilders.multiMatchQuery(keyWords, "title").analyzer("ik_smart"));

        // 4.????????????
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        // ???????????????????????????????????? ????????????
        highlightBuilder.requireFieldMatch(true);
        highlightBuilder.field("title");
        highlightBuilder.preTags("<span style='color:red'>");
        highlightBuilder.postTags("</span>");
        sourceBuilder.highlighter(highlightBuilder);

        searchRequest.source(sourceBuilder);
        SearchResponse response = restHighLevelClient.search(searchRequest, EsConfig.COMMON_OPTIONS);
        SearchHit[] hits = response.getHits().getHits();
        for (SearchHit hit : hits) {
            // ??????????????????????????????????????????json?????????????????????
            // String value = hit.getSourceAsString();
            // ESProductTO esProductTO = JSON.parseObject(value, ESProductTO.class);
            // ??????????????????
            // ?????????????????????????????????????????????
            Map<String, HighlightField> highlightFields = hit.getHighlightFields();
            HighlightField title = highlightFields.get("title");
            StringBuilder newName = new StringBuilder();
            if (title != null) {
                //????????????????????????????????????
                Text[] fragments = title.getFragments();

                //??????????????????????????????????????????
                for (Text fragment : fragments) {
                    newName.append(fragment);
                }
            }

            //?????????????????????????????????
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
            sourceAsMap.put("title", newName.toString());
            String json = JSON.toJSONString(sourceAsMap);
            Item item = JSON.parseObject(json, Item.class);
            items.add(item);
        }

        return items;
    }
}
