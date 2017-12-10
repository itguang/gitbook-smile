package com.itguang.springbootfastjson.config;

import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

/**
 * @author itguang
 * @create 2017-12-10 9:57
 **/
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
