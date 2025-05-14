package com.yarmak.neoHelper.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yarmak.neoHelper.model.score.Score;
import com.yarmak.neoHelper.repository.ScoreRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ScoreService {
    
    private final ScoreRepository scoreRepository;
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public Score getById(Integer scoreId) {
        return scoreRepository.findById(scoreId).get();
    }

    public List<Score> getAllScores() {
        return scoreRepository.findAll();
    }
    
    public static Map<String, Map<String, Integer>> parseParameters(String json) {
        try {
            return objectMapper.readValue(json, 
                new TypeReference<Map<String, Map<String, Integer>>>() {});
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Ошибка парсинга параметров шкалы", e);
        }
    }
}