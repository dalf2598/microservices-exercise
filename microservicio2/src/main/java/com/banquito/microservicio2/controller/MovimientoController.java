package com.banquito.microservicio2.controller;

import com.banquito.microservicio2.controller.dto.*;
import com.banquito.microservicio2.controller.mapper.MovimientoMapper;
import com.banquito.microservicio2.exception.CustomRuntimeException;
import com.banquito.microservicio2.exception.dto.RSError;
import com.banquito.microservicio2.model.Movimiento;
import com.banquito.microservicio2.service.MovimientoService;
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
@RequestMapping("api/movimientos")
public class MovimientoController {

    @Autowired
    MovimientoService movimientoService;

    @GetMapping("/all")
    public ResponseEntity<List<RSMovimiento>> getAll(HttpServletRequest request) {
        String user = request.getHeader("User-Agent");
        try {
            List<RSMovimiento> rsMovimientos = movimientoService.getAllMovimientos();
            if(rsMovimientos.isEmpty()){
                throw new CustomRuntimeException("No se encontraron movimientos", HttpStatus.SERVICE_UNAVAILABLE);
            }
            log.info("Usuario {} hizo un peticion para obtener todos los movimientos", user);
            return  ResponseEntity.status(HttpStatus.OK).body(rsMovimientos);
        } catch (CustomRuntimeException ex){
            log.error("RuntimeException: {}", ex.getMessage());
            throw ex;
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<RSMovimiento> getById(HttpServletRequest request, @PathVariable String id){
        String user = request.getHeader("User-Agent");
        try {
            if (Utils.isNullOrEmpty(id) || !Utils.isValidUUID(id)) {
                throw new CustomRuntimeException("Parametros Invalidos", HttpStatus.BAD_REQUEST);
            }
            UUID movimientoId = UUID.fromString(id);
            RSMovimiento rSMovimiento = movimientoService.getMovimientoById(movimientoId);
            log.info("Usuario {} obtuvo los datos de movimiento {}", user, id);
            return ResponseEntity.status(HttpStatus.OK).body(rSMovimiento);
        } catch (CustomRuntimeException ex) {
            log.error("RuntimeException: {}", ex.getMessage());
            throw ex;
        }
    }

    @PostMapping
    public ResponseEntity<RSMovimiento> create(HttpServletRequest request, @RequestBody RQCreateMovimiento rqCreateMovimiento){
        String user = request.getHeader("User-Agent");
        try{
            if(!Utils.hasAllAttributes(rqCreateMovimiento)){
                throw new CustomRuntimeException("Parametros Invalidos", HttpStatus.BAD_REQUEST);
            }
            Movimiento movimiento = movimientoService.createMovimiento(rqCreateMovimiento);
            log.info("Usuario {} creo el movimiento con ID {}", user, movimiento.getMovimientoId());

            return  ResponseEntity.status(HttpStatus.CREATED).body(MovimientoMapper.mapGet(movimiento));
        }catch (CustomRuntimeException ex) {
            log.error("RuntimeException: {}", ex.getMessage());
            throw ex;
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<RSMovimiento> update(HttpServletRequest request, @PathVariable String id, @RequestBody RQUpdateMovimiento rqUpdateMovimiento){
        String user = request.getHeader("User-Agent");
        try {
            if (Utils.isNullOrEmpty(id) || !Utils.isValidUUID(id) || !Utils.hasAllAttributes(rqUpdateMovimiento)) {
                throw new CustomRuntimeException("Parametros Invalidos", HttpStatus.BAD_REQUEST);
            }
            UUID movimientoId = UUID.fromString(id);
            Movimiento newMovimiento = movimientoService.updateMovimiento(movimientoId, rqUpdateMovimiento);
            log.info("Usuario {} actualizo los datos de movimiento {}", user, id);
            return ResponseEntity.status(HttpStatus.OK).body(MovimientoMapper.mapGet(newMovimiento));
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
            UUID movimientoId = UUID.fromString(id);
            movimientoService.deleteMovimiento(movimientoId);
            log.info("Usuario {} elimino el movimiento con ID {}", user, id);

            Map<String, String> response = new HashMap<>();
            response.put("msg", "Movimiento " + id + " eliminado exitosamente");
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
