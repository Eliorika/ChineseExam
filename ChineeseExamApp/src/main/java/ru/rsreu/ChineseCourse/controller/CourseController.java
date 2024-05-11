package ru.rsreu.ChineseCourse.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.rsreu.ChineseCourse.config.ReportClient;
import ru.rsreu.ChineseCourse.dto.LessonInfoDto;
import ru.rsreu.ChineseCourse.dto.request.CourseInfoRequest;
import ru.rsreu.ChineseCourse.dto.response.CourseInfoAdminResponse;
import ru.rsreu.ChineseCourse.dto.LessonInfoDto;
import ru.rsreu.ChineseCourse.dto.response.QuestionInfoResponse;
import ru.rsreu.ChineseCourse.model.Course;
import ru.rsreu.ChineseCourse.model.User;
import ru.rsreu.ChineseCourse.model.enums.CourseDifficulty;
import ru.rsreu.ChineseCourse.model.enums.SystemRole;
import ru.rsreu.ChineseCourse.service.ICourseService;
import ru.rsreu.ChineseCourse.service.IUserService;

import java.security.Principal;
import java.util.*;

@RequestMapping("/courses")
@RequiredArgsConstructor
@Controller
public class CourseController {

    private final ICourseService courseService;
    private final IUserService userService;
    private final ReportClient reportClient;

    //admin - (course-count)
    private final Map<Long, Map<Long, Integer>> blankLessons = new HashMap<>();

    //admin - (lesson-count)
    private final Map<Long, Map<Long, Integer>> blankQuestions = new HashMap<>();

    @GetMapping()
    public String allCoursesPage(Principal principal,Model model){
        List<Course> all = new ArrayList<>(courseService.allCourses());

        User user = userService.findByEmail(principal.getName());
        if(!user.getSystemRole().equals(SystemRole.ROLE_SUPER_ADMIN)){
            all = all.stream().filter(course -> course.getIsVisible()).toList();
        }

        List<Course> checked = all.stream().filter(c-> c.getStudents().stream().anyMatch(u-> u.getId() == user.getId()) && c.getIsVisible()).toList();
        if(user.getSystemRole() == SystemRole.ROLE_USER){
            all = all.stream().filter(c-> c.getStudents().stream().allMatch(u-> u.getId() != user.getId()) && c.getIsVisible()).toList();
        }
        model.addAttribute("courses", all);
        model.addAttribute("checked", checked);
        model.addAttribute("isAdmin", user.getSystemRole().equals(SystemRole.ROLE_SUPER_ADMIN));
        return "courses";
    }

    @GetMapping("/{id}")
    //@PreAuthorize("isSuperAdmin()")
    public String openCourse(@PathVariable Long id, Principal principal, Model model){
        User user = userService.findByEmail(principal.getName());
        Course course = courseService.getById(id);
        //TODO add handling not found
        if(user.getSystemRole().equals(SystemRole.ROLE_SUPER_ADMIN)){
            model.addAttribute("courseInfo", CourseInfoAdminResponse.fromCourse(course));
            model.addAttribute("difficulties", CourseDifficulty.values());
            model.addAttribute("isPresent", true);
            return "pageCourseInfo";
        }


        var courseStudentInfo = courseService.getStudentCourseInfo(course.getId(), user.getId());
        model.addAttribute("course",courseStudentInfo);
        return "pageCoursePassing";
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
        return "pageCourseInfo";
    }

    @PostMapping("/new")
    @PreAuthorize("isSuperAdmin()")
    public String createCourse(@Valid @ModelAttribute CourseInfoRequest req, Principal principal){
        User user = userService.findByEmail(principal.getName());
        var course = courseService.createCourse(req, user);
        return "redirect:/courses" + "/" + course.getId();
    }

    @GetMapping("/{id}/settings")
    @PreAuthorize("isSuperAdmin()")
    public String courseSettings(Principal principal, Model model, @PathVariable Long id){
//        User admin = userService.findByEmail(principal.getName());
//        Course course = courseService.getById(id);
//        List<LessonInfoDto> lessons = new ArrayList<>(course.getLessons().stream()
//                .map(LessonInfoDto::fromLesson).toList());
//        try{
//            Integer count = blankLessons.get(admin.getId()).get(id);
//            for (int i = 0; i < count; i++){
//                var les = new LessonInfoDto();
//                les.setCourseId(id);
//                lessons.add(les);
//            }
//
//
//        } catch (Exception e){
//
//        }
//
//        try {
//            Integer count = blankQuestions.get(admin.getId()).get(id);
//            var addLes = lessons.stream()
//                    .filter(les -> les.getId() == id)
//                    .findFirst().orElse(null);
//
//            for (int i = 0; i < count; i++){
//                var q = new QuestionInfoResponse();
//                var ls = new ArrayList<>(addLes.getQuestions());
//                        ls.add(q);
//                addLes.setQuestions(ls);
//            }
//
//        } catch (Exception e){
//
//        }
//        model.addAttribute("lessons", lessons);
        return "settingCourse";
    }

    @PostMapping("/{id}/settings/add-lesson")
    @PreAuthorize("isSuperAdmin()")
    public String getAddCourseLessons(@PathVariable Long id, Principal principal) {
        User admin = userService.findByEmail(principal.getName());
        Integer count;
        try{
        count = blankLessons.get(admin.getId()).get(id) + 1;
        }catch (Exception e){
            count = 1;
            blankLessons.put(admin.getId(), new HashMap<>());
        }

        blankLessons.get(admin.getId()).put(id, count);
        return "redirect:/courses/" + id + "/settings";
    }

    @PostMapping("/{id}/settings/add-question")
    @PreAuthorize("isSuperAdmin()")
    public String getAddCourseLessonQuestions(@PathVariable Long id, Principal principal, @RequestParam Long lessonId) {
        User admin = userService.findByEmail(principal.getName());
        Integer count;
        try{
            count = blankQuestions.get(admin.getId()).get(lessonId) + 1;
        }catch (Exception e){
            count = 1;
            blankQuestions.put(admin.getId(), new HashMap<>());
        }

        blankQuestions.get(admin.getId()).put(id, count);
        return "redirect:/courses/" + id + "/settings";
    }

    @GetMapping("/{id}/report")
    @ResponseBody
    public byte[] generateReport(@PathVariable Integer id, Principal principal){
        User user = userService.findByEmail(principal.getName());
        return reportClient.getUserStatReport(Math.toIntExact(user.getId()), id);
        //return "redirect:/courses/" + id;
    }

    @PostMapping("/{id}/check-in")
    public String checkIn(@PathVariable Long id, Principal principal){
        User user = userService.findByEmail(principal.getName());
        courseService.checkInForCourse(id, user);
        return "redirect:/courses/" + id;
    }
}
