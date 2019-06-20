package com.ws.spring;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.ws.spring.model.UserDetails;
import com.ws.spring.repository.UserRepository;
import com.ws.spring.service.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MdogApplicationTests {

	@Autowired
	UserService userService;

	@Test
	public void contextLoads() {
		List<UserDetails> userList = userService.queryUserDetailsByUserNameOrMobile("aaa", "9999");
		assertEquals(1, userList.size());

	}

}
