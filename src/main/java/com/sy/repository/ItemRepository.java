package com.sy.repository;

import com.sy.model.Item;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

/**
 * 全文检索接口
 *
 * @author lfeiyang
 * @since 2022-05-15 14:48
 */
public interface ItemRepository extends ElasticsearchRepository<Item, Long> {
    List<Item> getByBrandAndPrice(String brand, double price);
}
