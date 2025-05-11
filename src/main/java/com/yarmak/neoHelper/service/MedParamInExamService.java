package com.yarmak.neoHelper.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yarmak.neoHelper.dao.MedParamInExamRepository;
import com.yarmak.neoHelper.model.exam.MedParamInExam;

@Service
public class MedParamInExamService {
	private final MedParamInExamRepository repository;

    @Autowired
    public MedParamInExamService(MedParamInExamRepository repository) {
        this.repository = repository;
    }

    public List<MedParamInExam> getParamsByExam(int examId) {
        return repository.findParamsByExamId(examId);
    }
}
