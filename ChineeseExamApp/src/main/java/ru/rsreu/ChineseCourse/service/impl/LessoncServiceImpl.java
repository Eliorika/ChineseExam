package ru.rsreu.ChineseCourse.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.rsreu.ChineseCourse.dto.LessonInfoDto;
import ru.rsreu.ChineseCourse.exception.AlreadyExistsException;
import ru.rsreu.ChineseCourse.exception.NotFoundException;
import ru.rsreu.ChineseCourse.model.*;
import ru.rsreu.ChineseCourse.repo.ICourseRepo;
import ru.rsreu.ChineseCourse.repo.ILessonRepo;
import ru.rsreu.ChineseCourse.repo.IResourceMaterialRepo;
import ru.rsreu.ChineseCourse.repo.IUserAccessResourceMaterialRepo;
import ru.rsreu.ChineseCourse.service.ILessonService;

import java.util.ArrayList;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class LessoncServiceImpl implements ILessonService {
    private final ILessonRepo lessonRepo;
    private final ICourseRepo courseRepo;
    private final IUserAccessResourceMaterialRepo userAccessResourceMaterialRepo;
    private final IResourceMaterialRepo resourceMaterialRepo;

    @Override
    public Lesson findById(Long id) {
        Lesson lesson = lessonRepo.findById(id).orElse(null);
        if(lesson == null){
            throw new NotFoundException("Урок с номером " + id + " не найден");
        }

        return lesson;
    }

    @Override
    @Transactional
    public void logMaterial(Long id, User user){
        Lesson lesson = findById(id);
        for (ResourceMaterial material:
             lesson.getResourceMaterialList()) {
            UserAccessResourceMaterial userAccessResourceMaterial = new UserAccessResourceMaterial();
            UserAccessResourceMaterialPk pk = new UserAccessResourceMaterialPk();
            pk.setResourceId(material.getId());
            pk.setUserId(user.getId());
            pk.setGetDate(new Date());
            userAccessResourceMaterial.setId(pk);
            userAccessResourceMaterial.setUser(user);
            userAccessResourceMaterial.setResourceMaterial(material);
            userAccessResourceMaterialRepo.save(userAccessResourceMaterial);
        }

    }

    @Override
    @Transactional
    public Lesson createLesson(User admin, LessonInfoDto lessonRequest){
        if(lessonRepo.findByLessonName(lessonRequest.getName()).orElse(null) != null)
            throw new AlreadyExistsException("Такой урок уже существует");

        Course course = courseRepo.findById(lessonRequest.getCourseId()).orElse(null);

        if(course == null)
            throw new AlreadyExistsException("Такой курс не существует");

        Lesson lesson = new Lesson();
        lesson.setAdmin(admin);
        lesson.setDescription(lessonRequest.getDescription());
        lesson.setLessonName(lessonRequest.getName());
        lesson.setCourse(course);
        course.getLessons().add(lesson);
        lesson.setTests(new ArrayList<>());
        lesson.setAddDate(new Date());
        lesson.setEditDate(new Date());
        lesson.setIsVisible(false);
        lesson.setResourceMaterialList(new ArrayList<>());
        return lessonRepo.save(lesson);
    }

    @Override
    @Transactional
    public Lesson updateLesson(User admin, LessonInfoDto lessonRequest){
        Course course = courseRepo.findById(lessonRequest.getCourseId()).orElse(null);

        if(course == null)
            throw new AlreadyExistsException("Такой курс не существует");

        Lesson lesson = lessonRepo.findById(lessonRequest.getId()).orElse(null);
        if(lesson == null)
            throw new AlreadyExistsException("Такой курс не существует");
        lesson.setAdmin(admin);
        lesson.setDescription(lessonRequest.getDescription());
        lesson.setLessonName(lessonRequest.getName());
        lesson.setCourse(course);
        course.getLessons().add(lesson);
        lesson.setEditDate(new Date());
        lesson.setIsVisible(lessonRequest.isVisible());
        lesson.setResourceMaterialList(lessonRequest.getResourceMaterialList().stream().map(r -> resourceMaterialRepo.findById(r.getId()).orElse(null)).toList());
        return lessonRepo.save(lesson);
    }

    @Override
    @Transactional
    public Lesson deleteLessonById(Long lessonId){
       Lesson lesson = findById(lessonId);
       lessonRepo.delete(lesson);
       return lesson;
    }
}
