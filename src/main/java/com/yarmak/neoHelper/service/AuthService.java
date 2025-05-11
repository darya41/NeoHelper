package com.yarmak.neoHelper.service;

import org.hibernate.service.spi.ServiceException;
import org.springframework.stereotype.Service;

import com.yarmak.neoHelper.dao.AuthRepository;
import com.yarmak.neoHelper.model.Doctor;
import com.yarmak.neoHelper.util.CustomPasswordEncoder;

import jakarta.transaction.Transactional;

@Service
public class AuthService {

	private final AuthRepository authRepository;
	private final CustomPasswordEncoder passwordEncoder;

	public AuthService(AuthRepository authRepository, CustomPasswordEncoder passwordEncoder) {
		this.authRepository = authRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Transactional
	public Doctor authenticate(String login, String password) {
		try {
			Doctor doctor = authRepository.findByLogin(login)
					.orElseThrow(() -> new RuntimeException("Пользователь не найден"));

			if (!passwordEncoder.matches(password, doctor.getPassword())) {
				throw new RuntimeException("Неверный пароль");
			}

			return doctor;
		} catch (RuntimeException e) {
			throw new ServiceException("Ошибка авторизации", e);
		}
	}

	@Transactional
	public boolean emailExists(String email) {
		return authRepository.existsByWorkEmail(email);
	}

	@Transactional
	public boolean loginExists(String login) {
		return authRepository.existsByLogin(login);
	}

	@Transactional
	public void registerDoctor(Doctor doctor) {
		String encodedPassword = passwordEncoder.encode(doctor.getPassword());
		doctor.setPassword(encodedPassword);
		authRepository.save(doctor);
	}

}
