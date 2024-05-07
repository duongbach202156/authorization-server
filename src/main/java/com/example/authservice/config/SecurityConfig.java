package com.example.authservice.config;

import com.example.authservice.model.entity.Role;
import com.example.authservice.model.entity.User;
import com.example.authservice.repository.jpa.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;

import java.util.HashSet;
import java.util.Set;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private UserRepository userRepository;

//    @Bean
//    public SecurityFilterChain defaultFilterChain(HttpSecurity httpSecurity) throws Exception {
//        httpSecurity
//                .csrf(AbstractHttpConfigurer::disable)
//                .cors(Customizer.withDefaults())
//                .authorizeHttpRequests(request -> request
//                        .requestMatchers("/auth/login", "/swagger-ui/**", "/v3/api-docs**").permitAll()
//                        .anyRequest().authenticated())
//                .formLogin(form -> form
//                        .loginPage("http://127.0.0.1:9090/login")
//                        .usernameParameter("email")
//                        .loginProcessingUrl("/auth/login")
//                                .successHandler(this.authenticationSuccessHandler())
////                                .failureHandler(this.authenticationFailureHandler())
//                )
//                .logout(logout -> logout
//                        .logoutSuccessUrl("http://localhost:9090/login?logout")
//                )
//                .exceptionHandling(handler -> handler
//                        .authenticationEntryPoint(
//                                new HttpStatusEntryPoint(HttpStatus.FORBIDDEN)
//                        )
//                )
//        ;
//        return httpSecurity.build();
//    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                User user = userRepository.findByEmail(username);
                if (user == null) {
                    throw new UsernameNotFoundException("No user with email: " + username);
                }
                return user;
//                return new org.springframework.security.core.userdetails.User(
//                        user.getEmail(),
//                        user.getPassword(),
//                        user.isEnabled(),
//                        user.isAccountNonExpired(),
//                        user.isCredentialsNonExpired(),
//                        user.isAccountNonLocked(),
//                        getAuthorities(user.getRole())
//                        );
            }
        };
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(this.userDetailsService());
        provider.setPasswordEncoder(this.passwordEncoder());
        return new ProviderManager(provider);
    }

    private Set<GrantedAuthority> getAuthorities(Role role) {
        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
        return authorities;
    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return new CustomAuthenticationSuccessHandler();
    }

    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return new CustomAuthenticationFailureHandler();
    }


//        @Bean
//    public UserDetailsService userDetailsService() {
//        UserDetails userDetails = User.builder()
//                .username("user")
//                .password(passwordEncoder().encode("123"))
//                .build();
//        return new InMemoryUserDetailsManager(userDetails);
//    }
}
