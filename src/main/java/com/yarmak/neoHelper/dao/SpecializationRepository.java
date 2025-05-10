package com.yarmak.neoHelper.dao;

import com.yarmak.neoHelper.model.Specialization;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface SpecializationRepository extends JpaRepository<Specialization, Long> {

	Optional<Specialization> findById(int id);
}
