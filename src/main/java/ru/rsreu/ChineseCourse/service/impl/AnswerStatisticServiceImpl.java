package ru.rsreu.ChineseCourse.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.rsreu.ChineseCourse.model.AnswerStatistic;
import ru.rsreu.ChineseCourse.model.AnswerStatisticPk;
import ru.rsreu.ChineseCourse.repo.IAnswerStatisticRepo;
import ru.rsreu.ChineseCourse.service.IAnswerStatisticService;
import ru.rsreu.ChineseCourse.service.IQuestionService;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AnswerStatisticServiceImpl implements IAnswerStatisticService {
    private final IAnswerStatisticRepo answerStatisticRepo;
    private final IQuestionService questionService;

    public void saveStatistics(List<AnswerStatistic> answerStatisticList){
        for (AnswerStatistic ans: answerStatisticList){
            AnswerStatisticPk answerStatisticPk = new AnswerStatisticPk();
            answerStatisticPk.setQuestionId(ans.getQuestion().getId());
            answerStatisticPk.setUserId(ans.getUser().getId());

            ans.setId(answerStatisticPk);
            ans.getId().setResponseDateTime(new Date());
            answerStatisticRepo.save(ans);
        }

    }
}
