package com.example.authservice.model.form;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AdminRegistrationForm {

    private String email;

    private String password;
}
