package ru.rsreu.ChineseCourse.service.impl;

import com.github.f4b6a3.uuid.UuidCreator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.rsreu.ChineseCourse.dto.TestQuestionsDto;
import ru.rsreu.ChineseCourse.exception.NotFoundException;
import ru.rsreu.ChineseCourse.model.Question;
import ru.rsreu.ChineseCourse.model.Test;
import ru.rsreu.ChineseCourse.model.Topic;
import ru.rsreu.ChineseCourse.model.Variant;
import ru.rsreu.ChineseCourse.model.enums.QuestionType;
import ru.rsreu.ChineseCourse.repo.ITestRepo;
import ru.rsreu.ChineseCourse.repo.IVariantRepo;
import ru.rsreu.ChineseCourse.service.IQuestionService;
import ru.rsreu.ChineseCourse.service.ITestService;

import java.util.*;

@Service
@RequiredArgsConstructor
public class TestServiceImpl implements ITestService {
    private final ITestRepo testRepo;
    private final IVariantRepo variantRepo;
    private final IQuestionService questionService;
    private final Random random = new Random();
    @Override
    public List<TestQuestionsDto> getQuestionsForTest(Long testId) {
        Test test = testRepo.findById(testId).orElse(null);
        if(test == null)
            throw new NotFoundException("Такой тест не существует");

        List<TestQuestionsDto> questionsDtos = new ArrayList<>();

        questionsDtos.addAll(test.getQuestions().stream().filter(question -> !question.isGenerated()).map(TestQuestionsDto::fromQuestion).toList());

        var generated = getGeneratedQuestionsForTest(test.getQuestions().stream().filter(question -> question.isGenerated()).toList());
        questionsDtos.addAll(generated);

        return questionsDtos;
    }

    @Override
    public Test getTestById(Long testId) {
        Test test = testRepo.findById(testId).orElse(null);
        if(test == null)
            throw new NotFoundException("Такой тест не существует");
        return test;
    }



    private List<TestQuestionsDto> getGeneratedQuestionsForTest(List<Question> questions) {

        List<TestQuestionsDto> questionsDtos = new ArrayList<>();
        for (Question question: questions) {
            UUID uuid = UuidCreator.getTimeBased();
            long timestamp = (uuid.timestamp() - 0x01b21dd213814000L) / 10000;
            TestQuestionsDto testQuestionsDto = new TestQuestionsDto();
            testQuestionsDto.setQuestionId(timestamp);
            testQuestionsDto.setCardText(question.getCardText());
            testQuestionsDto.setQuestionType(question.getQuestionType());
            testQuestionsDto.setTestId(question.getTest().getId());

            List<Variant> variants = question.getTopic().getVariants();
            Variant rightVariant = variants.get(random.nextInt(variants.size()-1));
            //String textQuestion = (question.getCardText() == null || question.getCardText().isBlank())? question.getQuestion():question.getCardText();
            String generatedQuestion = rightVariant.getVariant();
            if(question.getQuestionType() != QuestionType.NO_CARD_VARIATION){
                testQuestionsDto.setCardText(generatedQuestion);
                testQuestionsDto.setQuestion(question.getQuestion());
            } else {
                testQuestionsDto.setQuestion(question.getQuestion() + generatedQuestion);
            }



            //testQuestionsDto.setQuestion(generatedQuestion);

            if(question.isTest()){
                testQuestionsDto.setVariants(generateOptions(question, rightVariant) );
            }
            Variant translation = variantRepo.getTranslation(rightVariant.getId());
            question.setAnswer(question.getAnswer()+translation.getVariant());


            questionService.addGeneratedQuestion(timestamp, question);
            questionsDtos.add(testQuestionsDto);
        }

        return questionsDtos;
    }


    private List<String> generateOptions(Question q, Variant rightVariant) {
        Variant translation = variantRepo.getTranslation(rightVariant.getId());
        List<Variant> topicVariants = q.getTopic().getVariants();
        topicVariants.removeIf(variant -> variant.getId() == rightVariant.getId());
        Collections.shuffle(topicVariants);
        List<String> generatedVariants = new ArrayList<>();
        if (translation != null) {
            generatedVariants.add(q.getAnswer() + translation.getVariant());
            for (int i = 0; i < topicVariants.size(); i++) {
                if(i >= 3)
                    break;
                Variant variant = topicVariants.get(i);
                generatedVariants.add(q.getAnswer() + variantRepo.getTranslation(variant.getId()).getVariant());
            }
        }
        Collections.shuffle(generatedVariants);
        return generatedVariants;
    }

}
