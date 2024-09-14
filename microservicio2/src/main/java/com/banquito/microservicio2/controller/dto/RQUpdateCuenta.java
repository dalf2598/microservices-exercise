package com.banquito.microservicio2.controller.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class RQUpdateCuenta {
    private String tipoCuenta;
    private BigDecimal saldoInicial;
    private UUID clienteId;
    private boolean estado;
}
