package com.banquito.microservicio2.controller.dto;

import lombok.Data;

import java.util.List;

@Data
public class RSEstadoCuenta {
    private String fechaInicio;
    private String fechaFin;
    private RSReportePersona persona;
    private List<RSReporteCuenta> cuentas;
}



