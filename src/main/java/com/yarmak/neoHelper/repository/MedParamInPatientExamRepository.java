package com.yarmak.neoHelper.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yarmak.neoHelper.model.exam.MedParamInPatientExam;

public interface MedParamInPatientExamRepository extends JpaRepository<MedParamInPatientExam, Long> {
}
