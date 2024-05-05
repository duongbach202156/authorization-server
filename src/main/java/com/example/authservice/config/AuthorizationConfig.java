package com.example.authservice.config;

import com.example.authservice.repository.ClientRepository;
import com.example.authservice.repository.impl.JpaRegisteredClientRepository;
import com.example.authservice.repository.jpa.JpaAuthorizationConsentRepository;
import com.example.authservice.repository.jpa.JpaAuthorizationRepository;
import com.example.authservice.repository.jpa.JpaClientRepository;
import com.example.authservice.service.AuthorizationConsentService;
import com.example.authservice.service.AuthorizationService;
import com.example.authservice.service.impl.JpaOAuth2AuthorizationConsentService;
import com.example.authservice.service.impl.JpaOAuth2AuthorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class AuthorizationConfig {

    @Autowired
    private JpaClientRepository jpaClientRepository;

    @Autowired
    private JpaAuthorizationRepository jpaAuthorizationRepository;

    @Autowired
    private JpaAuthorizationConsentRepository jpaAuthorizationConsentRepository;

    @Autowired
    private AuthenticationSuccessHandler authenticationSuccessHandler;

    @Autowired
    private AuthenticationFailureHandler authenticationFailureHandler;

    @Bean
    public SecurityFilterChain authorizationFilterChain(HttpSecurity httpSecurity) throws Exception {
        OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(httpSecurity);

        httpSecurity.getConfigurer(OAuth2AuthorizationServerConfigurer.class)
                .oidc(Customizer.withDefaults())
                .registeredClientRepository(this.registeredClientRepository())
                .authorizationService(this.auth2AuthorizationService())
                .authorizationConsentService(this.authorizationConsentService())
                .authorizationServerSettings(this.authorizationServerSettings());

        httpSecurity.exceptionHandling(exceptions -> exceptions
//                .authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("http://127.0.0.1:9090/auth/admin/login")))
                .authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("http://127.0.0.1:9090/login")))
        ;

        httpSecurity.oauth2ResourceServer(resource -> resource.jwt(Customizer.withDefaults()));

        return httpSecurity.build();
    }
    @Bean
    public SecurityFilterChain authenticationFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .formLogin(form -> form
                        .loginPage("http://127.0.0.1:9090/login")
//                        .usernameParameter("email")
                        .loginProcessingUrl("/login")
                        .successHandler(authenticationSuccessHandler)
                        .failureHandler(authenticationFailureHandler)
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("http://localhost:9090/login?logout")
                )
                .exceptionHandling(handler -> handler
                        .authenticationEntryPoint(
                                new HttpStatusEntryPoint(HttpStatus.FORBIDDEN)
                        )
                )
        ;
        return httpSecurity.build();
    }


    @Bean
    @Primary
    public ClientRepository registeredClientRepository() {
        return new JpaRegisteredClientRepository(jpaClientRepository);
    }

    @Bean
    public AuthorizationService auth2AuthorizationService() {
        return new JpaOAuth2AuthorizationService(jpaAuthorizationRepository, this.registeredClientRepository());
    }

    @Bean
    public AuthorizationConsentService authorizationConsentService() {
        return new JpaOAuth2AuthorizationConsentService(jpaAuthorizationConsentRepository, this.registeredClientRepository());
    }

    @Bean
    public AuthorizationServerSettings authorizationServerSettings() {
        return AuthorizationServerSettings.builder().build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("http://127.0.0.1:9090");
        corsConfiguration.addAllowedMethod("*");
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.setAllowCredentials(true);
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }
}
