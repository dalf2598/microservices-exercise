package com.banquito.microservicio2.controller;

import com.banquito.microservicio2.controller.dto.RQCreateMovimiento;
import com.banquito.microservicio2.controller.dto.RQUpdateMovimiento;
import com.banquito.microservicio2.controller.dto.RSMovimiento;
import com.banquito.microservicio2.controller.mapper.MovimientoMapper;
import com.banquito.microservicio2.mocks.MovimientoMocks;
import com.banquito.microservicio2.model.Movimiento;
import com.banquito.microservicio2.service.MovimientoService;
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

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

@WebMvcTest(controllers = MovimientoController.class)
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class MovimientoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MovimientoService movimientoService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void MovimientoController_GetAll_ReturnSuccess() throws Exception{
        List<RSMovimiento> mockMovimientos = MovimientoMocks.rsMovimientoListMock();
        String mockUrl = "/api/movimientos/all";

        given(movimientoService.getAllMovimientos()).willReturn(mockMovimientos);

        ResultActions response = mockMvc.perform(get(mockUrl));

        response.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
        response.andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(mockMovimientos)));
    }

    @Test
    public void MovimientoController_GetAll_ReturnException() throws Exception{
        List<RSMovimiento> mockMovimientos = Collections.emptyList();
        String mockUrl = "/api/movimientos/all";

        given(movimientoService.getAllMovimientos()).willReturn(mockMovimientos);

        ResultActions response = mockMvc.perform(get(mockUrl));

        response.andExpect(MockMvcResultMatchers.status().is5xxServerError());
        response.andExpect(MockMvcResultMatchers.content().json(MovimientoMocks.expectedNotAvailableException));
    }

    @Test
    public void MovimientoController_GetByID_ReturnSucess() throws Exception{
        RSMovimiento mockMovimiento = MovimientoMocks.rsMovimientoMock();
        String mockUrl = "/api/movimientos/".concat(mockMovimiento.getMovimientoId().toString());

        given(movimientoService.getMovimientoById(ArgumentMatchers.any())).willReturn(mockMovimiento);

        ResultActions response = mockMvc.perform(get(mockUrl));

        response.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
        response.andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(mockMovimiento)));
    }

    @Test
    public void MovimientoController_GetByID_ReturnException() throws Exception{
        RSMovimiento mockMovimiento = MovimientoMocks.rsMovimientoMock();
        String mockUrl = "/api/movimientos/cccc";

        given(movimientoService.getMovimientoById(ArgumentMatchers.any())).willReturn(mockMovimiento);

        ResultActions response = mockMvc.perform(get(mockUrl));

        response.andExpect(MockMvcResultMatchers.status().is4xxClientError());
        response.andExpect(MockMvcResultMatchers.content().json(MovimientoMocks.expectedException));
    }

    @Test
    public void MovimientoController_Create_ReturnSuccess() throws Exception{
        RQCreateMovimiento mockRQCreateMovimiento = MovimientoMocks.rqCreateMovimientoMock();
        Movimiento mockMovimiento = MovimientoMocks.movimientoMock();
        RSMovimiento expectedRSMovimiento = MovimientoMapper.mapGet(mockMovimiento);
        String mockUrl = "/api/movimientos";

        given(movimientoService.createMovimiento(ArgumentMatchers.any())).willReturn(mockMovimiento);

        ResultActions response = mockMvc.perform(post(mockUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(mockRQCreateMovimiento)));

        response.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
        response.andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(expectedRSMovimiento)));
    }

    @Test
    public void MovimientoController_Create_ReturnException() throws Exception{
        RQCreateMovimiento mockRQCreateMovimiento = MovimientoMocks.rqCreateMovimientoInvalidaMock();
        Movimiento mockMovimiento = MovimientoMocks.movimientoMock();
        String mockUrl = "/api/movimientos";

        given(movimientoService.createMovimiento(ArgumentMatchers.any())).willReturn(mockMovimiento);

        ResultActions response = mockMvc.perform(post(mockUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(mockRQCreateMovimiento)));

        response.andExpect(MockMvcResultMatchers.status().is4xxClientError());
        response.andExpect(MockMvcResultMatchers.content().json(MovimientoMocks.expectedException));
    }

    @Test
    public void MovimientoController_Update_ReturnSuccess() throws Exception{
        Movimiento mockMovimiento = MovimientoMocks.movimientoMock();
        RQUpdateMovimiento rqUpdateMovimiento = MovimientoMocks.rqUpdateMovimientoMock();
        RSMovimiento expectedRSMovimiento = MovimientoMapper.mapGet(mockMovimiento);
        String mockUrl = "/api/movimientos/".concat(mockMovimiento.getMovimientoId().toString());

        given(movimientoService.updateMovimiento(ArgumentMatchers.any(), ArgumentMatchers.any())).willReturn(mockMovimiento);

        ResultActions response = mockMvc.perform(put(mockUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(rqUpdateMovimiento)));

        response.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
        response.andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(expectedRSMovimiento)));
    }

    @Test
    public void MovimientoController_Update_ReturnException() throws Exception{
        Movimiento mockMovimiento = MovimientoMocks.movimientoMock();
        RQUpdateMovimiento rqUpdateMovimiento = MovimientoMocks.rqUpdateMovimientoInvalidoMock();
        String mockUrl = "/api/movimientos/".concat(mockMovimiento.getMovimientoId().toString());

        given(movimientoService.updateMovimiento(ArgumentMatchers.any(), ArgumentMatchers.any())).willReturn(mockMovimiento);

        ResultActions response = mockMvc.perform(put(mockUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(rqUpdateMovimiento)));

        response.andExpect(MockMvcResultMatchers.status().is4xxClientError());
        response.andExpect(MockMvcResultMatchers.content().json(MovimientoMocks.expectedException));
    }

    @Test
    public void MovimientoController_Delete_ReturnSuccess() throws Exception{
        Movimiento mockMovimiento = MovimientoMocks.movimientoMock();
        String mockUrl = "/api/movimientos/".concat(mockMovimiento.getMovimientoId().toString());

        willDoNothing().given(movimientoService).deleteMovimiento(ArgumentMatchers.any());

        ResultActions response = mockMvc.perform(delete(mockUrl));

        response.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
        response.andExpect(MockMvcResultMatchers.content().json(MovimientoMocks.expectedDeletedNotification));
    }

    @Test
    public void MovimientoController_Delete_ReturnException() throws Exception{
        String mockUrl = "/api/movimientos/cccc";

        willDoNothing().given(movimientoService).deleteMovimiento(ArgumentMatchers.any());

        ResultActions response = mockMvc.perform(delete(mockUrl));

        response.andExpect(MockMvcResultMatchers.status().is4xxClientError());
        response.andExpect(MockMvcResultMatchers.content().json(MovimientoMocks.expectedException));
    }
}
