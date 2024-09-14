package com.banquito.microservicio2.controller.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class RSCuenta {
    private UUID cuentaId;
    private String numeroCuenta;
    private String tipoCuenta;
    private BigDecimal saldoInicial;
    private Boolean estado;
    private UUID clienteId;
    private String cliente;
}
