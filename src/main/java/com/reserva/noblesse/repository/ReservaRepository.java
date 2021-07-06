package com.reserva.noblesse.repository;

import com.reserva.noblesse.domain.Reserva;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Reserva entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {
    @Query("select reserva from Reserva reserva where reserva.user.login = ?#{principal.username}")
    List<Reserva> findByUserIsCurrentUser();
}
