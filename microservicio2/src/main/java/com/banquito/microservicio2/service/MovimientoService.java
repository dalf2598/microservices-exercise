package com.banquito.microservicio2.service;

import com.banquito.microservicio2.controller.dto.*;
import com.banquito.microservicio2.controller.mapper.CuentaMapper;
import com.banquito.microservicio2.controller.mapper.MovimientoMapper;
import com.banquito.microservicio2.exception.CustomRuntimeException;
import com.banquito.microservicio2.model.Cuenta;
import com.banquito.microservicio2.model.Movimiento;
import com.banquito.microservicio2.repository.CuentaRepository;
import com.banquito.microservicio2.repository.MovimientoRepository;
import com.banquito.microservicio2.utils.Utils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class MovimientoService {

    @Autowired
    MovimientoRepository movimientoRepository;

    @Autowired
    CuentaRepository cuentaRepository;

    public List<RSMovimiento> getAllMovimientos() {
        List<Movimiento> movimientos = movimientoRepository.findAll();
        List<RSMovimiento> rsMovimientos = new ArrayList<>();

        movimientos.forEach(movimiento -> {
            RSMovimiento rSMovimiento = MovimientoMapper.mapGet(movimiento);
            rsMovimientos.add(rSMovimiento);
        });

        return rsMovimientos;
    }

    public RSMovimiento getMovimientoById(UUID movimientoId){
        Optional<Movimiento> movimientoOptional = movimientoRepository.findById(movimientoId);

        if(movimientoOptional.isEmpty()){
            throw new CustomRuntimeException("No se encontro un movimiento con ese ID", HttpStatus.BAD_REQUEST);
        }

        return MovimientoMapper.mapGet(movimientoOptional.get());
    }

    public Movimiento createMovimiento(RQCreateMovimiento rqCreateMovimiento){
        if(!Utils.isValidTipoMovimiento(rqCreateMovimiento.getTipoMovimiento())){
            throw new CustomRuntimeException("Tipo de movimiento invalido", HttpStatus.BAD_REQUEST);
        }

        Optional<Cuenta> cuentaOptional = cuentaRepository.findByNumeroCuenta(rqCreateMovimiento.getNumeroCuenta());

        if(cuentaOptional.isEmpty()){
            throw new CustomRuntimeException("Numero de cuenta invalido", HttpStatus.BAD_REQUEST);
        }

        Cuenta cuenta = cuentaOptional.get();
        BigDecimal saldoDisponible = new BigDecimal(0);
        Utils.TipoMovimiento tipoMovimiento = Utils.TipoMovimiento.DEPOSITO;
        BigDecimal saldoInicial = cuenta.getSaldoInicial();
        BigDecimal valor = rqCreateMovimiento.getValor();

        if(rqCreateMovimiento.getTipoMovimiento().equalsIgnoreCase(Utils.TipoMovimiento.DEPOSITO.toString())){
            saldoDisponible = saldoInicial .add(valor);
        }

        if(rqCreateMovimiento.getTipoMovimiento().equalsIgnoreCase(Utils.TipoMovimiento.RETIRO.toString())){
            tipoMovimiento = Utils.TipoMovimiento.RETIRO;
            saldoDisponible = saldoInicial.subtract(valor);
        }

        if(saldoDisponible.compareTo(BigDecimal.ZERO) < 0){
            throw new CustomRuntimeException("Fondos insuficientes para realizar el movimiento", HttpStatus.BAD_REQUEST);
        }

        Movimiento movimiento = new Movimiento();
        UUID movimientoId = UUID.randomUUID();
        Long currentUnixTime =  Utils.getCurrentUnixTime();
        movimiento.setMovimientoId(movimientoId);
        movimiento.setFecha(currentUnixTime);
        movimiento.setTipoMovimiento(tipoMovimiento);
        movimiento.setSaldoInicial(saldoInicial);
        movimiento.setValor(valor);
        movimiento.setSaldoDisponible(saldoDisponible);
        movimiento.setCuenta(cuenta);

        cuenta.setSaldoInicial(saldoDisponible);
        cuentaRepository.save(cuenta); // Actualiza saldo en cuenta

        return movimientoRepository.save(movimiento);
    }

    public Movimiento updateMovimiento(UUID movimientoId, RQUpdateMovimiento rqUpdateMovimiento){
        Optional<Movimiento> movimientoOptional = movimientoRepository.findById(movimientoId);

        if(movimientoOptional.isEmpty()){
            throw new CustomRuntimeException("No se encontro un movimiento con ese ID", HttpStatus.BAD_REQUEST);
        }

        if(!Utils.isValidTipoMovimiento(rqUpdateMovimiento.getTipoMovimiento())){
            throw new CustomRuntimeException("Tipo de movimiento invalido", HttpStatus.BAD_REQUEST);
        }

        Optional<Cuenta> cuentaOptional = cuentaRepository.findByNumeroCuenta(rqUpdateMovimiento.getNumeroCuenta());

        if(cuentaOptional.isEmpty()){
            throw new CustomRuntimeException("Numero de cuenta invalido", HttpStatus.BAD_REQUEST);
        }

        Movimiento newMovimiento = MovimientoMapper.mapUpdate(movimientoOptional.get(), rqUpdateMovimiento, cuentaOptional.get());
        return movimientoRepository.save(newMovimiento);
    }


    @Transactional
    public void deleteMovimiento(UUID movimientoId){
        Optional<Movimiento> movimientoOptional = movimientoRepository.findById(movimientoId);
        if(movimientoOptional.isEmpty()){
            throw new CustomRuntimeException("No se encontro un movimiento con ese ID", HttpStatus.BAD_REQUEST);
        }

        movimientoRepository.deleteById(movimientoId);
    }
}
