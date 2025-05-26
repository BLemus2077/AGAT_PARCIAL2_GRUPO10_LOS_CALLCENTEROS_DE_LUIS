package com.libcode.crud.controller;

import com.libcode.crud.model.Estudiante;
import com.libcode.crud.model.Grupo;
import com.libcode.crud.repository.EstudianteRepository;
import com.libcode.crud.repository.GrupoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/estudiantes")
public class EstudianteApiController {

    @Autowired
    private EstudianteRepository estudianteRepository;

    @Autowired
    private GrupoRepository grupoRepository;

    @GetMapping
    public List<Estudiante> listarEstudiantes() {
        return estudianteRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Estudiante> obtenerEstudiante(@PathVariable Long id) {
        Optional<Estudiante> estudiante = estudianteRepository.findById(id);
        return estudiante.map(ResponseEntity::ok)
                         .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Estudiante> crearEstudiante(@RequestBody Estudiante estudiante) {
        Optional<Grupo> grupoOpt = grupoRepository.findById(estudiante.getGrupo().getId());
        if (grupoOpt.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        estudiante.setGrupo(grupoOpt.get());
        Estudiante guardado = estudianteRepository.save(estudiante);
        return ResponseEntity.ok(guardado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Estudiante> actualizarEstudiante(@PathVariable Long id, @RequestBody Estudiante nuevo) {
        return estudianteRepository.findById(id).map(est -> {
            est.setNie(nuevo.getNie());
            est.setNombre(nuevo.getNombre());
            est.setApellidos(nuevo.getApellidos());
            est.setGrado(nuevo.getGrado());
            Optional<Grupo> grupoOpt = grupoRepository.findById(nuevo.getGrupo().getId());
            grupoOpt.ifPresent(est::setGrupo);
            return ResponseEntity.ok(estudianteRepository.save(est));
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarEstudiante(@PathVariable Long id) {
        if (estudianteRepository.existsById(id)) {
            estudianteRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
