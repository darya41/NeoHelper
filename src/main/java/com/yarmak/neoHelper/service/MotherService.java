package com.yarmak.neoHelper.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yarmak.neoHelper.dao.MotherRepository;
import com.yarmak.neoHelper.model.Mother;

import jakarta.transaction.Transactional;

@Service
public class MotherService {

	private MotherRepository motherRepository;
	private static final Logger log = LoggerFactory.getLogger(MotherService.class);
	
	@Autowired
	public MotherService(MotherRepository motherRepository) {
		this.motherRepository = motherRepository;
	}

	@Transactional
	public List<Mother> getAllMothers() {
	    log.info("Начало извлечения списка матерей из базы...");
	    List<Mother> mothers = motherRepository.findAllWithAddress();
	    log.info("Найдено матерей в БД: {}", mothers.size());
	    return mothers;
	}


	@Transactional
	public Mother save(Mother mother) {
		return motherRepository.save(mother);
	}

	public Optional<Mother> getById(int id) {
		return motherRepository.findById(id);
	}

}
