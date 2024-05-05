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
@Table(name = "`authorization`")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Authorization {
    @Id
    @Column
    private String id;

    private String registeredClientId;

    private String principalName;

    private String authorizationGrantType;

    @Column(length = 1000, columnDefinition = "TEXT")
    private String authorizedScopes;

    @Column(length = 4000, columnDefinition = "TEXT")
    private String attributes;

    @Column(length = 500, columnDefinition = "TEXT")
    private String state;

    @Column(length = 4000, columnDefinition = "TEXT")
    private String authorizationCodeValue;

    private Instant authorizationCodeIssuedAt;

    private Instant authorizationCodeExpiresAt;

    private String authorizationCodeMetadata;

    @Column(length = 4000, columnDefinition = "TEXT")
    private String accessTokenValue;

    private Instant accessTokenIssuedAt;

    private Instant accessTokenExpiresAt;

    @Column(length = 2000, columnDefinition = "TEXT")
    private String accessTokenMetadata;

    private String accessTokenType;

    @Column(length = 1000, columnDefinition = "TEXT")
    private String accessTokenScopes;

    @Column(length = 4000, columnDefinition = "TEXT")
    private String refreshTokenValue;

    private Instant refreshTokenIssuedAt;

    private Instant refreshTokenExpiresAt;

    @Column(length = 2000, columnDefinition = "TEXT")
    private String refreshTokenMetadata;

    @Column(length = 4000, columnDefinition = "TEXT")
    private String oidcIdTokenValue;

    private Instant oidcIdTokenIssuedAt;

    private Instant oidcIdTokenExpiresAt;

    @Column(length = 2000, columnDefinition = "TEXT")
    private String oidcIdTokenMetadata;

    @Column(length = 2000, columnDefinition = "TEXT")
    private String oidcIdTokenClaims;

    @Column(length = 4000, columnDefinition = "TEXT")
    private String userCodeValue;

    private Instant userCodeIssuedAt;

    private Instant userCodeExpiresAt;

    @Column(length = 2000, columnDefinition = "TEXT")
    private String userCodeMetadata;

    @Column(length = 4000, columnDefinition = "TEXT")
    private String deviceCodeValue;

    private Instant deviceCodeIssuedAt;

    private Instant deviceCodeExpiresAt;

    @Column(length = 2000, columnDefinition = "TEXT")
    private String deviceCodeMetadata;
}
