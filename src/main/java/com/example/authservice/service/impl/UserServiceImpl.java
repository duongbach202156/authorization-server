package com.example.authservice.service.impl;

//import com.example.authservice.mapper.UserMapper;
import com.example.authservice.event.ForgotPasswordEvent;
import com.example.authservice.exception.ForgotPasswordTokenException;
import com.example.authservice.exception.ResourceAlreadyExistException;
import com.example.authservice.exception.ResourceNotFoundException;
import com.example.authservice.mapper.UserMapper;
import com.example.authservice.model.entity.ForgotPasswordToken;
import com.example.authservice.model.entity.Role;
//import com.example.authservice.model.entity.User;
import com.example.authservice.model.entity.User;
import com.example.authservice.model.form.AdminRegistrationForm;
//import com.example.authservice.repository.jpa.UserRepository;
import com.example.authservice.model.form.ResetPasswordForm;
import com.example.authservice.repository.jpa.ForgotPasswordRepository;
import com.example.authservice.repository.jpa.UserRepository;
import com.example.authservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ForgotPasswordRepository forgotPasswordRepository;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void createAdmin(AdminRegistrationForm adminRegistrationForm) {
        if (emailExists(adminRegistrationForm.getEmail())) {
            throw new ResourceAlreadyExistException("Email already exist: " + adminRegistrationForm.getEmail());
        }
        User user = userMapper.toAdminEntity(adminRegistrationForm);
        user.setRole(Role.ADMIN);
        userRepository.save(user);
    }

    @Override
    public void forgotPassword(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new ResourceNotFoundException("Email not found!");
        }
        ForgotPasswordToken forgotPasswordToken = new ForgotPasswordToken();
        forgotPasswordToken.setUser(user);
        forgotPasswordToken.setToken(UUID.randomUUID().toString());
        forgotPasswordToken.setExpiredTime(LocalDateTime.now().plusSeconds(ForgotPasswordToken.EXPIRATION));
        forgotPasswordToken = forgotPasswordRepository.save(forgotPasswordToken);
        ForgotPasswordEvent forgotPasswordEvent = new ForgotPasswordEvent(email, forgotPasswordToken);
        applicationEventPublisher.publishEvent(forgotPasswordEvent);
    }

    @Override
    public void resetPassword(ResetPasswordForm resetPasswordForm) {
        ForgotPasswordToken forgotPasswordToken = forgotPasswordRepository.findByToken(resetPasswordForm.getToken());
        if (forgotPasswordToken == null) {
            throw new ForgotPasswordTokenException("Mã đặt lại mật khẩu không hợp lệ!");
        }
        if (checkForgotPasswordTokenIsExpired(forgotPasswordToken)) {
            forgotPasswordRepository.delete(forgotPasswordToken);
            throw new ForgotPasswordTokenException("Mã đặt lại mật khẩu hết hiệu lực");
        }
        User user = forgotPasswordToken.getUser();
        user.setPassword(passwordEncoder.encode(resetPasswordForm.getNewPassword()));
        userRepository.save(user);
        forgotPasswordRepository.delete(forgotPasswordToken);
    }


    public boolean checkForgotPasswordTokenIsExpired(ForgotPasswordToken forgotPasswordToken) {
        return forgotPasswordToken.getExpiredTime().isBefore(LocalDateTime.now());
    }

    private boolean emailExists(String email) {
        return userRepository.findByEmail(email) != null;
    }
}
