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
import ru.rsreu.ChineseCourse.dto.request.UserInfoRequest;
import ru.rsreu.ChineseCourse.service.IUserService;

import java.security.Principal;

@Controller
@RequestMapping("/user-change")
@RequiredArgsConstructor
public class ChangeUserInfoController {
    private final IUserService userService;

    @PostMapping
    public String updateUserInfo(@Valid @ModelAttribute("userRegReq") UserInfoRequest req, Principal principal, Errors errors){

        if(errors.hasErrors()){
            return "pool";
        }

        userService.updateUser(req, principal.getName());
        return "redirect:/login";
    }

    @GetMapping
    public String registration(Model model){
        model.addAttribute("userRegReq", new UserInfoRequest());
        return "registration";
    }

}
