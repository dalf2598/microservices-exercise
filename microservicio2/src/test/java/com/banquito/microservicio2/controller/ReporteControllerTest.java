package com.banquito.microservicio2.controller;

import com.banquito.microservicio2.controller.dto.RSEstadoCuenta;
import com.banquito.microservicio2.mocks.CuentaMocks;
import com.banquito.microservicio2.mocks.ReporteMocks;
import com.banquito.microservicio2.service.ReporteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@WebMvcTest(controllers = ReporteController.class)
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class ReporteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReporteService reporteService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void ReporteController_GetEstadoCuenta_ReturnSucess() throws Exception{
        RSEstadoCuenta mockEstadoCuenta = ReporteMocks.estadoCuentaMock();
        String mockClienId = CuentaMocks.cuentaMock().getClienteId().toString();
        long mockInicio = 1726273889L;
        long mockFin = 1726273889L;

        String mockUrl = "/api/reportes/" + mockClienId + "?inicio=" + mockInicio + "&fin=" + mockFin;

        given(reporteService.generateEstadoCuenta(ArgumentMatchers.any(), ArgumentMatchers.any(), ArgumentMatchers.any()))
                .willReturn(mockEstadoCuenta);

        ResultActions response = mockMvc.perform(get(mockUrl));

        response.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
        response.andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(mockEstadoCuenta)));
    }

    @Test
    public void ReporteController_GetEstadoCuenta_ReturnIDException() throws Exception{
        RSEstadoCuenta mockEstadoCuenta = ReporteMocks.estadoCuentaMock();
        String mockClienId = "zzzzzzz";
        long mockInicio = 1726273889L;
        long mockFin = 1726273889L;

        String mockUrl = "/api/reportes/" + mockClienId + "?inicio=" + mockInicio + "&fin=" + mockFin;

        given(reporteService.generateEstadoCuenta(ArgumentMatchers.any(), ArgumentMatchers.any(), ArgumentMatchers.any()))
                .willReturn(mockEstadoCuenta);

        ResultActions response = mockMvc.perform(get(mockUrl));

        response.andExpect(MockMvcResultMatchers.status().is4xxClientError());
        response.andExpect(MockMvcResultMatchers.content().json(ReporteMocks.expectedInvalIdException));
    }

    @Test
    public void ReporteController_GetEstadoCuenta_ReturnDateRangeException() throws Exception{
        RSEstadoCuenta mockEstadoCuenta = ReporteMocks.estadoCuentaMock();
        String mockClienId = CuentaMocks.cuentaMock().getClienteId().toString();
        long mockInicio = 1726273889L;
        long mockFin = 1726273789L;

        String mockUrl = "/api/reportes/" + mockClienId + "?inicio=" + mockInicio + "&fin=" + mockFin;

        given(reporteService.generateEstadoCuenta(ArgumentMatchers.any(), ArgumentMatchers.any(), ArgumentMatchers.any()))
                .willReturn(mockEstadoCuenta);

        ResultActions response = mockMvc.perform(get(mockUrl));

        response.andExpect(MockMvcResultMatchers.status().is4xxClientError());
        response.andExpect(MockMvcResultMatchers.content().json(ReporteMocks.expectedInvalDateRangeException));
    }
}
