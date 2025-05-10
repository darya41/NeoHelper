package com.yarmak.neoHelper.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.yarmak.neoHelper.dao.DoctorRepository;
import com.yarmak.neoHelper.model.Doctor;

@Service
public class DoctorService {
	
	private final DoctorRepository doctorRepository;

    public DoctorService(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }
    
	public Optional<Doctor> findDoctorById(int doctorId) {
        return doctorRepository.findById(doctorId);
    }

	public Doctor update(Doctor updatedDoctor) {
		return doctorRepository.save(updatedDoctor);
	}

}
