package com.banquito.microservicio2.request.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class RSClient {

    private UUID id;

    private String nombre;

    private String genero;

    private int edad;

    private String identificacion;

    private String direccion;

    private String telefono;

    private String contrasena;

    private boolean estado;

    private UUID clienteId;
}
