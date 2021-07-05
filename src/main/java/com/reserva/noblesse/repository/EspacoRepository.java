package com.reserva.noblesse.repository;

import com.reserva.noblesse.domain.Espaco;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Espaco entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EspacoRepository extends JpaRepository<Espaco, Long> {}
