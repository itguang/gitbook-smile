# JsonView 

在Spring MVC返回json数据时,我们经常要返回不同的json数据,多一个字段或者少一个字段


## @JsonView 使用步骤


1. 使用接口来声明多个视图

```java
/**
 * @author itguang
 * @create 2017-12-09 16:28
 **/
public class User {

    //指定视图

    public interface UserSimpleView {};

    public interface UserDetailView extends UserSimpleView{};




    private String username;
    private String password;
    private String emial;


    public User() {
    }

    public User(String username, String password, String emial) {
        this.username = username;
        this.password = password;
        this.emial = emial;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public String getEmial() {
        return emial;
    }

    public void setEmial(String emial) {
        this.emial = emial;
    }
}
```

上面我们指定了两个视图, UserSimpleView 和 UserDetailView.

并且 UserDetailView 继承了 UserSimpleView,也就意味着当一个属性使用了 UserDetailView 也就可以使用 UserSimpleView所注解的属性





2. 在值对象的get方法上指定视图


```java
/**
 * @author itguang
 * @create 2017-12-09 16:28
 **/
public class User {

    //指定视图

    public interface UserSimpleView {};

    public interface UserDetailView extends UserSimpleView{};




    private String username;
    private String password;
    private String emial;


    public User() {
    }

    public User(String username, String password, String emial) {
        this.username = username;
        this.password = password;
        this.emial = emial;
    }

    @JsonView(UserSimpleView.class)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


   @JsonView(UserDetailView.class)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    @JsonView(UserSimpleView.class)
    public String getEmial() {
        return emial;
    }

    public void setEmial(String emial) {
        this.emial = emial;
    }
}
```


我们使用  @JsonView(UserSimpleView.class) 放在属性的get方法上,表明这个 属性属于那个 JsonView


3. 在Controller 方法上指定视图

```java
/**
 * @author itguang
 * @create 2017-12-09 16:35
 **/
@RestController
public class UserController {



    @RequestMapping("/getUser")
    @JsonView(User.UserSimpleView.class)
    public User getUser(){

        User user = new User("itguang", "123456", "123@qq.com");

        return user;
    }


}
```

我们在Controller的方法上使用  @JsonView(User.UserSimpleView.class) 就可以指定我们自定义的 JsonView视图













































