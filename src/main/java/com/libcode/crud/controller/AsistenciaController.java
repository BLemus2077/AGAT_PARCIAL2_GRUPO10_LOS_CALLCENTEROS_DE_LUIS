package com.libcode.crud.controller;

import com.libcode.crud.model.Asistencia;
import com.libcode.crud.model.Estudiante;
import com.libcode.crud.model.Grupo;
import com.libcode.crud.repository.AsistenciaRepository;
import com.libcode.crud.repository.EstudianteRepository;
import com.libcode.crud.repository.GrupoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;

@Controller
@RequestMapping("/asistencias")
public class AsistenciaController {

    @Autowired
    private AsistenciaRepository asistenciaRepository;

    @Autowired
    private EstudianteRepository estudianteRepository;

    @Autowired
    private GrupoRepository grupoRepository;

    @GetMapping
    public String listarAsistencias(Model model, @RequestParam(required = false) String mensaje) {
        List<Estudiante> estudiantes = estudianteRepository.findAll();
        List<Asistencia> asistencias = asistenciaRepository.findAll();

        Map<Long, Asistencia> asistenciasMap = new HashMap<>();
        for (Asistencia asistencia : asistencias) {
            asistenciasMap.put(asistencia.getEstudiante().getId(), asistencia);
        }

        List<Asistencia> listaFinal = new ArrayList<>();
        for (Estudiante estudiante : estudiantes) {
            Asistencia asistencia = asistenciasMap.getOrDefault(estudiante.getId(), new Asistencia());
            asistencia.setEstudiante(estudiante);
            asistencia.setGrupo(estudiante.getGrupo());
            if (asistencia.getId() == null) {
                asistencia.setAsistencias(0);
                asistencia.setTardanzas(0);
                asistencia.setFaltas(0);
                asistencia.setJustificadas(0);
            }
            listaFinal.add(asistencia);
        }

        model.addAttribute("asistencias", listaFinal);
        model.addAttribute("mensaje", mensaje);
        return "asistencias/asistenciasindex";
    }

    @PostMapping("/guardar")
    public String guardarAsistencia(@ModelAttribute Asistencia asistencia, RedirectAttributes redirectAttributes) {
    if (asistencia.getEstudiante() == null || asistencia.getEstudiante().getId() == null ||
        asistencia.getGrupo() == null || asistencia.getGrupo().getId() == null) {
        redirectAttributes.addAttribute("mensaje", "Error: Estudiante o grupo no válidos.");
        return "redirect:/asistencias";
    }

    Long estudianteId = asistencia.getEstudiante().getId();
    Long grupoId = asistencia.getGrupo().getId();

    Estudiante estudiante = estudianteRepository.findById(estudianteId).orElse(null);
    Grupo grupo = grupoRepository.findById(grupoId).orElse(null);

    if (estudiante == null || grupo == null) {
        redirectAttributes.addAttribute("mensaje", "Error: Estudiante o grupo no encontrados.");
        return "redirect:/asistencias";
    }

    asistencia.setEstudiante(estudiante);
    asistencia.setGrupo(grupo);

    int total = asistencia.getAsistencias() + asistencia.getTardanzas() +
                asistencia.getFaltas() + asistencia.getJustificadas();

    if (total != 88) {
    redirectAttributes.addAttribute("mensaje", "Error: El total de días debe ser exactamente 88.");
    return "redirect:/asistencias";
    }

    asistenciaRepository.save(asistencia);
    redirectAttributes.addAttribute("mensaje", "Asistencia registrada/modificada correctamente");
    return "redirect:/asistencias";
    }


    @GetMapping("/reiniciar/{id}")
    public String reiniciarAsistencia(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        Optional<Asistencia> optional = asistenciaRepository.findById(id);
        if (optional.isPresent()) {
            Asistencia asistencia = optional.get();
            asistencia.setAsistencias(0);
            asistencia.setTardanzas(0);
            asistencia.setFaltas(0);
            asistencia.setJustificadas(0);
            asistenciaRepository.save(asistencia);
        }
        redirectAttributes.addAttribute("mensaje", "Asistencia reiniciada correctamente");
        return "redirect:/asistencias";
    }
}
