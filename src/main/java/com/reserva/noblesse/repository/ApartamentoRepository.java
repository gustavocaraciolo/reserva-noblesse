package com.reserva.noblesse.repository;

import com.reserva.noblesse.domain.Apartamento;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Apartamento entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ApartamentoRepository extends JpaRepository<Apartamento, Long> {}
