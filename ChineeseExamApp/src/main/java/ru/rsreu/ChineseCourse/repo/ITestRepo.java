package ru.rsreu.ChineseCourse.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.rsreu.ChineseCourse.model.Course;
import ru.rsreu.ChineseCourse.model.Test;

@Repository
public interface ITestRepo extends JpaRepository<Test, Long> {

    @Query(value = "select check_answer_statistic(?1, ?2)", nativeQuery = true)
    boolean isTestCompletedByUser(Long testId, Long userId);

}
