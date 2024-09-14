package com.banquito.microservicio1.controller;

import com.banquito.microservicio1.controller.dto.RQCreateCliente;
import com.banquito.microservicio1.controller.dto.RQUpdateCliente;
import com.banquito.microservicio1.controller.mapper.ClientMapper;
import com.banquito.microservicio1.exception.CustomRuntimeException;
import com.banquito.microservicio1.exception.dto.RSError;
import com.banquito.microservicio1.model.Cliente;
import com.banquito.microservicio1.service.ClienteService;
import com.banquito.microservicio1.utils.Utils;
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
@RequestMapping("api/clientes")
public class ClienteController {
    @Autowired
    private ClienteService clienteService;

    @GetMapping("/all")
    public ResponseEntity<List<Cliente>> getAll(HttpServletRequest request) {
        String user = request.getHeader("User-Agent");
        try {
            List<Cliente> clients = clienteService.getAllClients();
            if(clients.isEmpty()){
                throw new CustomRuntimeException("No se encontraron clientes", HttpStatus.SERVICE_UNAVAILABLE);
            }
            log.info("Usuario {} hizo un peticion para obtener todos los clientes", user);
            return  ResponseEntity.status(HttpStatus.OK).body(clients);
        } catch (CustomRuntimeException ex){
            log.error("RuntimeException: {}", ex.getMessage());
            throw ex;
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> getById(HttpServletRequest request, @PathVariable String id){
        String user = request.getHeader("User-Agent");
        try {
            if (Utils.isNullOrEmpty(id) || !Utils.isValidUUID(id)) {
                throw new CustomRuntimeException("Parametros Invalidos", HttpStatus.BAD_REQUEST);
            }
            UUID clientId = UUID.fromString(id);
            Cliente client = clienteService.getClientById(clientId);
            log.info("Usuario {} obtuvo los datos de {}", user, id);
            return ResponseEntity.status(HttpStatus.OK).body(client);
        } catch (CustomRuntimeException ex) {
            log.error("RuntimeException: {}", ex.getMessage());
            throw ex;
        }
    }

    @PostMapping
    public ResponseEntity<Cliente> create(HttpServletRequest request, @RequestBody RQCreateCliente rqCreateCliente){
        String user = request.getHeader("User-Agent");
        try{
            if(!Utils.hasAllAttributes(rqCreateCliente)){
                throw new CustomRuntimeException("Parametros Invalidos", HttpStatus.BAD_REQUEST);
            }
            Cliente client = clienteService.createClient(ClientMapper.mapCreate(rqCreateCliente));
            log.info("Usuario {} creo al cliente con ID {}", user, client.getId());
            return  ResponseEntity.status(HttpStatus.CREATED).body(client);
        }catch (CustomRuntimeException ex) {
            log.error("RuntimeException: {}", ex.getMessage());
            throw ex;
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cliente> update(HttpServletRequest request, @PathVariable String id, @RequestBody RQUpdateCliente rqUpdateCliente){
        String user = request.getHeader("User-Agent");
        try {
            if (Utils.isNullOrEmpty(id) || !Utils.isValidUUID(id) || !Utils.hasAllAttributes(rqUpdateCliente)) {
                throw new CustomRuntimeException("Parametros Invalidos", HttpStatus.BAD_REQUEST);
            }
            UUID clientId = UUID.fromString(id);
            Cliente newClient = clienteService.updateClient(clientId, rqUpdateCliente);
            log.info("Usuario {} actualizo los datos de {}", user, id);
            return ResponseEntity.status(HttpStatus.OK).body(newClient);
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
            UUID clientId = UUID.fromString(id);
            clienteService.deleteClient(clientId);
            log.info("Usuario {} elimino al cliente con ID {}", user, id);

            Map<String, String> response = new HashMap<>();
            response.put("msg", "Cliente " + id + " eliminado exitosamente");
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
