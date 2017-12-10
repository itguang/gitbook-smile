package com.itguang.springbootfastjson.vo;

import lombok.Data;

/**
 * @author itguang
 * @create 2017-12-10 9:38
 **/
@Data
public class ResultVO<T> {

    private int code;
    private String msg;
    private T data;

    public ResultVO(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
}
