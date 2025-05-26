package com.libcode.crud.config;

import java.util.Collection;
import java.util.Collections;
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
        List<String> roles = jwt.getClaimAsStringList(ROLES_CLAIM);

        if (roles == null || roles.isEmpty()) {
            return Collections.emptyList(); 
        }

        return roles.stream()
            .map(role -> "ROLE_" + normalize(role))
            .map(SimpleGrantedAuthority::new)
            .collect(Collectors.toList());
    }

    private String normalize(String role) {
        
        return role.trim();
    }
}
