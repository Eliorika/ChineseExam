package ru.rsreu.ChineseCourse.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.rsreu.ChineseCourse.model.Lesson;
import ru.rsreu.ChineseCourse.model.UserAccessResourceMaterial;
import ru.rsreu.ChineseCourse.model.UserAccessResourceMaterialPk;

@Repository
public interface IUserAccessResourceMaterialRepo extends JpaRepository<UserAccessResourceMaterial, UserAccessResourceMaterialPk> {



}
