package com.sy.java.collection_.list_;

import com.sy.model.Book;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 冒泡排序
 *
 * @author lfeiyang
 * @since 2022-08-29 21:11
 */
@Slf4j
public class ListOrder {
    public static void main(String[] args) {
        Book sgBook = new Book("三国志", "罗贯中", 28.6);
        Book shBook = new Book("水浒传", "吴承恩", 18.6);
        Book xyBook = new Book("西游记", "施耐庵", 78.6);
        Book hlBook = new Book("红楼梦", "曹雪芹", 68.6);
        List<Book> bookList = new ArrayList<>(Arrays.asList(sgBook, shBook, xyBook, hlBook));

        ListOrder.sort(bookList);
        if (CollectionUtils.isEmpty(bookList)) {
            return;
        }

        for (Book book : bookList) {
            log.warn(book.toString());
        }
    }

    private static void sort(List<Book> list) {
        if (CollectionUtils.isEmpty(list)) {
            return;
        }

        for (int i = 0; i < list.size() - 1; i++) {
            for (int j = 0; j < list.size() - 1 - i; j++) {
                Book book1 = list.get(j);
                Book book2 = list.get(j + 1);
                if (book1.getPrice() > book2.getPrice()) {
                    list.set(j, book2);
                    list.set(j + 1, book1);
                }
            }
        }
    }
}
