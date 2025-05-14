package com.yarmak.neoHelper.repository;

import org.springframework.stereotype.Repository;

import com.yarmak.neoHelper.model.doctor.Specialization;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface SpecializationRepository extends JpaRepository<Specialization, Long> {

	Optional<Specialization> findById(int id);
}
