package ru.rsreu.ChineseCourse.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.rsreu.ChineseCourse.dto.request.RegistrationRequest;
import ru.rsreu.ChineseCourse.service.IUserService;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserPageController {

    @GetMapping
    public String userPage(){
        return "user_page";
    }
}
