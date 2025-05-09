package com.yarmak.neoHelper.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yarmak.neoHelper.model.Doctor;

import java.util.Optional;

@Repository
public interface AuthRepository extends JpaRepository<Doctor, Long> {

    Optional<Doctor> findByLogin(String login);
}
