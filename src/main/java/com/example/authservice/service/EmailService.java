package com.example.authservice.service;

import com.example.authservice.event.ForgotPasswordEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;

public interface EmailService {

    void sendForgotPasswordEmail(ForgotPasswordEvent forgotPasswordEvent);
}
