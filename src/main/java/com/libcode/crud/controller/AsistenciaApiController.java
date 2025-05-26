package com.libcode.crud.controller;

import com.libcode.crud.model.Asistencia;
import com.libcode.crud.model.Estudiante;
import com.libcode.crud.model.Grupo;
import com.libcode.crud.repository.AsistenciaRepository;
import com.libcode.crud.repository.EstudianteRepository;
import com.libcode.crud.repository.GrupoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/asistencias")
public class AsistenciaApiController {

    @Autowired
    private AsistenciaRepository asistenciaRepository;

    @Autowired
    private EstudianteRepository estudianteRepository;

    @Autowired
    private GrupoRepository grupoRepository;

    @GetMapping
    public List<Asistencia> listarAsistencias() {
        return asistenciaRepository.findAll();
    }

    @GetMapping("/completas")
    public List<Asistencia> listarCompletas() {
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

        return listaFinal;
    }

    @PostMapping
    public ResponseEntity<?> guardarAsistencia(@RequestBody Asistencia asistencia) {
        if (asistencia.getEstudiante() == null || asistencia.getEstudiante().getId() == null ||
            asistencia.getGrupo() == null || asistencia.getGrupo().getId() == null) {
            return ResponseEntity.badRequest().body("Estudiante o grupo no válidos.");
        }

        Estudiante estudiante = estudianteRepository.findById(asistencia.getEstudiante().getId()).orElse(null);
        Grupo grupo = grupoRepository.findById(asistencia.getGrupo().getId()).orElse(null);

        if (estudiante == null || grupo == null) {
            return ResponseEntity.badRequest().body("Estudiante o grupo no encontrados.");
        }

        int total = asistencia.getAsistencias() + asistencia.getTardanzas() +
                    asistencia.getFaltas() + asistencia.getJustificadas();

        if (total != 88) {
            return ResponseEntity.badRequest().body("El total de días debe ser exactamente 88.");
        }

        asistencia.setEstudiante(estudiante);
        asistencia.setGrupo(grupo);

        Asistencia guardada = asistenciaRepository.save(asistencia);
        return ResponseEntity.ok(guardada);
    }

    @PutMapping("/reiniciar/{id}")
    public ResponseEntity<?> reiniciarAsistencia(@PathVariable Long id) {
        Optional<Asistencia> optional = asistenciaRepository.findById(id);
        if (optional.isPresent()) {
            Asistencia asistencia = optional.get();
            asistencia.setAsistencias(0);
            asistencia.setTardanzas(0);
            asistencia.setFaltas(0);
            asistencia.setJustificadas(0);
            asistenciaRepository.save(asistencia);
            return ResponseEntity.ok("Asistencia reiniciada correctamente");
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarAsistencia(@PathVariable Long id) {
        if (asistenciaRepository.existsById(id)) {
            asistenciaRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
