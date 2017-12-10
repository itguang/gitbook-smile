package com.itguang.springbootfastjson.entity;

import lombok.Data;

/**
 * @author itguang
 * @create 2017-12-10 10:08
 **/

@Data
public class Entity {


    private Integer id;
    private String name;
    private Object data;

    public Entity() {
    }

    public Entity(String name) {
        this.name = name;
    }

    public Entity(Integer id, String name, Object data) {
        this.id = id;
        this.name = name;
        this.data = data;
    }

    public Entity(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Entity(Integer id, Object data) {
        this.id = id;
        this.data = data;
    }
}
