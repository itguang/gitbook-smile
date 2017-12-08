package com.itguang.springbootmultidatasource;

import com.itguang.springbootmultidatasource.domain.User;
import com.itguang.springbootmultidatasource.repository.test1.UserTest1Repository;
import com.itguang.springbootmultidatasource.repository.test2.UserTest2Repository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.util.Date;

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
