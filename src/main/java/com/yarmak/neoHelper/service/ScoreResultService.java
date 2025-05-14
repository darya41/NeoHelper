package com.yarmak.neoHelper.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yarmak.neoHelper.model.patient.Patient;
import com.yarmak.neoHelper.model.score.Score;
import com.yarmak.neoHelper.model.score.ScoreResult;
import com.yarmak.neoHelper.repository.ScoreResultRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ScoreResultService {
    private final ScoreResultRepository scoreResultRepository;
    private final PatientService patientService;
    private final ScoreService scoreService;
    private final ScoreCalculationService scoreCalculationService;
    
    public List<ScoreResult> getPatientScoreResults(Integer patientId) {
        return scoreResultRepository.findByPatientIdWithScore(patientId);
    }
    
    public ScoreResult saveScoreResult(ScoreResult scoreResult) {
        return scoreResultRepository.save(scoreResult);
    }

	public Object findAllByPatientId(int patientId) {
		
		return scoreResultRepository.findAllByPatientId(patientId);
	}

	public void save(int patientId, int scoreId, Map<String, String> allParams) {
		 Score score = scoreService.getById(scoreId);
	        Patient patient = patientService.findById(patientId)
	                .orElseThrow(() -> new RuntimeException("Пациент не найден"));

	        Map<String, Map<String, Integer>> parameters = ScoreService.parseParameters(score.getParameters());
	        
	        int totalScore = scoreCalculationService.calculateTotalScore(allParams, parameters);
	        String valuesResults = null;
			try {
				valuesResults = scoreCalculationService.buildResultDetailsJson(allParams, parameters);
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	        ScoreResult scoreResult = new ScoreResult();
	        scoreResult.setPatient(patient);
	        scoreResult.setScore(score);
	        scoreResult.setDate(LocalDateTime.now());
	        scoreResult.setTotalScore(totalScore);
	        scoreResult.setValuesResults(valuesResults);
	        
	        scoreResultRepository.save(scoreResult);
		
	}
}