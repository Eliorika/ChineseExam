package ru.rsreu.ChineseCourse.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.rsreu.ChineseCourse.model.User;
import ru.rsreu.ChineseCourse.service.ILessonService;
import ru.rsreu.ChineseCourse.service.IUserService;
import org.springframework.http.ResponseEntity;


import java.security.Principal;

@Controller
@RequestMapping("/lesson")
@RequiredArgsConstructor
public class LessonController {
    private final ILessonService lessonService;
    private final IUserService userService;

    @PostMapping("/{id}")
    @ResponseBody
    public ResponseEntity<String> logResourceMaterial(Principal principal, @PathVariable Long id){
        User user = userService.findByEmail(principal.getName());
        lessonService.logMaterial(id, user);
        return ResponseEntity.ok().build();
    }
}
