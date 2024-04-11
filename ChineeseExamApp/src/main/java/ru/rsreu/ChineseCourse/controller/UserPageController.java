package ru.rsreu.ChineseCourse.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.rsreu.ChineseCourse.dto.UserInfo;
import ru.rsreu.ChineseCourse.dto.request.ChangePasswordRequest;
import ru.rsreu.ChineseCourse.model.User;
import ru.rsreu.ChineseCourse.service.IUserService;

import java.security.Principal;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserPageController {
    private final IUserService userService;

    @GetMapping
    public String userPage(Principal principal, Model model){
        User user = userService.findByEmail(principal.getName());
        if(user!= null){
            model.addAttribute("user", UserInfo.fromUser(user));
        }
        return "personalAccount";
    }

    @GetMapping("/settings")
    public String userSettingsPage(Principal principal, Model model){
        User user = userService.findByEmail(principal.getName());
        if(user!= null){
            model.addAttribute("user", UserInfo.fromUser(user));
            model.addAttribute("changePasswordReq", new ChangePasswordRequest());
        }
        return "userSetting";
    }

    @PostMapping("/save-changes")
    public String userSaveSettings(Principal principal, @ModelAttribute UserInfo user){
        //User actor = userService.findByEmail(principal.getName());
        userService.updateUser(user, principal.getName());

        return "redirect:/user/settings";
    }

    @PostMapping("/save-password")
    public String userSavePassword(Principal principal, @ModelAttribute ChangePasswordRequest req, BindingResult bindingResult){
        //User actor = userService.findByEmail(principal.getName());
        //userService.updateUser(user, principal.getName());

        if (!req.getNewPassword().equals(req.getConfirmPassword())) {
            bindingResult.rejectValue("confirmPassword", "error.confirmPassword", "Пароли не совпадают");
        }

        try {
            userService.changePassword(principal.getName(), req);
        } catch (HttpMessageNotReadableException ex){
            bindingResult.rejectValue("confirmPassword", "error.confirmPassword", "Неверный пароль");

        }

        if (bindingResult.hasErrors()) {
            return "redirect:/user/settings?error=Passwords+do+not+match"; // Перенаправить с сообщением об ошибке
        }


        return "redirect:/user/settings";
    }


}
