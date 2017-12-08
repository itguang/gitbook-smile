package com.itguang.springbootmultidatasource.repository.test2;

import com.itguang.springbootmultidatasource.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author itguang
 * @create 2017-12-07 16:47
 **/
public interface UserTest2Repository extends JpaRepository<User,Long>{
    User findByUserName(String userName);
    User findByUserNameOrEmail(String username, String email);
}
