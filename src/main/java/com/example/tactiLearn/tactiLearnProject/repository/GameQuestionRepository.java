package com.example.tactiLearn.tactiLearnProject.repository;

import com.example.tactiLearn.tactiLearnProject.entity.GameQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameQuestionRepository extends JpaRepository<GameQuestion, Long> {
}