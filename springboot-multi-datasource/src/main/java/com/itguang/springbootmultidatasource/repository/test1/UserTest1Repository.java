package com.itguang.springbootmultidatasource.repository.test1;

import com.itguang.springbootmultidatasource.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author itguang
 * @create 2017-12-07 16:46
 **/
public interface UserTest1Repository extends JpaRepository<User,Long> {
    User findByUserName(String userName);
    User findByUserNameOrEmail(String username, String email);
}
