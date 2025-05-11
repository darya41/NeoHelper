package com.yarmak.neoHelper.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.yarmak.neoHelper.dao.DoctorRepository;
import com.yarmak.neoHelper.model.doctor.Doctor;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DoctorService {

	private final DoctorRepository doctorRepository;

	@Transactional
	public Optional<Doctor> findDoctorById(int doctorId) {
		return doctorRepository.findById(doctorId);
	}

	@Transactional
	public Doctor update(Doctor updatedDoctor) {
		return doctorRepository.save(updatedDoctor);
	}

}
