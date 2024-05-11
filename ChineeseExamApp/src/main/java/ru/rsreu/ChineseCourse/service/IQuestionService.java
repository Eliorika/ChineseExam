package ru.rsreu.ChineseCourse.service;

import jakarta.transaction.Transactional;
import ru.rsreu.ChineseCourse.dto.QuestionInfoDto;
import ru.rsreu.ChineseCourse.dto.TestQuestionsDto;
import ru.rsreu.ChineseCourse.dto.request.CourseInfoRequest;
import ru.rsreu.ChineseCourse.model.Course;
import ru.rsreu.ChineseCourse.model.Question;
import ru.rsreu.ChineseCourse.model.User;

import java.util.List;

public interface IQuestionService {

    boolean checkAnswer(Long questionId, String answer);

    Question findById(Long id);

    @Transactional
    Question updateQuestion(QuestionInfoDto questionInfoDto, User admin);

    @Transactional
    Question deleteQuestion(Long questionId);

    void addGeneratedQuestion(Long id, Question question);


    void clearGeneratedQuestions(List<Long> questionsIds);
    void clearGeneratedQuestion(Long questionsIds);
}
