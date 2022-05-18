package com.sy.controller.es;

import com.sy.model.Item;
import com.sy.repository.ItemRepository;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.document.Document;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.ByQueryResponse;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * SpringBoot集成es
 *
 * @author lfeiyang
 * @since 2022-05-15 18:56
 */
@Slf4j
@RestController
public class ElasticsearchRestTemplateController {
    // 对索引操作
    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    // 对索引库数据操作
    @Autowired
    private ItemRepository itemRepository;

    /**
     * 自动生成，这块其实不需要了
     **/
    @GetMapping("/createIndexTemplate")
    public void createIndexTemplate() {
        // 创建索引库
        elasticsearchRestTemplate.indexOps(IndexCoordinates.of("item")).create();

        // 设置映射
        Document mapping = elasticsearchRestTemplate.indexOps(IndexCoordinates.of("item")).createMapping(Item.class);
        elasticsearchRestTemplate.indexOps(IndexCoordinates.of("item")).putMapping(mapping);
    }

    /**
     * 删除索引库
     *
     * @return java.lang.String
     **/
    @GetMapping("/deleteIndex")
    public boolean deleteIndex() {
        return elasticsearchRestTemplate.indexOps(IndexCoordinates.of("item")).delete();
    }

    /**
     * 保存文档
     *
     * @return com.sy.model.Item
     **/
    @GetMapping("/saveIndex")
    public Item saveIndex() {
        Item item = new Item(1L, "小米8", "手机", "小米", 3499.00, "https://image.baidu.com/13123.jpg");

        return itemRepository.save(item);
    }

    /**
     * 批量保存文档
     *
     * @return java.util.List<com.sy.model.Item>
     **/
    @GetMapping("/saveAllIndex")
    public List<Item> saveAllIndex() {
        List<Item> list = new ArrayList<>();

        list.add(new Item(2L, "小米8", "手机", "小米", 3499.00, "https://image.baidu.com/13123.jpg"));
        list.add(new Item(3L, "小米8", "手机", "小米", 3499.00, "https://image.baidu.com/13123.jpg"));
        list.add(new Item(4L, "小米8", "手机", "小米", 3499.00, "https://image.baidu.com/13123.jpg"));
        list.add(new Item(5L, "小米8", "手机", "小米", 3499.00, "https://image.baidu.com/13123.jpg"));

        return (List<Item>) itemRepository.saveAll(list);
    }

    /**
     * 主键删除
     *
     * @param id 文档主键
     * @return java.lang.String
     **/
    @GetMapping("/deleteDocumentById")
    public String deleteDocumentById(String id) {
        return elasticsearchRestTemplate.delete(id, IndexCoordinates.of("item"));
    }

    /**
     * 根据条件删除
     *
     * @return org.springframework.data.elasticsearch.core.query.ByQueryResponse
     **/
    @GetMapping("/deleteByQuery")
    public ByQueryResponse deleteByQuery() {
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();

        // 并且 and
        queryBuilder.must(QueryBuilders.termQuery("category", "手机"));
        queryBuilder.must(QueryBuilders.termQuery("id", 2));
        Query query = new NativeSearchQuery(queryBuilder);

        return elasticsearchRestTemplate.delete(query, Item.class, IndexCoordinates.of("item"));
    }

    /**
     * 分页查询
     *
     * @return java.util.List<com.sy.model.Item>
     **/
    @GetMapping("/getPage")
    public List<Item> getPage() {
        QueryBuilder queryBuilder = QueryBuilders.termQuery("title", "lisi");

        // 构建分页，page 从0开始
        Pageable pageable = PageRequest.of(0, 2);

        Query query = new NativeSearchQueryBuilder()
                .withQuery(queryBuilder)
                .withPageable(pageable)
                //排序
                .withSorts(SortBuilders.fieldSort("id").order(SortOrder.ASC))
                .build();

        SearchHits<Item> search = elasticsearchRestTemplate.search(query, Item.class);
        Stream<SearchHit<Item>> searchHitStream = search.get();
        return searchHitStream.map(SearchHit::getContent).collect(Collectors.toList());
    }

    /**
     * 精确单查询
     *
     * @param id 主键
     * @return java.util.List<com.sy.model.Item>
     **/
    @GetMapping("/termItem")
    public List<Item> termItem(String id) {
        QueryBuilder queryBuilder = QueryBuilders.termQuery("id", id);

        return getResult(queryBuilder, Item.class);
    }

    /**
     * 精确多查询
     *
     * @param ids 主键集
     * @return java.util.List<com.sy.model.Item>
     **/
    @GetMapping("/termsItem")
    public List<Item> termsItem(@RequestParam List<String> ids) {
        QueryBuilder queryBuilder = QueryBuilders.termsQuery("id", ids);

        return getResult(queryBuilder, Item.class);
    }

