package ru.rsreu.ChineseCourse.service.impl;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.rsreu.ChineseCourse.dto.UserInfo;
import ru.rsreu.ChineseCourse.dto.request.ChangePasswordRequest;
import ru.rsreu.ChineseCourse.dto.request.UserInfoRequest;
import ru.rsreu.ChineseCourse.exception.AlreadyExistsException;
import ru.rsreu.ChineseCourse.model.User;
import ru.rsreu.ChineseCourse.model.enums.SystemRole;
import ru.rsreu.ChineseCourse.repo.IUserRepo;
import ru.rsreu.ChineseCourse.service.IUserService;

import java.util.Date;

@Service
@AllArgsConstructor
@Transactional
public class UserServiceImpl implements IUserService {
    private IUserRepo userRepo;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);


    @Override
    @Transactional
    public User createUser(UserInfoRequest req) {
        if(userRepo.findByEmail(req.getEmail()) != null)
            throw new AlreadyExistsException("Пользователь с такой почтой уже существует!");

        User user = User.builder()
                .email(req.getEmail())
                .password(bCryptPasswordEncoder.encode(req.getPassword()))
                .firstName(req.getFirstName())
                .lastName(req.getLastName())
                .systemRole(SystemRole.ROLE_USER)
                .build();
        return userRepo.save(user);
    }

    @Override
    public User findByEmail(String email) {
        return userRepo.findByEmail(email);
    }

    @Override
    @Transactional
    public User updateUser(UserInfo req, String username) {

        User user = findByEmail(username);
        user.setFirstName(req.getFirstName());
        user.setLastName(req.getLastName());
        //user.setPassword(bCryptPasswordEncoder.encode(req.getPassword()));
        return userRepo.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByEmail(username);
        log.info("Finding a user " + username);
        if(user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return user;
    }

    @Override
    @Transactional
    public void changePassword(String email, ChangePasswordRequest changePasswordRequest) {
        log.info("User {} changing password", email);
        User user = findByEmail(email);

        if (bCryptPasswordEncoder.matches(changePasswordRequest.getOldPassword(), user.getPassword())) {
            user.setPassword(bCryptPasswordEncoder.encode(changePasswordRequest.getNewPassword()));
            userRepo.save(user);
        } else {
            throw new HttpMessageNotReadableException("Неверный пароль");
        }
    }


}
