package com.banquito.microservicio2.service;

import com.banquito.microservicio2.controller.dto.*;
import com.banquito.microservicio2.controller.mapper.ReporteMapper;
import com.banquito.microservicio2.model.Cuenta;
import com.banquito.microservicio2.model.Movimiento;
import com.banquito.microservicio2.repository.CuentaRepository;
import com.banquito.microservicio2.repository.MovimientoRepository;
import com.banquito.microservicio2.request.ClientRequest;
import com.banquito.microservicio2.request.dto.RSClient;
import com.banquito.microservicio2.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ReporteService {
    @Autowired
    MovimientoRepository movimientoRepository;

    @Autowired
    CuentaRepository cuentaRepository;

    @Autowired
    private ClientRequest clientRequest;

    public RSEstadoCuenta generateEstadoCuenta(UUID clienteId, Long fechaInicio, Long fechaFin){
        RSEstadoCuenta estadoCuenta = new RSEstadoCuenta();

        RSClient client = clientRequest.getClient(clienteId);
        RSReportePersona reportePersona = ReporteMapper.mapReportePersona(client);

        List<RSReporteCuenta> reporteCuentas = new ArrayList<>();
        List<Cuenta> cuentas = cuentaRepository.findByClienteId(clienteId);

        cuentas.forEach(cuenta -> {
            List<RSReporteMovimiento> reporteMovimientos = new ArrayList<>();
            List<Movimiento> movimientos = movimientoRepository.findByCuentaAndFechaBetween(cuenta, fechaInicio, fechaFin);
            movimientos.forEach(movimiento -> {
                reporteMovimientos.add(ReporteMapper.mapReporteMovimiento(movimiento));
            });
            reporteCuentas.add(ReporteMapper.mapReporteCuenta(cuenta, reporteMovimientos));
        });

        estadoCuenta.setFechaInicio(Utils.formatUnixTimeToDate(fechaInicio));
        estadoCuenta.setFechaFin(Utils.formatUnixTimeToDate(fechaFin));
        estadoCuenta.setPersona(reportePersona);
        estadoCuenta.setCuentas(reporteCuentas);

        return estadoCuenta;
    }
}
