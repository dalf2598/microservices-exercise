package com.banquito.microservicio2.mocks;

import com.banquito.microservicio2.controller.dto.RQCreateMovimiento;
import com.banquito.microservicio2.controller.dto.RQUpdateMovimiento;
import com.banquito.microservicio2.controller.dto.RSCuenta;
import com.banquito.microservicio2.controller.dto.RSMovimiento;
import com.banquito.microservicio2.model.Movimiento;
import com.banquito.microservicio2.utils.Utils;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class MovimientoMocks {

    public static String expectedException = "{\"status\":\"RuntimeException\",\"description\":\"Parametros Invalidos\"}";

    public static String expectedDeletedNotification = "{\"msg\":\"Movimiento 550e8400-e29b-41d4-a716-446655441111 eliminado exitosamente\"}";

    public static String expectedNotAvailableException = "{\"status\":\"RuntimeException\",\"description\":\"No se encontraron movimientos\"}";

    public static List<RSMovimiento> rsMovimientoListMock(){
        RSMovimiento mockMovimiento = new RSMovimiento();
        mockMovimiento.setMovimientoId(UUID.fromString("550e8400-e29b-41d4-a716-446655441111"));
        mockMovimiento.setFecha("25-01-1990");
        mockMovimiento.setNumeroCuenta("12345678");
        mockMovimiento.setTipoCuenta("Ahorro");
        mockMovimiento.setSaldoInicial(new BigDecimal(1000.00));
        mockMovimiento.setTipoMovimiento(Utils.TipoMovimiento.RETIRO);
        mockMovimiento.setValor(new BigDecimal(500.00));
        mockMovimiento.setSaldoDisponible(new BigDecimal(500.00));
        return Collections.singletonList(mockMovimiento);
    }

    public static RSMovimiento rsMovimientoMock(){
        RSMovimiento mockMovimiento = new RSMovimiento();
        mockMovimiento.setMovimientoId(UUID.fromString("550e8400-e29b-41d4-a716-446655441111"));
        mockMovimiento.setFecha("25-01-1990");
        mockMovimiento.setNumeroCuenta("12345678");
        mockMovimiento.setTipoCuenta("Ahorro");
        mockMovimiento.setSaldoInicial(new BigDecimal(1000.00));
        mockMovimiento.setTipoMovimiento(Utils.TipoMovimiento.RETIRO);
        mockMovimiento.setValor(new BigDecimal(500.00));
        mockMovimiento.setSaldoDisponible(new BigDecimal(500.00));
        return mockMovimiento;
    }

    public static Movimiento movimientoMock(){
        Movimiento mockMovimiento = new Movimiento();
        mockMovimiento.setMovimientoId(UUID.fromString("550e8400-e29b-41d4-a716-446655441111"));
        mockMovimiento.setFecha(1726273889L);
        mockMovimiento.setSaldoInicial(new BigDecimal(1000.00));
        mockMovimiento.setTipoMovimiento(Utils.TipoMovimiento.RETIRO);
        mockMovimiento.setValor(new BigDecimal(500.00));
        mockMovimiento.setSaldoDisponible(new BigDecimal(500.00));
        mockMovimiento.setCuenta(CuentaMocks.cuentaMock());
        return mockMovimiento;
    }

    public static RQCreateMovimiento rqCreateMovimientoMock(){
        RQCreateMovimiento mockMovimiento = new RQCreateMovimiento();
        mockMovimiento.setTipoMovimiento(Utils.TipoMovimiento.DEPOSITO.toString());
        mockMovimiento.setValor(new BigDecimal(1000.00));
        mockMovimiento.setNumeroCuenta("12345678");
        return mockMovimiento;
    }

    public static RQCreateMovimiento rqCreateMovimientoInvalidaMock(){
        RQCreateMovimiento mockMovimiento = new RQCreateMovimiento();
        mockMovimiento.setTipoMovimiento(Utils.TipoMovimiento.DEPOSITO.toString());
        return mockMovimiento;
    }

    public static RQUpdateMovimiento rqUpdateMovimientoMock(){
        RQUpdateMovimiento mockMovimiento = new RQUpdateMovimiento();
        mockMovimiento.setFecha(1726273899L);
        mockMovimiento.setTipoMovimiento(Utils.TipoMovimiento.RETIRO.toString());
        mockMovimiento.setSaldoInicial(new BigDecimal(1000.00));
        mockMovimiento.setValor(new BigDecimal(500.00));
        mockMovimiento.setSaldoDisponible(new BigDecimal(500.00));
        mockMovimiento.setNumeroCuenta("12345678");
        return mockMovimiento;
    }

    public static RQUpdateMovimiento rqUpdateMovimientoInvalidoMock(){
        RQUpdateMovimiento mockMovimiento = new RQUpdateMovimiento();
        mockMovimiento.setFecha(1726273899L);
        mockMovimiento.setTipoMovimiento(Utils.TipoMovimiento.RETIRO.toString());
        return mockMovimiento;
    }

}
