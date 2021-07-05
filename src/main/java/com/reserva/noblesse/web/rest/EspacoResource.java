package com.reserva.noblesse.web.rest;

import com.reserva.noblesse.domain.Espaco;
import com.reserva.noblesse.repository.EspacoRepository;
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
 * REST controller for managing {@link com.reserva.noblesse.domain.Espaco}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class EspacoResource {

    private final Logger log = LoggerFactory.getLogger(EspacoResource.class);

    private static final String ENTITY_NAME = "espaco";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EspacoRepository espacoRepository;

    public EspacoResource(EspacoRepository espacoRepository) {
        this.espacoRepository = espacoRepository;
    }

    /**
     * {@code POST  /espacos} : Create a new espaco.
     *
     * @param espaco the espaco to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new espaco, or with status {@code 400 (Bad Request)} if the espaco has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/espacos")
    public ResponseEntity<Espaco> createEspaco(@Valid @RequestBody Espaco espaco) throws URISyntaxException {
        log.debug("REST request to save Espaco : {}", espaco);
        if (espaco.getId() != null) {
            throw new BadRequestAlertException("A new espaco cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Espaco result = espacoRepository.save(espaco);
        return ResponseEntity
            .created(new URI("/api/espacos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /espacos/:id} : Updates an existing espaco.
     *
     * @param id the id of the espaco to save.
     * @param espaco the espaco to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated espaco,
     * or with status {@code 400 (Bad Request)} if the espaco is not valid,
     * or with status {@code 500 (Internal Server Error)} if the espaco couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/espacos/{id}")
    public ResponseEntity<Espaco> updateEspaco(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Espaco espaco
    ) throws URISyntaxException {
        log.debug("REST request to update Espaco : {}, {}", id, espaco);
        if (espaco.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, espaco.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!espacoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Espaco result = espacoRepository.save(espaco);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, espaco.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /espacos/:id} : Partial updates given fields of an existing espaco, field will ignore if it is null
     *
     * @param id the id of the espaco to save.
     * @param espaco the espaco to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated espaco,
     * or with status {@code 400 (Bad Request)} if the espaco is not valid,
     * or with status {@code 404 (Not Found)} if the espaco is not found,
     * or with status {@code 500 (Internal Server Error)} if the espaco couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/espacos/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<Espaco> partialUpdateEspaco(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Espaco espaco
    ) throws URISyntaxException {
        log.debug("REST request to partial update Espaco partially : {}, {}", id, espaco);
        if (espaco.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, espaco.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!espacoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Espaco> result = espacoRepository
            .findById(espaco.getId())
            .map(
                existingEspaco -> {
                    if (espaco.getNome() != null) {
                        existingEspaco.setNome(espaco.getNome());
                    }

                    return existingEspaco;
                }
            )
            .map(espacoRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, espaco.getId().toString())
        );
    }

    /**
     * {@code GET  /espacos} : get all the espacos.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of espacos in body.
     */
    @GetMapping("/espacos")
    public ResponseEntity<List<Espaco>> getAllEspacos(Pageable pageable) {
        log.debug("REST request to get a page of Espacos");
        Page<Espaco> page = espacoRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /espacos/:id} : get the "id" espaco.
     *
     * @param id the id of the espaco to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the espaco, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/espacos/{id}")
    public ResponseEntity<Espaco> getEspaco(@PathVariable Long id) {
        log.debug("REST request to get Espaco : {}", id);
        Optional<Espaco> espaco = espacoRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(espaco);
    }

    /**
     * {@code DELETE  /espacos/:id} : delete the "id" espaco.
     *
     * @param id the id of the espaco to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/espacos/{id}")
    public ResponseEntity<Void> deleteEspaco(@PathVariable Long id) {
        log.debug("REST request to delete Espaco : {}", id);
        espacoRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
