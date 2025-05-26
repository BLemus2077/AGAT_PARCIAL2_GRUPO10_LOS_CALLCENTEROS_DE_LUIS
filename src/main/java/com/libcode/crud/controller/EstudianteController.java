package com.libcode.crud.controller;

import com.libcode.crud.model.Estudiante;
import com.libcode.crud.model.Grupo;
import com.libcode.crud.repository.EstudianteRepository;
import com.libcode.crud.repository.GrupoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/estudiantes")
public class EstudianteController {

    @Autowired
    private EstudianteRepository estudianteRepository;

    @Autowired
    private GrupoRepository grupoRepository;


    @GetMapping
    public String listarEstudiantes(Model model,
                                    @RequestParam(value = "mensaje", required = false) String mensaje) {
        model.addAttribute("estudiantes", estudianteRepository.findAll());
        model.addAttribute("grupos", grupoRepository.findAll()); // Agregamos lista de grupos
        model.addAttribute("mensaje", mensaje);
        return "estudiantes/estudiantesindex";
    }

   
    @PostMapping("/guardar")
    public String guardarEstudiante(@ModelAttribute Estudiante estudiante,
                                    RedirectAttributes redirectAttributes) {
        estudianteRepository.save(estudiante);
        String nombre = estudiante.getNombre() + " " + estudiante.getApellidos();
        redirectAttributes.addAttribute("mensaje", "Modificación exitosa del estudiante " + nombre);
        return "redirect:/estudiantes";
    }

    
    @GetMapping("/eliminar/{id}")
    public String eliminarEstudiante(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        estudianteRepository.deleteById(id);
        redirectAttributes.addAttribute("mensaje", "Estudiante eliminado correctamente");
        return "redirect:/estudiantes";
    }
}
