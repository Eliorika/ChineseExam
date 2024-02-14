package ru.rsreu.ChineseCourse.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import ru.rsreu.ChineseCourse.dto.request.RegistrationRequest;
import ru.rsreu.ChineseCourse.service.IUserService;
import org.springframework.ui.Model;

@Controller
@RequestMapping("/registration")
@RequiredArgsConstructor
public class RegistrationController {

    private final IUserService userService;


    @PostMapping
    public String registration(@Valid @ModelAttribute("userRegReq") RegistrationRequest req, Errors errors){

        if(errors.hasErrors()){
            return "registration";
        }

        userService.createUser(req);
        return "redirect:/login";
    }

    @GetMapping
    public String registration(Model model){
        model.addAttribute("userRegReq", new RegistrationRequest());
        return "registration";
    }
}
