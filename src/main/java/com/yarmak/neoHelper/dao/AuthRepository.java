package com.yarmak.neoHelper.dao;

import com.yarmak.neoHelper.model.Doctor;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface AuthRepository extends JpaRepository<Doctor, Long> {
    Optional<Doctor> findByLogin(String login);
    boolean existsByWorkEmail(String workEmail);
    boolean existsByLogin(String login);
}