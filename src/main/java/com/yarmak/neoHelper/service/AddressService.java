package com.yarmak.neoHelper.service;

import org.springframework.stereotype.Service;

import com.yarmak.neoHelper.model.patient.Address;
import com.yarmak.neoHelper.repository.AddressRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AddressService {
	
	private final AddressRepository addressRepository;

	@Transactional
	public Address save(Address address) {
		return addressRepository.save(address);
	}

}


