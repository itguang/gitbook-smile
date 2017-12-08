package com.itguang.springbootmybatis.entity;

import com.itguang.springbootmybatis.enums.UserSexEnum;
import lombok.Data;

/**
 * @author itguang
 * @create 2017-12-08 14:53
 **/
@Data
public class UserEntity {

    private static final long serialVersionUID = 1L;
    private Long id;
    private String userName;
    private String passWord;
    private UserSexEnum userSex;
    private String nickName;

}
