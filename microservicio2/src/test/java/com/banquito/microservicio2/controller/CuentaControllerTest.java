package com.banquito.microservicio2.controller;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

import com.banquito.microservicio2.controller.dto.RQUpdateCuenta;
import com.banquito.microservicio2.controller.dto.RSCuenta;
import com.banquito.microservicio2.mocks.CuentaMocks;
import com.banquito.microservicio2.model.Cuenta;
import com.banquito.microservicio2.service.CuentaService;
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


@WebMvcTest(controllers = CuentaController.class)
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class CuentaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CuentaService cuentaService;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    public void CuentaController_GetAll_ReturnSuccess() throws Exception{
        List<RSCuenta> mockCuentas = CuentaMocks.rsCuentaListMock();

        String mockUrl = "/api/cuentas/all";

        given(cuentaService.getAllCuentas()).willReturn(mockCuentas);

        ResultActions response = mockMvc.perform(get(mockUrl));

        response.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
        response.andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(mockCuentas)));
    }

    @Test
    public void CuentaController_GetAll_ReturnException() throws Exception{
        List<RSCuenta> mockCuentas = Collections.emptyList();
        String mockUrl = "/api/cuentas/all";

        given(cuentaService.getAllCuentas()).willReturn(mockCuentas);

        ResultActions response = mockMvc.perform(get(mockUrl));

        response.andExpect(MockMvcResultMatchers.status().is5xxServerError());
        response.andExpect(MockMvcResultMatchers.content().json(CuentaMocks.expectedNotAvailableException));
    }

    @Test
    public void CuentaController_GetByID_ReturnSucess() throws Exception{
        RSCuenta mockCuenta = CuentaMocks.rsCuentaMock();
        String mockUrl = "/api/cuentas/".concat(CuentaMocks.cuentaMock().getCuentaId().toString());

        given(cuentaService.getCuentaById(ArgumentMatchers.any())).willReturn(mockCuenta);

        ResultActions response = mockMvc.perform(get(mockUrl));

        response.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
        response.andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(mockCuenta)));
    }

    @Test
    public void CuentaController_GetByID_ReturnException() throws Exception{
        RSCuenta mockCuenta = CuentaMocks.rsCuentaMock();
        String mockUrl = "/api/cuentas/bbbb";

        given(cuentaService.getCuentaById(ArgumentMatchers.any())).willReturn(mockCuenta);

        ResultActions response = mockMvc.perform(get(mockUrl));

        response.andExpect(MockMvcResultMatchers.status().is4xxClientError());
        response.andExpect(MockMvcResultMatchers.content().json(CuentaMocks.expectedException));
    }

    @Test
    public void CuentaController_Create_ReturnSuccess() throws Exception{
        Cuenta mockCuenta = CuentaMocks.cuentaMock();
        String mockUrl = "/api/cuentas";

        given(cuentaService.createCuenta(ArgumentMatchers.any())).willReturn(mockCuenta);

        ResultActions response = mockMvc.perform(post(mockUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(mockCuenta)));

        response.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
        response.andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(mockCuenta)));
    }

    @Test
    public void CuentaController_Create_ReturnException() throws Exception{
        Cuenta mockCuenta = CuentaMocks.cuentaInvalidaMock();
        String mockUrl = "/api/cuentas";

        given(cuentaService.createCuenta(ArgumentMatchers.any())).willReturn(mockCuenta);

        ResultActions response = mockMvc.perform(post(mockUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(mockCuenta)));

        response.andExpect(MockMvcResultMatchers.status().is4xxClientError());
        response.andExpect(MockMvcResultMatchers.content().json(CuentaMocks.expectedException));
    }

    @Test
    public void CuentaController_Update_ReturnSuccess() throws Exception{
        Cuenta mockCuenta = CuentaMocks.cuentaInvalidaMock();
        RQUpdateCuenta rqUpdateCuenta = CuentaMocks.rqUpdateCuentaMock();
        String mockUrl = "/api/cuentas/".concat(mockCuenta.getCuentaId().toString());

        given(cuentaService.updateCuenta(ArgumentMatchers.any(), ArgumentMatchers.any())).willReturn(mockCuenta);

        ResultActions response = mockMvc.perform(put(mockUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(rqUpdateCuenta)));

        response.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
        response.andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(mockCuenta)));
    }

    @Test
    public void CuentaController_Update_ReturnException() throws Exception{
        Cuenta mockCuenta = CuentaMocks.cuentaInvalidaMock();
        RQUpdateCuenta rqUpdateCuenta = CuentaMocks.rqUpdateCuentaInvalidoMock();
        String mockUrl = "/api/cuentas/".concat(mockCuenta.getCuentaId().toString());

        given(cuentaService.updateCuenta(ArgumentMatchers.any(), ArgumentMatchers.any())).willReturn(mockCuenta);

        ResultActions response = mockMvc.perform(put(mockUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(rqUpdateCuenta)));

        response.andExpect(MockMvcResultMatchers.status().is4xxClientError());
        response.andExpect(MockMvcResultMatchers.content().json(CuentaMocks.expectedException));
    }

    @Test
    public void CuentaController_Delete_ReturnSuccess() throws Exception{
        Cuenta mockCuenta = CuentaMocks.cuentaInvalidaMock();
        String mockUrl = "/api/cuentas/".concat(mockCuenta.getCuentaId().toString());

        willDoNothing().given(cuentaService).deleteCuenta(ArgumentMatchers.any());

        ResultActions response = mockMvc.perform(delete(mockUrl));

        response.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
        response.andExpect(MockMvcResultMatchers.content().json(CuentaMocks.expectedDeletedNotification));
    }

    @Test
    public void CuentaController_Delete_ReturnException() throws Exception{
        String mockUrl = "/api/cuentas/bbbb";

        willDoNothing().given(cuentaService).deleteCuenta(ArgumentMatchers.any());

        ResultActions response = mockMvc.perform(delete(mockUrl));

        response.andExpect(MockMvcResultMatchers.status().is4xxClientError());
        response.andExpect(MockMvcResultMatchers.content().json(CuentaMocks.expectedException));
    }
}
