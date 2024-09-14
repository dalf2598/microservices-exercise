package com.banquito.microservicio2.controller.mapper;

import com.banquito.microservicio2.controller.dto.RQUpdateMovimiento;
import com.banquito.microservicio2.controller.dto.RSMovimiento;
import com.banquito.microservicio2.model.Cuenta;
import com.banquito.microservicio2.model.Movimiento;
import com.banquito.microservicio2.utils.Utils;

public class MovimientoMapper {

    public static RSMovimiento mapGet(Movimiento movimiento){
        RSMovimiento rsMovimiento = new RSMovimiento();
        rsMovimiento.setMovimientoId(movimiento.getMovimientoId());
        rsMovimiento.setFecha(Utils.formatUnixTimeToDate(movimiento.getFecha()));
        rsMovimiento.setNumeroCuenta(movimiento.getCuenta().getNumeroCuenta());
        rsMovimiento.setTipoCuenta(movimiento.getCuenta().getTipoCuenta());
        rsMovimiento.setSaldoInicial(movimiento.getSaldoInicial());
        rsMovimiento.setTipoMovimiento(movimiento.getTipoMovimiento());
        rsMovimiento.setValor(movimiento.getValor());
        rsMovimiento.setSaldoDisponible(movimiento.getSaldoDisponible());
        return  rsMovimiento;
    }

    public static Movimiento mapUpdate(Movimiento movimiento, RQUpdateMovimiento rqUpdateMovimiento, Cuenta newCuenta){
        movimiento.setFecha(rqUpdateMovimiento.getFecha());
        movimiento.setTipoMovimiento(Utils.TipoMovimiento.valueOf(rqUpdateMovimiento.getTipoMovimiento().toUpperCase()));
        movimiento.setSaldoInicial(rqUpdateMovimiento.getSaldoInicial());
        movimiento.setValor(rqUpdateMovimiento.getValor());
        movimiento.setSaldoDisponible(rqUpdateMovimiento.getSaldoDisponible());
        movimiento.setCuenta(newCuenta);
        return movimiento;
    }
}
