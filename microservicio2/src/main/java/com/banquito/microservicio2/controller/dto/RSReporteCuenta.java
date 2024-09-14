package com.banquito.microservicio2.controller.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class RSReporteCuenta {
    private String numeroCuenta;
    private String tipoCuenta;
    private BigDecimal saldo;
    private List<RSReporteMovimiento> movimientos;
}
