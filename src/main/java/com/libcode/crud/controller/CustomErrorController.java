package com.libcode.crud.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class CustomErrorController {

    @GetMapping("/unauthorized")
    public String handleUnauthorized() {
        return "unauthorized"; // archivo templates/unauthorized.html
    }

    @GetMapping("/error")
    public String handleError(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("jakarta.servlet.error.status_code");

        if (statusCode != null && statusCode == 401) {
            return "unauthorized";
        }

        return "index";
    }
}
