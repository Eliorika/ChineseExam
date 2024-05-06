package ru.rsreu.ChineseCourse.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.rsreu.ChineseCourse.model.AnswerStatistic;

@Repository
public interface IAnswerStatisticRepo extends JpaRepository<AnswerStatistic, Long> {


}
