package com.libcode.crud.controller;

import com.libcode.crud.model.Grupo;
import com.libcode.crud.repository.GrupoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/grupos")
public class GrupoController {

    @Autowired
    private GrupoRepository grupoRepository;

    @GetMapping
    public String listarGrupos(Model model, @RequestParam(required = false) String mensaje) {
        model.addAttribute("grupos", grupoRepository.findAll());
        model.addAttribute("mensaje", mensaje);
        return "grupos/gruposindex";
    }

    @PostMapping("/guardar")
    public String guardarGrupo(@ModelAttribute Grupo grupo, RedirectAttributes redirectAttributes) {
        grupoRepository.save(grupo);
        redirectAttributes.addAttribute("mensaje", "Grupo guardado correctamente");
        return "redirect:/grupos";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarGrupo(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        grupoRepository.deleteById(id);
        redirectAttributes.addAttribute("mensaje", "Grupo eliminado correctamente");
        return "redirect:/grupos";
    }
}
