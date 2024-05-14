package ru.rsreu.ChineseCourse;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.Rollback;
import org.testcontainers.shaded.com.google.errorprone.annotations.Immutable;
import ru.rsreu.ChineseCourse.dto.request.UserInfoRequest;
import ru.rsreu.ChineseCourse.model.User;
import ru.rsreu.ChineseCourse.service.IUserService;

//@SpringBootTest
@DataJpaTest(excludeAutoConfiguration = LiquibaseAutoConfiguration.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ComponentScan(basePackages = "ru.rsreu.ChineseCourse")
class ChineseCourseApplicationTests extends IntegrationEnvironment{

	@Autowired
	private IUserService userService;

	@Test
	@Rollback
	void createUserTest() {
		UserInfoRequest userInfoRequest = new UserInfoRequest();
		userInfoRequest.setEmail("gggggggggggg@a.ru");
		userInfoRequest.setFirstName("A");
		userInfoRequest.setLastName("B");
		userInfoRequest.setPassword("0000");
		userService.createUser(userInfoRequest);
		User user = userService.findByEmail(userInfoRequest.getEmail());
		Assertions.assertEquals(userInfoRequest.getEmail(), user.getEmail());
	}

}
