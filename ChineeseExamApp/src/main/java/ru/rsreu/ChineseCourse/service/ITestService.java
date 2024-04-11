package ru.rsreu.ChineseCourse.service;

import ru.rsreu.ChineseCourse.dto.TestQuestionsDto;
import ru.rsreu.ChineseCourse.model.Test;

import java.util.List;

public interface ITestService {

    List<TestQuestionsDto> getQuestionsForTest(Long testId);

    Test getTestById(Long testId);

}
