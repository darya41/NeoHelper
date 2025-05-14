package com.yarmak.neoHelper.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yarmak.neoHelper.model.patient.Patient;
import com.yarmak.neoHelper.repository.PatientRepository;

import jakarta.transaction.Transactional;

@Service
public class PatientService {

	private PatientRepository patientRepository;

	@Autowired
	public PatientService(PatientRepository patientRepository) {
		this.patientRepository = patientRepository;
	}

	@Transactional
	public List<Patient> findByMotherId(int id) {
		return patientRepository.findByMotherId(id);
	}

	@Transactional
	public Patient save(Patient patient) {
		return patientRepository.save(patient);
		
	}

	public Optional<Patient> findById(Integer patientId) {
		return patientRepository.findById(patientId);
	}

	public boolean existsById(Integer patientId) {
		
		return patientRepository.existsById(patientId);
	}

	

}
