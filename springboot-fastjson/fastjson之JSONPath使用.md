# 1. JSONPath介绍

官网地址: https://github.com/alibaba/fastjson/wiki/JSONPath



fastjson 1.2.0之后的版本支持JSONPath。这是一个很强大的功能，可以在java框架中当作对象查询语言（OQL）来使用。

# 2. API
```java
package com.alibaba.fastjson;

public class JSONPath {          
     //  求值，静态方法
     public static Object eval(Object rootObject, String path);
     
     // 计算Size，Map非空元素个数，对象非空元素个数，Collection的Size，数组的长度。其他无法求值返回-1
     public static int size(Object rootObject, String path);
     
     // 是否包含，path中是否存在对象
     public static boolean contains(Object rootObject, String path) { }
     
     // 是否包含，path中是否存在指定值，如果是集合或者数组，在集合中查找value是否存在
     public static boolean containsValue(Object rootObject, String path, Object value) { }
     
     // 修改制定路径的值，如果修改成功，返回true，否则返回false
     public static boolean set(Object rootObject, String path, Object value) {}

     // 在数组或者集合中添加元素
     public static boolean array_add(Object rootObject, String path, Object... values);
}
```
建议缓存JSONPath对象，这样能够提高求值的性能。

# 3. 支持语法
<table>
<tr><td>JSONPATH</td><td>描述</td></tr>
<tr><td>$</td><td>根对象，例如$.name</td></tr>
<tr><td>[num]</td><td>数组访问，其中num是数字，可以是负数。例如$[0].leader.departments[-1].name</td></tr>
<tr><td>[num0,num1,num2...]</td><td>数组多个元素访问，其中num是数字，可以是负数，返回数组中的多个元素。例如$[0,3,-2,5]</td></tr>
<tr><td>[start:end]</td><td>数组范围访问，其中start和end是开始小表和结束下标，可以是负数，返回数组中的多个元素。例如$[0:5]</td></tr>
<tr><td>[start:end :step]</td><td>数组范围访问，其中start和end是开始小表和结束下标，可以是负数；step是步长，返回数组中的多个元素。例如$[0:5:2]</td></tr>
<tr><td>[?(key)]</td><td>对象属性非空过滤，例如$.departs[?(name)]</td></tr>
<tr><td>[key > 123]</td><td>数值类型对象属性比较过滤，例如$.departs[id >= 123]，比较操作符支持=,!=,>,>=,&lt;,&lt;= </td></tr>
<tr><td>[key = '123']</td><td>字符串类型对象属性比较过滤，例如$.departs[name = '123']，比较操作符支持=,!=,>,>=,&lt;,&lt;= </td></tr>
<tr><td>[key like 'aa%']</td><td>字符串类型like过滤，<br/>例如$.departs[name like 'sz*']，通配符只支持% <br/>支持not like</td></tr>
<tr><td>[key rlike 'regexpr']</td><td>字符串类型正则匹配过滤，<br/>例如departs[name like 'aa(.)*']，<br/>正则语法为jdk的正则语法，支持not rlike </td></tr>
<tr><td>[key in ('v0', 'v1')]</td><td>IN过滤, 支持字符串和数值类型 <br>例如: <br/>$.departs[name in ('wenshao','Yako')] <br/>$.departs[id not in (101,102)]</td></tr>
<tr><td>[key between 234 and 456]</td><td>BETWEEN过滤, 支持数值类型，支持not between <br>例如: <br/>$.departs[id between 101 and 201]<br/>$.departs[id not between 101 and 201]</td></tr>
<tr><td>length() 或者 size()</td><td>数组长度。例如$.values.size() <br/>支持类型java.util.Map和java.util.Collection和数组</td></tr>
<tr><td>.</td><td>属性访问，例如$.name</td></tr>
<tr><td>..</td><td>deepScan属性访问，例如$..name</td></tr>
<tr><td>*</td><td>对象的所有属性，例如$.leader.*</td></tr>
<tr><td>['key']</td><td>属性访问。例如$['name']</td></tr>
<tr><td>['key0','key1']</td><td>多个属性访问。例如$['id','name']</td></tr>
</table>

以下两种写法的语义是相同的：
```
$.store.book[0].title
```
和
```
$['store']['book'][0]['title']
```

# 4. 语法示例
<table>
<tr><td>JSONPath</td><td>语义</td></tr>
<tr><td>$</td><td>根对象</td></tr>
<tr><td>$[-1]</td><td>最后元素</td></tr>
<tr><td>$[:-2]</td><td>第1个至倒数第2个</td></tr>
<tr><td>$[1:]</td><td>第2个之后所有元素</td></tr>
<tr><td>$[1,2,3]</td><td>集合中1,2,3个元素</td></tr>
</td>
</table>

