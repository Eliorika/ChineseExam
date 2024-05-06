package ru.rsreu.ChineseCourse.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.rsreu.ChineseCourse.dto.UserInfo;
import ru.rsreu.ChineseCourse.dto.request.ChangePasswordRequest;
import ru.rsreu.ChineseCourse.dto.request.UserInfoRequest;
import ru.rsreu.ChineseCourse.model.User;

public interface IUserService extends UserDetailsService {

    User createUser(UserInfoRequest req);

    User findByEmail(String email);
    User updateUser(UserInfo req, String username);

    void changePassword(String email, ChangePasswordRequest changePasswordRequest);
}
