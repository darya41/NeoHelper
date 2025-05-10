package com.yarmak.neoHelper.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yarmak.neoHelper.dao.MotherRepository;
import com.yarmak.neoHelper.model.Mother;

@Service
public class MotherService {
	
	private  MotherRepository  motherRepository;
	
	@Autowired // ✅ Автоматическая инициализация
    public MotherService(MotherRepository motherRepository) {
        this.motherRepository = motherRepository;
    }

	 public List<Mother> getAllMothers() {
		 return motherRepository.findAll();
	 }

}
