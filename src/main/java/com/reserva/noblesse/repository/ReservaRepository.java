package com.reserva.noblesse.repository;

import com.reserva.noblesse.domain.Reserva;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Reserva entity.
 */
@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {
    @Query("select reserva from Reserva reserva where reserva.user.login = ?#{principal.username}")
    List<Reserva> findByUserIsCurrentUser();

    @Query(
        value = "select distinct reserva from Reserva reserva left join fetch reserva.espacos",
        countQuery = "select count(distinct reserva) from Reserva reserva"
    )
    Page<Reserva> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct reserva from Reserva reserva left join fetch reserva.espacos")
    List<Reserva> findAllWithEagerRelationships();

    @Query("select reserva from Reserva reserva left join fetch reserva.espacos where reserva.id =:id")
    Optional<Reserva> findOneWithEagerRelationships(@Param("id") Long id);
}
