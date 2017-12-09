## 在 Spring MVC 中集成 Fastjson

官网地址: https://github.com/alibaba/fastjson/wiki/%E5%9C%A8-Spring-%E4%B8%AD%E9%9B%86%E6%88%90-Fastjson

如果你使用 Spring MVC 来构建 Web 应用并对性能有较高的要求的话，可以使用 Fastjson 提供的```FastJsonHttpMessageConverter``` 来替换 Spring MVC 默认的 ```HttpMessageConverter```  以提高 `@RestController @ResponseBody @RequestBody` 注解的 JSON序列化速度。下面是配置方式，非常简单。
#### XML式
如果是使用 XML 的方式配置 Spring MVC 的话，只需在 Spring MVC 的 XML 配置文件中加入下面配置即可。
```xml
<mvc:annotation-driven>
    <mvc:message-converters>
        <bean class="com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter"/>      
    </mvc:message-converters>
</mvc:annotation-driven>

```
通常默认配置已经可以满足大部分使用场景，如果你想对它进行自定义配置的话，你可以添加 ```FastJsonConfig``` Bean。
```xml
<mvc:annotation-driven>
    <mvc:message-converters>
        <bean class="com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter">
            <property name="fastJsonConfig" ref="fastJsonConfig"/>
        </bean>
    </mvc:message-converters>
</mvc:annotation-driven>

<bean id="fastJsonConfig" class="com.alibaba.fastjson.support.config.FastJsonConfig">
    <!--   自定义配置...   -->
</bean>
```
#### 编程式
如果是使用编程的方式（通常是基于 Spring Boot 项目）配置 Spring MVC 的话只需继承 ```WebMvcConfigurerAdapter``` 覆写 ```configureMessageConverters``` 方法即可，就像下面这样。
```java
@Configuration
public class WebMvcConfigurer extends WebMvcConfigurerAdapter {
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
        //自定义配置...
        //FastJsonConfig config = new FastJsonConfig();
        //config.set ...
        //converter.setFastJsonConfig(config);
        converters.add(converter);
    }
}
```
注：如果你使用的 Fastjson 版本小于```1.2.36```的话(强烈建议使用最新版本)，在与Spring MVC 4.X 版本集成时需使用 ```FastJsonHttpMessageConverter4```。
> 参考：Spring Framework 官方文档 Message Converters 部分， [点我查看](http://docs.spring.io/spring/docs/current/spring-framework-reference/htmlsingle/#mvc-config-message-converters)。

-------------

## 在 Spring Data Redis 中集成 Fastjson
通常我们在 Spring 中使用 Redis 是通过 Spring Data Redis 提供的 ```RedisTemplate``` 来进行的，如果你准备使用 JSON 作为对象序列/反序列化的方式并对序列化速度有较高的要求的话，建议使用 Fastjson 提供的 ```GenericFastJsonRedisSerializer``` 或 ```FastJsonRedisSerializer``` 作为 ```RedisTemplate``` 的 ```RedisSerializer```。下面是配置方式，非常简单。
#### XML式
如果是使用 XML 的方式配置 Spring Data Redis 的话，只需将 ```RedisTemplate``` 中的 ```Serializer``` 替换为 ```GenericFastJsonRedisSerializer``` 即可。

```xml
<bean id="redisTemplate" class="org.springframework.data.redis.core.RedisTemplate">
    <property name="connectionFactory" ref="jedisConnectionFactory"/>
    <property name="defaultSerializer">
        <bean class="com.alibaba.fastjson.support.spring.GenericFastJsonRedisSerializer"/>
    </property>
</bean>
```
下面是完整的 Spring 集成 Redis 配置供参考。
```xml
<!-- Redis 连接池配置(可选) -->
<bean id="jedisPoolConfig" class="redis.clients.jedis.JedisPoolConfig">
    <property name="maxTotal" value="${redis.pool.maxActive}"/>
    <property name="maxIdle" value="${redis.pool.maxIdle}"/>
    <property name="maxWaitMillis" value="${redis.pool.maxWait}"/>
    <property name="testOnBorrow" value="${redis.pool.testOnBorrow}"/>
     <!-- 更多连接池配置...-->
</bean>
<!-- Redis 连接工厂配置 -->
<bean id="jedisConnectionFactory" class="org.springframework.data.redis.connection.jedis.JedisConnectionFactory">
    <!--设置连接池配置，不设置的话会使用默认的连接池配置，若想禁用连接池可设置 usePool = false -->   
    <property name="poolConfig" ref="jedisPoolConfig" />  
    <property name="hostName" value="${host}"/>
    <property name="port" value="${port}"/>
    <property name="password" value="${password}"/>
    <property name="database" value="${database}"/>
    <!-- 更多连接工厂配置...-->
</bean>
<!-- RedisTemplate 配置 -->
<bean id="redisTemplate" class="org.springframework.data.redis.core.RedisTemplate">
    <!-- 设置 Redis 连接工厂-->
    <property name="connectionFactory" ref="jedisConnectionFactory"/>
    <!-- 设置默认 Serializer ，包含 keySerializer & valueSerializer -->
    <property name="defaultSerializer">
        <bean class="com.alibaba.fastjson.support.spring.GenericFastJsonRedisSerializer"/>
    </property>
    <!-- 单独设置 keySerializer -->
    <property name="keySerializer">
        <bean class="com.alibaba.fastjson.support.spring.GenericFastJsonRedisSerializer"/>
    </property>
    <!-- 单独设置 valueSerializer -->
    <property name="valueSerializer">
        <bean class="com.alibaba.fastjson.support.spring.GenericFastJsonRedisSerializer"/>
    </property>
</bean>
```
#### 编程式
如果是使用编程的方式（通常是基于 Spring Boot 项目）配置 RedisTemplate 的话只需在你的配置类(被```@Configuration```注解修饰的类)中显式创建 ```RedisTemplate``` Bean，设置 ```Serializer``` 即可。
```java
@Bean
public RedisTemplate redisTemplate(RedisConnectionFactory redisConnectionFactory) {
    RedisTemplate redisTemplate = new RedisTemplate();
    redisTemplate.setConnectionFactory(redisConnectionFactory);

    GenericFastJsonRedisSerializer fastJsonRedisSerializer = new GenericFastJsonRedisSerializer();
    redisTemplate.setDefaultSerializer(fastJsonRedisSerializer);//设置默认的Serialize，包含 keySerializer & valueSerializer

    //redisTemplate.setKeySerializer(fastJsonRedisSerializer);//单独设置keySerializer
    //redisTemplate.setValueSerializer(fastJsonRedisSerializer);//单独设置valueSerializer
    return redisTemplate;
}
```
通常使用 ```GenericFastJsonRedisSerializer``` 即可满足大部分场景，如果你想定义特定类型专用的 ```RedisTemplate``` 可以使用 ```FastJsonRedisSerializer``` 来代替 ```GenericFastJsonRedisSerializer``` ，配置是类似的。

> 参考：Spring Data Redis 官方文档， [点我查看](https://docs.spring.io/spring-data/redis/docs/1.8.6.RELEASE/reference/html/)。