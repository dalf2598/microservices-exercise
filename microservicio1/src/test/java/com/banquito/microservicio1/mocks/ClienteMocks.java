package com.banquito.microservicio1.mocks;

import com.banquito.microservicio1.controller.dto.RQUpdateCliente;
import com.banquito.microservicio1.model.Cliente;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class ClienteMocks {

    public static String expectedException = "{\"status\":\"RuntimeException\",\"description\":\"Parametros Invalidos\"}";

    public static String expectedDeletedNotification = "{\"msg\":\"Cliente 550e8400-e29b-41d4-a716-446655441111 eliminado exitosamente\"}";

    public static String expectedNotAvailableException = "{\"status\":\"RuntimeException\",\"description\":\"No se encontraron clientes\"}";

    public static List<Cliente> clienteListMock() {
        Cliente mockCliente = new Cliente();
        mockCliente.setId(UUID.fromString("550e8400-e29b-41d4-a716-446655441111"));
        mockCliente.setNombre("TestNombre");
        mockCliente.setGenero("TestGenero");
        mockCliente.setEdad(18);
        mockCliente.setIdentificacion("TestIdentificacion");
        mockCliente.setDireccion("TestDireccion");
        mockCliente.setTelefono("TestTelefono");
        mockCliente.setContrasena("TestContrasena");
        mockCliente.setClienteId(UUID.fromString("550e8400-e29b-41d4-a716-446655441122"));

        return Collections.singletonList(mockCliente); // Return a list containing only the mockCliente
    }

    public static Cliente clienteMock(){
        Cliente mockCliente = new Cliente();
        mockCliente.setId(UUID.fromString("550e8400-e29b-41d4-a716-446655441111"));
        mockCliente.setNombre("TestNombre");
        mockCliente.setGenero("TestGenero");
        mockCliente.setEdad(18);
        mockCliente.setIdentificacion("TestIdentificacion");
        mockCliente.setDireccion("TestDireccion");
        mockCliente.setTelefono("TestTelefono");
        mockCliente.setContrasena("TestContrasena");
        mockCliente.setClienteId(UUID.fromString("550e8400-e29b-41d4-a716-446655441122"));
        return mockCliente;
    }

    public static Cliente clienteInvalidoMock(){
        Cliente mockCliente = new Cliente();
        mockCliente.setId(UUID.fromString("550e8400-e29b-41d4-a716-446655441111"));
        mockCliente.setNombre("TestNombre");
        return mockCliente;
    }

    public static RQUpdateCliente rQUpdateClienteMock(){
        RQUpdateCliente mockCliente = new RQUpdateCliente();
        mockCliente.setNombre("TestNombre");
        mockCliente.setGenero("TestGenero");
        mockCliente.setEdad(18);
        mockCliente.setIdentificacion("TestIdentificacion");
        mockCliente.setDireccion("TestDireccion");
        mockCliente.setTelefono("TestTelefono");
        mockCliente.setContrasena("TestContrasena");
        mockCliente.setEstado(true);
        return mockCliente;
    }

    public static RQUpdateCliente rQUpdateClienteInvalidoMock(){
        RQUpdateCliente mockCliente = new RQUpdateCliente();
        mockCliente.setNombre("TestNombre");
        return mockCliente;
    }

}
