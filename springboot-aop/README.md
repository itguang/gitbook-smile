# 使用自定义注解+Spring切面 实现日志记录

在平常的项目编程中,我们会经常使用到日志,用来记录各种事件.但是,有些日志记录套路实在是太像了,我们不得不要写很多遍.

比如在Spring中,我们要使用日志记录每个controller的访问和结束时间,该怎么办呢.

下面是我认为比较简单的一种方法:  **自定义注解+Spring切面** .

下面使用SpringBoot快速搭建一个项目来进行演示.具体pom文件查看源码


1. 创建一个Controller:

```java
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
```

我们要在controller的方法 hello() 上使用自定义注解以便AOP进行捕获

2. 自定义一个注解: LoggerManage

```java
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LoggerManage {

    public String logDescription();
}
```

这个注解很简单只有一个对方法的描述.并且这是一个方法级别的注解.

3. 创建切面

```java
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
        logger.info(joinPoint.getSignature().toString());

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

```

需要注意的是我们需要把创建的切面类标识为一个Spring可管理的Bean. 即添加@Component 注解或@Service注解都可以.

通过 JoinPoint 我们可以可到切入点的很多信息,包括全限定名,和参数

within(): 限制连接点指定匹配的类型.

对Spring AOP 不熟的可以看看我的这篇文章: https://github.com/itguang/springbootLearn/blob/master/spring_aop/README.md


## 测试

现在我们的工作就做完了,我们测试下

浏览器访问: http://localhost/hello

查看日志输出: 

```
: 2017-12-09T10:45:38.194执行[hello]开始
: String com.itguang.springbootaop.web.HelloController.hello()
: 2017-12-09T10:45:38.196执行 [hello] 结束
```


浏览器访问: http://localhost/login/itguang/123456

再次查看日志输出:
```
2017-12-09T10:54:15.516执行[登陆]开始
 String com.itguang.springbootaop.web.HelloController.login(String,String)
传入参数 2 个:[ itguang ,123456 ]
2017-12-09T10:54:15.522执行 [登陆] 结束
```

之后,我们如果想记录某个Controller里方法的执行情况,就可以在方法上添加此注解就可以了,怎么样?是不是很优雅呢.






















































