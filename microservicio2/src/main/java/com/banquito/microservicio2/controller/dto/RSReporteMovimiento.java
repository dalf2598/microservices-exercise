package com.banquito.microservicio2.controller.dto;

import com.banquito.microservicio2.utils.Utils;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class RSReporteMovimiento {
    private String fecha;
    private BigDecimal saldoInicial;
    private Utils.TipoMovimiento tipoMovimiento;
    private BigDecimal valor;
    private BigDecimal saldoDisponible;
}
