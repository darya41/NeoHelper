package com.yarmak.neoHelper.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.yarmak.neoHelper.model.exam.MedParamInExam;

@Repository
public interface MedParamInExamRepository extends JpaRepository<MedParamInExam, Long> {
	
	@Query("SELECT mpe FROM MedParamInExam mpe "
			+ "LEFT JOIN FETCH mpe.medicalParameter mp "
			+ "LEFT JOIN FETCH mp.parameterValues "
			+ "WHERE mpe.exam.id = :examId")
	List<MedParamInExam> findParamsByExamId(@Param("examId") int examId);

}
