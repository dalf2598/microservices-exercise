package com.banquito.microservicio2.mocks;

import com.banquito.microservicio2.controller.dto.RSEstadoCuenta;
import com.banquito.microservicio2.controller.dto.RSReporteCuenta;
import com.banquito.microservicio2.controller.dto.RSReporteMovimiento;
import com.banquito.microservicio2.controller.dto.RSReportePersona;
import com.banquito.microservicio2.utils.Utils;

import java.math.BigDecimal;
import java.util.Collections;

public class ReporteMocks {

    public static String expectedInvalIdException = "{\"status\":\"RuntimeException\",\"description\":\"ID Invalido\"}";

    public static String expectedInvalDateRangeException = "{\"status\":\"RuntimeException\",\"description\":\"Fechas Invalidas\"}";

    public static RSEstadoCuenta estadoCuentaMock(){
        RSReportePersona mockReportePersona = new RSReportePersona();
        mockReportePersona.setIdentificacion("123456789");
        mockReportePersona.setNombre("Kevin Garcia");

        RSReporteMovimiento mockReporteMovimiento = new RSReporteMovimiento();
        mockReporteMovimiento.setFecha("01-08-2024");
        mockReporteMovimiento.setSaldoInicial(new BigDecimal(1000.00));
        mockReporteMovimiento.setTipoMovimiento(Utils.TipoMovimiento.RETIRO);
        mockReporteMovimiento.setValor(new BigDecimal(500.00));
        mockReporteMovimiento.setSaldoDisponible(new BigDecimal(500.00));


        RSReporteCuenta mockReporteCuenta = new RSReporteCuenta();
        mockReporteCuenta.setNumeroCuenta("12345678");
        mockReporteCuenta.setTipoCuenta("Ahorros");
        mockReporteCuenta.setSaldo(new BigDecimal(500.00));
        mockReporteCuenta.setMovimientos(Collections.singletonList(mockReporteMovimiento));


        RSEstadoCuenta mockEstadoCuenta = new RSEstadoCuenta();
        mockEstadoCuenta.setFechaInicio("01-08-2024");
        mockEstadoCuenta.setFechaFin("30-08-2024");
        mockEstadoCuenta.setPersona(mockReportePersona);
        mockEstadoCuenta.setCuentas(Collections.singletonList(mockReporteCuenta));

        return mockEstadoCuenta;
    }
}
