package com.itguang.springbootfastjson.entity;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import org.hibernate.validator.constraints.Email;

/**
 * @author itguang
 * @create 2017-12-09 16:28
 **/
@Data
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
