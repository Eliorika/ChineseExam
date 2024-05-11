package ru.rsreu.ChineseCourse.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.rsreu.ChineseCourse.dto.QuestionInfoDto;
import ru.rsreu.ChineseCourse.exception.NotFoundException;
import ru.rsreu.ChineseCourse.model.Question;
import ru.rsreu.ChineseCourse.model.User;
import ru.rsreu.ChineseCourse.repo.IQuestionRepo;
import ru.rsreu.ChineseCourse.service.IQuestionService;

import java.util.Date;
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
    @Transactional
    public Question updateQuestion(QuestionInfoDto questionInfoDto, User admin){
        Question question = findById(questionInfoDto.getId());
        question.setQuestion(questionInfoDto.getQuestion());
        question.setAnswer(questionInfoDto.getAnswer());
        question.setQuestionType(question.getQuestionType());
        question.setAdmin(admin);
        question.setCardText(questionInfoDto.getCardText());
        question.setVariants(questionInfoDto.getVariants());
        question.setTopic(questionInfoDto.getTopic());
        question.setEditDate(new Date());
        question.setGenerated(question.isGenerated());
        question.setTest(questionInfoDto.isTest());
        return  questionRepo.save(question);
    }

    @Override
    @Transactional
    public Question deleteQuestion(Long questionId){
        Question question = findById(questionId);
        questionRepo.delete(question);
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
