package com.banquito.microservicio2.service;

import com.banquito.microservicio2.controller.dto.RQUpdateCuenta;
import com.banquito.microservicio2.controller.dto.RSCuenta;
import com.banquito.microservicio2.controller.mapper.CuentaMapper;
import com.banquito.microservicio2.exception.CustomRuntimeException;
import com.banquito.microservicio2.model.Cuenta;
import com.banquito.microservicio2.repository.CuentaRepository;
import com.banquito.microservicio2.request.ClientRequest;
import com.banquito.microservicio2.request.dto.RSClient;
import com.banquito.microservicio2.utils.Utils;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CuentaService {

    @Autowired
    private CuentaRepository cuentaRepository;

    @Autowired
    private ClientRequest clientRequest;

    public List<RSCuenta> getAllCuentas(){
        List<Cuenta> cuentas = cuentaRepository.findAll();
        List<RSCuenta> rsCuentas = new ArrayList<>();

        cuentas.forEach(cuenta -> {
            RSClient client = clientRequest.getClient(cuenta.getClienteId());
            RSCuenta rsCuenta = CuentaMapper.mapGet(cuenta, client);
            rsCuentas.add(rsCuenta);
        });

        return rsCuentas;
    }

    public RSCuenta getCuentaById(UUID cuentaId){
        Optional<Cuenta> cuentaOptional = cuentaRepository.findById(cuentaId);

        if(cuentaOptional.isEmpty()){
            throw new CustomRuntimeException("No se encontro una cuenta con ese ID", HttpStatus.BAD_REQUEST);
        }

        Cuenta cuenta = cuentaOptional.get();
        RSClient client = clientRequest.getClient(cuenta.getClienteId());

        return CuentaMapper.mapGet(cuenta, client);
    }

    public Cuenta getCuentaByClienteID(UUID clienteId){
        List<Cuenta> cuentaOptional = cuentaRepository.findByClienteId(clienteId);

        if(cuentaOptional.isEmpty()){
            throw new CustomRuntimeException("No se encontro una cuenta con ese ID de cliente", HttpStatus.BAD_REQUEST);
        }

        return cuentaOptional.getFirst();
    }

    public Cuenta createCuenta(Cuenta cuenta){
        clientRequest.getClient(cuenta.getClienteId());

        UUID cuentaId = UUID.randomUUID();
        String accountNumber = Utils.generateAccountNumber();
        cuenta.setCuentaId(cuentaId);
        cuenta.setEstado(true);
        cuenta.setNumeroCuenta(accountNumber);

        return cuentaRepository.save(cuenta);
    }

    public Cuenta updateCuenta(UUID cuentaId, RQUpdateCuenta rqUpdateCuenta){
        Optional<Cuenta> cuentaOptional = cuentaRepository.findById(cuentaId);

        if(cuentaOptional.isEmpty()){
            throw new CustomRuntimeException("No se encontro un cliente con ese ID", HttpStatus.BAD_REQUEST);
        }

        clientRequest.getClient(cuentaOptional.get().getClienteId());

        Cuenta newCuenta = CuentaMapper.mapUpdate(cuentaOptional.get(), rqUpdateCuenta);
        return cuentaRepository.save(newCuenta);
    }


    @Transactional
    public void deleteCuenta(UUID cuentaId){
        Optional<Cuenta> cuentaOptional = cuentaRepository.findById(cuentaId);
        if(cuentaOptional.isEmpty()){
            throw new CustomRuntimeException("No se encontro un cliente con ese ID", HttpStatus.BAD_REQUEST);
        }

        cuentaRepository.deleteById(cuentaId);
    }
}