# 5. API 示例

### 5.1 例1
```java
public void test_entity() throws Exception {
   Entity entity = new Entity(123, new Object());
   
  Assert.assertSame(entity.getValue(), JSONPath.eval(entity, "$.value")); 
  Assert.assertTrue(JSONPath.contains(entity, "$.value"));
  Assert.assertTrue(JSONPath.containsValue(entity, "$.id", 123));
  Assert.assertTrue(JSONPath.containsValue(entity, "$.value", entity.getValue())); 
  Assert.assertEquals(2, JSONPath.size(entity, "$"));
  Assert.assertEquals(0, JSONPath.size(new Object[], "$")); 
}

public static class Entity {
   private Integer id;
   private String name;
   private Object value;

   public Entity() {}
   public Entity(Integer id, Object value) { this.id = id; this.value = value; }
   public Entity(Integer id, String name) { this.id = id; this.name = name; }
   public Entity(String name) { this.name = name; }

   public Integer getId() { return id; }
   public Object getValue() { return value; }        
   public String getName() { return name; }
   
   public void setId(Integer id) { this.id = id; }
   public void setName(String name) { this.name = name; }
   public void setValue(Object value) { this.value = value; }
}
```

### 5.2 例2
读取集合多个元素的某个属性
```java
List<Entity> entities = new ArrayList<Entity>();
entities.add(new Entity("wenshao"));
entities.add(new Entity("ljw2083"));

List<String> names = (List<String>)JSONPath.eval(entities, "$.name"); // 返回enties的所有名称
Assert.assertSame(entities.get(0).getName(), names.get(0));
Assert.assertSame(entities.get(1).getName(), names.get(1));
```
### 5.3 例3
返回集合中多个元素
```java
List<Entity> entities = new ArrayList<Entity>();
entities.add(new Entity("wenshao"));
entities.add(new Entity("ljw2083"));
entities.add(new Entity("Yako"));

List<Entity> result = (List<Entity>)JSONPath.eval(entities, "[1,2]"); // 返回下标为1和2的元素
Assert.assertEquals(2, result.size());
Assert.assertSame(entities.get(1), result.get(0));
Assert.assertSame(entities.get(2), result.get(1));
```
### 5.4 例4
按范围返回集合的子集
```java
List<Entity> entities = new ArrayList<Entity>();
entities.add(new Entity("wenshao"));
entities.add(new Entity("ljw2083"));
entities.add(new Entity("Yako"));

List<Entity> result = (List<Entity>)JSONPath.eval(entities, "[0:2]"); // 返回下标从0到2的元素
Assert.assertEquals(3, result.size());
Assert.assertSame(entities.get(0), result.get(0));
Assert.assertSame(entities.get(1), result.get(1));
Assert.assertSame(entities.get(2), result.get(1));
```
### 5.5 例5
通过条件过滤，返回集合的子集
```java
List<Entity> entities = new ArrayList<Entity>();
entities.add(new Entity(1001, "ljw2083"));
entities.add(new Entity(1002, "wenshao"));
entities.add(new Entity(1003, "yakolee"));
entities.add(new Entity(1004, null));

List<Object> result = (List<Object>) JSONPath.eval(entities, "[id in (1001)]");
Assert.assertEquals(1, result.size());
Assert.assertSame(entities.get(0), result.get(0));
```
### 5.6 例6
根据属性值过滤条件判断是否返回对象，修改对象，数组属性添加元素
```java
Entity entity = new Entity(1001, "ljw2083");
Assert.assertSame(entity , JSONPath.eval(entity, "[id = 1001]"));
Assert.assertNull(JSONPath.eval(entity, "[id = 1002]"));

JSONPath.set(entity, "id", 123456); //将id字段修改为123456
Assert.assertEquals(123456, entity.getId().intValue());

JSONPath.set(entity, "value", new int[0]); //将value字段赋值为长度为0的数组
JSONPath.arrayAdd(entity, "value", 1, 2, 3); //将value字段的数组添加元素1,2,3
```

### 5.7 例7
```java
Map root = Collections.singletonMap("company", //
                                    Collections.singletonMap("departs", //
                                                             Arrays.asList( //
                                                                            Collections.singletonMap("id",
                                                                                                     1001), //
                                                                            Collections.singletonMap("id",
                                                                                                     1002), //
                                                                            Collections.singletonMap("id", 1003) //
                                                             ) //
                                    ));

List<Object> ids = (List<Object>) JSONPath.eval(root, "$..id");
assertEquals(3, ids.size());
assertEquals(1001, ids.get(0));
assertEquals(1002, ids.get(1));
assertEquals(1003, ids.get(2));
```
