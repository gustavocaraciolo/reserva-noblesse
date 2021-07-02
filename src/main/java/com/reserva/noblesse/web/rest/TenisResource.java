package com.reserva.noblesse.web.rest;

import com.reserva.noblesse.domain.Tenis;
import com.reserva.noblesse.repository.TenisRepository;
import com.reserva.noblesse.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.reserva.noblesse.domain.Tenis}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class TenisResource {

    private final Logger log = LoggerFactory.getLogger(TenisResource.class);

    private static final String ENTITY_NAME = "tenis";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TenisRepository tenisRepository;

    public TenisResource(TenisRepository tenisRepository) {
        this.tenisRepository = tenisRepository;
    }

    /**
     * {@code POST  /tenis} : Create a new tenis.
     *
     * @param tenis the tenis to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tenis, or with status {@code 400 (Bad Request)} if the tenis has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tenis")
    public ResponseEntity<Tenis> createTenis(@Valid @RequestBody Tenis tenis) throws URISyntaxException {
        log.debug("REST request to save Tenis : {}", tenis);
        if (tenis.getId() != null) {
            throw new BadRequestAlertException("A new tenis cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Tenis result = tenisRepository.save(tenis);
        return ResponseEntity
            .created(new URI("/api/tenis/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tenis/:id} : Updates an existing tenis.
     *
     * @param id the id of the tenis to save.
     * @param tenis the tenis to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tenis,
     * or with status {@code 400 (Bad Request)} if the tenis is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tenis couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tenis/{id}")
    public ResponseEntity<Tenis> updateTenis(@PathVariable(value = "id", required = false) final Long id, @Valid @RequestBody Tenis tenis)
        throws URISyntaxException {
        log.debug("REST request to update Tenis : {}, {}", id, tenis);
        if (tenis.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tenis.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tenisRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Tenis result = tenisRepository.save(tenis);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tenis.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /tenis/:id} : Partial updates given fields of an existing tenis, field will ignore if it is null
     *
     * @param id the id of the tenis to save.
     * @param tenis the tenis to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tenis,
     * or with status {@code 400 (Bad Request)} if the tenis is not valid,
     * or with status {@code 404 (Not Found)} if the tenis is not found,
     * or with status {@code 500 (Internal Server Error)} if the tenis couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/tenis/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<Tenis> partialUpdateTenis(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Tenis tenis
    ) throws URISyntaxException {
        log.debug("REST request to partial update Tenis partially : {}, {}", id, tenis);
        if (tenis.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tenis.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tenisRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Tenis> result = tenisRepository
            .findById(tenis.getId())
            .map(
                existingTenis -> {
                    if (tenis.getDate() != null) {
                        existingTenis.setDate(tenis.getDate());
                    }
                    if (tenis.getNotes() != null) {
                        existingTenis.setNotes(tenis.getNotes());
                    }

                    return existingTenis;
                }
            )
            .map(tenisRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tenis.getId().toString())
        );
    }

    /**
     * {@code GET  /tenis} : get all the tenis.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tenis in body.
     */
    @GetMapping("/tenis")
    public ResponseEntity<List<Tenis>> getAllTenis(Pageable pageable) {
        log.debug("REST request to get a page of Tenis");
        Page<Tenis> page = tenisRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /tenis/:id} : get the "id" tenis.
     *
     * @param id the id of the tenis to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tenis, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tenis/{id}")
    public ResponseEntity<Tenis> getTenis(@PathVariable Long id) {
        log.debug("REST request to get Tenis : {}", id);
        Optional<Tenis> tenis = tenisRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(tenis);
    }

    /**
     * {@code DELETE  /tenis/:id} : delete the "id" tenis.
     *
     * @param id the id of the tenis to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tenis/{id}")
    public ResponseEntity<Void> deleteTenis(@PathVariable Long id) {
        log.debug("REST request to delete Tenis : {}", id);
        tenisRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
