package com.libcode.crud.controller;

import com.libcode.crud.ReporteAsistencia;
import com.libcode.crud.repository.AsistenciaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/reportes")
public class ReporteController {

    @Autowired
    private AsistenciaRepository asistenciaRepository;

    @GetMapping
    public String mostrarReporte(Model model) {
        List<ReporteAsistencia> reporte = asistenciaRepository.generarReporte();
        model.addAttribute("reporte", reporte);
        return "reporte/reporteasistencia";
    }
}
