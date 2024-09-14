package com.banquito.microservicio1.request.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class RSAccount {
    private UUID cuentaId;
    private String numeroCuenta;
    private String tipoCuenta;
    private BigDecimal saldoInicial;
    private boolean estado;
    private UUID clienteId;
}
