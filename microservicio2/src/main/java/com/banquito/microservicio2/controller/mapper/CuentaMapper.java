package com.banquito.microservicio2.controller.mapper;

import com.banquito.microservicio2.controller.dto.RQCreateCuenta;
import com.banquito.microservicio2.controller.dto.RQUpdateCuenta;
import com.banquito.microservicio2.controller.dto.RSCuenta;
import com.banquito.microservicio2.model.Cuenta;
import com.banquito.microservicio2.request.dto.RSClient;

public class CuentaMapper {

    public static RSCuenta mapGet(Cuenta cuenta, RSClient client){
        RSCuenta rsCuenta = new RSCuenta();
        rsCuenta.setCuentaId(cuenta.getCuentaId());
        rsCuenta.setNumeroCuenta(cuenta.getNumeroCuenta());
        rsCuenta.setTipoCuenta(cuenta.getTipoCuenta());
        rsCuenta.setSaldoInicial(cuenta.getSaldoInicial());
        rsCuenta.setEstado(cuenta.isEstado());
        rsCuenta.setClienteId(cuenta.getClienteId());
        rsCuenta.setCliente(client.getNombre());
        return  rsCuenta;
    }

    public static Cuenta mapCreate(RQCreateCuenta rqCreateCuenta){
        Cuenta cuenta = new Cuenta();
        cuenta.setTipoCuenta(rqCreateCuenta.getTipoCuenta());
        cuenta.setSaldoInicial(rqCreateCuenta.getSaldoInicial());
        cuenta.setClienteId(rqCreateCuenta.getClienteId());
        return cuenta;
    }

    public static Cuenta mapUpdate(Cuenta cuenta, RQUpdateCuenta rqUpdateCuenta){
        cuenta.setTipoCuenta(rqUpdateCuenta.getTipoCuenta());
        cuenta.setSaldoInicial(rqUpdateCuenta.getSaldoInicial());
        cuenta.setEstado(rqUpdateCuenta.isEstado());
        cuenta.setClienteId(rqUpdateCuenta.getClienteId());
        return cuenta;
    }
}
