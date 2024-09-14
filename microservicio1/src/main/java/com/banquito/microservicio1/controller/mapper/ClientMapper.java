package com.banquito.microservicio1.controller.mapper;

import com.banquito.microservicio1.controller.dto.RQCreateCliente;
import com.banquito.microservicio1.controller.dto.RQUpdateCliente;
import com.banquito.microservicio1.model.Cliente;

public class ClientMapper {

    public static Cliente mapCreate(RQCreateCliente rqCreateCliente){
        Cliente client = new Cliente();
        client.setNombre(rqCreateCliente.getNombre());
        client.setGenero(rqCreateCliente.getGenero());
        client.setEdad(rqCreateCliente.getEdad());
        client.setIdentificacion(rqCreateCliente.getIdentificacion());
        client.setDireccion(rqCreateCliente.getDireccion());
        client.setTelefono(rqCreateCliente.getTelefono());
        client.setContrasena(rqCreateCliente.getContrasena());
        return client;
    }

    public static Cliente mapUpdate(Cliente client, RQUpdateCliente rqUpdateCliente){
        client.setNombre(rqUpdateCliente.getNombre());
        client.setGenero(rqUpdateCliente.getGenero());
        client.setEdad(rqUpdateCliente.getEdad());
        client.setIdentificacion(rqUpdateCliente.getIdentificacion());
        client.setDireccion(rqUpdateCliente.getDireccion());
        client.setTelefono(rqUpdateCliente.getTelefono());
        client.setContrasena(rqUpdateCliente.getContrasena());
        client.setEstado(rqUpdateCliente.isEstado());
        return client;
    }

}
