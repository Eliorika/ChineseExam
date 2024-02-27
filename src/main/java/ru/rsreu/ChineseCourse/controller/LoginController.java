package ru.rsreu.ChineseCourse.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.rsreu.ChineseCourse.model.User;
import ru.rsreu.ChineseCourse.service.IUserService;
import ru.rsreu.ChineseCourse.service.impl.UserServiceImpl;

import java.security.Principal;

@Controller
@AllArgsConstructor
@RequestMapping("/login")
public class LoginController {

    @GetMapping
    public String login(){
        return "login";
    }

    @PostMapping
    public String auth(){
        return "user_page";

    }

}
