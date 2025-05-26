package com.libcode.crud.controller;

import com.libcode.crud.model.Grupo;
import com.libcode.crud.repository.GrupoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/grupos")
public class GrupoApiController {

    @Autowired
    private GrupoRepository grupoRepository;

    @GetMapping
    public List<Grupo> obtenerTodos() {
        return grupoRepository.findAll();
    }

    @PostMapping
    public Grupo guardar(@RequestBody Grupo grupo) {
        return grupoRepository.save(grupo);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        grupoRepository.deleteById(id);
    }
}
