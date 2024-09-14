package com.banquito.microservicio2.controller;

import com.banquito.microservicio2.controller.dto.RSEstadoCuenta;
import com.banquito.microservicio2.exception.CustomRuntimeException;
import com.banquito.microservicio2.exception.dto.RSError;
import com.banquito.microservicio2.service.ReporteService;
import com.banquito.microservicio2.utils.Utils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("api/reportes")
public class ReporteController {

    @Autowired
    ReporteService reporteService;

    @GetMapping("/{id}")
    public ResponseEntity<RSEstadoCuenta> getEstadoCuenta(
            HttpServletRequest request,
            @PathVariable String id,
            @RequestParam(name = "inicio") long inicio,
            @RequestParam(name = "fin") long fin
            ){
        String user = request.getHeader("User-Agent");
        try {
            if (Utils.isNullOrEmpty(id) || !Utils.isValidUUID(id) ) {
                throw new CustomRuntimeException("ID Invalido", HttpStatus.BAD_REQUEST);
            }
            if(!Utils.isValidUnixTimeRange(inicio,fin)){
                throw new CustomRuntimeException("Fechas Invalidas", HttpStatus.BAD_REQUEST);
            }

            UUID clienteId = UUID.fromString(id);
            RSEstadoCuenta estadoCuenta = reporteService.generateEstadoCuenta(clienteId, inicio, fin);
            log.info("Usuario {} obtuvo el estado de cuenta de cliente {}", user, id);
            return ResponseEntity.status(HttpStatus.OK).body(estadoCuenta);
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
