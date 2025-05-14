package com.yarmak.neoHelper.service;

import org.hibernate.service.spi.ServiceException;
import org.springframework.stereotype.Service;

import com.yarmak.neoHelper.model.doctor.Doctor;
import com.yarmak.neoHelper.repository.AuthRepository;
import com.yarmak.neoHelper.util.CustomPasswordEncoder;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

	private final AuthRepository authRepository;
	private final CustomPasswordEncoder passwordEncoder;
	
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
	public void registerDoctor(Doctor doctor) throws Exception {
		if (emailExists(doctor.getWorkEmail())) {
            throw new Exception("Email уже используется");
        }
        if (loginExists(doctor.getLogin())) {
            throw new Exception("Логин уже занят");
        }
		String encodedPassword = passwordEncoder.encode(doctor.getPassword());
		doctor.setPassword(encodedPassword);
		authRepository.save(doctor);
	}
	public void validateDoctorRegistration(Doctor doctor, String confirmPassword) throws Exception {
        if (!doctor.isTermsAccepted()) {
            throw new Exception("Необходимо согласиться с условиями использования");
        }
        if (!doctor.getPassword().equals(confirmPassword)) {
            throw new Exception("Пароли не совпадают");
        }
    }

}
