package co.edu.sena.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import co.edu.sena.IntegrationTest;
import co.edu.sena.domain.Qualification;
import co.edu.sena.domain.enumeration.Designation;
import co.edu.sena.domain.enumeration.Designation;
import co.edu.sena.domain.enumeration.Designation;
import co.edu.sena.domain.enumeration.Designation;
import co.edu.sena.domain.enumeration.Designation;
import co.edu.sena.repository.QualificationRepository;
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
 * Integration tests for the {@link QualificationResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class QualificationResourceIT {

    private static final Designation DEFAULT_QUALIF_1 = Designation.One;
    private static final Designation UPDATED_QUALIF_1 = Designation.Two;

    private static final Designation DEFAULT_QUALIF_2 = Designation.One;
    private static final Designation UPDATED_QUALIF_2 = Designation.Two;

    private static final Designation DEFAULT_QUALIF_3 = Designation.One;
    private static final Designation UPDATED_QUALIF_3 = Designation.Two;

    private static final Designation DEFAULT_QUALIF_4 = Designation.One;
    private static final Designation UPDATED_QUALIF_4 = Designation.Two;

    private static final Designation DEFAULT_QUALIF_1_BUSSINESS_VIABILITY = Designation.One;
    private static final Designation UPDATED_QUALIF_1_BUSSINESS_VIABILITY = Designation.Two;

    private static final String ENTITY_API_URL = "/api/qualifications";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private QualificationRepository qualificationRepository;

    @Mock
    private QualificationRepository qualificationRepositoryMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restQualificationMockMvc;

    private Qualification qualification;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Qualification createEntity(EntityManager em) {
        Qualification qualification = new Qualification()
            .qualif1(DEFAULT_QUALIF_1)
            .qualif2(DEFAULT_QUALIF_2)
            .qualif3(DEFAULT_QUALIF_3)
            .qualif4(DEFAULT_QUALIF_4)
            .qualif1BussinessViability(DEFAULT_QUALIF_1_BUSSINESS_VIABILITY);
        return qualification;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Qualification createUpdatedEntity(EntityManager em) {
        Qualification qualification = new Qualification()
            .qualif1(UPDATED_QUALIF_1)
            .qualif2(UPDATED_QUALIF_2)
            .qualif3(UPDATED_QUALIF_3)
            .qualif4(UPDATED_QUALIF_4)
            .qualif1BussinessViability(UPDATED_QUALIF_1_BUSSINESS_VIABILITY);
        return qualification;
    }

    @BeforeEach
    public void initTest() {
        qualification = createEntity(em);
    }

    @Test
    @Transactional
    void createQualification() throws Exception {
        int databaseSizeBeforeCreate = qualificationRepository.findAll().size();
        // Create the Qualification
        restQualificationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(qualification)))
            .andExpect(status().isCreated());

        // Validate the Qualification in the database
        List<Qualification> qualificationList = qualificationRepository.findAll();
        assertThat(qualificationList).hasSize(databaseSizeBeforeCreate + 1);
        Qualification testQualification = qualificationList.get(qualificationList.size() - 1);
        assertThat(testQualification.getQualif1()).isEqualTo(DEFAULT_QUALIF_1);
        assertThat(testQualification.getQualif2()).isEqualTo(DEFAULT_QUALIF_2);
        assertThat(testQualification.getQualif3()).isEqualTo(DEFAULT_QUALIF_3);
        assertThat(testQualification.getQualif4()).isEqualTo(DEFAULT_QUALIF_4);
        assertThat(testQualification.getQualif1BussinessViability()).isEqualTo(DEFAULT_QUALIF_1_BUSSINESS_VIABILITY);
    }

    @Test
    @Transactional
    void createQualificationWithExistingId() throws Exception {
        // Create the Qualification with an existing ID
        qualification.setId(1L);

        int databaseSizeBeforeCreate = qualificationRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restQualificationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(qualification)))
            .andExpect(status().isBadRequest());

        // Validate the Qualification in the database
        List<Qualification> qualificationList = qualificationRepository.findAll();
        assertThat(qualificationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkQualif1IsRequired() throws Exception {
        int databaseSizeBeforeTest = qualificationRepository.findAll().size();
        // set the field null
        qualification.setQualif1(null);

        // Create the Qualification, which fails.

        restQualificationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(qualification)))
            .andExpect(status().isBadRequest());

        List<Qualification> qualificationList = qualificationRepository.findAll();
        assertThat(qualificationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkQualif2IsRequired() throws Exception {
        int databaseSizeBeforeTest = qualificationRepository.findAll().size();
        // set the field null
        qualification.setQualif2(null);

        // Create the Qualification, which fails.

        restQualificationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(qualification)))
            .andExpect(status().isBadRequest());

        List<Qualification> qualificationList = qualificationRepository.findAll();
        assertThat(qualificationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkQualif3IsRequired() throws Exception {
        int databaseSizeBeforeTest = qualificationRepository.findAll().size();
        // set the field null
        qualification.setQualif3(null);

        // Create the Qualification, which fails.

        restQualificationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(qualification)))
            .andExpect(status().isBadRequest());

        List<Qualification> qualificationList = qualificationRepository.findAll();
        assertThat(qualificationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkQualif4IsRequired() throws Exception {
        int databaseSizeBeforeTest = qualificationRepository.findAll().size();
        // set the field null
        qualification.setQualif4(null);

        // Create the Qualification, which fails.

        restQualificationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(qualification)))
            .andExpect(status().isBadRequest());

        List<Qualification> qualificationList = qualificationRepository.findAll();
        assertThat(qualificationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkQualif1BussinessViabilityIsRequired() throws Exception {
        int databaseSizeBeforeTest = qualificationRepository.findAll().size();
        // set the field null
        qualification.setQualif1BussinessViability(null);

        // Create the Qualification, which fails.

        restQualificationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(qualification)))
            .andExpect(status().isBadRequest());

        List<Qualification> qualificationList = qualificationRepository.findAll();
        assertThat(qualificationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllQualifications() throws Exception {
        // Initialize the database
        qualificationRepository.saveAndFlush(qualification);

        // Get all the qualificationList
        restQualificationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(qualification.getId().intValue())))
            .andExpect(jsonPath("$.[*].qualif1").value(hasItem(DEFAULT_QUALIF_1.toString())))
            .andExpect(jsonPath("$.[*].qualif2").value(hasItem(DEFAULT_QUALIF_2.toString())))
            .andExpect(jsonPath("$.[*].qualif3").value(hasItem(DEFAULT_QUALIF_3.toString())))
            .andExpect(jsonPath("$.[*].qualif4").value(hasItem(DEFAULT_QUALIF_4.toString())))
            .andExpect(jsonPath("$.[*].qualif1BussinessViability").value(hasItem(DEFAULT_QUALIF_1_BUSSINESS_VIABILITY.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllQualificationsWithEagerRelationshipsIsEnabled() throws Exception {
        when(qualificationRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restQualificationMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(qualificationRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllQualificationsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(qualificationRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restQualificationMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(qualificationRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    void getQualification() throws Exception {
        // Initialize the database
        qualificationRepository.saveAndFlush(qualification);

        // Get the qualification
        restQualificationMockMvc
            .perform(get(ENTITY_API_URL_ID, qualification.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(qualification.getId().intValue()))
            .andExpect(jsonPath("$.qualif1").value(DEFAULT_QUALIF_1.toString()))
            .andExpect(jsonPath("$.qualif2").value(DEFAULT_QUALIF_2.toString()))
            .andExpect(jsonPath("$.qualif3").value(DEFAULT_QUALIF_3.toString()))
            .andExpect(jsonPath("$.qualif4").value(DEFAULT_QUALIF_4.toString()))
            .andExpect(jsonPath("$.qualif1BussinessViability").value(DEFAULT_QUALIF_1_BUSSINESS_VIABILITY.toString()));
    }

    @Test
    @Transactional
    void getNonExistingQualification() throws Exception {
        // Get the qualification
        restQualificationMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewQualification() throws Exception {
        // Initialize the database
        qualificationRepository.saveAndFlush(qualification);

        int databaseSizeBeforeUpdate = qualificationRepository.findAll().size();

        // Update the qualification
        Qualification updatedQualification = qualificationRepository.findById(qualification.getId()).get();
        // Disconnect from session so that the updates on updatedQualification are not directly saved in db
        em.detach(updatedQualification);
        updatedQualification
            .qualif1(UPDATED_QUALIF_1)
            .qualif2(UPDATED_QUALIF_2)
            .qualif3(UPDATED_QUALIF_3)
            .qualif4(UPDATED_QUALIF_4)
            .qualif1BussinessViability(UPDATED_QUALIF_1_BUSSINESS_VIABILITY);

        restQualificationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedQualification.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedQualification))
            )
            .andExpect(status().isOk());

        // Validate the Qualification in the database
        List<Qualification> qualificationList = qualificationRepository.findAll();
        assertThat(qualificationList).hasSize(databaseSizeBeforeUpdate);
        Qualification testQualification = qualificationList.get(qualificationList.size() - 1);
        assertThat(testQualification.getQualif1()).isEqualTo(UPDATED_QUALIF_1);
        assertThat(testQualification.getQualif2()).isEqualTo(UPDATED_QUALIF_2);
        assertThat(testQualification.getQualif3()).isEqualTo(UPDATED_QUALIF_3);
        assertThat(testQualification.getQualif4()).isEqualTo(UPDATED_QUALIF_4);
        assertThat(testQualification.getQualif1BussinessViability()).isEqualTo(UPDATED_QUALIF_1_BUSSINESS_VIABILITY);
    }

    @Test
    @Transactional
    void putNonExistingQualification() throws Exception {
        int databaseSizeBeforeUpdate = qualificationRepository.findAll().size();
        qualification.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restQualificationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, qualification.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(qualification))
            )
            .andExpect(status().isBadRequest());

        // Validate the Qualification in the database
        List<Qualification> qualificationList = qualificationRepository.findAll();
        assertThat(qualificationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchQualification() throws Exception {
        int databaseSizeBeforeUpdate = qualificationRepository.findAll().size();
        qualification.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQualificationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(qualification))
            )
            .andExpect(status().isBadRequest());

        // Validate the Qualification in the database
        List<Qualification> qualificationList = qualificationRepository.findAll();
        assertThat(qualificationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamQualification() throws Exception {
        int databaseSizeBeforeUpdate = qualificationRepository.findAll().size();
        qualification.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQualificationMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(qualification)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Qualification in the database
        List<Qualification> qualificationList = qualificationRepository.findAll();
        assertThat(qualificationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateQualificationWithPatch() throws Exception {
        // Initialize the database
        qualificationRepository.saveAndFlush(qualification);

        int databaseSizeBeforeUpdate = qualificationRepository.findAll().size();

        // Update the qualification using partial update
        Qualification partialUpdatedQualification = new Qualification();
        partialUpdatedQualification.setId(qualification.getId());

        partialUpdatedQualification
            .qualif2(UPDATED_QUALIF_2)
            .qualif4(UPDATED_QUALIF_4)
            .qualif1BussinessViability(UPDATED_QUALIF_1_BUSSINESS_VIABILITY);

        restQualificationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedQualification.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedQualification))
            )
            .andExpect(status().isOk());

        // Validate the Qualification in the database
        List<Qualification> qualificationList = qualificationRepository.findAll();
        assertThat(qualificationList).hasSize(databaseSizeBeforeUpdate);
        Qualification testQualification = qualificationList.get(qualificationList.size() - 1);
        assertThat(testQualification.getQualif1()).isEqualTo(DEFAULT_QUALIF_1);
        assertThat(testQualification.getQualif2()).isEqualTo(UPDATED_QUALIF_2);
        assertThat(testQualification.getQualif3()).isEqualTo(DEFAULT_QUALIF_3);
        assertThat(testQualification.getQualif4()).isEqualTo(UPDATED_QUALIF_4);
        assertThat(testQualification.getQualif1BussinessViability()).isEqualTo(UPDATED_QUALIF_1_BUSSINESS_VIABILITY);
    }

    @Test
    @Transactional
    void fullUpdateQualificationWithPatch() throws Exception {
        // Initialize the database
        qualificationRepository.saveAndFlush(qualification);

        int databaseSizeBeforeUpdate = qualificationRepository.findAll().size();

        // Update the qualification using partial update
        Qualification partialUpdatedQualification = new Qualification();
        partialUpdatedQualification.setId(qualification.getId());

        partialUpdatedQualification
            .qualif1(UPDATED_QUALIF_1)
            .qualif2(UPDATED_QUALIF_2)
            .qualif3(UPDATED_QUALIF_3)
            .qualif4(UPDATED_QUALIF_4)
            .qualif1BussinessViability(UPDATED_QUALIF_1_BUSSINESS_VIABILITY);

        restQualificationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedQualification.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedQualification))
            )
            .andExpect(status().isOk());

        // Validate the Qualification in the database
        List<Qualification> qualificationList = qualificationRepository.findAll();
        assertThat(qualificationList).hasSize(databaseSizeBeforeUpdate);
        Qualification testQualification = qualificationList.get(qualificationList.size() - 1);
        assertThat(testQualification.getQualif1()).isEqualTo(UPDATED_QUALIF_1);
        assertThat(testQualification.getQualif2()).isEqualTo(UPDATED_QUALIF_2);
        assertThat(testQualification.getQualif3()).isEqualTo(UPDATED_QUALIF_3);
        assertThat(testQualification.getQualif4()).isEqualTo(UPDATED_QUALIF_4);
        assertThat(testQualification.getQualif1BussinessViability()).isEqualTo(UPDATED_QUALIF_1_BUSSINESS_VIABILITY);
    }

    @Test
    @Transactional
    void patchNonExistingQualification() throws Exception {
        int databaseSizeBeforeUpdate = qualificationRepository.findAll().size();
        qualification.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restQualificationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, qualification.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(qualification))
            )
            .andExpect(status().isBadRequest());

        // Validate the Qualification in the database
        List<Qualification> qualificationList = qualificationRepository.findAll();
        assertThat(qualificationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchQualification() throws Exception {
        int databaseSizeBeforeUpdate = qualificationRepository.findAll().size();
        qualification.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQualificationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(qualification))
            )
            .andExpect(status().isBadRequest());

        // Validate the Qualification in the database
        List<Qualification> qualificationList = qualificationRepository.findAll();
        assertThat(qualificationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamQualification() throws Exception {
        int databaseSizeBeforeUpdate = qualificationRepository.findAll().size();
        qualification.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQualificationMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(qualification))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Qualification in the database
        List<Qualification> qualificationList = qualificationRepository.findAll();
        assertThat(qualificationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteQualification() throws Exception {
        // Initialize the database
        qualificationRepository.saveAndFlush(qualification);

        int databaseSizeBeforeDelete = qualificationRepository.findAll().size();

        // Delete the qualification
        restQualificationMockMvc
            .perform(delete(ENTITY_API_URL_ID, qualification.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Qualification> qualificationList = qualificationRepository.findAll();
        assertThat(qualificationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
