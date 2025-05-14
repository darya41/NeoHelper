package com.yarmak.neoHelper.service;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ScoreCalculationService {

    private final ObjectMapper objectMapper;

    public int calculateTotalScore(Map<String, String> allParams, Map<String, Map<String, Integer>> parameters) {
        int totalScore = 0;
        for (Map.Entry<String, Map<String, Integer>> param : parameters.entrySet()) {
            String paramName = param.getKey();
            String selectedValueStr = allParams.get(paramName);
            if (selectedValueStr != null) {
                totalScore += Integer.parseInt(selectedValueStr);
            }
        }
        return totalScore;
    }

    public String buildResultDetailsJson(Map<String, String> allParams, Map<String, Map<String, Integer>> parameters) throws JsonProcessingException {
        Map<String, Object> resultDetails = new LinkedHashMap<>();
        for (Map.Entry<String, Map<String, Integer>> param : parameters.entrySet()) {
            String paramName = param.getKey();
            String selectedValueStr = allParams.get(paramName);
            if (selectedValueStr != null) {
                int selectedValue = Integer.parseInt(selectedValueStr);
                String selectedOption = param.getValue().entrySet().stream()
                        .filter(e -> e.getValue() == selectedValue)
                        .map(Map.Entry::getKey)
                        .findFirst()
                        .orElse("");
                
                Map<String, Object> paramResult = new LinkedHashMap<>();
                paramResult.put("selected", selectedOption);
                paramResult.put("score", selectedValue);
                resultDetails.put(paramName, paramResult);
            }
        }
        return objectMapper.writeValueAsString(resultDetails);
    }
}
