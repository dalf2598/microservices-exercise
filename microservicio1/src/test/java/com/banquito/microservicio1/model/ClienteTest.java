package com.banquito.microservicio1.model;

import com.banquito.microservicio1.mocks.ClienteMocks;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ClienteTest {

    @Test
    public void Cliente_Creation(){
        Cliente mockCliente = ClienteMocks.clienteMock();
        assert("550e8400-e29b-41d4-a716-446655441111").equals(mockCliente.getId().toString());
        assert("TestNombre").equals(mockCliente.getNombre());
        assert("TestGenero").equals(mockCliente.getGenero());
        assertEquals(18, mockCliente.getEdad());
        assert("TestIdentificacion").equals(mockCliente.getIdentificacion());
        assert("TestTelefono").equals(mockCliente.getTelefono());
        assert("TestContrasena").equals(mockCliente.getContrasena());
        assert("550e8400-e29b-41d4-a716-446655441122").equals(mockCliente.getClienteId().toString());
    }
}
