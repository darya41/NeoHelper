package com.yarmak.neoHelper.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yarmak.neoHelper.model.Doctor;

public interface DoctorRepository extends JpaRepository<Doctor, Integer> {
}
