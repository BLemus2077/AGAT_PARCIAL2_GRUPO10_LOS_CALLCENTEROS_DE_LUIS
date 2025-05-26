package com.libcode.crud.config;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class GrantedAuthoritiesConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

    private static final String ROLES_CLAIM = "https://agat.app/roles"; 

    @Override
    public Collection<GrantedAuthority> convert(Jwt jwt) {
        List<String> roles = jwt.getClaimAsStringList(ROLES_CLAIM);

        if (roles == null || roles.isEmpty()) {
            return List.of(); // No roles = sin privilegios
        }

        return roles.stream()
                .map(role -> "ROLE_" + role.toUpperCase()) 
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
}
