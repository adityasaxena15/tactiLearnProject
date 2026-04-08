package com.example.tactiLearn.tactiLearnProject.controller;

import com.example.tactiLearn.tactiLearnProject.entity.GameQuestion;
import com.example.tactiLearn.tactiLearnProject.entity.UserScore;
import com.example.tactiLearn.tactiLearnProject.repository.TactiLearn;
import com.example.tactiLearn.tactiLearnProject.repository.UserScoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/game")
public class GameController {

    @Autowired
    private TactiLearn repo;

    @Autowired
    private UserScoreRepository scoreRepo;

    private List<GameQuestion> quizQuestions;
    private int currentIndex = 0;
    private int score = 0;

    @GetMapping("/startQuiz")
    public String startQuiz() {
        List<GameQuestion> all = repo.findAll();
        if (all.isEmpty()) return "No questions available!";
        Collections.shuffle(all);
        quizQuestions = all.subList(0, Math.min(5, all.size()));
        currentIndex = 0;
        score = 0;
        return "Quiz started!";
    }

    @GetMapping("/nextQuestion")
    public GameQuestion nextQuestion() {
        if (quizQuestions == null || currentIndex >= quizQuestions.size()) {
            return new GameQuestion();
        }
        return quizQuestions.get(currentIndex++);
    }

    @PostMapping("/submitAnswer")
    public Map<String, Object> submitAnswer(@RequestBody Map<String, String> request) {
        Map<String, Object> response = new HashMap<>();
        if (quizQuestions == null || currentIndex == 0) {
            response.put("error", "Quiz not started!");
            return response;
        }

        String selectedAnswer = request.get("selectedAnswer");
        GameQuestion currentQ = quizQuestions.get(currentIndex - 1);
        boolean isCorrect = currentQ.getCorrectAnswer().equalsIgnoreCase(selectedAnswer);
        if (isCorrect) score++;

        response.put("correct", isCorrect);
        response.put("correctAnswer", currentQ.getCorrectAnswer());
        response.put("explanation", currentQ.getExplanation());

        if (currentIndex == quizQuestions.size()) {
            UserScore finalScore = new UserScore();
            finalScore.setUsername("Player 1");
            finalScore.setScore(score);
            scoreRepo.save(finalScore);
        }
        return response;
    }

    @GetMapping("/result")
    public String getResult() {
        return "Your score is: " + score + "/" + (quizQuestions != null ? quizQuestions.size() : 0);
    }

    @GetMapping("/leaderboard")
    public List<UserScore> getLeaderboard() {
        return scoreRepo.findTop10ByOrderByScoreDesc();
    }

}