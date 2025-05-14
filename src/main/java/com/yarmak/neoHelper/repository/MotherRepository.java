package com.yarmak.neoHelper.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.yarmak.neoHelper.model.patient.Mother;

@Repository
public interface MotherRepository extends JpaRepository<Mother, Integer> {
	@Query("SELECT m FROM Mother m LEFT JOIN FETCH m.address")
	List<Mother> findAllWithAddress();

}
