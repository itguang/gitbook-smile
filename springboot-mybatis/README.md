# Spring Boot 集成 MyBatis

参考: http://gitbook.cn/gitchat/column/59f5daa149cd4330613605ba/topic/59f97e7e68673133615f7427


## MyBatis 几个重要的概念

* **Mapper 配置** Mapper 配置可以使用基于 XML 的 Mapper 配置文件来实现，也可以使用基于 Java 注解的 MyBatis 注解来实现，甚至可以直接使用 MyBatis 提供的 API 来实现。

* **Mapper 接口**： Mapper 接口是指自行定义的一个数据操做接口，类似于通常所说的 DAO 接口。早期的 Mapper 接口需要自定义去实现，现在 MyBatis 会自动为 Mapper 接口创建动态代理对象。Mapper 接口的方法通常与 Mapper 配置文件中的 select、insert、update、delete 等 XML 结点存在一一对应关系。

* **Executor**： MyBatis 中所有的 Mapper 语句的执行都是通过 Executor 进行的，Executor 是 MyBatis 的一个核心接口。

* **SqlSession：** SqlSession 是 MyBatis 的关键对象，是执行持久化操作的独享，类似于 JDBC 中的 Connection，SqlSession 对象完全包含以数据库为背景的所有执行 SQL 操作的方法，它的底层封装了 JDBC 连接，可以用 SqlSession 实例来直接执行被映射的 SQL 语句。

* **SqlSessionFactory**： SqlSessionFactory 是 MyBatis 的关键对象，它是单个数据库映射关系经过编译后的内存镜像。SqlSessionFactory 对象的实例可以通过 SqlSessionFactoryBuilder 对象类获得，而 SqlSessionFactoryBuilder 则可以从 XML 配置文件或一个预先定制的 Configuration 的实例构建出。


MyBatis 的工作流程如下：

