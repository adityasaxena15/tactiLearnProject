package com.example.tactiLearn.tactiLearnProject.controller;

import com.example.tactiLearn.tactiLearnProject.entity.GameQuestion;
import com.example.tactiLearn.tactiLearnProject.repository.GameQuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/game")
public class GameController {

    @Autowired
    private GameQuestionRepository repo;

    private List<GameQuestion> quizQuestions;
    private int currentIndex = 0;
    private int score = 0;

    // 🚀 START QUIZ
    @GetMapping("/startQuiz")
    public String startQuiz() {

        List<GameQuestion> all = repo.findAll();

        if (all.isEmpty()) {
            return "No questions available!";
        }

        Collections.shuffle(all);

        quizQuestions = all.subList(0, Math.min(5, all.size()));
        currentIndex = 0;
        score = 0;

        return "Quiz started!";
    }

    // ❓ NEXT QUESTION
    @GetMapping("/nextQuestion")
    public GameQuestion nextQuestion() {

        if (quizQuestions == null || currentIndex >= quizQuestions.size()) {
            return new GameQuestion(); // ✅ NEVER return null
        }

        GameQuestion q = quizQuestions.get(currentIndex);
        currentIndex++;

        return q;
    }

    // 📤 SUBMIT ANSWER
    @PostMapping("/submitAnswer")
    public String submitAnswer(@RequestBody Map<String, String> request) {

        if (quizQuestions == null || currentIndex == 0) {
            return "Quiz not started!";
        }

        String selectedAnswer = request.get("selectedAnswer");

        GameQuestion currentQ = quizQuestions.get(currentIndex - 1);

        if (currentQ.getCorrectAnswer().equalsIgnoreCase(selectedAnswer)) {
            score++;
        }

        return "Answer submitted!";
    }

    // 📊 RESULT
    @GetMapping("/result")
    public String getResult() {

        if (quizQuestions == null) {
            return "Quiz not started!";
        }

        return "Your score is: " + score + "/" + quizQuestions.size();
    }
}