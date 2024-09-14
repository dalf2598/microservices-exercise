package com.banquito.microservicio2.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Entity
@Table(name = "cuenta")
public class Cuenta {
    @Id
    @Column(name = "cuentaid", columnDefinition = "UUID")
    private UUID cuentaId;

    @Column(name = "numerocuenta", length = 8, nullable = false, unique = true)
    private String numeroCuenta;

    @Column(name = "tipocuenta", length = 50, nullable = false)
    private String tipoCuenta;

    @Column(name = "saldoinicial", precision = 15, scale = 2, nullable = false)
    private BigDecimal saldoInicial;

    @Column(name = "estado", nullable = false)
    private boolean estado;

    @Column(name = "clientid", columnDefinition = "UUID", nullable = false)
    private UUID clienteId;

}
