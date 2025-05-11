package com.yarmak.neoHelper.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yarmak.neoHelper.model.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {

}
