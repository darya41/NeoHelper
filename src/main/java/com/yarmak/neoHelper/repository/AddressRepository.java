package com.yarmak.neoHelper.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yarmak.neoHelper.model.patient.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {

}
