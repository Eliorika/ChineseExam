package ru.rsreu.ChineseCourse.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.rsreu.ChineseCourse.model.User;

@Repository
public interface IUserRepo extends JpaRepository<User, Long> {

    User findByEmail(String email);






}
