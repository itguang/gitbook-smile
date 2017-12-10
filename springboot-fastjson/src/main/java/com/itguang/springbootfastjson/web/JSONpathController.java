package com.itguang.springbootfastjson.web;

import com.alibaba.fastjson.JSONPath;
import com.fasterxml.jackson.annotation.JsonView;
import com.itguang.springbootfastjson.entity.User;
import com.itguang.springbootfastjson.vo.ResultVO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author itguang
 * @create 2017-12-10 10:00
 **/

@RestController
public class JSONpathController {



    @RequestMapping("/test1")
    public User test1(){

        User user = new User("itguang", "123456", "123@qq.com");
        String username = (String) JSONPath.eval(user, "$.username");


        return user;
    }



    @RequestMapping("/test2")
    public ResultVO<User> test2(){


        ArrayList<User> users = new ArrayList<>();
        users.add(new User("itguang", "123456", "123@qq.com"));
        users.add(new User("itguang", "123456", "123@qq.com"));
        users.add(new User("itguang", "123456", "123@qq.com"));
        users.add(new User("itguang", "123456", "123@qq.com"));

        ResultVO resultVO = new ResultVO(1, "成功", users);
        return resultVO;
    }



}
