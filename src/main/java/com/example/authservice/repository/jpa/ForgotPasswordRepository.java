package com.example.authservice.repository.jpa;

import com.example.authservice.model.entity.ForgotPasswordToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ForgotPasswordRepository extends JpaRepository<ForgotPasswordToken, Long> {
    ForgotPasswordToken findByToken(String token);
}
