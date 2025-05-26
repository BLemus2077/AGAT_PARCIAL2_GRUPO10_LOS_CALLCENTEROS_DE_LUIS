package com.libcode.crud.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class UsuarioApiController {

    @GetMapping("/api/user-info")
    public Map<String, Object> userInfo(@AuthenticationPrincipal Jwt principal) {
        Map<String, Object> datos = new HashMap<>();
        datos.put("email", principal.getClaimAsString("email"));
        datos.put("name", principal.getClaimAsString("name"));

        List<String> roles = principal.getClaimAsStringList("https://agat.app/roles");
        if (roles == null) {
            roles = principal.getClaimAsStringList("roles");
        }

        datos.put("roles", roles != null ? roles : Collections.emptyList());
        return datos;
    }
}
