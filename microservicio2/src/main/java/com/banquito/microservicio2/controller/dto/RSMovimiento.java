package com.banquito.microservicio2.controller.dto;

import com.banquito.microservicio2.utils.Utils;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class RSMovimiento {
    private UUID movimientoId;
    private String fecha;
    private String numeroCuenta;
    private String tipoCuenta;
    private BigDecimal saldoInicial;
    private Utils.TipoMovimiento tipoMovimiento;
    private BigDecimal valor;
    private BigDecimal saldoDisponible;
}
