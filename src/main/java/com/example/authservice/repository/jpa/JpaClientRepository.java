package com.example.authservice.repository.jpa;

import com.example.authservice.model.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JpaClientRepository extends JpaRepository<Client, String> {
    Optional<Client> findByClientId(String clientId);
}
