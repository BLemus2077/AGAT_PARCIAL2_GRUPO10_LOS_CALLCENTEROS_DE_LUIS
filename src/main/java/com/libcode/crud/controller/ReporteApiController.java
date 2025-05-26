package com.libcode.crud.controller;

import com.libcode.crud.ReporteAsistencia;
import com.libcode.crud.repository.AsistenciaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reportes")
public class ReporteApiController {

    @Autowired
    private AsistenciaRepository asistenciaRepository;

    @GetMapping
    public List<ReporteAsistencia> obtenerReporte() {
        return asistenciaRepository.generarReporte();
    }
}
