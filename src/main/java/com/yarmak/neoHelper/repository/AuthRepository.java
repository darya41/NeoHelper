package com.yarmak.neoHelper.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yarmak.neoHelper.model.doctor.Doctor;

import java.util.Optional;

@Repository
public interface AuthRepository extends JpaRepository<Doctor, Long> {
	Optional<Doctor> findByLogin(String login);

	boolean existsByWorkEmail(String workEmail);

	boolean existsByLogin(String login);
}