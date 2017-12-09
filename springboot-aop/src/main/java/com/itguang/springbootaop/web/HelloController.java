package com.itguang.springbootaop.web;

import com.itguang.springbootaop.common.anno.LoggerManage;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author itguang
 * @create 2017-12-09 8:52
 **/

@RestController
public class HelloController {


    @RequestMapping("/hello")
    @LoggerManage(logDescription = "hello")
    public String hello() {
        return "hello";
    }

    @RequestMapping("/login/{username}/{password}")
    @LoggerManage(logDescription = "登陆")
    public String login(@PathVariable("username") String username,
                        @PathVariable("password") String password ) {
        return "登陆成功";


    }


}
