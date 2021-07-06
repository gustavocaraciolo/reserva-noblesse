package com.reserva.noblesse.repository;

import com.reserva.noblesse.domain.Torre;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Torre entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TorreRepository extends JpaRepository<Torre, Long> {}
