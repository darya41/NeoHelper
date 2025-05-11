package com.yarmak.neoHelper.service;

import jakarta.transaction.Transactional;

import com.yarmak.neoHelper.dao.DaoException;
import com.yarmak.neoHelper.dao.SpecializationRepository;
import com.yarmak.neoHelper.model.doctor.Specialization;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SpecializationService {

	private final SpecializationRepository specializationRepository;

	public SpecializationService(SpecializationRepository specializationRepository) {
		this.specializationRepository = specializationRepository;
	}

	@Transactional
	public List<Specialization> getAllSpecializations() {
		return specializationRepository.findAll();
	}

	@Transactional
	public Optional<Specialization> findById(Long id) throws DaoException {
		return specializationRepository.findById(id);
	}

	@Transactional
	public Optional<Specialization> findById(int id) {
		return specializationRepository.findById(id);
	}

}