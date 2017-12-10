package com.itguang.springbootfastjson.entity;

import lombok.Data;

/**
 * @author itguang
 * @create 2017-12-10 11:01
 **/
@Data
public class Book {

    /**
     *    "author": "Nigel Rees",
     "price": 8.95,
     "category": "reference",
     "title": "Sayings of the Century"
     */
    private String author;
    private Double price;
    private String category;
    private String title;

}
