package com.banquito.microservicio2.repository;

import com.banquito.microservicio2.model.Cuenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CuentaRepository extends JpaRepository<Cuenta, UUID> {
    Optional<Cuenta> findByNumeroCuenta(String numeroCuenta);

    List<Cuenta> findByClienteId(UUID clienteId);
}
