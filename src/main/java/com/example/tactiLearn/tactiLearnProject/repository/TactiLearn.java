package com.example.tactiLearn.tactiLearnProject.repository;

import com.example.tactiLearn.tactiLearnProject.entity.GameQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TactiLearn extends JpaRepository<GameQuestion, Long> {
}