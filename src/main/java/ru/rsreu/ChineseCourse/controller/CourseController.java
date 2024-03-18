package ru.rsreu.ChineseCourse.controller;

import jakarta.validation.Valid;
import jdk.jfr.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.rsreu.ChineseCourse.dto.request.CreateCourseRequest;
import ru.rsreu.ChineseCourse.dto.request.UserInfoRequest;
import ru.rsreu.ChineseCourse.dto.response.CourseInfoResponse;
import ru.rsreu.ChineseCourse.model.User;
import ru.rsreu.ChineseCourse.model.enums.CourseDifficulty;
import ru.rsreu.ChineseCourse.service.ICourseService;
import ru.rsreu.ChineseCourse.service.IUserService;

import java.security.Principal;

@RequestMapping("/course")
@RequiredArgsConstructor
@Controller
public class CourseController {

    private final ICourseService courseService;
    private final IUserService userService;

    @PostMapping("/create")
    @PreAuthorize("isSuperAdmin()")
    public String createCourse(@Valid @ModelAttribute CreateCourseRequest req, Principal principal){
        User user = userService.findByEmail(principal.getName());
        var course = courseService.createCourse(req, user);
        var response = CourseInfoResponse.fromCourse(course);

        return "redirect:/course";
    }

    @GetMapping
    public String registration(Model model){
        model.addAttribute("courseInfo", new CreateCourseRequest());
        model.addAttribute("difficulties", CourseDifficulty.values());
        return "course";
    }
}
