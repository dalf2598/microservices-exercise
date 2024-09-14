package com.banquito.microservicio1.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
@Table(name = "persona")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Persona {
    @Id
    @Column(name = "id", columnDefinition = "UUID")
    private UUID id;

    @Column(name = "nombre", length = 100, nullable = false)
    private String nombre;

    @Column(name = "genero", length = 10, nullable = false)
    private String genero;

    @Column(name = "edad", nullable = false)
    private int edad;

    @Column(name = "identificacion", length = 10, nullable = false)
    private String identificacion;

    @Column(name = "direccion", length = 100, nullable = false)
    private String direccion;

    @Column(name = "telefono", length = 15, nullable = false)
    private String telefono;
}
