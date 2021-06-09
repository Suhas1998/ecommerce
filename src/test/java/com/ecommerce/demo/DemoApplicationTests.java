package com.ecommerce.demo;

import com.ecommerce.demo.model.User;
import com.ecommerce.demo.repository.UserRepository;
import com.ecommerce.demo.service.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class DemoApplicationTests {
	@Autowired
	private UserServiceImpl userService;

	@MockBean //Mocks the database repository
	private UserRepository userRepository;

	@Test
	public void getUsersTest(){
		when(userRepository.findAll())
				.thenReturn( Stream.of(
						new User(1, "newuser1", "user1@gmail.com", 123456) ,
						new User(2, "newuser2", "user2@gmail.com", 54285))
						.collect(Collectors.toList()));
		assertEquals(2, userService.getAllUsers().size());
	}

	@Test
	public void saveUserTest(){
		User user = new User(3, "newuser3", "user3@gmail.com", 123456789);
		when(userRepository.save(user)).thenReturn(user);
		assertEquals(user, userService.saveUser(user));
	}

	@Test
	public void deleteUserTest(){
		User user = new User(3, "newuser3", "user3@gmail.com", 123456789);
		userService.deleteUser(user.getId());
		verify(userRepository, times(1)).deleteById(user.getId());
	}
}