![](http://www.ityouknow.com/assets/images/2017/chat/mybat.png)


* 1 首先加载 Mapper 配置的SQL映射文件,或是注解相关的 SQL 内容.

* 2 创建会话工厂 SqlSessionFactory ,Mybatis通过读取配置文件的信息来构造会话工厂.

* 3 创建会话 SqlSession ,根据会话工厂 SqlSessionFactory,Mybatis就会通过它来创建会话(SqlSession).
   SqlSession 是一个接口,改接口包含了对数据库的增删改查的方法.

* 4 创建执行器 Excuter ,因为 SqlSession对象本身不能直接操作数据库,所以它使用了一个叫做数据库执行器 (Excutor) 的接口来帮它执行操作.

* 5 封装SQL对象,在这一步,执行器将待处理的SQL信息封装到一个对象中(MappedStatement),
   改对象包括 SQL 语句,输入参数映射信息和输出结果映射信息
  
* 6 操作数据库. 拥有了执行器和SQL信息封装对象就可以使用他们来访问数据库了,最后返回结果,结束流程.

## SpringBoot 和Mybatis整合

MyBatis-Spring-Boot-Starter 是 MyBatis 帮助我们快速集成 Spring Boot 提供的一个组件包。

使用这个组件可以做到以下几点：

* 构建独立的应用
* 几乎可以零配置
* 需要很少的 XML 配置

其实就是 MyBatis 看 Spring Boot 这么火热也开发出一套解决方案主动来集成， 但这一集成确实解决了很多问题，使用起来比以前简单顺畅了许多。
mybatis-spring-boot-starter主要提供了两种解决方案，一种是简化后的 XML 配置版，一种是使用注解解决一切问题。

## XML 版本

### 第一步: 引入maven依赖,添加application 配置

```xml
<dependency>
			<groupId>org.mybatis.spring.boot</groupId>
			<artifactId>mybatis-spring-boot-starter</artifactId>
			<version>1.3.1</version>
		</dependency>

```
完整pom文件可查看源代码

application 配置:

```properties
############ mybatis配置  ###################

# 配置 mybatis-config.xml 路径，mybatis-config.xml 中配置 MyBatis 基础属性
mybatis.config-location=classpath:mybatis/mybatis-config.xml
# 配置 Mapper 对应的 XML 文件路径
mybatis.mapper-locations=classpath:mybatis/mapper/*.xml
# 配置项目中实体类包路径
mybatis.type-aliases-package=com.itguang.springbootmybatis.entity

############ mybatis配置 END ###################

############ 数据源配置 ####################

spring.datasource.driverClassName = com.mysql.jdbc.Driver
spring.datasource.url = jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=utf-8
spring.datasource.username = root
spring.datasource.password = root

############ 数据源配置 END ###################
```

SpringBoot 启动时数据源会自动注入到 SqlSessionFactory 中去,使用SqlSessionFactory 构建 SqlSession,然后再注入到 Mapper中.
最后我们直接使用Mapper即可.

### 添加 MapperScan 扫描包Mapper.java

**MyBatis 公共属性 mybatis-config.xml**

主要配置常用的 typeAliases，设置类型别名为 Java 类型设置一个短的名字。它只和 XML 配置有关，存在的意义仅在于用来减少类完全限定名的冗余。

```xml
<configuration>
    <typeAliases>
        <typeAlias alias="Integer" type="java.lang.Integer" />
        <typeAlias alias="Long" type="java.lang.Long" />
        <typeAlias alias="HashMap" type="java.util.HashMap" />
        <typeAlias alias="LinkedHashMap" type="java.util.LinkedHashMap" />
        <typeAlias alias="ArrayList" type="java.util.ArrayList" />
        <typeAlias alias="LinkedList" type="java.util.LinkedList" />
    </typeAliases>
</configuration>
```

**UserMapper.xml**

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTDMapper3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd" >
<mapper namespace="com.neo.mapper.UserMapper" >
    <resultMap id="BaseResultMap" type="com.neo.entity.UserEntity" >
        <id column="id" property="id" jdbcType="BIGINT" />
        <result column="userName" property="userName" jdbcType="VARCHAR" />
        <result column="passWord" property="passWord" jdbcType="VARCHAR" />
        <result column="user_sex" property="userSex" javaType="com.neo.enums.UserSexEnum"/>
        <result column="nick_name" property="nickName" jdbcType="VARCHAR" />
    </resultMap>

    <sql id="Base_Column_List" >
        id, userName, passWord, user_sex, nick_name
    </sql>

    <sql id="Base_Where_List">
        <if test="userName != null  and userName != ''">
            and userName = #{userName}
        </if>
        <if test="userSex != null and userSex != ''">
            and user_sex = #{userSex}
        </if>
    </sql>

    <select id="getAll" resultMap="BaseResultMap"  >
        SELECT
        <include refid="Base_Column_List" />
        FROM users
    </select>

    <select id="getList" resultMap="BaseResultMap" parameterType="com.neo.param.UserParam">
        select
        <include refid="Base_Column_List" />
        from users
        where 1=1
        <include refid="Base_Where_List" />
        order by id desc
        limit #{beginLine} , #{pageSize}
    </select>

    <select id="getCount" resultType="Integer" parameterType="com.neo.param.UserParam">
        select
        count(1)
        from users
        where 1=1
        <include refid="Base_Where_List" />
    </select>

    <select id="getOne" parameterType="Long" resultMap="BaseResultMap" >
        SELECT
        <include refid="Base_Column_List" />
        FROM users
        WHERE id = #{id}
    </select>

    <insert id="insert" parameterType="com.neo.entity.UserEntity" >
        INSERT INTO
        users
        (userName,passWord,user_sex)
        VALUES
        (#{userName}, #{passWord}, #{userSex})
    </insert>

    <update id="update" parameterType="com.neo.entity.UserEntity" >
        UPDATE
        users
        SET
        <if test="userName != null">userName = #{userName},</if>
        <if test="passWord != null">passWord = #{passWord},</if>
        nick_name = #{nickName}
        WHERE
        id = #{id}
    </update>

    <delete id="delete" parameterType="Long" >
        DELETE FROM
        users
        WHERE
        id =#{id}
    </delete>

</mapper>
```

指明对应文件的 Mapper 类地址：

配置表结构和类的对应关系：

## 无配置文件注解版

个人推荐项目中使用注解版，因为注解版内容更简化。具体的执行流程都和 XML 版本比较类似，因此这里只介绍两者有差异的部分。


相关配置：注解版在 application.properties 只需要指明实体类的包路径即可。

        mybatis.type-aliases-package=com.neo.entity

其他配置和上面相同。

注解版最大的特点是具体的 SQL 文件需要写在 Mapper 中。

```java
public interface UserMapper {

    @Select("SELECT * FROM users")
    @Results({
        @Result(property = "userSex",  column = "user_sex", javaType = UserSexEnum.class),
        @Result(property = "nickName", column = "nick_name")
    })
    List<UserEntity> getAll();

    @Select("SELECT * FROM users WHERE id = #{id}")
    @Results({
        @Result(property = "userSex",  column = "user_sex", javaType = UserSexEnum.class),
        @Result(property = "nickName", column = "nick_name")
    })
    UserEntity getOne(Long id);

    @Insert("INSERT INTO users(userName,passWord,user_sex) VALUES(#{userName}, #{passWord}, #{userSex})")
    void insert(UserEntity user);

    @Update("UPDATE users SET userName=#{userName},nick_name=#{nickName} WHERE id =#{id}")
    void update(UserEntity user);

    @Delete("DELETE FROM users WHERE id =#{id}")
    void delete(Long id);

}

```

* @Select 是查询类的注解，所有的查询均使用这个

* @Result 修饰返回的结果集，关联实体类属性和数据库字段一一对应，如果实体类属性和数据库属性名保持一致，就不需要这个属性来修饰。

* @Insert 插入数据库使用，直接传入实体类会自动解析属性到对应的值

* @Update 负责修改，也可以直接传入对象

* @delete 负责删除
  


### **动态 SQL**


如果我们需要写动态的 SQL，或者需要写复杂的 SQL，全部写在注解中会比较麻烦，MyBatis 还提供了另外的一种支持。还是以分页为例：

首先定义一个 UserSql 类，提供方法拼接需要执行的 SQL：

```java
public class UserSql {
    public String getList(UserParam userParam) {
        StringBuffer sql = new StringBuffer("select id, userName, passWord, user_sex as userSex, nick_name as nickName");
        sql.append(" from users where 1=1 ");
        if (userParam != null) {
            if (StringUtils.isNotBlank(userParam.getUserName())) {
                sql.append(" and userName = #{userName}");
            }
            if (StringUtils.isNotBlank(userParam.getUserSex())) {
                sql.append(" and user_sex = #{userSex}");
            }
        }
        sql.append(" order by id desc");
        sql.append(" limit " + userParam.getBeginLine() + "," + userParam.getPageSize());
        log.info("getList sql is :" +sql.toString());
        return sql.toString();
    }
}
```

可以看出 UserSql 中有一个方法 getList，使用 StringBuffer 进行 SQL 拼接，最后返回需要执行的 SQL 语句。

接下来只需要在 Mapper 中引入这个类和方法即可。

```java
@SelectProvider(type = UserSql.class, method = "getList")
List<UserEntity> getList(UserParam userParam);
```
* type ：动态生成 SQL 的类
* method ： 类中具体的方法名

可能会觉得这样拼接 SQL 很麻烦，SQL 语句太复杂也比较乱，别着急！MyBatis 给我们提供了一种升级的方案：结构化 SQL。

```java
public String getCount(UserParam userParam) {
   String sql= new SQL(){{
        SELECT("count(1)");
        FROM("users");
        if (StringUtils.isNotBlank(userParam.getUserName())) {
            WHERE("userName = #{userName}");
        }
        if (StringUtils.isNotBlank(userParam.getUserSex())) {
            WHERE("user_sex = #{userSex}");
        }
        //从这个 toString 可以看出，其内部使用高效的 StringBuilder 实现 SQL 拼接
    }}.toString();

    log.info("getCount sql is :" +sql);
    return sql;
}
```

## > 更多结构化的 SQL 语法参考:[SQL语句构建器类](http://www.mybatis.org/mybatis-3/zh/statement-builders.html)













































