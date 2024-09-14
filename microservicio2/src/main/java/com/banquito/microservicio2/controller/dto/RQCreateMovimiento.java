package com.banquito.microservicio2.controller.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class RQCreateMovimiento {
    private String tipoMovimiento;
    private BigDecimal valor;
    private String numeroCuenta;
}
