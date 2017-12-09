package com.itguang.springbootaop.common.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Service;

/**
 * @author itguang
 * @create 2017-12-09 9:55
 **/
@Aspect
@Service
public class TestAOP {

    @Pointcut("execution(* com.itguang.springbootaop.web.HelloController.hello(..))")
    public void performance(){

    }



    /**
     * 手机静音
     */
    @Before("performance()")
    public void  silenceCellPhone(){
//        System.out.println("Silence Cell Phone");
    }

}
