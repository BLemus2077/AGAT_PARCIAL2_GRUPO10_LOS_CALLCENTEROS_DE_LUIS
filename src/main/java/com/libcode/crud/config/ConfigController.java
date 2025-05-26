package com.libcode.crud.config;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/config")
public class ConfigController {

    @GetMapping("/auth0")
    public Map<String, String> getAuth0Config() {
        Map<String, String> config = new HashMap<>();
        config.put("domain", System.getenv("AUTH0_DOMAIN"));
        config.put("clientId", System.getenv("AUTH0_CLIENT_ID"));
        config.put("audience", System.getenv("AUTH0_AUDIENCE"));
        return config;
    }
}
