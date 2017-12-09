package com.itguang.springbootaop.common.aop;

import com.itguang.springbootaop.common.anno.LoggerManage;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * 日志切面
 *
 * @author itguang
 * @create 2017-12-09 9:18
 **/
@Aspect
@Component
public class LoggerAdvice {
    private Logger logger = Logger.getLogger(this.getClass());

    @Before("within(com.itguang.springbootaop..*) && @annotation(loggerManage)")
    public void addBeforeLogger(JoinPoint joinPoint, LoggerManage loggerManage) {
        LocalDateTime now = LocalDateTime.now();

        logger.info(now.toString()+"执行[" + loggerManage.logDescription() + "]开始");
        logger.info("[连接点]:"+joinPoint.getSignature().toString());

        logger.info(parseParames(joinPoint.getArgs()));

    }

    @AfterReturning("within(com.itguang.springbootaop..*) && @annotation(loggerManage)")
    public void addAfterReturningLogger(JoinPoint joinPoint, LoggerManage loggerManage) {
        LocalDateTime now = LocalDateTime.now();
        logger.info(now.toString()+"执行 [" + loggerManage.logDescription() + "] 结束");
    }

    @AfterThrowing(pointcut = "within(com.itguang.springbootaop..*) && @annotation(loggerManage)", throwing = "ex")
    public void addAfterThrowingLogger(JoinPoint joinPoint, LoggerManage loggerManage, Exception ex) {
        LocalDateTime now = LocalDateTime.now();
        logger.error(now.toString()+"执行 [" + loggerManage.logDescription() + "] 异常", ex);
    }

    private String parseParames(Object[] parames) {

        if (null == parames || parames.length <= 0) {
            return "";

        }
        StringBuffer param = new StringBuffer("传入参数 # 个:[ ");
        int i =0;
        for (Object obj : parames) {
            i++;
            if (i==1){
                param.append(obj.toString());
                continue;
            }
            param.append(" ,").append(obj.toString());
        }
        return param.append(" ]").toString().replace("#",String.valueOf(i));
    }


}
