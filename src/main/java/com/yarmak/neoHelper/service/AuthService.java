package com.yarmak.neoHelper.service;

import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import com.yarmak.neoHelper.dao.AuthRepository;
import com.yarmak.neoHelper.model.Doctor;

@Service
public class AuthService {
    
    private final AuthRepository  authRepository;
    
    public AuthService(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }
    
    public Doctor authenticate(String login, String password) {
        Doctor doctor = authRepository.findByLogin(login)
            .orElseThrow(() -> new RuntimeException("Пользователь не найден"));
        
        String hashedPassword = DigestUtils.md5DigestAsHex(password.getBytes());
        
        if (!doctor.getPassword().equals(hashedPassword)) {
            throw new RuntimeException("Неверный пароль");
        }
        
        return doctor;
    }
    
    public static String hashPassword(String password) {
        return DigestUtils.md5DigestAsHex(password.getBytes());
    }
}
