package co.edu.sena.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import co.edu.sena.IntegrationTest;
import co.edu.sena.domain.CompanyType;
import co.edu.sena.repository.CompanyTypeRepository;
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
 * Integration tests for the {@link CompanyTypeResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class CompanyTypeResourceIT {

    private static final Boolean DEFAULT_PRIMARY_SECTOR = false;
    private static final Boolean UPDATED_PRIMARY_SECTOR = true;

    private static final Boolean DEFAULT_SECONDARY_SECTOR = false;
    private static final Boolean UPDATED_SECONDARY_SECTOR = true;

    private static final Boolean DEFAULT_TERTIARY_SECTOR = false;
    private static final Boolean UPDATED_TERTIARY_SECTOR = true;

    private static final String ENTITY_API_URL = "/api/company-types";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CompanyTypeRepository companyTypeRepository;

    @Mock
    private CompanyTypeRepository companyTypeRepositoryMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCompanyTypeMockMvc;

    private CompanyType companyType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CompanyType createEntity(EntityManager em) {
        CompanyType companyType = new CompanyType()
            .primarySector(DEFAULT_PRIMARY_SECTOR)
            .secondarySector(DEFAULT_SECONDARY_SECTOR)
            .tertiarySector(DEFAULT_TERTIARY_SECTOR);
        return companyType;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CompanyType createUpdatedEntity(EntityManager em) {
        CompanyType companyType = new CompanyType()
            .primarySector(UPDATED_PRIMARY_SECTOR)
            .secondarySector(UPDATED_SECONDARY_SECTOR)
            .tertiarySector(UPDATED_TERTIARY_SECTOR);
        return companyType;
    }

    @BeforeEach
    public void initTest() {
        companyType = createEntity(em);
    }

    @Test
    @Transactional
    void createCompanyType() throws Exception {
        int databaseSizeBeforeCreate = companyTypeRepository.findAll().size();
        // Create the CompanyType
        restCompanyTypeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(companyType)))
            .andExpect(status().isCreated());

        // Validate the CompanyType in the database
        List<CompanyType> companyTypeList = companyTypeRepository.findAll();
        assertThat(companyTypeList).hasSize(databaseSizeBeforeCreate + 1);
        CompanyType testCompanyType = companyTypeList.get(companyTypeList.size() - 1);
        assertThat(testCompanyType.getPrimarySector()).isEqualTo(DEFAULT_PRIMARY_SECTOR);
        assertThat(testCompanyType.getSecondarySector()).isEqualTo(DEFAULT_SECONDARY_SECTOR);
        assertThat(testCompanyType.getTertiarySector()).isEqualTo(DEFAULT_TERTIARY_SECTOR);
    }

    @Test
    @Transactional
    void createCompanyTypeWithExistingId() throws Exception {
        // Create the CompanyType with an existing ID
        companyType.setId(1L);

        int databaseSizeBeforeCreate = companyTypeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCompanyTypeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(companyType)))
            .andExpect(status().isBadRequest());

        // Validate the CompanyType in the database
        List<CompanyType> companyTypeList = companyTypeRepository.findAll();
        assertThat(companyTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCompanyTypes() throws Exception {
        // Initialize the database
        companyTypeRepository.saveAndFlush(companyType);

        // Get all the companyTypeList
        restCompanyTypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(companyType.getId().intValue())))
            .andExpect(jsonPath("$.[*].primarySector").value(hasItem(DEFAULT_PRIMARY_SECTOR.booleanValue())))
            .andExpect(jsonPath("$.[*].secondarySector").value(hasItem(DEFAULT_SECONDARY_SECTOR.booleanValue())))
            .andExpect(jsonPath("$.[*].tertiarySector").value(hasItem(DEFAULT_TERTIARY_SECTOR.booleanValue())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllCompanyTypesWithEagerRelationshipsIsEnabled() throws Exception {
        when(companyTypeRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restCompanyTypeMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(companyTypeRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllCompanyTypesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(companyTypeRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restCompanyTypeMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(companyTypeRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    void getCompanyType() throws Exception {
        // Initialize the database
        companyTypeRepository.saveAndFlush(companyType);

        // Get the companyType
        restCompanyTypeMockMvc
            .perform(get(ENTITY_API_URL_ID, companyType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(companyType.getId().intValue()))
            .andExpect(jsonPath("$.primarySector").value(DEFAULT_PRIMARY_SECTOR.booleanValue()))
            .andExpect(jsonPath("$.secondarySector").value(DEFAULT_SECONDARY_SECTOR.booleanValue()))
            .andExpect(jsonPath("$.tertiarySector").value(DEFAULT_TERTIARY_SECTOR.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingCompanyType() throws Exception {
        // Get the companyType
        restCompanyTypeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCompanyType() throws Exception {
        // Initialize the database
        companyTypeRepository.saveAndFlush(companyType);

        int databaseSizeBeforeUpdate = companyTypeRepository.findAll().size();

        // Update the companyType
        CompanyType updatedCompanyType = companyTypeRepository.findById(companyType.getId()).get();
        // Disconnect from session so that the updates on updatedCompanyType are not directly saved in db
        em.detach(updatedCompanyType);
        updatedCompanyType
            .primarySector(UPDATED_PRIMARY_SECTOR)
            .secondarySector(UPDATED_SECONDARY_SECTOR)
            .tertiarySector(UPDATED_TERTIARY_SECTOR);

        restCompanyTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCompanyType.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedCompanyType))
            )
            .andExpect(status().isOk());

        // Validate the CompanyType in the database
        List<CompanyType> companyTypeList = companyTypeRepository.findAll();
        assertThat(companyTypeList).hasSize(databaseSizeBeforeUpdate);
        CompanyType testCompanyType = companyTypeList.get(companyTypeList.size() - 1);
        assertThat(testCompanyType.getPrimarySector()).isEqualTo(UPDATED_PRIMARY_SECTOR);
        assertThat(testCompanyType.getSecondarySector()).isEqualTo(UPDATED_SECONDARY_SECTOR);
        assertThat(testCompanyType.getTertiarySector()).isEqualTo(UPDATED_TERTIARY_SECTOR);
    }

    @Test
    @Transactional
    void putNonExistingCompanyType() throws Exception {
        int databaseSizeBeforeUpdate = companyTypeRepository.findAll().size();
        companyType.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCompanyTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, companyType.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(companyType))
            )
            .andExpect(status().isBadRequest());

        // Validate the CompanyType in the database
        List<CompanyType> companyTypeList = companyTypeRepository.findAll();
        assertThat(companyTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCompanyType() throws Exception {
        int databaseSizeBeforeUpdate = companyTypeRepository.findAll().size();
        companyType.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCompanyTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(companyType))
            )
            .andExpect(status().isBadRequest());

        // Validate the CompanyType in the database
        List<CompanyType> companyTypeList = companyTypeRepository.findAll();
        assertThat(companyTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCompanyType() throws Exception {
        int databaseSizeBeforeUpdate = companyTypeRepository.findAll().size();
        companyType.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCompanyTypeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(companyType)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CompanyType in the database
        List<CompanyType> companyTypeList = companyTypeRepository.findAll();
        assertThat(companyTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCompanyTypeWithPatch() throws Exception {
        // Initialize the database
        companyTypeRepository.saveAndFlush(companyType);

        int databaseSizeBeforeUpdate = companyTypeRepository.findAll().size();

        // Update the companyType using partial update
        CompanyType partialUpdatedCompanyType = new CompanyType();
        partialUpdatedCompanyType.setId(companyType.getId());

        restCompanyTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCompanyType.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCompanyType))
            )
            .andExpect(status().isOk());

        // Validate the CompanyType in the database
        List<CompanyType> companyTypeList = companyTypeRepository.findAll();
        assertThat(companyTypeList).hasSize(databaseSizeBeforeUpdate);
        CompanyType testCompanyType = companyTypeList.get(companyTypeList.size() - 1);
        assertThat(testCompanyType.getPrimarySector()).isEqualTo(DEFAULT_PRIMARY_SECTOR);
        assertThat(testCompanyType.getSecondarySector()).isEqualTo(DEFAULT_SECONDARY_SECTOR);
        assertThat(testCompanyType.getTertiarySector()).isEqualTo(DEFAULT_TERTIARY_SECTOR);
    }

    @Test
    @Transactional
    void fullUpdateCompanyTypeWithPatch() throws Exception {
        // Initialize the database
        companyTypeRepository.saveAndFlush(companyType);

        int databaseSizeBeforeUpdate = companyTypeRepository.findAll().size();

        // Update the companyType using partial update
        CompanyType partialUpdatedCompanyType = new CompanyType();
        partialUpdatedCompanyType.setId(companyType.getId());

        partialUpdatedCompanyType
            .primarySector(UPDATED_PRIMARY_SECTOR)
            .secondarySector(UPDATED_SECONDARY_SECTOR)
            .tertiarySector(UPDATED_TERTIARY_SECTOR);

        restCompanyTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCompanyType.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCompanyType))
            )
            .andExpect(status().isOk());

        // Validate the CompanyType in the database
        List<CompanyType> companyTypeList = companyTypeRepository.findAll();
        assertThat(companyTypeList).hasSize(databaseSizeBeforeUpdate);
        CompanyType testCompanyType = companyTypeList.get(companyTypeList.size() - 1);
        assertThat(testCompanyType.getPrimarySector()).isEqualTo(UPDATED_PRIMARY_SECTOR);
        assertThat(testCompanyType.getSecondarySector()).isEqualTo(UPDATED_SECONDARY_SECTOR);
        assertThat(testCompanyType.getTertiarySector()).isEqualTo(UPDATED_TERTIARY_SECTOR);
    }

    @Test
    @Transactional
    void patchNonExistingCompanyType() throws Exception {
        int databaseSizeBeforeUpdate = companyTypeRepository.findAll().size();
        companyType.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCompanyTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, companyType.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(companyType))
            )
            .andExpect(status().isBadRequest());

        // Validate the CompanyType in the database
        List<CompanyType> companyTypeList = companyTypeRepository.findAll();
        assertThat(companyTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCompanyType() throws Exception {
        int databaseSizeBeforeUpdate = companyTypeRepository.findAll().size();
        companyType.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCompanyTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(companyType))
            )
            .andExpect(status().isBadRequest());

        // Validate the CompanyType in the database
        List<CompanyType> companyTypeList = companyTypeRepository.findAll();
        assertThat(companyTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCompanyType() throws Exception {
        int databaseSizeBeforeUpdate = companyTypeRepository.findAll().size();
        companyType.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCompanyTypeMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(companyType))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CompanyType in the database
        List<CompanyType> companyTypeList = companyTypeRepository.findAll();
        assertThat(companyTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCompanyType() throws Exception {
        // Initialize the database
        companyTypeRepository.saveAndFlush(companyType);

        int databaseSizeBeforeDelete = companyTypeRepository.findAll().size();

        // Delete the companyType
        restCompanyTypeMockMvc
            .perform(delete(ENTITY_API_URL_ID, companyType.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CompanyType> companyTypeList = companyTypeRepository.findAll();
        assertThat(companyTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
