package com.yarmak.neoHelper.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yarmak.neoHelper.model.exam.MedParamInExam;
import com.yarmak.neoHelper.repository.MedParamInExamRepository;

import jakarta.transaction.Transactional;

@Service
public class MedParamInExamService {
	private final MedParamInExamRepository repository;

    @Autowired
    public MedParamInExamService(MedParamInExamRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public List<MedParamInExam> getParamsByExam(int examId) {
        return repository.findParamsByExamId(examId);
    }
}
