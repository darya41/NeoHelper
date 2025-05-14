package com.yarmak.neoHelper.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yarmak.neoHelper.model.doctor.Doctor;

public interface DoctorRepository extends JpaRepository<Doctor, Integer> {
}
