package co.edu.sena.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import co.edu.sena.IntegrationTest;
import co.edu.sena.domain.Relation;
import co.edu.sena.repository.RelationRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link RelationResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class RelationResourceIT {

    private static final Boolean DEFAULT_FAMILY_RELATION = false;
    private static final Boolean UPDATED_FAMILY_RELATION = true;

    private static final Boolean DEFAULT_WORK_RELATION = false;
    private static final Boolean UPDATED_WORK_RELATION = true;

    private static final Boolean DEFAULT_PERSONAL_RELATION = false;
    private static final Boolean UPDATED_PERSONAL_RELATION = true;

    private static final String ENTITY_API_URL = "/api/relations";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private RelationRepository relationRepository;

    @Mock
    private RelationRepository relationRepositoryMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRelationMockMvc;

    private Relation relation;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Relation createEntity(EntityManager em) {
        Relation relation = new Relation()
            .familyRelation(DEFAULT_FAMILY_RELATION)
            .workRelation(DEFAULT_WORK_RELATION)
            .personalRelation(DEFAULT_PERSONAL_RELATION);
        return relation;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Relation createUpdatedEntity(EntityManager em) {
        Relation relation = new Relation()
            .familyRelation(UPDATED_FAMILY_RELATION)
            .workRelation(UPDATED_WORK_RELATION)
            .personalRelation(UPDATED_PERSONAL_RELATION);
        return relation;
    }

    @BeforeEach
    public void initTest() {
        relation = createEntity(em);
    }

    @Test
    @Transactional
    void createRelation() throws Exception {
        int databaseSizeBeforeCreate = relationRepository.findAll().size();
        // Create the Relation
        restRelationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(relation)))
            .andExpect(status().isCreated());

        // Validate the Relation in the database
        List<Relation> relationList = relationRepository.findAll();
        assertThat(relationList).hasSize(databaseSizeBeforeCreate + 1);
        Relation testRelation = relationList.get(relationList.size() - 1);
        assertThat(testRelation.getFamilyRelation()).isEqualTo(DEFAULT_FAMILY_RELATION);
        assertThat(testRelation.getWorkRelation()).isEqualTo(DEFAULT_WORK_RELATION);
        assertThat(testRelation.getPersonalRelation()).isEqualTo(DEFAULT_PERSONAL_RELATION);
    }

    @Test
    @Transactional
    void createRelationWithExistingId() throws Exception {
        // Create the Relation with an existing ID
        relation.setId(1L);

        int databaseSizeBeforeCreate = relationRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRelationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(relation)))
            .andExpect(status().isBadRequest());

        // Validate the Relation in the database
        List<Relation> relationList = relationRepository.findAll();
        assertThat(relationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkFamilyRelationIsRequired() throws Exception {
        int databaseSizeBeforeTest = relationRepository.findAll().size();
        // set the field null
        relation.setFamilyRelation(null);

        // Create the Relation, which fails.

        restRelationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(relation)))
            .andExpect(status().isBadRequest());

        List<Relation> relationList = relationRepository.findAll();
        assertThat(relationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkWorkRelationIsRequired() throws Exception {
        int databaseSizeBeforeTest = relationRepository.findAll().size();
        // set the field null
        relation.setWorkRelation(null);

        // Create the Relation, which fails.

        restRelationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(relation)))
            .andExpect(status().isBadRequest());

        List<Relation> relationList = relationRepository.findAll();
        assertThat(relationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPersonalRelationIsRequired() throws Exception {
        int databaseSizeBeforeTest = relationRepository.findAll().size();
        // set the field null
        relation.setPersonalRelation(null);

        // Create the Relation, which fails.

        restRelationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(relation)))
            .andExpect(status().isBadRequest());

        List<Relation> relationList = relationRepository.findAll();
        assertThat(relationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllRelations() throws Exception {
        // Initialize the database
        relationRepository.saveAndFlush(relation);

        // Get all the relationList
        restRelationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(relation.getId().intValue())))
            .andExpect(jsonPath("$.[*].familyRelation").value(hasItem(DEFAULT_FAMILY_RELATION.booleanValue())))
            .andExpect(jsonPath("$.[*].workRelation").value(hasItem(DEFAULT_WORK_RELATION.booleanValue())))
            .andExpect(jsonPath("$.[*].personalRelation").value(hasItem(DEFAULT_PERSONAL_RELATION.booleanValue())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllRelationsWithEagerRelationshipsIsEnabled() throws Exception {
        when(relationRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restRelationMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(relationRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllRelationsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(relationRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restRelationMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(relationRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    void getRelation() throws Exception {
        // Initialize the database
        relationRepository.saveAndFlush(relation);

        // Get the relation
        restRelationMockMvc
            .perform(get(ENTITY_API_URL_ID, relation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(relation.getId().intValue()))
            .andExpect(jsonPath("$.familyRelation").value(DEFAULT_FAMILY_RELATION.booleanValue()))
            .andExpect(jsonPath("$.workRelation").value(DEFAULT_WORK_RELATION.booleanValue()))
            .andExpect(jsonPath("$.personalRelation").value(DEFAULT_PERSONAL_RELATION.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingRelation() throws Exception {
        // Get the relation
        restRelationMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewRelation() throws Exception {
        // Initialize the database
        relationRepository.saveAndFlush(relation);

        int databaseSizeBeforeUpdate = relationRepository.findAll().size();

        // Update the relation
        Relation updatedRelation = relationRepository.findById(relation.getId()).get();
        // Disconnect from session so that the updates on updatedRelation are not directly saved in db
        em.detach(updatedRelation);
        updatedRelation
            .familyRelation(UPDATED_FAMILY_RELATION)
            .workRelation(UPDATED_WORK_RELATION)
            .personalRelation(UPDATED_PERSONAL_RELATION);

        restRelationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedRelation.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedRelation))
            )
            .andExpect(status().isOk());

        // Validate the Relation in the database
        List<Relation> relationList = relationRepository.findAll();
        assertThat(relationList).hasSize(databaseSizeBeforeUpdate);
        Relation testRelation = relationList.get(relationList.size() - 1);
        assertThat(testRelation.getFamilyRelation()).isEqualTo(UPDATED_FAMILY_RELATION);
        assertThat(testRelation.getWorkRelation()).isEqualTo(UPDATED_WORK_RELATION);
        assertThat(testRelation.getPersonalRelation()).isEqualTo(UPDATED_PERSONAL_RELATION);
    }

    @Test
    @Transactional
    void putNonExistingRelation() throws Exception {
        int databaseSizeBeforeUpdate = relationRepository.findAll().size();
        relation.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRelationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, relation.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(relation))
            )
            .andExpect(status().isBadRequest());

        // Validate the Relation in the database
        List<Relation> relationList = relationRepository.findAll();
        assertThat(relationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRelation() throws Exception {
        int databaseSizeBeforeUpdate = relationRepository.findAll().size();
        relation.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRelationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(relation))
            )
            .andExpect(status().isBadRequest());

        // Validate the Relation in the database
        List<Relation> relationList = relationRepository.findAll();
        assertThat(relationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRelation() throws Exception {
        int databaseSizeBeforeUpdate = relationRepository.findAll().size();
        relation.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRelationMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(relation)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Relation in the database
        List<Relation> relationList = relationRepository.findAll();
        assertThat(relationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRelationWithPatch() throws Exception {
        // Initialize the database
        relationRepository.saveAndFlush(relation);

        int databaseSizeBeforeUpdate = relationRepository.findAll().size();

        // Update the relation using partial update
        Relation partialUpdatedRelation = new Relation();
        partialUpdatedRelation.setId(relation.getId());

        partialUpdatedRelation.familyRelation(UPDATED_FAMILY_RELATION).personalRelation(UPDATED_PERSONAL_RELATION);

        restRelationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRelation.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRelation))
            )
            .andExpect(status().isOk());

        // Validate the Relation in the database
        List<Relation> relationList = relationRepository.findAll();
        assertThat(relationList).hasSize(databaseSizeBeforeUpdate);
        Relation testRelation = relationList.get(relationList.size() - 1);
        assertThat(testRelation.getFamilyRelation()).isEqualTo(UPDATED_FAMILY_RELATION);
        assertThat(testRelation.getWorkRelation()).isEqualTo(DEFAULT_WORK_RELATION);
        assertThat(testRelation.getPersonalRelation()).isEqualTo(UPDATED_PERSONAL_RELATION);
    }

    @Test
    @Transactional
    void fullUpdateRelationWithPatch() throws Exception {
        // Initialize the database
        relationRepository.saveAndFlush(relation);

        int databaseSizeBeforeUpdate = relationRepository.findAll().size();

        // Update the relation using partial update
        Relation partialUpdatedRelation = new Relation();
        partialUpdatedRelation.setId(relation.getId());

        partialUpdatedRelation
            .familyRelation(UPDATED_FAMILY_RELATION)
            .workRelation(UPDATED_WORK_RELATION)
            .personalRelation(UPDATED_PERSONAL_RELATION);

        restRelationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRelation.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRelation))
            )
            .andExpect(status().isOk());

        // Validate the Relation in the database
        List<Relation> relationList = relationRepository.findAll();
        assertThat(relationList).hasSize(databaseSizeBeforeUpdate);
        Relation testRelation = relationList.get(relationList.size() - 1);
        assertThat(testRelation.getFamilyRelation()).isEqualTo(UPDATED_FAMILY_RELATION);
        assertThat(testRelation.getWorkRelation()).isEqualTo(UPDATED_WORK_RELATION);
        assertThat(testRelation.getPersonalRelation()).isEqualTo(UPDATED_PERSONAL_RELATION);
    }

    @Test
    @Transactional
    void patchNonExistingRelation() throws Exception {
        int databaseSizeBeforeUpdate = relationRepository.findAll().size();
        relation.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRelationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, relation.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(relation))
            )
            .andExpect(status().isBadRequest());

        // Validate the Relation in the database
        List<Relation> relationList = relationRepository.findAll();
        assertThat(relationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRelation() throws Exception {
        int databaseSizeBeforeUpdate = relationRepository.findAll().size();
        relation.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRelationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(relation))
            )
            .andExpect(status().isBadRequest());

        // Validate the Relation in the database
        List<Relation> relationList = relationRepository.findAll();
        assertThat(relationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRelation() throws Exception {
        int databaseSizeBeforeUpdate = relationRepository.findAll().size();
        relation.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRelationMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(relation)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Relation in the database
        List<Relation> relationList = relationRepository.findAll();
        assertThat(relationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRelation() throws Exception {
        // Initialize the database
        relationRepository.saveAndFlush(relation);

        int databaseSizeBeforeDelete = relationRepository.findAll().size();

        // Delete the relation
        restRelationMockMvc
            .perform(delete(ENTITY_API_URL_ID, relation.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Relation> relationList = relationRepository.findAll();
        assertThat(relationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
