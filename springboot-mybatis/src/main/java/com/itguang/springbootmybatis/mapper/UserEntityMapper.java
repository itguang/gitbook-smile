package com.itguang.springbootmybatis.mapper;

import com.itguang.springbootmybatis.entity.UserEntity;
import com.itguang.springbootmybatis.enums.UserParam;

import java.util.List;

/**
 * @author itguang
 * @create 2017-12-08 14:59
 **/


public interface UserEntityMapper {

    List<UserEntity> getAll();

    List<UserEntity> getList(UserParam userParam);

    int getCount(UserParam userParam);

    UserEntity getOne(Long id);

    void insert(UserEntity user);

    int update(UserEntity user);

    int delete(Long id);


}
