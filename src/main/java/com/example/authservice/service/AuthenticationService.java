package com.example.authservice.service;

import com.example.authservice.model.form.LoginForm;

public interface AuthenticationService {
    void authenticate(LoginForm loginForm);

}
