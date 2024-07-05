package com.example.demo.repository;

import com.example.demo.entity.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {
    List<Reserva> findByProductoIdAndFechaFinAfterAndFechaInicioBefore(Long productoId, LocalDate fechaInicio, LocalDate fechaFin);
    List<Reserva> findByUsuarioEmail(String email);
}
