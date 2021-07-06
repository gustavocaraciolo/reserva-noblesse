package com.reserva.noblesse.repository;

import com.reserva.noblesse.domain.Apartamento;
import com.reserva.noblesse.domain.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Apartamento entity.
 */
@Repository
public interface ApartamentoRepository extends JpaRepository<Apartamento, Long> {
    @Query(
        value = "select distinct apartamento from Apartamento apartamento left join fetch apartamento.users",
        countQuery = "select count(distinct apartamento) from Apartamento apartamento"
    )
    Page<Apartamento> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct apartamento from Apartamento apartamento left join fetch apartamento.users")
    List<Apartamento> findAllWithEagerRelationships();

    @Query("select apartamento from Apartamento apartamento left join fetch apartamento.users where apartamento.id =:id")
    Optional<Apartamento> findOneWithEagerRelationships(@Param("id") Long id);

    List<Apartamento> findApartamentosByUsers(@Param("user") Optional<User> user);
}
