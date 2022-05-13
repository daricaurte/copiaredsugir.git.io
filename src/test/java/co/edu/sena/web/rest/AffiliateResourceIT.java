package co.edu.sena.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import co.edu.sena.IntegrationTest;
import co.edu.sena.domain.Affiliate;
import co.edu.sena.domain.enumeration.Rol;
import co.edu.sena.repository.AffiliateRepository;
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
 * Integration tests for the {@link AffiliateResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class AffiliateResourceIT {

    private static final String DEFAULT_DOCUMENT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_DOCUMENT_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_SECOND_NAME = "AAAAAAAAAA";
    private static final String UPDATED_SECOND_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_FIRST_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_SECOND_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_SECOND_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_NEIGHBORHOOD = "AAAAAAAAAA";
    private static final String UPDATED_NEIGHBORHOOD = "BBBBBBBBBB";

    private static final String DEFAULT_CELL_PHONE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_CELL_PHONE_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_CITY = "AAAAAAAAAA";
    private static final String UPDATED_CITY = "BBBBBBBBBB";

    private static final String DEFAULT_DEPARTMENT = "AAAAAAAAAA";
    private static final String UPDATED_DEPARTMENT = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_CALLSIGN = "AAAA";
    private static final String UPDATED_CALLSIGN = "BBBB";

    private static final String DEFAULT_COUNTRY = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PHONE_NUMBER = "BBBBBBBBBB";

    private static final Rol DEFAULT_ROL = Rol.AFFILIATE;
    private static final Rol UPDATED_ROL = Rol.COACH;

    private static final String ENTITY_API_URL = "/api/affiliates";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AffiliateRepository affiliateRepository;

    @Mock
    private AffiliateRepository affiliateRepositoryMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAffiliateMockMvc;

    private Affiliate affiliate;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Affiliate createEntity(EntityManager em) {
        Affiliate affiliate = new Affiliate()
            .documentNumber(DEFAULT_DOCUMENT_NUMBER)
            .firstName(DEFAULT_FIRST_NAME)
            .secondName(DEFAULT_SECOND_NAME)
            .firstLastName(DEFAULT_FIRST_LAST_NAME)
            .secondLastName(DEFAULT_SECOND_LAST_NAME)
            .neighborhood(DEFAULT_NEIGHBORHOOD)
            .cellPhoneNumber(DEFAULT_CELL_PHONE_NUMBER)
            .city(DEFAULT_CITY)
            .department(DEFAULT_DEPARTMENT)
            .email(DEFAULT_EMAIL)
            .callsign(DEFAULT_CALLSIGN)
            .country(DEFAULT_COUNTRY)
            .phoneNumber(DEFAULT_PHONE_NUMBER)
            .rol(DEFAULT_ROL);
        return affiliate;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Affiliate createUpdatedEntity(EntityManager em) {
        Affiliate affiliate = new Affiliate()
            .documentNumber(UPDATED_DOCUMENT_NUMBER)
            .firstName(UPDATED_FIRST_NAME)
            .secondName(UPDATED_SECOND_NAME)
            .firstLastName(UPDATED_FIRST_LAST_NAME)
            .secondLastName(UPDATED_SECOND_LAST_NAME)
            .neighborhood(UPDATED_NEIGHBORHOOD)
            .cellPhoneNumber(UPDATED_CELL_PHONE_NUMBER)
            .city(UPDATED_CITY)
            .department(UPDATED_DEPARTMENT)
            .email(UPDATED_EMAIL)
            .callsign(UPDATED_CALLSIGN)
            .country(UPDATED_COUNTRY)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .rol(UPDATED_ROL);
        return affiliate;
    }

    @BeforeEach
    public void initTest() {
        affiliate = createEntity(em);
    }

    @Test
    @Transactional
    void createAffiliate() throws Exception {
        int databaseSizeBeforeCreate = affiliateRepository.findAll().size();
        // Create the Affiliate
        restAffiliateMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(affiliate)))
            .andExpect(status().isCreated());

        // Validate the Affiliate in the database
        List<Affiliate> affiliateList = affiliateRepository.findAll();
        assertThat(affiliateList).hasSize(databaseSizeBeforeCreate + 1);
        Affiliate testAffiliate = affiliateList.get(affiliateList.size() - 1);
        assertThat(testAffiliate.getDocumentNumber()).isEqualTo(DEFAULT_DOCUMENT_NUMBER);
        assertThat(testAffiliate.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testAffiliate.getSecondName()).isEqualTo(DEFAULT_SECOND_NAME);
        assertThat(testAffiliate.getFirstLastName()).isEqualTo(DEFAULT_FIRST_LAST_NAME);
        assertThat(testAffiliate.getSecondLastName()).isEqualTo(DEFAULT_SECOND_LAST_NAME);
        assertThat(testAffiliate.getNeighborhood()).isEqualTo(DEFAULT_NEIGHBORHOOD);
        assertThat(testAffiliate.getCellPhoneNumber()).isEqualTo(DEFAULT_CELL_PHONE_NUMBER);
        assertThat(testAffiliate.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testAffiliate.getDepartment()).isEqualTo(DEFAULT_DEPARTMENT);
        assertThat(testAffiliate.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testAffiliate.getCallsign()).isEqualTo(DEFAULT_CALLSIGN);
        assertThat(testAffiliate.getCountry()).isEqualTo(DEFAULT_COUNTRY);
        assertThat(testAffiliate.getPhoneNumber()).isEqualTo(DEFAULT_PHONE_NUMBER);
        assertThat(testAffiliate.getRol()).isEqualTo(DEFAULT_ROL);
    }

    @Test
    @Transactional
    void createAffiliateWithExistingId() throws Exception {
        // Create the Affiliate with an existing ID
        affiliate.setId(1L);

        int databaseSizeBeforeCreate = affiliateRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAffiliateMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(affiliate)))
            .andExpect(status().isBadRequest());

        // Validate the Affiliate in the database
        List<Affiliate> affiliateList = affiliateRepository.findAll();
        assertThat(affiliateList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkDocumentNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = affiliateRepository.findAll().size();
        // set the field null
        affiliate.setDocumentNumber(null);

        // Create the Affiliate, which fails.

        restAffiliateMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(affiliate)))
            .andExpect(status().isBadRequest());

        List<Affiliate> affiliateList = affiliateRepository.findAll();
        assertThat(affiliateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFirstNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = affiliateRepository.findAll().size();
        // set the field null
        affiliate.setFirstName(null);

        // Create the Affiliate, which fails.

        restAffiliateMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(affiliate)))
            .andExpect(status().isBadRequest());

        List<Affiliate> affiliateList = affiliateRepository.findAll();
        assertThat(affiliateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFirstLastNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = affiliateRepository.findAll().size();
        // set the field null
        affiliate.setFirstLastName(null);

        // Create the Affiliate, which fails.

        restAffiliateMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(affiliate)))
            .andExpect(status().isBadRequest());

        List<Affiliate> affiliateList = affiliateRepository.findAll();
        assertThat(affiliateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNeighborhoodIsRequired() throws Exception {
        int databaseSizeBeforeTest = affiliateRepository.findAll().size();
        // set the field null
        affiliate.setNeighborhood(null);

        // Create the Affiliate, which fails.

        restAffiliateMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(affiliate)))
            .andExpect(status().isBadRequest());

        List<Affiliate> affiliateList = affiliateRepository.findAll();
        assertThat(affiliateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCellPhoneNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = affiliateRepository.findAll().size();
        // set the field null
        affiliate.setCellPhoneNumber(null);

        // Create the Affiliate, which fails.

        restAffiliateMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(affiliate)))
            .andExpect(status().isBadRequest());

        List<Affiliate> affiliateList = affiliateRepository.findAll();
        assertThat(affiliateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCityIsRequired() throws Exception {
        int databaseSizeBeforeTest = affiliateRepository.findAll().size();
        // set the field null
        affiliate.setCity(null);

        // Create the Affiliate, which fails.

        restAffiliateMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(affiliate)))
            .andExpect(status().isBadRequest());

        List<Affiliate> affiliateList = affiliateRepository.findAll();
        assertThat(affiliateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDepartmentIsRequired() throws Exception {
        int databaseSizeBeforeTest = affiliateRepository.findAll().size();
        // set the field null
        affiliate.setDepartment(null);

        // Create the Affiliate, which fails.

        restAffiliateMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(affiliate)))
            .andExpect(status().isBadRequest());

        List<Affiliate> affiliateList = affiliateRepository.findAll();
        assertThat(affiliateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = affiliateRepository.findAll().size();
        // set the field null
        affiliate.setEmail(null);

        // Create the Affiliate, which fails.

        restAffiliateMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(affiliate)))
            .andExpect(status().isBadRequest());

        List<Affiliate> affiliateList = affiliateRepository.findAll();
        assertThat(affiliateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCallsignIsRequired() throws Exception {
        int databaseSizeBeforeTest = affiliateRepository.findAll().size();
        // set the field null
        affiliate.setCallsign(null);

        // Create the Affiliate, which fails.

        restAffiliateMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(affiliate)))
            .andExpect(status().isBadRequest());

        List<Affiliate> affiliateList = affiliateRepository.findAll();
        assertThat(affiliateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCountryIsRequired() throws Exception {
        int databaseSizeBeforeTest = affiliateRepository.findAll().size();
        // set the field null
        affiliate.setCountry(null);

        // Create the Affiliate, which fails.

        restAffiliateMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(affiliate)))
            .andExpect(status().isBadRequest());

        List<Affiliate> affiliateList = affiliateRepository.findAll();
        assertThat(affiliateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPhoneNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = affiliateRepository.findAll().size();
        // set the field null
        affiliate.setPhoneNumber(null);

        // Create the Affiliate, which fails.

        restAffiliateMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(affiliate)))
            .andExpect(status().isBadRequest());

        List<Affiliate> affiliateList = affiliateRepository.findAll();
        assertThat(affiliateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkRolIsRequired() throws Exception {
        int databaseSizeBeforeTest = affiliateRepository.findAll().size();
        // set the field null
        affiliate.setRol(null);

        // Create the Affiliate, which fails.

        restAffiliateMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(affiliate)))
            .andExpect(status().isBadRequest());

        List<Affiliate> affiliateList = affiliateRepository.findAll();
        assertThat(affiliateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllAffiliates() throws Exception {
        // Initialize the database
        affiliateRepository.saveAndFlush(affiliate);

        // Get all the affiliateList
        restAffiliateMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(affiliate.getId().intValue())))
            .andExpect(jsonPath("$.[*].documentNumber").value(hasItem(DEFAULT_DOCUMENT_NUMBER)))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].secondName").value(hasItem(DEFAULT_SECOND_NAME)))
            .andExpect(jsonPath("$.[*].firstLastName").value(hasItem(DEFAULT_FIRST_LAST_NAME)))
            .andExpect(jsonPath("$.[*].secondLastName").value(hasItem(DEFAULT_SECOND_LAST_NAME)))
            .andExpect(jsonPath("$.[*].neighborhood").value(hasItem(DEFAULT_NEIGHBORHOOD)))
            .andExpect(jsonPath("$.[*].cellPhoneNumber").value(hasItem(DEFAULT_CELL_PHONE_NUMBER)))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY)))
            .andExpect(jsonPath("$.[*].department").value(hasItem(DEFAULT_DEPARTMENT)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].callsign").value(hasItem(DEFAULT_CALLSIGN)))
            .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY)))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER)))
            .andExpect(jsonPath("$.[*].rol").value(hasItem(DEFAULT_ROL.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllAffiliatesWithEagerRelationshipsIsEnabled() throws Exception {
        when(affiliateRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restAffiliateMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(affiliateRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllAffiliatesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(affiliateRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restAffiliateMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(affiliateRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    void getAffiliate() throws Exception {
        // Initialize the database
        affiliateRepository.saveAndFlush(affiliate);

        // Get the affiliate
        restAffiliateMockMvc
            .perform(get(ENTITY_API_URL_ID, affiliate.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(affiliate.getId().intValue()))
            .andExpect(jsonPath("$.documentNumber").value(DEFAULT_DOCUMENT_NUMBER))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME))
            .andExpect(jsonPath("$.secondName").value(DEFAULT_SECOND_NAME))
            .andExpect(jsonPath("$.firstLastName").value(DEFAULT_FIRST_LAST_NAME))
            .andExpect(jsonPath("$.secondLastName").value(DEFAULT_SECOND_LAST_NAME))
            .andExpect(jsonPath("$.neighborhood").value(DEFAULT_NEIGHBORHOOD))
            .andExpect(jsonPath("$.cellPhoneNumber").value(DEFAULT_CELL_PHONE_NUMBER))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY))
            .andExpect(jsonPath("$.department").value(DEFAULT_DEPARTMENT))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.callsign").value(DEFAULT_CALLSIGN))
            .andExpect(jsonPath("$.country").value(DEFAULT_COUNTRY))
            .andExpect(jsonPath("$.phoneNumber").value(DEFAULT_PHONE_NUMBER))
            .andExpect(jsonPath("$.rol").value(DEFAULT_ROL.toString()));
    }

    @Test
    @Transactional
    void getNonExistingAffiliate() throws Exception {
        // Get the affiliate
        restAffiliateMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewAffiliate() throws Exception {
        // Initialize the database
        affiliateRepository.saveAndFlush(affiliate);

        int databaseSizeBeforeUpdate = affiliateRepository.findAll().size();

        // Update the affiliate
        Affiliate updatedAffiliate = affiliateRepository.findById(affiliate.getId()).get();
        // Disconnect from session so that the updates on updatedAffiliate are not directly saved in db
        em.detach(updatedAffiliate);
        updatedAffiliate
            .documentNumber(UPDATED_DOCUMENT_NUMBER)
            .firstName(UPDATED_FIRST_NAME)
            .secondName(UPDATED_SECOND_NAME)
            .firstLastName(UPDATED_FIRST_LAST_NAME)
            .secondLastName(UPDATED_SECOND_LAST_NAME)
            .neighborhood(UPDATED_NEIGHBORHOOD)
            .cellPhoneNumber(UPDATED_CELL_PHONE_NUMBER)
            .city(UPDATED_CITY)
            .department(UPDATED_DEPARTMENT)
            .email(UPDATED_EMAIL)
            .callsign(UPDATED_CALLSIGN)
            .country(UPDATED_COUNTRY)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .rol(UPDATED_ROL);

        restAffiliateMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedAffiliate.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedAffiliate))
            )
            .andExpect(status().isOk());

        // Validate the Affiliate in the database
        List<Affiliate> affiliateList = affiliateRepository.findAll();
        assertThat(affiliateList).hasSize(databaseSizeBeforeUpdate);
        Affiliate testAffiliate = affiliateList.get(affiliateList.size() - 1);
        assertThat(testAffiliate.getDocumentNumber()).isEqualTo(UPDATED_DOCUMENT_NUMBER);
        assertThat(testAffiliate.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testAffiliate.getSecondName()).isEqualTo(UPDATED_SECOND_NAME);
        assertThat(testAffiliate.getFirstLastName()).isEqualTo(UPDATED_FIRST_LAST_NAME);
        assertThat(testAffiliate.getSecondLastName()).isEqualTo(UPDATED_SECOND_LAST_NAME);
        assertThat(testAffiliate.getNeighborhood()).isEqualTo(UPDATED_NEIGHBORHOOD);
        assertThat(testAffiliate.getCellPhoneNumber()).isEqualTo(UPDATED_CELL_PHONE_NUMBER);
        assertThat(testAffiliate.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testAffiliate.getDepartment()).isEqualTo(UPDATED_DEPARTMENT);
        assertThat(testAffiliate.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testAffiliate.getCallsign()).isEqualTo(UPDATED_CALLSIGN);
        assertThat(testAffiliate.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(testAffiliate.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
        assertThat(testAffiliate.getRol()).isEqualTo(UPDATED_ROL);
    }

    @Test
    @Transactional
    void putNonExistingAffiliate() throws Exception {
        int databaseSizeBeforeUpdate = affiliateRepository.findAll().size();
        affiliate.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAffiliateMockMvc
            .perform(
                put(ENTITY_API_URL_ID, affiliate.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(affiliate))
            )
            .andExpect(status().isBadRequest());

        // Validate the Affiliate in the database
        List<Affiliate> affiliateList = affiliateRepository.findAll();
        assertThat(affiliateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAffiliate() throws Exception {
        int databaseSizeBeforeUpdate = affiliateRepository.findAll().size();
        affiliate.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAffiliateMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(affiliate))
            )
            .andExpect(status().isBadRequest());

        // Validate the Affiliate in the database
        List<Affiliate> affiliateList = affiliateRepository.findAll();
        assertThat(affiliateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAffiliate() throws Exception {
        int databaseSizeBeforeUpdate = affiliateRepository.findAll().size();
        affiliate.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAffiliateMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(affiliate)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Affiliate in the database
        List<Affiliate> affiliateList = affiliateRepository.findAll();
        assertThat(affiliateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAffiliateWithPatch() throws Exception {
        // Initialize the database
        affiliateRepository.saveAndFlush(affiliate);

        int databaseSizeBeforeUpdate = affiliateRepository.findAll().size();

        // Update the affiliate using partial update
        Affiliate partialUpdatedAffiliate = new Affiliate();
        partialUpdatedAffiliate.setId(affiliate.getId());

        partialUpdatedAffiliate
            .firstName(UPDATED_FIRST_NAME)
            .firstLastName(UPDATED_FIRST_LAST_NAME)
            .secondLastName(UPDATED_SECOND_LAST_NAME)
            .neighborhood(UPDATED_NEIGHBORHOOD)
            .department(UPDATED_DEPARTMENT)
            .email(UPDATED_EMAIL)
            .rol(UPDATED_ROL);

        restAffiliateMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAffiliate.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAffiliate))
            )
            .andExpect(status().isOk());

        // Validate the Affiliate in the database
        List<Affiliate> affiliateList = affiliateRepository.findAll();
        assertThat(affiliateList).hasSize(databaseSizeBeforeUpdate);
        Affiliate testAffiliate = affiliateList.get(affiliateList.size() - 1);
        assertThat(testAffiliate.getDocumentNumber()).isEqualTo(DEFAULT_DOCUMENT_NUMBER);
        assertThat(testAffiliate.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testAffiliate.getSecondName()).isEqualTo(DEFAULT_SECOND_NAME);
        assertThat(testAffiliate.getFirstLastName()).isEqualTo(UPDATED_FIRST_LAST_NAME);
        assertThat(testAffiliate.getSecondLastName()).isEqualTo(UPDATED_SECOND_LAST_NAME);
        assertThat(testAffiliate.getNeighborhood()).isEqualTo(UPDATED_NEIGHBORHOOD);
        assertThat(testAffiliate.getCellPhoneNumber()).isEqualTo(DEFAULT_CELL_PHONE_NUMBER);
        assertThat(testAffiliate.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testAffiliate.getDepartment()).isEqualTo(UPDATED_DEPARTMENT);
        assertThat(testAffiliate.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testAffiliate.getCallsign()).isEqualTo(DEFAULT_CALLSIGN);
        assertThat(testAffiliate.getCountry()).isEqualTo(DEFAULT_COUNTRY);
        assertThat(testAffiliate.getPhoneNumber()).isEqualTo(DEFAULT_PHONE_NUMBER);
        assertThat(testAffiliate.getRol()).isEqualTo(UPDATED_ROL);
    }

    @Test
    @Transactional
    void fullUpdateAffiliateWithPatch() throws Exception {
        // Initialize the database
        affiliateRepository.saveAndFlush(affiliate);

        int databaseSizeBeforeUpdate = affiliateRepository.findAll().size();

        // Update the affiliate using partial update
        Affiliate partialUpdatedAffiliate = new Affiliate();
        partialUpdatedAffiliate.setId(affiliate.getId());

        partialUpdatedAffiliate
            .documentNumber(UPDATED_DOCUMENT_NUMBER)
            .firstName(UPDATED_FIRST_NAME)
            .secondName(UPDATED_SECOND_NAME)
            .firstLastName(UPDATED_FIRST_LAST_NAME)
            .secondLastName(UPDATED_SECOND_LAST_NAME)
            .neighborhood(UPDATED_NEIGHBORHOOD)
            .cellPhoneNumber(UPDATED_CELL_PHONE_NUMBER)
            .city(UPDATED_CITY)
            .department(UPDATED_DEPARTMENT)
            .email(UPDATED_EMAIL)
            .callsign(UPDATED_CALLSIGN)
            .country(UPDATED_COUNTRY)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .rol(UPDATED_ROL);

        restAffiliateMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAffiliate.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAffiliate))
            )
            .andExpect(status().isOk());

        // Validate the Affiliate in the database
        List<Affiliate> affiliateList = affiliateRepository.findAll();
        assertThat(affiliateList).hasSize(databaseSizeBeforeUpdate);
        Affiliate testAffiliate = affiliateList.get(affiliateList.size() - 1);
        assertThat(testAffiliate.getDocumentNumber()).isEqualTo(UPDATED_DOCUMENT_NUMBER);
        assertThat(testAffiliate.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testAffiliate.getSecondName()).isEqualTo(UPDATED_SECOND_NAME);
        assertThat(testAffiliate.getFirstLastName()).isEqualTo(UPDATED_FIRST_LAST_NAME);
        assertThat(testAffiliate.getSecondLastName()).isEqualTo(UPDATED_SECOND_LAST_NAME);
        assertThat(testAffiliate.getNeighborhood()).isEqualTo(UPDATED_NEIGHBORHOOD);
        assertThat(testAffiliate.getCellPhoneNumber()).isEqualTo(UPDATED_CELL_PHONE_NUMBER);
        assertThat(testAffiliate.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testAffiliate.getDepartment()).isEqualTo(UPDATED_DEPARTMENT);
        assertThat(testAffiliate.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testAffiliate.getCallsign()).isEqualTo(UPDATED_CALLSIGN);
        assertThat(testAffiliate.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(testAffiliate.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
        assertThat(testAffiliate.getRol()).isEqualTo(UPDATED_ROL);
    }

    @Test
    @Transactional
    void patchNonExistingAffiliate() throws Exception {
        int databaseSizeBeforeUpdate = affiliateRepository.findAll().size();
        affiliate.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAffiliateMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, affiliate.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(affiliate))
            )
            .andExpect(status().isBadRequest());

        // Validate the Affiliate in the database
        List<Affiliate> affiliateList = affiliateRepository.findAll();
        assertThat(affiliateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAffiliate() throws Exception {
        int databaseSizeBeforeUpdate = affiliateRepository.findAll().size();
        affiliate.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAffiliateMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(affiliate))
            )
            .andExpect(status().isBadRequest());

        // Validate the Affiliate in the database
        List<Affiliate> affiliateList = affiliateRepository.findAll();
        assertThat(affiliateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAffiliate() throws Exception {
        int databaseSizeBeforeUpdate = affiliateRepository.findAll().size();
        affiliate.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAffiliateMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(affiliate))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Affiliate in the database
        List<Affiliate> affiliateList = affiliateRepository.findAll();
        assertThat(affiliateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAffiliate() throws Exception {
        // Initialize the database
        affiliateRepository.saveAndFlush(affiliate);

        int databaseSizeBeforeDelete = affiliateRepository.findAll().size();

        // Delete the affiliate
        restAffiliateMockMvc
            .perform(delete(ENTITY_API_URL_ID, affiliate.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Affiliate> affiliateList = affiliateRepository.findAll();
        assertThat(affiliateList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
