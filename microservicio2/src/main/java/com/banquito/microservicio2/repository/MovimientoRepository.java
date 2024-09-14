package com.banquito.microservicio2.repository;

import com.banquito.microservicio2.model.Cuenta;
import com.banquito.microservicio2.model.Movimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface MovimientoRepository extends JpaRepository<Movimiento, UUID> {
    List<Movimiento> findByCuenta(Cuenta cuenta);

    List<Movimiento> findByCuentaAndFechaBetween(Cuenta cuenta, long fechaInicio, long fechaFin);
}
