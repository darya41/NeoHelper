package com.yarmak.neoHelper.controller;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.yarmak.neoHelper.model.patient.Patient;
import com.yarmak.neoHelper.model.score.Score;
import com.yarmak.neoHelper.service.PatientService;

import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.yarmak.neoHelper.service.ScoreResultService;
import com.yarmak.neoHelper.service.ScoreService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/scale")
public class ScaleController {

    private final ScoreResultService scoreResultService;
    private final PatientService patientService;
    private final ScoreService scoreService;

    @GetMapping("/results/{patientId}")
    public String getPatientResults(@PathVariable int patientId, Model model) {
        Patient patient = patientService.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Пациент с ID " + patientId + " не найден"));
        
        var results = scoreResultService.findAllByPatientId(patientId);
        
        model.addAttribute("results", results);
        model.addAttribute("patient", patient);
        model.addAttribute("hasResults", !(results == null));
        
        return "patientScales";
    }
    
    @GetMapping("/add/{patientId}")
    public String showAddScaleForm(@PathVariable int patientId, Model model) {
        Score score = scoreService.getById(1);
        if (score == null) {
            throw new RuntimeException("Шкала не найдена");
        }
        
        Patient patient = patientService.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Пациент не найден"));

        model.addAttribute("patient", patient);
        model.addAttribute("score", score);
        
        return "addScale";
    }
    
    @PostMapping("/save/{patientId}")
    public String saveScaleResults(
            @PathVariable int patientId,
            @RequestParam int scoreId,
            @RequestParam Map<String, String> allParams,
            RedirectAttributes redirectAttributes) {
        
        scoreResultService.save(patientId, scoreId, allParams);
        return "redirect:/scale/results/" + patientId;
    }
}