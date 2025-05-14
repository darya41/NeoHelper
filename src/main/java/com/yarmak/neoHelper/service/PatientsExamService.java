package com.yarmak.neoHelper.service;

import org.springframework.stereotype.Service;

import com.yarmak.neoHelper.model.exam.PatientsExam;
import com.yarmak.neoHelper.repository.PatientsExamRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PatientsExamService {
	
	private final PatientsExamRepository patientsExamRepository;
	
	@Transactional
	public PatientsExam save(PatientsExam patientExam) {
		return patientsExamRepository.save(patientExam);
	}

}
