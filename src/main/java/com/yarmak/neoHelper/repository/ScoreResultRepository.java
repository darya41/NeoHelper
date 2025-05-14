package com.yarmak.neoHelper.repository;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.yarmak.neoHelper.model.score.ScoreResult;

public interface ScoreResultRepository extends JpaRepository<ScoreResult, Integer> {
    
	@EntityGraph(attributePaths = {"score"})
    @Query("SELECT sr FROM ScoreResult sr WHERE sr.patient.id = :patientId")
    List<ScoreResult> findByPatientIdWithScore(@Param("patientId") Integer patientId);

	List<ScoreResult> findAllByPatientId(int patientId);
}