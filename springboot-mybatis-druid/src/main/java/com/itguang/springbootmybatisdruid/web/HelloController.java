package com.itguang.springbootmybatisdruid.web;

import com.itguang.springbootmybatisdruid.mapper.one.UserMapperOne;
import com.itguang.springbootmybatisdruid.mapper.two.UserMapperTwo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author itguang
 * @create 2017-12-08 12:23
 **/
@RestController
public class HelloController {

   @Autowired
    private UserMapperOne userMapperOne;

   @Autowired
   private UserMapperTwo userMapperTwo;

   @RequestMapping("/getList")
   public void hello(){
       userMapperOne.getList();
       userMapperTwo.getList();
   }

}
