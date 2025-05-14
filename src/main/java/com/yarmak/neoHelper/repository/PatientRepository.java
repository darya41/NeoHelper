package com.yarmak.neoHelper.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.yarmak.neoHelper.model.patient.Patient;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Integer>  {

	List<Patient> findByMotherId(int id);

	
	@EntityGraph(attributePaths = {"scoreResults", "scoreResults.score"})
    @Query("SELECT p FROM Patient p WHERE p.id = :patientId")
    Optional<Patient> findByIdWithScores(@Param("patientId") Integer patientId);

}
