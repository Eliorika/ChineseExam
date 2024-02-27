package ru.rsreu.ChineseCourse.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.rsreu.ChineseCourse.dto.request.UserInfoRequest;
import ru.rsreu.ChineseCourse.model.User;

public interface IUserService extends UserDetailsService {

    User createUser(UserInfoRequest req);

    User findByEmail(String email);
    User updateUser(UserInfoRequest req, String username);
}
