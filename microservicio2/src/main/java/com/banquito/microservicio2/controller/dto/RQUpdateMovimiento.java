package com.banquito.microservicio2.controller.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class RQUpdateMovimiento {
    private Long fecha;
    private String tipoMovimiento;
    private BigDecimal saldoInicial;
    private BigDecimal valor;
    private BigDecimal saldoDisponible;
    private String numeroCuenta;
}
