package com.itguang.springbootmybatisdruid.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * @author itguang
 * @create 2017-12-08 13:28
 **/
@Configuration
@MapperScan(basePackages = "com.itguang.springbootmybatisdruid.mapper.one", sqlSessionTemplateRef  = "oneSqlSessionTemplate")
public class OneDataSourceConfig {

    /**
     * 配置SqlSessionFactory
     * @param dataSource
     * @return
     * @throws Exception
     */
    @Primary
    @Bean("oneSqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("oneDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);
        return factoryBean.getObject();

    }

    /**
     * 配置数据源1的事物管理器
     * @param dataSource
     * @return
     */
    @Primary
    @Bean("oneTransactionManager")
    public DataSourceTransactionManager dataSourceTransactionManager(@Qualifier("oneDataSource") DataSource dataSource){
        return new DataSourceTransactionManager(dataSource);

    }


    /**
     * 使用Spring的模版技术创建 SqlSessionTemplate
     * @param sqlSessionFactory
     * @return
     */
    @Primary
    @Bean("oneSqlSessionTemplate")
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("oneSqlSessionFactory") SqlSessionFactory sqlSessionFactory){
        return new SqlSessionTemplate(sqlSessionFactory);

    }

}
