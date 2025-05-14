package com.yarmak.neoHelper.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yarmak.neoHelper.model.score.Score;

public interface ScoreRepository extends JpaRepository<Score, Integer> {
}
