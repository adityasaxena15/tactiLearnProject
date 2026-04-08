package com.example.tactiLearn.tactiLearnProject.repository;

import com.example.tactiLearn.tactiLearnProject.entity.UserScore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface UserScoreRepository extends JpaRepository<UserScore, Long> {
    List<UserScore> findTop10ByOrderByScoreDesc();
}