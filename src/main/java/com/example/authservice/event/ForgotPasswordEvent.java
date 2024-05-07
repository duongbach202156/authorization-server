package com.example.authservice.event;

import com.example.authservice.model.entity.ForgotPasswordToken;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ForgotPasswordEvent {
    private String email;

    private ForgotPasswordToken forgotPasswordToken;
}
