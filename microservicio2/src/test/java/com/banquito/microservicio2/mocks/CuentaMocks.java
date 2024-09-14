package com.banquito.microservicio2.mocks;

import com.banquito.microservicio2.controller.dto.RQUpdateCuenta;
import com.banquito.microservicio2.controller.dto.RSCuenta;
import com.banquito.microservicio2.model.Cuenta;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class CuentaMocks {

    public static String expectedException = "{\"status\":\"RuntimeException\",\"description\":\"Parametros Invalidos\"}";

    public static String expectedDeletedNotification = "{\"msg\":\"Cuenta 550e8400-e29b-41d4-a716-446655441111 eliminada exitosamente\"}";

    public static String expectedNotAvailableException = "{\"status\":\"RuntimeException\",\"description\":\"No se encontraron cuentas\"}";

    public static List<RSCuenta> rsCuentaListMock(){
        RSCuenta mockCuenta = new RSCuenta();
        mockCuenta.setCuentaId(UUID.fromString("550e8400-e29b-41d4-a716-446655441111"));
        mockCuenta.setNumeroCuenta("12345678");
        mockCuenta.setTipoCuenta("Ahorros");
        mockCuenta.setSaldoInicial(new BigDecimal(1000.00));
        mockCuenta.setEstado(true);
        mockCuenta.setClienteId(UUID.fromString("550e8400-e29b-41d4-a716-446655441112"));
        mockCuenta.setCliente("Paco");

        return Collections.singletonList(mockCuenta);
    }

    public static RSCuenta rsCuentaMock(){
        RSCuenta mockCuenta = new RSCuenta();
        mockCuenta.setCuentaId(UUID.fromString("550e8400-e29b-41d4-a716-446655441111"));
        mockCuenta.setNumeroCuenta("12345678");
        mockCuenta.setTipoCuenta("Ahorros");
        mockCuenta.setSaldoInicial(new BigDecimal(1000.00));
        mockCuenta.setEstado(true);
        mockCuenta.setClienteId(UUID.fromString("550e8400-e29b-41d4-a716-446655441112"));
        mockCuenta.setCliente("Paco");

        return mockCuenta;
    }

    public static Cuenta cuentaMock(){
        Cuenta mockCuenta = new Cuenta();
        mockCuenta.setCuentaId(UUID.fromString("550e8400-e29b-41d4-a716-446655441111"));
        mockCuenta.setNumeroCuenta("12345678");
        mockCuenta.setTipoCuenta("Ahorros");
        mockCuenta.setSaldoInicial(new BigDecimal(1000.00));
        mockCuenta.setEstado(true);
        mockCuenta.setClienteId(UUID.fromString("550e8400-e29b-41d4-a716-446655441112"));

        return mockCuenta;
    }

    public static Cuenta cuentaInvalidaMock(){
        Cuenta mockCuenta = new Cuenta();
        mockCuenta.setCuentaId(UUID.fromString("550e8400-e29b-41d4-a716-446655441111"));
        mockCuenta.setNumeroCuenta("12345678");

        return mockCuenta;
    }

    public static RQUpdateCuenta rqUpdateCuentaMock(){
        RQUpdateCuenta mockCuenta = new RQUpdateCuenta();
        mockCuenta.setTipoCuenta("Ahorros");
        mockCuenta.setSaldoInicial(new BigDecimal(1000.00));
        mockCuenta.setEstado(true);
        mockCuenta.setClienteId(UUID.fromString("550e8400-e29b-41d4-a716-446655441112"));
        return mockCuenta;
    }

    public static RQUpdateCuenta rqUpdateCuentaInvalidoMock(){
        RQUpdateCuenta mockCuenta = new RQUpdateCuenta();
        mockCuenta.setTipoCuenta("Ahorros");
        return mockCuenta;
    }
}
