package com.example.authservice.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "password_reset_token")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ForgotPasswordToken {
    public static final Long EXPIRATION = 60 * 10L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String token;

    @OneToOne
    private User user;

    private LocalDateTime expiredTime;
}
