package ru.rsreu.ChineseCourse.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.rsreu.ChineseCourse.exception.NotFoundException;
import ru.rsreu.ChineseCourse.model.Question;
import ru.rsreu.ChineseCourse.repo.IQuestionRepo;
import ru.rsreu.ChineseCourse.service.IQuestionService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements IQuestionService {
    private final IQuestionRepo questionRepo;
    private Map<Long, Question> generatedQuestions = new HashMap<>();

    @Override
    public boolean checkAnswer(Long questionId, String answer) {
        Question question = questionRepo.findById(questionId).orElse(null);
        if (question == null){
            question = generatedQuestions.get(questionId);
        }
        if(question == null)
            throw new NotFoundException("Такой вопрос не найден");
        return question.getAnswer().equals(answer.replaceAll("[!.,;\n]",""));
    }

    @Override
    public Question findById(Long id) {
        Question question = questionRepo.findById(id).orElse(null);
        if (question == null){
            question = generatedQuestions.get(id);
        }
        if (question == null)
            throw new NotFoundException("Такой вопрос не найден");
        return question;
    }

    @Override
    public void addGeneratedQuestion(Long id, Question question) {
        generatedQuestions.put(id, question);
    }

    @Override
    public void clearGeneratedQuestions(List<Long> questionsIds) {
        for (Long questionId: questionsIds) {
            generatedQuestions.remove(questionId);
        }
    }

    @Override
    public void clearGeneratedQuestion(Long questionId) {
            generatedQuestions.remove(questionId);
    }


}
