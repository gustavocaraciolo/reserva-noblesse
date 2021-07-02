package com.reserva.noblesse.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.reserva.noblesse.IntegrationTest;
import com.reserva.noblesse.domain.Tenis;
import com.reserva.noblesse.repository.TenisRepository;
import java.time.LocalDate;
import java.time.ZoneId;
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
 * Integration tests for the {@link TenisResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TenisResourceIT {

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_NOTES = "AAAAAAAAAA";
    private static final String UPDATED_NOTES = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/tenis";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TenisRepository tenisRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTenisMockMvc;

    private Tenis tenis;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Tenis createEntity(EntityManager em) {
        Tenis tenis = new Tenis().date(DEFAULT_DATE).notes(DEFAULT_NOTES);
        return tenis;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Tenis createUpdatedEntity(EntityManager em) {
        Tenis tenis = new Tenis().date(UPDATED_DATE).notes(UPDATED_NOTES);
        return tenis;
    }

    @BeforeEach
    public void initTest() {
        tenis = createEntity(em);
    }

    @Test
    @Transactional
    void createTenis() throws Exception {
        int databaseSizeBeforeCreate = tenisRepository.findAll().size();
        // Create the Tenis
        restTenisMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tenis)))
            .andExpect(status().isCreated());

        // Validate the Tenis in the database
        List<Tenis> tenisList = tenisRepository.findAll();
        assertThat(tenisList).hasSize(databaseSizeBeforeCreate + 1);
        Tenis testTenis = tenisList.get(tenisList.size() - 1);
        assertThat(testTenis.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testTenis.getNotes()).isEqualTo(DEFAULT_NOTES);
    }

    @Test
    @Transactional
    void createTenisWithExistingId() throws Exception {
        // Create the Tenis with an existing ID
        tenis.setId(1L);

        int databaseSizeBeforeCreate = tenisRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTenisMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tenis)))
            .andExpect(status().isBadRequest());

        // Validate the Tenis in the database
        List<Tenis> tenisList = tenisRepository.findAll();
        assertThat(tenisList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = tenisRepository.findAll().size();
        // set the field null
        tenis.setDate(null);

        // Create the Tenis, which fails.

        restTenisMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tenis)))
            .andExpect(status().isBadRequest());

        List<Tenis> tenisList = tenisRepository.findAll();
        assertThat(tenisList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllTenis() throws Exception {
        // Initialize the database
        tenisRepository.saveAndFlush(tenis);

        // Get all the tenisList
        restTenisMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tenis.getId().intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].notes").value(hasItem(DEFAULT_NOTES)));
    }

    @Test
    @Transactional
    void getTenis() throws Exception {
        // Initialize the database
        tenisRepository.saveAndFlush(tenis);

        // Get the tenis
        restTenisMockMvc
            .perform(get(ENTITY_API_URL_ID, tenis.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tenis.getId().intValue()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.notes").value(DEFAULT_NOTES));
    }

    @Test
    @Transactional
    void getNonExistingTenis() throws Exception {
        // Get the tenis
        restTenisMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewTenis() throws Exception {
        // Initialize the database
        tenisRepository.saveAndFlush(tenis);

        int databaseSizeBeforeUpdate = tenisRepository.findAll().size();

        // Update the tenis
        Tenis updatedTenis = tenisRepository.findById(tenis.getId()).get();
        // Disconnect from session so that the updates on updatedTenis are not directly saved in db
        em.detach(updatedTenis);
        updatedTenis.date(UPDATED_DATE).notes(UPDATED_NOTES);

        restTenisMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedTenis.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedTenis))
            )
            .andExpect(status().isOk());

        // Validate the Tenis in the database
        List<Tenis> tenisList = tenisRepository.findAll();
        assertThat(tenisList).hasSize(databaseSizeBeforeUpdate);
        Tenis testTenis = tenisList.get(tenisList.size() - 1);
        assertThat(testTenis.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testTenis.getNotes()).isEqualTo(UPDATED_NOTES);
    }

    @Test
    @Transactional
    void putNonExistingTenis() throws Exception {
        int databaseSizeBeforeUpdate = tenisRepository.findAll().size();
        tenis.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTenisMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tenis.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tenis))
            )
            .andExpect(status().isBadRequest());

        // Validate the Tenis in the database
        List<Tenis> tenisList = tenisRepository.findAll();
        assertThat(tenisList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTenis() throws Exception {
        int databaseSizeBeforeUpdate = tenisRepository.findAll().size();
        tenis.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTenisMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tenis))
            )
            .andExpect(status().isBadRequest());

        // Validate the Tenis in the database
        List<Tenis> tenisList = tenisRepository.findAll();
        assertThat(tenisList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTenis() throws Exception {
        int databaseSizeBeforeUpdate = tenisRepository.findAll().size();
        tenis.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTenisMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tenis)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Tenis in the database
        List<Tenis> tenisList = tenisRepository.findAll();
        assertThat(tenisList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTenisWithPatch() throws Exception {
        // Initialize the database
        tenisRepository.saveAndFlush(tenis);

        int databaseSizeBeforeUpdate = tenisRepository.findAll().size();

        // Update the tenis using partial update
        Tenis partialUpdatedTenis = new Tenis();
        partialUpdatedTenis.setId(tenis.getId());

        partialUpdatedTenis.notes(UPDATED_NOTES);

        restTenisMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTenis.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTenis))
            )
            .andExpect(status().isOk());

        // Validate the Tenis in the database
        List<Tenis> tenisList = tenisRepository.findAll();
        assertThat(tenisList).hasSize(databaseSizeBeforeUpdate);
        Tenis testTenis = tenisList.get(tenisList.size() - 1);
        assertThat(testTenis.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testTenis.getNotes()).isEqualTo(UPDATED_NOTES);
    }

    @Test
    @Transactional
    void fullUpdateTenisWithPatch() throws Exception {
        // Initialize the database
        tenisRepository.saveAndFlush(tenis);

        int databaseSizeBeforeUpdate = tenisRepository.findAll().size();

        // Update the tenis using partial update
        Tenis partialUpdatedTenis = new Tenis();
        partialUpdatedTenis.setId(tenis.getId());

        partialUpdatedTenis.date(UPDATED_DATE).notes(UPDATED_NOTES);

        restTenisMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTenis.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTenis))
            )
            .andExpect(status().isOk());

        // Validate the Tenis in the database
        List<Tenis> tenisList = tenisRepository.findAll();
        assertThat(tenisList).hasSize(databaseSizeBeforeUpdate);
        Tenis testTenis = tenisList.get(tenisList.size() - 1);
        assertThat(testTenis.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testTenis.getNotes()).isEqualTo(UPDATED_NOTES);
    }

    @Test
    @Transactional
    void patchNonExistingTenis() throws Exception {
        int databaseSizeBeforeUpdate = tenisRepository.findAll().size();
        tenis.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTenisMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, tenis.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tenis))
            )
            .andExpect(status().isBadRequest());

        // Validate the Tenis in the database
        List<Tenis> tenisList = tenisRepository.findAll();
        assertThat(tenisList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTenis() throws Exception {
        int databaseSizeBeforeUpdate = tenisRepository.findAll().size();
        tenis.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTenisMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tenis))
            )
            .andExpect(status().isBadRequest());

        // Validate the Tenis in the database
        List<Tenis> tenisList = tenisRepository.findAll();
        assertThat(tenisList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTenis() throws Exception {
        int databaseSizeBeforeUpdate = tenisRepository.findAll().size();
        tenis.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTenisMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(tenis)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Tenis in the database
        List<Tenis> tenisList = tenisRepository.findAll();
        assertThat(tenisList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTenis() throws Exception {
        // Initialize the database
        tenisRepository.saveAndFlush(tenis);

        int databaseSizeBeforeDelete = tenisRepository.findAll().size();

        // Delete the tenis
        restTenisMockMvc
            .perform(delete(ENTITY_API_URL_ID, tenis.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Tenis> tenisList = tenisRepository.findAll();
        assertThat(tenisList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
