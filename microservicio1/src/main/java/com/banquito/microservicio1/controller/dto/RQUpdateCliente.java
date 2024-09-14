package com.banquito.microservicio1.controller.dto;

import lombok.Data;

@Data
public class RQUpdateCliente {
    private String nombre;

    private String genero;

    private int edad;

    private String identificacion;

    private String direccion;

    private String telefono;

    private String contrasena;

    private boolean estado;
}
