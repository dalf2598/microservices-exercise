package com.banquito.microservicio1.request;

import com.banquito.microservicio1.exception.CustomRuntimeException;
import com.banquito.microservicio1.request.dto.RSAccount;
import com.banquito.microservicio1.utils.AccountMicroservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Service
public class AccountRequest {

    @Autowired
    private AccountMicroservice accountMicroserviceURL;

    private final static RestTemplate restTemplate = new RestTemplate();

    public boolean getAccount(UUID clientId){
        try {
            String url = accountMicroserviceURL.getValue().concat("/client/{clientId}");

            ResponseEntity<RSAccount> response = restTemplate.getForEntity(url, RSAccount.class, clientId);
            if (response.getStatusCode().is2xxSuccessful()) {
                return true;
            }
            return false;
        } catch (Exception ex) {
            return false;
        }
    }

}
