package com.reserva.noblesse.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.reserva.noblesse.IntegrationTest;
import com.reserva.noblesse.domain.Torre;
import com.reserva.noblesse.repository.TorreRepository;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link TorreResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TorreResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/torres";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TorreRepository torreRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTorreMockMvc;

    private Torre torre;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Torre createEntity(EntityManager em) {
        Torre torre = new Torre().nome(DEFAULT_NOME);
        return torre;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Torre createUpdatedEntity(EntityManager em) {
        Torre torre = new Torre().nome(UPDATED_NOME);
        return torre;
    }

    @BeforeEach
    public void initTest() {
        torre = createEntity(em);
    }

    @Test
    @Transactional
    void createTorre() throws Exception {
        int databaseSizeBeforeCreate = torreRepository.findAll().size();
        // Create the Torre
        restTorreMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(torre)))
            .andExpect(status().isCreated());

        // Validate the Torre in the database
        List<Torre> torreList = torreRepository.findAll();
        assertThat(torreList).hasSize(databaseSizeBeforeCreate + 1);
        Torre testTorre = torreList.get(torreList.size() - 1);
        assertThat(testTorre.getNome()).isEqualTo(DEFAULT_NOME);
    }

    @Test
    @Transactional
    void createTorreWithExistingId() throws Exception {
        // Create the Torre with an existing ID
        torre.setId(1L);

        int databaseSizeBeforeCreate = torreRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTorreMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(torre)))
            .andExpect(status().isBadRequest());

        // Validate the Torre in the database
        List<Torre> torreList = torreRepository.findAll();
        assertThat(torreList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTorres() throws Exception {
        // Initialize the database
        torreRepository.saveAndFlush(torre);

        // Get all the torreList
        restTorreMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(torre.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)));
    }

    @Test
    @Transactional
    void getTorre() throws Exception {
        // Initialize the database
        torreRepository.saveAndFlush(torre);

        // Get the torre
        restTorreMockMvc
            .perform(get(ENTITY_API_URL_ID, torre.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(torre.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME));
    }

    @Test
    @Transactional
    void getNonExistingTorre() throws Exception {
        // Get the torre
        restTorreMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewTorre() throws Exception {
        // Initialize the database
        torreRepository.saveAndFlush(torre);

        int databaseSizeBeforeUpdate = torreRepository.findAll().size();

        // Update the torre
        Torre updatedTorre = torreRepository.findById(torre.getId()).get();
        // Disconnect from session so that the updates on updatedTorre are not directly saved in db
        em.detach(updatedTorre);
        updatedTorre.nome(UPDATED_NOME);

        restTorreMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedTorre.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedTorre))
            )
            .andExpect(status().isOk());

        // Validate the Torre in the database
        List<Torre> torreList = torreRepository.findAll();
        assertThat(torreList).hasSize(databaseSizeBeforeUpdate);
        Torre testTorre = torreList.get(torreList.size() - 1);
        assertThat(testTorre.getNome()).isEqualTo(UPDATED_NOME);
    }

    @Test
    @Transactional
    void putNonExistingTorre() throws Exception {
        int databaseSizeBeforeUpdate = torreRepository.findAll().size();
        torre.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTorreMockMvc
            .perform(
                put(ENTITY_API_URL_ID, torre.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(torre))
            )
            .andExpect(status().isBadRequest());

        // Validate the Torre in the database
        List<Torre> torreList = torreRepository.findAll();
        assertThat(torreList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTorre() throws Exception {
        int databaseSizeBeforeUpdate = torreRepository.findAll().size();
        torre.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTorreMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(torre))
            )
            .andExpect(status().isBadRequest());

        // Validate the Torre in the database
        List<Torre> torreList = torreRepository.findAll();
        assertThat(torreList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTorre() throws Exception {
        int databaseSizeBeforeUpdate = torreRepository.findAll().size();
        torre.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTorreMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(torre)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Torre in the database
        List<Torre> torreList = torreRepository.findAll();
        assertThat(torreList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTorreWithPatch() throws Exception {
        // Initialize the database
        torreRepository.saveAndFlush(torre);

        int databaseSizeBeforeUpdate = torreRepository.findAll().size();

        // Update the torre using partial update
        Torre partialUpdatedTorre = new Torre();
        partialUpdatedTorre.setId(torre.getId());

        restTorreMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTorre.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTorre))
            )
            .andExpect(status().isOk());

        // Validate the Torre in the database
        List<Torre> torreList = torreRepository.findAll();
        assertThat(torreList).hasSize(databaseSizeBeforeUpdate);
        Torre testTorre = torreList.get(torreList.size() - 1);
        assertThat(testTorre.getNome()).isEqualTo(DEFAULT_NOME);
    }

    @Test
    @Transactional
    void fullUpdateTorreWithPatch() throws Exception {
        // Initialize the database
        torreRepository.saveAndFlush(torre);

        int databaseSizeBeforeUpdate = torreRepository.findAll().size();

        // Update the torre using partial update
        Torre partialUpdatedTorre = new Torre();
        partialUpdatedTorre.setId(torre.getId());

        partialUpdatedTorre.nome(UPDATED_NOME);

        restTorreMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTorre.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTorre))
            )
            .andExpect(status().isOk());

        // Validate the Torre in the database
        List<Torre> torreList = torreRepository.findAll();
        assertThat(torreList).hasSize(databaseSizeBeforeUpdate);
        Torre testTorre = torreList.get(torreList.size() - 1);
        assertThat(testTorre.getNome()).isEqualTo(UPDATED_NOME);
    }

    @Test
    @Transactional
    void patchNonExistingTorre() throws Exception {
        int databaseSizeBeforeUpdate = torreRepository.findAll().size();
        torre.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTorreMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, torre.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(torre))
            )
            .andExpect(status().isBadRequest());

        // Validate the Torre in the database
        List<Torre> torreList = torreRepository.findAll();
        assertThat(torreList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTorre() throws Exception {
        int databaseSizeBeforeUpdate = torreRepository.findAll().size();
        torre.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTorreMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(torre))
            )
            .andExpect(status().isBadRequest());

        // Validate the Torre in the database
        List<Torre> torreList = torreRepository.findAll();
        assertThat(torreList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTorre() throws Exception {
        int databaseSizeBeforeUpdate = torreRepository.findAll().size();
        torre.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTorreMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(torre)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Torre in the database
        List<Torre> torreList = torreRepository.findAll();
        assertThat(torreList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTorre() throws Exception {
        // Initialize the database
        torreRepository.saveAndFlush(torre);

        int databaseSizeBeforeDelete = torreRepository.findAll().size();

        // Delete the torre
        restTorreMockMvc
            .perform(delete(ENTITY_API_URL_ID, torre.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Torre> torreList = torreRepository.findAll();
        assertThat(torreList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
