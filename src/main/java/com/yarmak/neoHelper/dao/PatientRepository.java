package com.yarmak.neoHelper.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yarmak.neoHelper.model.Patient;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Integer>  {

	List<Patient> findByMotherId(int id);

}
