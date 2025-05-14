package com.yarmak.neoHelper.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.yarmak.neoHelper.model.exam.MedParamInExam;
import com.yarmak.neoHelper.model.exam.MedParamInPatientExam;
import com.yarmak.neoHelper.model.exam.PatientsExam;
import com.yarmak.neoHelper.repository.MedParamInExamRepository;
import com.yarmak.neoHelper.repository.MedParamInPatientExamRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MedParamInPatientExamService {
	private final MedParamInPatientExamRepository repository;
    private final MedParamInExamRepository medParamInExamRepository;
    
    @Transactional
    public void saveParamsForPatient(List<Long> paramIds, 
    		List<String> values, 
    		PatientsExam patientExam) {
    	
        for (int i = 0; i < paramIds.size(); i++) {
            MedParamInExam medParamExam = medParamInExamRepository.findById(paramIds.get(i))
                .orElseThrow(() -> new RuntimeException("Медицинский параметр не найден"));

            MedParamInPatientExam medParamInPatientExam = new MedParamInPatientExam();
            medParamInPatientExam.setPatientsExam(patientExam);
            medParamInPatientExam.setMedParamExam(medParamExam);
            medParamInPatientExam.setValue(values.get(i));

            repository.save(medParamInPatientExam);
        }
    }

}
