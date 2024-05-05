package com.example.authservice.service;

import com.example.authservice.model.form.AdminRegistrationForm;

public interface UserService {
    void createAdmin(AdminRegistrationForm adminRegistrationForm);
}
