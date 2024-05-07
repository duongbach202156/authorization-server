package com.example.authservice.controller;

import com.example.authservice.model.form.AdminRegistrationForm;
import com.example.authservice.model.form.LoginForm;
import com.example.authservice.model.form.ResetPasswordForm;
import com.example.authservice.response.GenericResponse;
import com.example.authservice.service.AuthenticationService;
import com.example.authservice.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;


    @Autowired
    private UserService userService;

    @PostMapping(value = "/login")
    public ResponseEntity<GenericResponse<?>> authenticate(
            @RequestBody LoginForm loginForm) {
        authenticationService.authenticate(loginForm);
        return ResponseEntity.ok(new GenericResponse<>());
    }

    @PostMapping("/register/admin")
    public ResponseEntity<GenericResponse<?>> registerAdmin(@RequestBody AdminRegistrationForm adminRegistrationForm) {
        userService.createAdmin(adminRegistrationForm);
        return new ResponseEntity<>(new GenericResponse<>(null, 201), HttpStatus.CREATED);
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<GenericResponse<?>> forgotPassword(@RequestBody String email) {
        userService.forgotPassword(email);
        return new ResponseEntity<>(new GenericResponse<>(null, 201), HttpStatus.CREATED);
    }

    @PostMapping("/reset-password")
    public ResponseEntity<GenericResponse<?>> resetPassword(@RequestBody ResetPasswordForm resetPasswordForm) {
        userService.resetPassword(resetPasswordForm);
        return new ResponseEntity<>(new GenericResponse<>(null, 201), HttpStatus.CREATED);
    }

    @GetMapping("/test/{id}")
    @PreAuthorize("#id == authentication.principal.id")
    public String test(@PathVariable("id") Long id) {
        return "TEST" + id;
    }
}
