package com.reserva.noblesse.repository;

import com.reserva.noblesse.domain.Tenis;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Tenis entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TenisRepository extends JpaRepository<Tenis, Long> {
    @Query("select tenis from Tenis tenis where tenis.user.login = ?#{principal.username}")
    List<Tenis> findByUserIsCurrentUser();
}
