package ru.rsreu.ChineseCourse.service;

import ru.rsreu.ChineseCourse.model.AnswerStatistic;

import java.util.List;

public interface IAnswerStatisticService {

    void saveStatistics(List<AnswerStatistic> answerStatisticList);
}
