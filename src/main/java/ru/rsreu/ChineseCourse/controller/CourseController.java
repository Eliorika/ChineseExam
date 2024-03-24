package ru.rsreu.ChineseCourse.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.rsreu.ChineseCourse.dto.request.CourseInfoRequest;
import ru.rsreu.ChineseCourse.dto.response.CourseInfoResponse;
import ru.rsreu.ChineseCourse.model.Course;
import ru.rsreu.ChineseCourse.model.User;
import ru.rsreu.ChineseCourse.model.enums.CourseDifficulty;
import ru.rsreu.ChineseCourse.model.enums.SystemRole;
import ru.rsreu.ChineseCourse.service.ICourseService;
import ru.rsreu.ChineseCourse.service.IUserService;

import java.security.Principal;

@RequestMapping("/courses")
@RequiredArgsConstructor
@Controller
public class CourseController {

    private final ICourseService courseService;
    private final IUserService userService;

    @GetMapping()
    public String allCoursesPage(Principal principal,Model model){
        var all = courseService.allCourses();
        User user = userService.findByEmail(principal.getName());
        model.addAttribute("courses", all);
        model.addAttribute("isAdmin", user.getSystemRole().equals(SystemRole.ROLE_SUPER_ADMIN));
        return "courses";
    }

    @GetMapping("/{id}")
    @PreAuthorize("isSuperAdmin()")
    public String openCourse(@PathVariable Long id, Principal principal, Model model){
        User user = userService.findByEmail(principal.getName());
        Course course = courseService.getById(id);
        //TODO add handling not found
        if(user.getSystemRole().equals(SystemRole.ROLE_SUPER_ADMIN)){
            model.addAttribute("courseInfo",CourseInfoResponse.fromCourse(course));
            model.addAttribute("difficulties", CourseDifficulty.values());
            model.addAttribute("isPresent", true);
            return "pageCourse";
        }

        return "courses";
    }

    @PostMapping("/{id}/edit")
    @PreAuthorize("isSuperAdmin()")
    public String editCourse(@Valid @ModelAttribute CourseInfoRequest req, @PathVariable Long id, Principal principal){
        User user = userService.findByEmail(principal.getName());
        var course = courseService.updateCourse(req, id);
        return "redirect:/courses" + "/" + id;
    }

    @PostMapping("/{id}/delete")
    @PreAuthorize("isSuperAdmin()")
    public String deleteCourse(@PathVariable Long id){
        courseService.deleteCourse(id);
        return "redirect:/courses";
    }

    @GetMapping("/new")
    @PreAuthorize("isSuperAdmin()")
    public String createCoursePage(Model model){
        model.addAttribute("courseInfo", new CourseInfoRequest());
        model.addAttribute("difficulties", CourseDifficulty.values());
        model.addAttribute("isPresent", false);
        return "pageCourse";
    }

    @PostMapping("/new")
    @PreAuthorize("isSuperAdmin()")
    public String createCourse(@Valid @ModelAttribute CourseInfoRequest req, Principal principal){
        User user = userService.findByEmail(principal.getName());
        var course = courseService.createCourse(req, user);
        return "redirect:/courses" + "/" + course.getId();
    }




}
