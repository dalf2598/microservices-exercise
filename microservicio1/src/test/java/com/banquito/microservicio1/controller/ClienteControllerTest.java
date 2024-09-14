package com.banquito.microservicio1.controller;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

import com.banquito.microservicio1.controller.dto.RQUpdateCliente;
import com.banquito.microservicio1.mocks.ClienteMocks;
import com.banquito.microservicio1.model.Cliente;
import com.banquito.microservicio1.service.ClienteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;
import java.util.List;

@WebMvcTest(controllers = ClienteController.class)
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class ClienteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClienteService clienteService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void ClienteController_GetAll_ReturnSuccess() throws Exception{
        List<Cliente> mockClientes = ClienteMocks.clienteListMock();
        String mockUrl = "/api/clientes/all";

        given(clienteService.getAllClients()).willReturn(mockClientes);

        ResultActions response = mockMvc.perform(get(mockUrl));

        response.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
        response.andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(mockClientes)));
    }

    @Test
    public void ClienteController_GetAll_ReturnException() throws Exception{
        List<Cliente> mockClientes = Collections.emptyList();
        String mockUrl = "/api/clientes/all";

        given(clienteService.getAllClients()).willReturn(mockClientes);

        ResultActions response = mockMvc.perform(get(mockUrl));

        response.andExpect(MockMvcResultMatchers.status().is5xxServerError());
        response.andExpect(MockMvcResultMatchers.content().json(ClienteMocks.expectedNotAvailableException));
    }

    @Test
    public void ClienteController_GetByID_ReturnSucess() throws Exception{
        Cliente mockCliente = ClienteMocks.clienteMock();
        String mockUrl = "/api/clientes/".concat(mockCliente.getId().toString());

        given(clienteService.getClientById(ArgumentMatchers.any())).willReturn(mockCliente);

        ResultActions response = mockMvc.perform(get(mockUrl));

        response.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
        response.andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(mockCliente)));
    }

    @Test
    public void ClienteController_GetByID_ReturnException() throws Exception{
        Cliente mockCliente = ClienteMocks.clienteMock();
        String mockUrl = "/api/clientes/aaaaa";

        given(clienteService.getClientById(ArgumentMatchers.any())).willReturn(mockCliente);

        ResultActions response = mockMvc.perform(get(mockUrl));

        response.andExpect(MockMvcResultMatchers.status().is4xxClientError());
        response.andExpect(MockMvcResultMatchers.content().json(ClienteMocks.expectedException));
    }


    @Test
    public void ClienteController_Create_ReturnSuccess() throws Exception{
        Cliente mockCliente = ClienteMocks.clienteMock();
        String mockUrl = "/api/clientes";

        given(clienteService.createClient(ArgumentMatchers.any())).willReturn(mockCliente);

        ResultActions response = mockMvc.perform(post(mockUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(mockCliente)));

        response.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
        response.andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(mockCliente)));
    }

    @Test
    public void ClienteController_Create_ReturnException() throws Exception{
        Cliente mockCliente = ClienteMocks.clienteInvalidoMock();
        String mockUrl = "/api/clientes";

        given(clienteService.createClient(ArgumentMatchers.any())).willReturn(mockCliente);

        ResultActions response = mockMvc.perform(post(mockUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(mockCliente)));

        response.andExpect(MockMvcResultMatchers.status().is4xxClientError());
        response.andExpect(MockMvcResultMatchers.content().json(ClienteMocks.expectedException));
    }

    @Test
    public void ClienteController_Update_ReturnSuccess() throws Exception{
        Cliente mockCliente = ClienteMocks.clienteMock();
        RQUpdateCliente rqUpdateCliente = ClienteMocks.rQUpdateClienteMock();
        String mockUrl = "/api/clientes/".concat(mockCliente.getId().toString());

        given(clienteService.updateClient(ArgumentMatchers.any(), ArgumentMatchers.any())).willReturn(mockCliente);

        ResultActions response = mockMvc.perform(put(mockUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(rqUpdateCliente)));

        response.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
        response.andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(mockCliente)));
    }

    @Test
    public void ClienteController_Update_ReturnException() throws Exception{
        Cliente mockCliente = ClienteMocks.clienteMock();
        RQUpdateCliente rqUpdateCliente = ClienteMocks.rQUpdateClienteInvalidoMock();
        String mockUrl = "/api/clientes/".concat(mockCliente.getId().toString());

        given(clienteService.updateClient(ArgumentMatchers.any(), ArgumentMatchers.any())).willReturn(mockCliente);

        ResultActions response = mockMvc.perform(put(mockUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(rqUpdateCliente)));

        response.andExpect(MockMvcResultMatchers.status().is4xxClientError());
        response.andExpect(MockMvcResultMatchers.content().json(ClienteMocks.expectedException));
    }


    @Test
    public void ClienteController_Delete_ReturnSuccess() throws Exception{
        Cliente mockCliente = ClienteMocks.clienteMock();
        String mockUrl = "/api/clientes/".concat(mockCliente.getId().toString());

        willDoNothing().given(clienteService).deleteClient(ArgumentMatchers.any());

        ResultActions response = mockMvc.perform(delete(mockUrl));

        response.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
        response.andExpect(MockMvcResultMatchers.content().json(ClienteMocks.expectedDeletedNotification));
    }

    @Test
    public void ClienteController_Delete_ReturnException() throws Exception{
        String mockUrl = "/api/clientes/aaaaa";

        willDoNothing().given(clienteService).deleteClient(ArgumentMatchers.any());

        ResultActions response = mockMvc.perform(delete(mockUrl));

        response.andExpect(MockMvcResultMatchers.status().is4xxClientError());
        response.andExpect(MockMvcResultMatchers.content().json(ClienteMocks.expectedException));
    }
}