    /**
     * 分词单查询
     *
     * @param title 标题
     * @return java.util.List<com.sy.model.Item>
     **/
    @GetMapping("/matchItem")
    public List<Item> matchItem(String title) {
        QueryBuilder queryBuilder = QueryBuilders.matchQuery("title", title).analyzer("ik_max_word");

        return getResult(queryBuilder, Item.class);
    }

    /**
     * 分词多查询
     *
     * @param title 标题
     * @return java.util.List<com.sy.model.Item>
     **/
    @GetMapping("/multiMatchItem")
    public List<Item> multiMatchItem(String title) {
        QueryBuilder queryBuilder = QueryBuilders.multiMatchQuery(title, "title", "brand").analyzer("ik_max_word");

        return getResult(queryBuilder, Item.class);
    }

    /**
     * 模糊查询
     *
     * @param title 标题
     * @return java.util.List<com.sy.model.Item>
     **/
    @GetMapping("/fuzzyItem")
    public List<Item> fuzzyItem(String title) {
        FuzzyQueryBuilder queryBuilder = QueryBuilders.fuzzyQuery("title", title).fuzziness(Fuzziness.TWO);

        return getResult(queryBuilder, Item.class);
    }

    /**
     * 前缀查询
     *
     * @param prefix 前缀
     * @return java.util.List<com.sy.model.Item>
     **/
    @GetMapping("/prefixItem")
    public List<Item> prefixItem(String prefix) {
        PrefixQueryBuilder queryBuilder = QueryBuilders.prefixQuery("title", prefix);

        return getResult(queryBuilder, Item.class);
    }

    /**
     * 通配符查询
     *
     * @param wildcard 通配符
     * @return java.util.List<com.sy.model.Item>
     **/
    @GetMapping("/wildcardItem")
    public List<Item> wildcardItem(String wildcard) {
        WildcardQueryBuilder queryBuilder = QueryBuilders.wildcardQuery("title", wildcard);

        return getResult(queryBuilder, Item.class);
    }

    /**
     * 正则查询
     *
     * @param regex 正则
     * @return java.util.List<com.sy.model.Item>
     **/
    @GetMapping("/regexItem")
    public List<Item> regexItem(String regex) {
        RegexpQueryBuilder queryBuilder = QueryBuilders.regexpQuery("title", regex);
        queryBuilder.boost(2);

        return getResult(queryBuilder, Item.class);
    }

    /**
     * 多条件查询 -- and
     *
     * @return java.util.List<com.sy.model.Item>
     **/
    @GetMapping("/boolItem")
    public List<Item> boolItem() {
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();

        boolQueryBuilder.must(QueryBuilders.termQuery("title", "小米"));
        boolQueryBuilder.must(QueryBuilders.termQuery("brand", "小米"));

        return getResult(boolQueryBuilder, Item.class);
    }

    /**
     * 多条件查询 -- or
     *
     * @return java.util.List<com.sy.model.Item>
     **/
    @GetMapping("/shouldItem")
    public List<Item> shouldItem() {
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();

        boolQueryBuilder.should(QueryBuilders.termQuery("title", "小米"));
        boolQueryBuilder.should(QueryBuilders.termQuery("brand", "小米"));

        return getResult(boolQueryBuilder, Item.class);
    }

    /**
     * 多条件查询 -- 不等于
     *
     * @return java.util.List<com.sy.model.Item>
     **/
    @GetMapping("/mustNotItem")
    public List<Item> mustNotItem() {
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();

        boolQueryBuilder.mustNot(QueryBuilders.termQuery("title", "大米"));

        return getResult(boolQueryBuilder, Item.class);
    }

    /**
     * 多条件查询 -- filter
     *
     * @return java.util.List<com.sy.model.Item>
     **/
    @GetMapping("/filterItem")
    public List<Item> filterItem() {
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();

        boolQueryBuilder.filter(QueryBuilders.termQuery("title", "小米"));

        return getResult(boolQueryBuilder, Item.class);
    }

    /**
     * 范围查询
     *
     * @return java.util.List<com.sy.model.Item>
     **/
    @GetMapping("/rangeItem")
    public List<Item> rangeItem() {
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();

        boolQueryBuilder.filter(QueryBuilders.rangeQuery("price").gte("2000").lte("4000"));

        return getResult(boolQueryBuilder, Item.class);
    }

    public <T> List<T> getResult(QueryBuilder queryBuilder, Class<T> clazz) {
        Pageable pageable = PageRequest.of(0, 10);

        NativeSearchQuery query = new NativeSearchQueryBuilder()
                .withQuery(queryBuilder)
                .withPageable(pageable)
                .withSorts(SortBuilders.fieldSort("id").order(SortOrder.ASC))
                .build();

        SearchHits<T> search = elasticsearchRestTemplate.search(query, clazz);
        Stream<SearchHit<T>> searchHitStream = search.get();
        return searchHitStream.map(SearchHit::getContent).collect(Collectors.toList());
    }

}
