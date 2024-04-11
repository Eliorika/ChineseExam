package ru.rsreu.ChineseCourse.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import ru.rsreu.ChineseCourse.dto.request.UserInfoRequest;
import ru.rsreu.ChineseCourse.exception.AlreadyExistsException;
import ru.rsreu.ChineseCourse.service.IUserService;
import org.springframework.ui.Model;

@Controller
@RequestMapping("/registration")
@RequiredArgsConstructor
public class RegistrationController {

    private final IUserService userService;


    @PostMapping
    public String registration(@Valid @ModelAttribute("userRegReq") UserInfoRequest req, Errors errors, Model model){

        if(errors.hasErrors()){
            return "registration";
        }
        try {
            userService.createUser(req);
        } catch (AlreadyExistsException ex){
            model.addAttribute("error", ex.getMessage());
            return "registration";
        }

        return "redirect:/login";
    }

    @GetMapping
    public String registration(Model model){
        model.addAttribute("userRegReq", new UserInfoRequest());
        return "registration";
    }
}
