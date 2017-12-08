package com.itguang.springbootmybatisdruid.entity;

import com.itguang.springbootmybatisdruid.enums.UserSexEnum;
import lombok.Data;

import java.io.Serializable;

/**
 * @author itguang
 * @create 2017-12-07 16:32
 **/
@Data
public class UserEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long id;
    private String userName;
    private String passWord;
    private UserSexEnum userSex;
    private String nickName;

}