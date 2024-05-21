package com.example.clientservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.ReactiveOAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Objects;

@org.springframework.stereotype.Controller
public class Controller {

    @Autowired
    private ReactiveOAuth2AuthorizedClientService auth2AuthorizedClientService;

    @GetMapping("/token")
    private String token(@RegisteredOAuth2AuthorizedClient OAuth2AuthorizedClient client) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        Mono<OAuth2AuthorizedClient> client =
//                auth2AuthorizedClientService.loadAuthorizedClient("demo",authentication.getName());
        OAuth2AccessToken auth2AccessToken = client.getAccessToken();
        OidcUser oidcUser;

        return auth2AccessToken.getTokenValue();
    }

    @GetMapping("/")
    public String hello() {
        return "hello";
    }

    @GetMapping("/home")
    public String home() {
        return "home";
    }
}
