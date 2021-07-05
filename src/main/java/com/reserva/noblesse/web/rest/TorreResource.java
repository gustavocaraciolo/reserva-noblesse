package com.reserva.noblesse.web.rest;

import com.reserva.noblesse.domain.Torre;
import com.reserva.noblesse.repository.TorreRepository;
import com.reserva.noblesse.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.reserva.noblesse.domain.Torre}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class TorreResource {

    private final Logger log = LoggerFactory.getLogger(TorreResource.class);

    private static final String ENTITY_NAME = "torre";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TorreRepository torreRepository;

    public TorreResource(TorreRepository torreRepository) {
        this.torreRepository = torreRepository;
    }

    /**
     * {@code POST  /torres} : Create a new torre.
     *
     * @param torre the torre to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new torre, or with status {@code 400 (Bad Request)} if the torre has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/torres")
    public ResponseEntity<Torre> createTorre(@RequestBody Torre torre) throws URISyntaxException {
        log.debug("REST request to save Torre : {}", torre);
        if (torre.getId() != null) {
            throw new BadRequestAlertException("A new torre cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Torre result = torreRepository.save(torre);
        return ResponseEntity
            .created(new URI("/api/torres/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /torres/:id} : Updates an existing torre.
     *
     * @param id the id of the torre to save.
     * @param torre the torre to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated torre,
     * or with status {@code 400 (Bad Request)} if the torre is not valid,
     * or with status {@code 500 (Internal Server Error)} if the torre couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/torres/{id}")
    public ResponseEntity<Torre> updateTorre(@PathVariable(value = "id", required = false) final Long id, @RequestBody Torre torre)
        throws URISyntaxException {
        log.debug("REST request to update Torre : {}, {}", id, torre);
        if (torre.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, torre.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!torreRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Torre result = torreRepository.save(torre);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, torre.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /torres/:id} : Partial updates given fields of an existing torre, field will ignore if it is null
     *
     * @param id the id of the torre to save.
     * @param torre the torre to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated torre,
     * or with status {@code 400 (Bad Request)} if the torre is not valid,
     * or with status {@code 404 (Not Found)} if the torre is not found,
     * or with status {@code 500 (Internal Server Error)} if the torre couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/torres/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<Torre> partialUpdateTorre(@PathVariable(value = "id", required = false) final Long id, @RequestBody Torre torre)
        throws URISyntaxException {
        log.debug("REST request to partial update Torre partially : {}, {}", id, torre);
        if (torre.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, torre.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!torreRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Torre> result = torreRepository
            .findById(torre.getId())
            .map(
                existingTorre -> {
                    if (torre.getNumero() != null) {
                        existingTorre.setNumero(torre.getNumero());
                    }

                    return existingTorre;
                }
            )
            .map(torreRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, torre.getId().toString())
        );
    }

    /**
     * {@code GET  /torres} : get all the torres.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of torres in body.
     */
    @GetMapping("/torres")
    public List<Torre> getAllTorres() {
        log.debug("REST request to get all Torres");
        return torreRepository.findAll();
    }

    /**
     * {@code GET  /torres/:id} : get the "id" torre.
     *
     * @param id the id of the torre to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the torre, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/torres/{id}")
    public ResponseEntity<Torre> getTorre(@PathVariable Long id) {
        log.debug("REST request to get Torre : {}", id);
        Optional<Torre> torre = torreRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(torre);
    }

    /**
     * {@code DELETE  /torres/:id} : delete the "id" torre.
     *
     * @param id the id of the torre to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/torres/{id}")
    public ResponseEntity<Void> deleteTorre(@PathVariable Long id) {
        log.debug("REST request to delete Torre : {}", id);
        torreRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
