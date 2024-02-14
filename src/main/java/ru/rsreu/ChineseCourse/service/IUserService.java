package ru.rsreu.ChineseCourse.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.rsreu.ChineseCourse.dto.request.RegistrationRequest;
import ru.rsreu.ChineseCourse.model.User;

public interface IUserService extends UserDetailsService {

    User createUser(RegistrationRequest req);

    User findByEmail(String email);
}
