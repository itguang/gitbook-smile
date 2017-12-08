package com.itguang.springbootmybatisdruid.mapper.one;

import com.itguang.springbootmybatisdruid.entity.UserEntity;
import com.itguang.springbootmybatisdruid.enums.UserSexEnum;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author itguang
 * @create 2017-12-08 10:54
 **/
public interface UserMapperOne {


    @Select("select * from users")
    @Results({
            @Result(property = "userSex",column = "user_sex",javaType = UserSexEnum.class),
            @Result(property = "nickName",column = "nick_name")
    })
    List<UserEntity> getList();


}
