package com.libcode.crud.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    @GetMapping("/")
    public String index() {
        return "index"; 
    }

    @GetMapping("/dashboard")
    public String dashboard() {
        return "dashboard"; 
    }

    @GetMapping("/asistencias")
    public String asistencias() {
        return "asistencias/asistenciasindex"; 
    }

    @GetMapping("/estudiantes")
    public String estudiantes() {
        return "estudiantes/estudiantesindex"; 
    }

    @GetMapping("/grupos")
    public String grupos() {
        return "grupos/gruposindex";
    }

    @GetMapping("/reportes")
    public String reportes() {
        return "reportes/reporteasistencia";
    }
    @GetMapping("/login")
    public String login() {
        return "login"; 
    }
    @GetMapping("/unauthorized")
    public String unauthorized() {
        return "unauthorized"; 
    }


}
