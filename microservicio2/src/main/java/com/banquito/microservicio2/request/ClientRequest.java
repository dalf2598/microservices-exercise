package com.banquito.microservicio2.request;

import com.banquito.microservicio2.exception.CustomRuntimeException;
import com.banquito.microservicio2.request.dto.RSClient;
import com.banquito.microservicio2.utils.ClientMicroservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Service
public class ClientRequest {
    @Autowired
    private ClientMicroservice clientMicroserviceURL;

    private final static RestTemplate restTemplate = new RestTemplate();

    public RSClient getClient(UUID clientId){
        try {
            String url = clientMicroserviceURL.getValue().concat("/{clientId}");

            ResponseEntity<RSClient> response = restTemplate.getForEntity(url, RSClient.class, clientId);
            if (response.getStatusCode().is2xxSuccessful()) {
                return response.getBody();
            } else {
                throw new CustomRuntimeException("Error al conectar con microservicio de cliente: " + response.getStatusCode(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception ex) {
            throw new CustomRuntimeException(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
