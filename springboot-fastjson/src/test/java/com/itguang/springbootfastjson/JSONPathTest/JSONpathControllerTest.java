package com.itguang.springbootfastjson.JSONPathTest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import com.itguang.springbootfastjson.entity.Book;
import com.itguang.springbootfastjson.entity.Entity;
import com.itguang.springbootfastjson.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author itguang
 * @create 2017-12-10 10:03
 **/

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class JSONpathControllerTest {


    @Test
    public void test() {

        User user = new User("itguang", "123456", "123@qq.com");
        String username = (String) JSONPath.eval(user, "$.username");

        log.info("$.username = {}", username);

        Entity entity = new Entity(123, user);
        User user1 = (User) JSONPath.eval(entity, "$.value");
        log.info("user={}", user1.toString());


    }


    @Test
    public void test2() {

        User user = new User("itguang", "123456", "123@qq.com");
        Entity entity = new Entity(123, user);

        //判断entity中是否有 data
        boolean contains = JSONPath.contains(entity, "$.data");
        Assert.assertTrue(contains);

        //判断 entity.data.username 属性值是否为 itguang
        boolean containsValue = JSONPath.containsValue(entity, "$.data.username", "itguang");
        Assert.assertTrue(containsValue);

        Assert.assertEquals(2, JSONPath.size(entity, "$"));


    }


    @Test
    public void test3() {

        List<Entity> entities = new ArrayList<Entity>();
        entities.add(new Entity("逻辑"));
        entities.add(new Entity("叶文杰"));
        entities.add(new Entity("程心"));

        //返回集合中多个元素
        List<String> names = (List<String>) JSONPath.eval(entities, "$.name");
        log.info("返回集合中多个元素names={}", names);


        //返回下标 0 和 2 的元素
        List<Entity> result = (List<Entity>) JSONPath.eval(entities, "[0,2]");
        log.info("返回下标 0 和 2 的元素={}", result);

        // 返回下标从0到2的元素
        List<Entity> result2 = (List<Entity>) JSONPath.eval(entities, "[0:2]");

        log.info("返回下标从0到2的元素={}", result2);


    }


    @Test
    public void test4() {

        List<Entity> entities = new ArrayList<Entity>();
        entities.add(new Entity(1001, "逻辑"));
        entities.add(new Entity(1002, "程心"));
        entities.add(new Entity(1003, "叶文杰"));
        entities.add(new Entity(1004, null));

        //通过条件过滤，返回集合的子集

        List<Entity> result = (List<Entity>) JSONPath.eval(entities, "[id in (1001)]");
        log.info("通过条件过滤，返回集合的子集={}", result);


    }

    /**
     * 使用JSONPrase 解析JSON字符串或者Object对象
     * <p>
     * read(String json, String path)//直接使用json字符串匹配
     * <p>
     * eval(Object rootObject, String path) //直接使用 对象匹配
     * <p>
     * <p>
     * {"store":{"bicycle":{"color":"red","price":19.95},"book":[{"author":"Nigel Rees","price":8.95,"category":"reference","title":"Sayings of the Century"},{"author":"Evelyn Waugh","price":12.99,"isbn":"0-553-21311-3","category":"fiction","title":"Sword of Honour"}]}}
     */
    @Test
    public void test5() {


        String jsonStr = "{\n" +
                "    \"store\": {\n" +
                "        \"bicycle\": {\n" +
                "            \"color\": \"red\",\n" +
                "            \"price\": 19.95\n" +
                "        },\n" +
                "        \"book\": [\n" +
                "            {\n" +
                "                \"author\": \"刘慈欣\",\n" +
                "                \"price\": 8.95,\n" +
                "                \"category\": \"科幻\",\n" +
                "                \"title\": \"三体\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"author\": \"itguang\",\n" +
                "                \"price\": 12.99,\n" +
                "                \"category\": \"编程语言\",\n" +
                "                \"title\": \"go语言实战\"\n" +
                "            }\n" +
                "        ]\n" +
                "    }\n" +
                "}";

        JSONObject jsonObject = JSON.parseObject(jsonStr);

        log.info(jsonObject.toString());

        //得到所有的书
        List<Book> books = (List<Book>) JSONPath.eval(jsonObject, "$.store.book");
        log.info("books={}", books);

        //得到所有的书名
        List<String> titles = (List<String>) JSONPath.eval(jsonObject, "$.store.book.title");
        log.info("titles={}", titles);

        //第一本书title
        String title = (String) JSONPath.read(jsonStr, "$.store.book[0].title");
        log.info("title={}", title);

        //price大于10元的book
        List<Book> list = (List<Book>) JSONPath.read(jsonStr, "$.store.book[price > 10]");
        log.info("price大于10元的book={}",list);

        //price大于10元的title
        List<String> list2 =(List<String>) JSONPath.read(jsonStr, "$.store.book[price > 10].title");
        log.info("price大于10元的title={}",list2);

        //category(类别)为科幻的book
        List<Book> list3 = (List<Book>) JSONPath.read(jsonStr,"$.store.book[category = '科幻']");
        log.info("category(类别)为科幻的book={}",list3);


        //bicycle的所有属性值

        Collection<String> values = (Collection<String>) JSONPath.eval(jsonObject, "$.store.bicycle.*");

        log.info("bicycle的所有属性值={}",values);


        //bicycle的color和price属性值
        List<String> read =(List<String>) JSONPath.read(jsonStr, "$.store.bicycle['color','price']");

        log.info("bicycle的color和price属性值={}",read);


    }


}
