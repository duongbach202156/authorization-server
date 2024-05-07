package com.example.authservice.service;

import com.example.authservice.model.form.AdminRegistrationForm;
import com.example.authservice.model.form.ResetPasswordForm;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;

public interface UserService {
    void createAdmin(AdminRegistrationForm adminRegistrationForm);

    void forgotPassword(String email);

    void resetPassword(ResetPasswordForm resetPasswordForm);
}
