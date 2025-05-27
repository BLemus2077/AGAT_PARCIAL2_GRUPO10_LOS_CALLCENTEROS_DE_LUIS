package com.libcode.crud.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TokenController {

    private final OAuth2AuthorizedClientService authorizedClientService;

    public TokenController(OAuth2AuthorizedClientService authorizedClientService) {
        this.authorizedClientService = authorizedClientService;
    }

    @GetMapping("/api/user-token")
    public ResponseEntity<String> getUserToken(OAuth2AuthenticationToken authentication) {
        if (authentication == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No autenticado");
        }

        OAuth2AuthorizedClient client = authorizedClientService.loadAuthorizedClient(
            authentication.getAuthorizedClientRegistrationId(),
            authentication.getName()
        );

        if (client == null || client.getAccessToken() == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token no disponible");
        }

        return ResponseEntity.ok(client.getAccessToken().getTokenValue());
    }
}
