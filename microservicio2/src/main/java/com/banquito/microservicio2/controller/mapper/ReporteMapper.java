package com.banquito.microservicio2.controller.mapper;

import com.banquito.microservicio2.controller.dto.RSReporteCuenta;
import com.banquito.microservicio2.controller.dto.RSReporteMovimiento;
import com.banquito.microservicio2.controller.dto.RSReportePersona;
import com.banquito.microservicio2.model.Cuenta;
import com.banquito.microservicio2.model.Movimiento;
import com.banquito.microservicio2.request.dto.RSClient;
import com.banquito.microservicio2.utils.Utils;

import java.util.List;

public class ReporteMapper {

    public static RSReportePersona mapReportePersona(RSClient client){
        RSReportePersona rsReportePersona = new RSReportePersona();
        rsReportePersona.setNombre(client.getNombre());
        rsReportePersona.setIdentificacion(client.getIdentificacion());
        return rsReportePersona;
    }

    public static RSReporteCuenta mapReporteCuenta(Cuenta cuenta, List<RSReporteMovimiento> movimientos){
        RSReporteCuenta rsReporteCuenta = new RSReporteCuenta();
        rsReporteCuenta.setNumeroCuenta(cuenta.getNumeroCuenta());
        rsReporteCuenta.setTipoCuenta(cuenta.getTipoCuenta());
        rsReporteCuenta.setSaldo(cuenta.getSaldoInicial());
        rsReporteCuenta.setMovimientos(movimientos);
        return rsReporteCuenta;
    }

    public static RSReporteMovimiento mapReporteMovimiento(Movimiento movimiento){
        RSReporteMovimiento rsReporteMovimiento = new RSReporteMovimiento();
        rsReporteMovimiento.setFecha(Utils.formatUnixTimeToDate(movimiento.getFecha()));
        rsReporteMovimiento.setSaldoInicial(movimiento.getSaldoInicial());
        rsReporteMovimiento.setTipoMovimiento(movimiento.getTipoMovimiento());
        rsReporteMovimiento.setValor(movimiento.getValor());
        rsReporteMovimiento.setSaldoDisponible(movimiento.getSaldoDisponible());
        return rsReporteMovimiento;
    }
}
