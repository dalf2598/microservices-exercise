package com.banquito.microservicio2.controller;

import com.banquito.microservicio2.controller.dto.RQCreateCuenta;
import com.banquito.microservicio2.controller.dto.RQUpdateCuenta;
import com.banquito.microservicio2.controller.dto.RSCuenta;
import com.banquito.microservicio2.controller.mapper.CuentaMapper;
import com.banquito.microservicio2.exception.CustomRuntimeException;
import com.banquito.microservicio2.exception.dto.RSError;
import com.banquito.microservicio2.model.Cuenta;
import com.banquito.microservicio2.service.CuentaService;
import com.banquito.microservicio2.utils.Utils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("api/cuentas")
public class CuentaController {
    @Autowired
    private CuentaService cuentaService;

    @GetMapping("/all")
    public ResponseEntity<List<RSCuenta>> getAll(HttpServletRequest request) {
        String user = request.getHeader("User-Agent");
        try {
            List<RSCuenta> rsCuentas = cuentaService.getAllCuentas();
            if(rsCuentas.isEmpty()){
                throw new CustomRuntimeException("No se encontraron cuentas", HttpStatus.SERVICE_UNAVAILABLE);
            }
            log.info("Usuario {} hizo un peticion para obtener todos los cuentas", user);
            return  ResponseEntity.status(HttpStatus.OK).body(rsCuentas);
        } catch (CustomRuntimeException ex){
            log.error("RuntimeException: {}", ex.getMessage());
            throw ex;
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<RSCuenta> getById(HttpServletRequest request, @PathVariable String id){
        String user = request.getHeader("User-Agent");
        try {
            if (Utils.isNullOrEmpty(id) || !Utils.isValidUUID(id)) {
                throw new CustomRuntimeException("Parametros Invalidos", HttpStatus.BAD_REQUEST);
            }
            UUID cuentaId = UUID.fromString(id);
            RSCuenta rsCuenta = cuentaService.getCuentaById(cuentaId);
            log.info("Usuario {} obtuvo los datos de cuenta {}", user, id);
            return ResponseEntity.status(HttpStatus.OK).body(rsCuenta);
        } catch (CustomRuntimeException ex) {
            log.error("RuntimeException: {}", ex.getMessage());
            throw ex;
        }
    }

    @GetMapping("client/{id}")
    public ResponseEntity<Cuenta> getByClientId(HttpServletRequest request, @PathVariable String id){
        String user = request.getHeader("User-Agent");
        try {
            if (Utils.isNullOrEmpty(id) || !Utils.isValidUUID(id)) {
                throw new CustomRuntimeException("Parametros Invalidos", HttpStatus.BAD_REQUEST);
            }
            UUID clientId = UUID.fromString(id);
            Cuenta cuenta = cuentaService.getCuentaByClienteID(clientId);
            log.info("Usuario {} obtuvo los datos de cuenta {}", user, cuenta.getCuentaId());
            return ResponseEntity.status(HttpStatus.OK).body(cuenta);
        } catch (CustomRuntimeException ex) {
            log.error("RuntimeException: {}", ex.getMessage());
            throw ex;
        }
    }

    @PostMapping
    public ResponseEntity<Cuenta> create(HttpServletRequest request, @RequestBody RQCreateCuenta rqCreateCuenta){
        String user = request.getHeader("User-Agent");
        try{
            if(!Utils.hasAllAttributes(rqCreateCuenta)){
                throw new CustomRuntimeException("Parametros Invalidos", HttpStatus.BAD_REQUEST);
            }
            Cuenta cuenta = cuentaService.createCuenta(CuentaMapper.mapCreate(rqCreateCuenta));
            log.info("Usuario {} creo la cuenta con ID {}", user, cuenta.getCuentaId());
            return  ResponseEntity.status(HttpStatus.CREATED).body(cuenta);
        }catch (CustomRuntimeException ex) {
            log.error("RuntimeException: {}", ex.getMessage());
            throw ex;
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cuenta> update(HttpServletRequest request, @PathVariable String id, @RequestBody RQUpdateCuenta rqUpdateCuenta){
        String user = request.getHeader("User-Agent");
        try {
            if (Utils.isNullOrEmpty(id) || !Utils.isValidUUID(id) || !Utils.hasAllAttributes(rqUpdateCuenta)) {
                throw new CustomRuntimeException("Parametros Invalidos", HttpStatus.BAD_REQUEST);
            }
            UUID cuentaId = UUID.fromString(id);
            Cuenta newCuenta = cuentaService.updateCuenta(cuentaId, rqUpdateCuenta);
            log.info("Usuario {} actualizo los datos de cuenta {}", user, id);
            return ResponseEntity.status(HttpStatus.OK).body(newCuenta);
        } catch (CustomRuntimeException ex) {
            log.error("RuntimeException: {}", ex.getMessage());
            throw ex;
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> delete(HttpServletRequest request, @PathVariable String id){
        String user = request.getHeader("User-Agent");
        try {
            if (Utils.isNullOrEmpty(id) || !Utils.isValidUUID(id)) {
                throw new CustomRuntimeException("Parametros Invalidos", HttpStatus.BAD_REQUEST);
            }
            UUID cuentaId = UUID.fromString(id);
            cuentaService.deleteCuenta(cuentaId);
            log.info("Usuario {} elimino la cuenta con ID {}", user, id);

            Map<String, String> response = new HashMap<>();
            response.put("msg", "Cuenta " + id + " eliminada exitosamente");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (CustomRuntimeException ex) {
            log.error("RuntimeException: {}", ex.getMessage());
            throw ex;
        }
    }

    @ExceptionHandler(CustomRuntimeException.class)
    public ResponseEntity<RSError> handleCustomRuntimeException(CustomRuntimeException ex) {
        RSError errorResponse = new RSError("RuntimeException", ex.getMessage());
        return new ResponseEntity<>(errorResponse, ex.getCode());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<RSError> handleGenericException(Exception ex) {
        RSError errorResponse = new RSError("Exception", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
