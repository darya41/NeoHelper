package com.yarmak.neoHelper.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yarmak.neoHelper.model.Mother;

@Repository
public interface MotherRepository extends JpaRepository<Mother, Long> {
}