package com.itguang.springbootfastjson.web;

import com.fasterxml.jackson.annotation.JsonView;
import com.itguang.springbootfastjson.entity.User;
import com.itguang.springbootfastjson.vo.ResultVO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author itguang
 * @create 2017-12-09 16:35
 **/
@RestController
public class UserController {



    @RequestMapping("/getUser")
    @JsonView(User.UserSimpleView.class)
    public List<User> getUser(){

        User user = new User("itguang", "123456", "123@qq.com");

        ArrayList<User> users = new ArrayList<>();
        users.add(new User("itguang", "123456", "123@qq.com"));
        users.add(new User("itguang", "123456", "123@qq.com"));
        users.add(new User("itguang", "123456", "123@qq.com"));
        users.add(new User("itguang", "123456", "123@qq.com"));
        return users;
    }



    @RequestMapping("/test")
//    @JsonView(User.UserSimpleView.class)
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
