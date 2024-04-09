package ru.rsreu.ChineseCourse.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.rsreu.ChineseCourse.dto.TestQuestionsDto;
import ru.rsreu.ChineseCourse.model.AnswerStatistic;
import ru.rsreu.ChineseCourse.model.Course;
import ru.rsreu.ChineseCourse.model.User;
import ru.rsreu.ChineseCourse.model.enums.QuestionType;
import ru.rsreu.ChineseCourse.service.IAnswerStatisticService;
import ru.rsreu.ChineseCourse.service.IQuestionService;
import ru.rsreu.ChineseCourse.service.ITestService;
import ru.rsreu.ChineseCourse.service.IUserService;

import java.security.Principal;
import java.util.*;

@Controller
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestController {

    private Map<Long, Queue<TestQuestionsDto>> questions = new HashMap<>();
    private Map<Long, List<AnswerStatistic>> currentTestAnswerStats = new HashMap<>();
    private final IQuestionService questionService;
    private final ITestService testService;
    private final IUserService userService;
    private final IAnswerStatisticService answerStatisticService;


    @GetMapping("/{testId}")
    private String getQuestion(@PathVariable Long testId, Principal principal, Model model){
        User user = userService.findByEmail(principal.getName());

        if(questions.get(user.getId()) == null){
            init(user.getId(), testId);
        }

        TestQuestionsDto question = questions.get(user.getId()).peek();

        if(question == null){
            int questionsNumber = currentTestAnswerStats.get(user.getId()).size();
            int rightAnswers = currentTestAnswerStats.get(user.getId()).stream().filter(answerStatistic -> answerStatistic.isCorrectness()).toList().size();
            int accuracy = (int) (rightAnswers * 100.0 / questionsNumber);
            Course course = testService.getTestById(testId).getLesson().getCourse();
            answerStatisticService.saveStatistics(currentTestAnswerStats.get(user.getId()));
            currentTestAnswerStats.remove(user.getId());
            questions.remove(user.getId());

            model.addAttribute("questions_number", questionsNumber);
            model.addAttribute("accuracy", accuracy);
            model.addAttribute("course_id", course.getId());
            return "test/lessonPassed";
        }

        if(question.getCorrectness() == null){
            question.setAnswer("");
            model.addAttribute("isWrong", false);
            model.addAttribute("isRight", false);
        } else if(question.getCorrectness()){
            questions.get(user.getId()).poll();
            question.setAnswer(currentTestAnswerStats.get(user.getId()).get(currentTestAnswerStats.get(user.getId()).size()-1).getAnswer());
            model.addAttribute("isWrong", false);
            model.addAttribute("isRight", true);
        } else if(!question.getCorrectness()){
            questions.get(user.getId()).poll();
            question.setCorrectness(null);
            questions.get(user.getId()).offer(question);
            question.setAnswer(currentTestAnswerStats.get(user.getId()).get(currentTestAnswerStats.get(user.getId()).size()-1).getAnswer());
            String rightAnswer = questionService.findById(question.getQuestionId()).getAnswer();
            question.setRightAnswer(rightAnswer);
            model.addAttribute("isWrong", true);
            model.addAttribute("isRight", false);
        }

        double progress =  currentTestAnswerStats.get(user.getId()).size() * 100.0 / (questions.get(user.getId()).size() + currentTestAnswerStats.get(user.getId()).size());
        model.addAttribute("question", question);
        model.addAttribute("progress", progress);

        if(question.getQuestionType() == QuestionType.PLAIN_TEXT)
        {
            return "test/writeAnswer";

        } else if(question.getQuestionType() == QuestionType.CARD_AND_VARIATIONS){
            return "test/questionVariantWithCard";

        } else if(question.getQuestionType() == QuestionType.NO_CARD_VARIATION){
            return "test/questionWithVariant";
        }

        return null;
    }

    private void init(Long userId, Long testId){
        Queue<TestQuestionsDto> queue = new LinkedList<>();
        queue.addAll(testService.getQuestionsForTest(testId));
        questions.put(userId,queue);
        currentTestAnswerStats.put(userId, new ArrayList<>());
    }

    @PostMapping("/{testId}")
    private String validQuestionAnswer(@PathVariable Long testId, Principal principal, @ModelAttribute TestQuestionsDto question, @RequestParam(required = false, defaultValue = "false") Boolean skip){
        User user = userService.findByEmail(principal.getName());
        if(skip){
            var questionFrom = questions.get(user.getId()).poll();
            questions.get(user.getId()).offer(questionFrom);
        } else {
            AnswerStatistic answerStatistic = new AnswerStatistic();
            boolean correctness = questionService.checkAnswer(question.getQuestionId(), question.getAnswer());
            answerStatistic.setQuestion(questionService.findById(question.getQuestionId()));
            answerStatistic.setUser(user);
            answerStatistic.setCorrectness(correctness);
            answerStatistic.setAnswer(question.getAnswer());
            currentTestAnswerStats.get(user.getId()).add(answerStatistic);
            questions.get(user.getId()).peek().setCorrectness(correctness);

            if(correctness){
                questionService.clearGeneratedQuestion(question.getQuestionId());
            }
        }

        return "redirect:/test/"+ testId;
    }

    @PostMapping("/{testId}/skip")
    private String skipQuestion(@PathVariable Long testId, Principal principal){
        User user = userService.findByEmail(principal.getName());
        var question = questions.get(user.getId()).poll();
        questions.get(user.getId()).offer(question);
        return "redirect:/test/"+ testId;
    }

    @PostMapping("/clear")
    private void clear(Principal principal){
        User user = userService.findByEmail(principal.getName());
        questions.remove(user.getId());
        currentTestAnswerStats.remove(user.getId());
    }

}
