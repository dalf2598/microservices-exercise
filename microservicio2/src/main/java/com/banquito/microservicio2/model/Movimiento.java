package com.banquito.microservicio2.model;

import com.banquito.microservicio2.utils.Utils;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Entity
@Table(name = "movimiento")
public class Movimiento {

    @Id
    @Column(name = "movimientoid", columnDefinition = "UUID")
    private UUID movimientoId;

    @Column(name = "fecha", nullable = false)
    private Long fecha;

    @Column(name = "tipomovimiento", length = 10, nullable = false)
    @Enumerated(EnumType.STRING)
    private Utils.TipoMovimiento tipoMovimiento;

    @Column(name = "saldoinicial", precision = 15, scale = 2, nullable = false)
    private BigDecimal saldoInicial;

    @Column(name = "valor", precision = 15, scale = 2, nullable = false)
    private BigDecimal valor;

    @Column(name = "saldodisponible", precision = 15, scale = 2, nullable = false)
    private BigDecimal saldoDisponible;

    @ManyToOne
    @JoinColumn(name = "cuentaid", nullable = false)
    private Cuenta cuenta;

}

