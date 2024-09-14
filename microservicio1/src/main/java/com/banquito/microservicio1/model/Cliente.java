package com.banquito.microservicio1.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "cliente")
@PrimaryKeyJoinColumn(name = "id")
public class Cliente extends Persona {

    @Column(name = "contrasena", length = 12, nullable = false)
    private String contrasena;

    @Column(name = "estado", nullable = false)
    private boolean estado;

    @Column(name = "clienteid")
    private UUID clienteId;
}
