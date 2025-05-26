package com.libcode.crud.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.Collections;
import java.util.List;

@Controller
public class ControlUsuario {

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    @GetMapping("/user-info")
    public String userInfo(@AuthenticationPrincipal Jwt principal, Model model) {
        List<String> roles = principal.getClaimAsStringList("https://agat.com/roles");
        if (roles == null) {
            roles = principal.getClaimAsStringList("roles");
        }
        
        model.addAttribute("email", principal.getClaimAsString("email"));
        model.addAttribute("name", principal.getClaimAsString("name"));
        model.addAttribute("roles", roles != null ? roles : Collections.emptyList());
        return "userInfo";
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model, @AuthenticationPrincipal Jwt principal) {
        model.addAttribute("user", principal);
        return "dashboard";
    }
}