package com.yarmak.neoHelper.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yarmak.neoHelper.model.exam.PatientsExam;

@Repository
public interface PatientsExamRepository extends JpaRepository<PatientsExam, Long>{

}
