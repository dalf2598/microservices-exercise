package com.banquito.microservicio1.service;

import com.banquito.microservicio1.controller.dto.RQUpdateCliente;
import com.banquito.microservicio1.controller.mapper.ClientMapper;
import com.banquito.microservicio1.exception.CustomRuntimeException;
import com.banquito.microservicio1.model.Cliente;
import com.banquito.microservicio1.repository.ClienteRepository;
import com.banquito.microservicio1.request.AccountRequest;
import com.banquito.microservicio1.request.dto.RSAccount;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private AccountRequest accountRequest;

    public List<Cliente> getAllClients(){
        return clienteRepository.findAll();
    }

    public Cliente getClientById(UUID clientId){
        return clienteRepository.findById(clientId).orElseThrow(
                () -> new CustomRuntimeException("No se encontro un cliente con ese ID", HttpStatus.BAD_REQUEST)
        );
    }

    public Cliente createClient(Cliente client){

        Optional<Cliente> duplicateClient = clienteRepository.findByIdentificacion(client.getIdentificacion());

        if(duplicateClient.isPresent()){
            throw new CustomRuntimeException("Ya existe un cliente con esa identificaion", HttpStatus.BAD_REQUEST);
        }

        UUID id = UUID.randomUUID();
        UUID clientId = UUID.randomUUID();
        client.setId(id);
        client.setEstado(true);
        client.setClienteId(clientId);

        return clienteRepository.save(client);
    }

    public Cliente updateClient(UUID clientId, RQUpdateCliente rqUpdateCliente){
        Optional<Cliente> client = clienteRepository.findById(clientId);

        if(client.isEmpty()){
            throw new CustomRuntimeException("No se encontro un cliente con ese ID", HttpStatus.BAD_REQUEST);
        }

        Cliente newClient = ClientMapper.mapUpdate(client.get(), rqUpdateCliente);
        return  clienteRepository.save(newClient);

    }

    @Transactional
    public void deleteClient(UUID clientId){
        Optional<Cliente> client = clienteRepository.findById(clientId);
        if(client.isEmpty()){
            throw new CustomRuntimeException("No se encontro un cliente con ese ID", HttpStatus.BAD_REQUEST);
        }

        boolean isClientLinkedToAccount = accountRequest.getAccount(clientId);
        if(isClientLinkedToAccount){
            throw new CustomRuntimeException("Cliente tiene cuenta activa, no se puede borrar", HttpStatus.FORBIDDEN);
        }

        clienteRepository.deleteById(clientId);
    }
}
