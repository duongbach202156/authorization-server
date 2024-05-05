package com.example.authservice.model.entity;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "`client`")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Client {
    @Id
    private String id;

    private String clientId;

    private Instant clientIdIssuedAt;

    private String clientSecret;

    private Instant clientSecretExpiresAt;

    private String clientName;

    @Column(length = 1000, columnDefinition = "TEXT")
    private String clientAuthenticationMethods;

    @Column(length = 1000, columnDefinition = "TEXT")
    private String authorizationGrantTypes;

    @Column(length = 1000, columnDefinition = "TEXT")
    private String redirectUris;

    @Column(length = 1000, columnDefinition = "TEXT")
    private String postLogoutRedirectUris;

    @Column(length = 1000, columnDefinition = "TEXT")
    private String scopes;

    @Column(length = 2000, columnDefinition = "TEXT")
    private String clientSettings;

    @Column(length = 2000, columnDefinition = "TEXT")
    private String tokenSettings;

}
