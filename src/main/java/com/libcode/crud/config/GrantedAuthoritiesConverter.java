package com.libcode.crud.config;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

public class GrantedAuthoritiesConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

    private static final String ROLES_CLAIM = "https://agat.app/roles"; 

    @Override
    public Collection<GrantedAuthority> convert(Jwt jwt) {
    List<String> roles = jwt.getClaimAsStringList("https://agat.app/roles");

    if (roles == null || roles.isEmpty()) {
        return List.of();
    }
    return roles.stream()
            .map(role -> "ROLE_" + role) // ROLE_Maestro, ROLE_Administrador, etc.
            .map(SimpleGrantedAuthority::new)
            .collect(Collectors.toList());
    }

}
