package com.libcode.crud.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;


@Entity
public class Estudiante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nie;
    private String nombre;
    private String apellidos;
    @ManyToOne
    @JoinColumn(name = "grupo_id")
    private Grupo grupo;
    private String grado;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNie() { return nie; }
    public void setNie(String nie) { this.nie = nie; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellidos() { return apellidos; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }

   public Grupo getGrupo() {
    return grupo;
    }

    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }

    public String getGrado() { return grado; }
    public void setGrado(String grado) { this.grado = grado; }
}
