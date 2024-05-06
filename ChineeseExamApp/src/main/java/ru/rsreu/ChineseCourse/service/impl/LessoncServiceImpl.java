package ru.rsreu.ChineseCourse.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.rsreu.ChineseCourse.exception.NotFoundException;
import ru.rsreu.ChineseCourse.model.*;
import ru.rsreu.ChineseCourse.repo.ILessonRepo;
import ru.rsreu.ChineseCourse.repo.IUserAccessResourceMaterialRepo;
import ru.rsreu.ChineseCourse.service.ILessonService;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class LessoncServiceImpl implements ILessonService {
    private final ILessonRepo lessonRepo;
    private final IUserAccessResourceMaterialRepo userAccessResourceMaterialRepo;

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
}
