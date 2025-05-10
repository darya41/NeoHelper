package com.yarmak.neoHelper.service;

import com.yarmak.neoHelper.model.Specialization;
import com.yarmak.neoHelper.dao.DaoException;
import com.yarmak.neoHelper.dao.SpecializationRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SpecializationService {

    private final SpecializationRepository specializationRepository;

    public SpecializationService(SpecializationRepository specializationRepository) {
        this.specializationRepository = specializationRepository;
    }

    public List<Specialization> getAllSpecializations() {
        return specializationRepository.findAll();
    }

    public Optional<Specialization> findById(Long id) throws DaoException {
        return specializationRepository.findById(id);
    }
}