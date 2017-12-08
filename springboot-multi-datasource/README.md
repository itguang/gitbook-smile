# SpringBoot JPA 多数据源的使用

参考: http://gitbook.cn/gitchat/column/59f5daa149cd4330613605ba/topic/59f6a809a5beea6a3fd8a7f2

## 多数据源的支持

在项目开发中,常常需要在一个项目中使用多个数据源,因此需要配置Spring data jpa对多数据源的使用.

一般分为以下三步:

1. 配置多数据源
2. 不同源的repsitory放入不同的包路径
3. 声明不同包路径下使用不同的数据源,事物支持

## 第一步:配置两个数据源的连接属性


其中 application.properties 配置如下:

```properties
################ dataSource START #################
#primary
spring.primary.datasource.url=jdbc:mysql://localhost:3306/test1
spring.primary.datasource.username=root
spring.primary.datasource.password=root
spring.primary.datasource.driver-class-name=com.mysql.jdbc.Driver

#secondary
spring.secondary.datasource.url=jdbc:mysql://localhost:3306/test2
spring.secondary.datasource.username=root
spring.secondary.datasource.password=root
spring.secondary.datasource.driver-class-name=com.mysql.jdbc.Driver

spring.jpa.properties.hibernate.hbm2ddl.auto=update
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL5InnoDBDialect
spring.jpa.show-sql= true

############# dataSource END #############
```


## 第二步: 读取两个数据源，：



```java
/**
 * @author itguang
 * @create 2017-12-07 16:22
 **/
@Configuration
public class DataSourceConfig {


    @Bean(name = "primaryDataSource")
    @Primary
    @Qualifier("primaryDataSource")
    @ConfigurationProperties(prefix = "spring.primary.datasource")
    public DataSource primaryDatasource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "secondaryDataSource")
    @Qualifier("secondaryDataSource")
    @ConfigurationProperties(prefix = "spring.secondary.datasource")
    public DataSource secondaryDataSource() {
        return DataSourceBuilder.create().build();
    }


}
```




## 第三步: 将数据源注入到 Factory，配置 repository、domian 的位置,构建两个 实体管理器 EntityManager 

```java
/**
 * 将数据源注入到 实体管理器工厂，配置 repository、domian 的位置
 *
 * @author itguang
 * @create 2017-12-07 16:35
 *
 **/

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "entityManagerFactoryPrimary",//配置连接工厂 entityManagerFactory
        transactionManagerRef = "transactionManagerPrimary", //配置 事物管理器  transactionManager
        basePackages = {"com.itguang.springbootmultidatasource.repository.test1"}//设置dao（repo）所在位置
)
public class PrimaryConfig {

    @Autowired
    private JpaProperties jpaProperties;

    @Autowired
    @Qualifier("primaryDataSource")
    private DataSource primaryDataSource;


    /**
     *
     * @param builder
     * @return
     */
    @Bean(name = "entityManagerFactoryPrimary")
    @Primary
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryPrimary(EntityManagerFactoryBuilder builder) {

        return builder
                //设置数据源
                .dataSource(primaryDataSource)
                //设置数据源属性
                .properties(getVendorProperties(primaryDataSource))
                //设置实体类所在位置.扫描所有带有 @Entity 注解的类
                .packages("com.itguang.springbootmultidatasource.domain")
                // Spring会将EntityManagerFactory注入到Repository之中.有了 EntityManagerFactory之后,
                // Repository就能用它来创建 EntityManager 了,然后 EntityManager 就可以针对数据库执行操作
                .persistenceUnit("primaryPersistenceUnit")
                .build();

    }

    private Map<String, String> getVendorProperties(DataSource dataSource) {
        return jpaProperties.getHibernateProperties(dataSource);
    }

    /**
     * 配置事物管理器
     *
     * @param builder
     * @return
     */
    @Bean(name = "transactionManagerPrimary")
    @Primary
    PlatformTransactionManager transactionManagerPrimary(EntityManagerFactoryBuilder builder) {
        return new JpaTransactionManager(entityManagerFactoryPrimary(builder).getObject());
    }


}
```


## 第四步:测试


```java
@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootMultiDatasourceApplicationTests {

	@Resource
	private UserTest1Repository userTest1Repository;
	@Resource
	private UserTest2Repository userTest2Repository;

	@Test
	public void testSave() throws Exception {
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG);
		String formattedDate = dateFormat.format(date);

		userTest1Repository.save(new User("aa", "aa123456","aa@126.com", "aa",  formattedDate));
		userTest2Repository.save(new User("cc", "cc123456","cc@126.com", "cc",  formattedDate));

	}

	@Test
	public void contextLoads() {
	}

}
```

实体类和respoitory实现请参考源代码.














































