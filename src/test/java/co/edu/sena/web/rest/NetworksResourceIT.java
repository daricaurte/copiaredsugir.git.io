package co.edu.sena.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import co.edu.sena.IntegrationTest;
import co.edu.sena.domain.Networks;
import co.edu.sena.repository.NetworksRepository;
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
 * Integration tests for the {@link NetworksResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class NetworksResourceIT {

    private static final Boolean DEFAULT_NETWORKS_3RD_SECTOR_HL = false;
    private static final Boolean UPDATED_NETWORKS_3RD_SECTOR_HL = true;

    private static final Boolean DEFAULT_ACADEMIC_NETWORKS_HL = false;
    private static final Boolean UPDATED_ACADEMIC_NETWORKS_HL = true;

    private static final Boolean DEFAULT_CUSTOMER_NETWORKS_HL = false;
    private static final Boolean UPDATED_CUSTOMER_NETWORKS_HL = true;

    private static final Boolean DEFAULT_EMPLOYEE_NETWORKS_HL = false;
    private static final Boolean UPDATED_EMPLOYEE_NETWORKS_HL = true;

    private static final Boolean DEFAULT_NETWORKS_ENT_FINAN_HL = false;
    private static final Boolean UPDATED_NETWORKS_ENT_FINAN_HL = true;

    private static final Boolean DEFAULT_STATE_NETWORKS_HL = false;
    private static final Boolean UPDATED_STATE_NETWORKS_HL = true;

    private static final Boolean DEFAULT_INTERNATIONAL_NETWORKS_HL = false;
    private static final Boolean UPDATED_INTERNATIONAL_NETWORKS_HL = true;

    private static final Boolean DEFAULT_MEDIA_NETWORKS_COMUNC_HL = false;
    private static final Boolean UPDATED_MEDIA_NETWORKS_COMUNC_HL = true;

    private static final Boolean DEFAULT_COMMUNITY_ORG_NETWORKS_HL = false;
    private static final Boolean UPDATED_COMMUNITY_ORG_NETWORKS_HL = true;

    private static final Boolean DEFAULT_REGULATORY_ORGANISMS_NETWORKS = false;
    private static final Boolean UPDATED_REGULATORY_ORGANISMS_NETWORKS = true;

    private static final Boolean DEFAULT_NETWORKS_PROVIDERS_HL = false;
    private static final Boolean UPDATED_NETWORKS_PROVIDERS_HL = true;

    private static final Boolean DEFAULT_SOCIAL_NETWORKS = false;
    private static final Boolean UPDATED_SOCIAL_NETWORKS = true;

    private static final Boolean DEFAULT_SHAREHOLDER_NETWORKS_HL = false;
    private static final Boolean UPDATED_SHAREHOLDER_NETWORKS_HL = true;

    private static final String ENTITY_API_URL = "/api/networks";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private NetworksRepository networksRepository;

    @Mock
    private NetworksRepository networksRepositoryMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restNetworksMockMvc;

    private Networks networks;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Networks createEntity(EntityManager em) {
        Networks networks = new Networks()
            .networks3rdSectorHl(DEFAULT_NETWORKS_3RD_SECTOR_HL)
            .academicNetworksHl(DEFAULT_ACADEMIC_NETWORKS_HL)
            .customerNetworksHl(DEFAULT_CUSTOMER_NETWORKS_HL)
            .employeeNetworksHl(DEFAULT_EMPLOYEE_NETWORKS_HL)
            .networksEntFinanHl(DEFAULT_NETWORKS_ENT_FINAN_HL)
            .stateNetworksHl(DEFAULT_STATE_NETWORKS_HL)
            .internationalNetworksHl(DEFAULT_INTERNATIONAL_NETWORKS_HL)
            .mediaNetworksComuncHl(DEFAULT_MEDIA_NETWORKS_COMUNC_HL)
            .communityOrgNetworksHl(DEFAULT_COMMUNITY_ORG_NETWORKS_HL)
            .regulatoryOrganismsNetworks(DEFAULT_REGULATORY_ORGANISMS_NETWORKS)
            .networksProvidersHl(DEFAULT_NETWORKS_PROVIDERS_HL)
            .socialNetworks(DEFAULT_SOCIAL_NETWORKS)
            .shareholderNetworksHl(DEFAULT_SHAREHOLDER_NETWORKS_HL);
        return networks;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Networks createUpdatedEntity(EntityManager em) {
        Networks networks = new Networks()
            .networks3rdSectorHl(UPDATED_NETWORKS_3RD_SECTOR_HL)
            .academicNetworksHl(UPDATED_ACADEMIC_NETWORKS_HL)
            .customerNetworksHl(UPDATED_CUSTOMER_NETWORKS_HL)
            .employeeNetworksHl(UPDATED_EMPLOYEE_NETWORKS_HL)
            .networksEntFinanHl(UPDATED_NETWORKS_ENT_FINAN_HL)
            .stateNetworksHl(UPDATED_STATE_NETWORKS_HL)
            .internationalNetworksHl(UPDATED_INTERNATIONAL_NETWORKS_HL)
            .mediaNetworksComuncHl(UPDATED_MEDIA_NETWORKS_COMUNC_HL)
            .communityOrgNetworksHl(UPDATED_COMMUNITY_ORG_NETWORKS_HL)
            .regulatoryOrganismsNetworks(UPDATED_REGULATORY_ORGANISMS_NETWORKS)
            .networksProvidersHl(UPDATED_NETWORKS_PROVIDERS_HL)
            .socialNetworks(UPDATED_SOCIAL_NETWORKS)
            .shareholderNetworksHl(UPDATED_SHAREHOLDER_NETWORKS_HL);
        return networks;
    }

    @BeforeEach
    public void initTest() {
        networks = createEntity(em);
    }

    @Test
    @Transactional
    void createNetworks() throws Exception {
        int databaseSizeBeforeCreate = networksRepository.findAll().size();
        // Create the Networks
        restNetworksMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(networks)))
            .andExpect(status().isCreated());

        // Validate the Networks in the database
        List<Networks> networksList = networksRepository.findAll();
        assertThat(networksList).hasSize(databaseSizeBeforeCreate + 1);
        Networks testNetworks = networksList.get(networksList.size() - 1);
        assertThat(testNetworks.getNetworks3rdSectorHl()).isEqualTo(DEFAULT_NETWORKS_3RD_SECTOR_HL);
        assertThat(testNetworks.getAcademicNetworksHl()).isEqualTo(DEFAULT_ACADEMIC_NETWORKS_HL);
        assertThat(testNetworks.getCustomerNetworksHl()).isEqualTo(DEFAULT_CUSTOMER_NETWORKS_HL);
        assertThat(testNetworks.getEmployeeNetworksHl()).isEqualTo(DEFAULT_EMPLOYEE_NETWORKS_HL);
        assertThat(testNetworks.getNetworksEntFinanHl()).isEqualTo(DEFAULT_NETWORKS_ENT_FINAN_HL);
        assertThat(testNetworks.getStateNetworksHl()).isEqualTo(DEFAULT_STATE_NETWORKS_HL);
        assertThat(testNetworks.getInternationalNetworksHl()).isEqualTo(DEFAULT_INTERNATIONAL_NETWORKS_HL);
        assertThat(testNetworks.getMediaNetworksComuncHl()).isEqualTo(DEFAULT_MEDIA_NETWORKS_COMUNC_HL);
        assertThat(testNetworks.getCommunityOrgNetworksHl()).isEqualTo(DEFAULT_COMMUNITY_ORG_NETWORKS_HL);
        assertThat(testNetworks.getRegulatoryOrganismsNetworks()).isEqualTo(DEFAULT_REGULATORY_ORGANISMS_NETWORKS);
        assertThat(testNetworks.getNetworksProvidersHl()).isEqualTo(DEFAULT_NETWORKS_PROVIDERS_HL);
        assertThat(testNetworks.getSocialNetworks()).isEqualTo(DEFAULT_SOCIAL_NETWORKS);
        assertThat(testNetworks.getShareholderNetworksHl()).isEqualTo(DEFAULT_SHAREHOLDER_NETWORKS_HL);
    }

    @Test
    @Transactional
    void createNetworksWithExistingId() throws Exception {
        // Create the Networks with an existing ID
        networks.setId(1L);

        int databaseSizeBeforeCreate = networksRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restNetworksMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(networks)))
            .andExpect(status().isBadRequest());

        // Validate the Networks in the database
        List<Networks> networksList = networksRepository.findAll();
        assertThat(networksList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNetworks3rdSectorHlIsRequired() throws Exception {
        int databaseSizeBeforeTest = networksRepository.findAll().size();
        // set the field null
        networks.setNetworks3rdSectorHl(null);

        // Create the Networks, which fails.

        restNetworksMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(networks)))
            .andExpect(status().isBadRequest());

        List<Networks> networksList = networksRepository.findAll();
        assertThat(networksList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAcademicNetworksHlIsRequired() throws Exception {
        int databaseSizeBeforeTest = networksRepository.findAll().size();
        // set the field null
        networks.setAcademicNetworksHl(null);

        // Create the Networks, which fails.

        restNetworksMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(networks)))
            .andExpect(status().isBadRequest());

        List<Networks> networksList = networksRepository.findAll();
        assertThat(networksList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCustomerNetworksHlIsRequired() throws Exception {
        int databaseSizeBeforeTest = networksRepository.findAll().size();
        // set the field null
        networks.setCustomerNetworksHl(null);

        // Create the Networks, which fails.

        restNetworksMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(networks)))
            .andExpect(status().isBadRequest());

        List<Networks> networksList = networksRepository.findAll();
        assertThat(networksList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEmployeeNetworksHlIsRequired() throws Exception {
        int databaseSizeBeforeTest = networksRepository.findAll().size();
        // set the field null
        networks.setEmployeeNetworksHl(null);

        // Create the Networks, which fails.

        restNetworksMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(networks)))
            .andExpect(status().isBadRequest());

        List<Networks> networksList = networksRepository.findAll();
        assertThat(networksList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNetworksEntFinanHlIsRequired() throws Exception {
        int databaseSizeBeforeTest = networksRepository.findAll().size();
        // set the field null
        networks.setNetworksEntFinanHl(null);

        // Create the Networks, which fails.

        restNetworksMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(networks)))
            .andExpect(status().isBadRequest());

        List<Networks> networksList = networksRepository.findAll();
        assertThat(networksList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkStateNetworksHlIsRequired() throws Exception {
        int databaseSizeBeforeTest = networksRepository.findAll().size();
        // set the field null
        networks.setStateNetworksHl(null);

        // Create the Networks, which fails.

        restNetworksMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(networks)))
            .andExpect(status().isBadRequest());

        List<Networks> networksList = networksRepository.findAll();
        assertThat(networksList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkInternationalNetworksHlIsRequired() throws Exception {
        int databaseSizeBeforeTest = networksRepository.findAll().size();
        // set the field null
        networks.setInternationalNetworksHl(null);

        // Create the Networks, which fails.

        restNetworksMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(networks)))
            .andExpect(status().isBadRequest());

        List<Networks> networksList = networksRepository.findAll();
        assertThat(networksList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMediaNetworksComuncHlIsRequired() throws Exception {
        int databaseSizeBeforeTest = networksRepository.findAll().size();
        // set the field null
        networks.setMediaNetworksComuncHl(null);

        // Create the Networks, which fails.

        restNetworksMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(networks)))
            .andExpect(status().isBadRequest());

        List<Networks> networksList = networksRepository.findAll();
        assertThat(networksList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCommunityOrgNetworksHlIsRequired() throws Exception {
        int databaseSizeBeforeTest = networksRepository.findAll().size();
        // set the field null
        networks.setCommunityOrgNetworksHl(null);

        // Create the Networks, which fails.

        restNetworksMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(networks)))
            .andExpect(status().isBadRequest());

        List<Networks> networksList = networksRepository.findAll();
        assertThat(networksList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkRegulatoryOrganismsNetworksIsRequired() throws Exception {
        int databaseSizeBeforeTest = networksRepository.findAll().size();
        // set the field null
        networks.setRegulatoryOrganismsNetworks(null);

        // Create the Networks, which fails.

        restNetworksMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(networks)))
            .andExpect(status().isBadRequest());

        List<Networks> networksList = networksRepository.findAll();
        assertThat(networksList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNetworksProvidersHlIsRequired() throws Exception {
        int databaseSizeBeforeTest = networksRepository.findAll().size();
        // set the field null
        networks.setNetworksProvidersHl(null);

        // Create the Networks, which fails.

        restNetworksMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(networks)))
            .andExpect(status().isBadRequest());

        List<Networks> networksList = networksRepository.findAll();
        assertThat(networksList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSocialNetworksIsRequired() throws Exception {
        int databaseSizeBeforeTest = networksRepository.findAll().size();
        // set the field null
        networks.setSocialNetworks(null);

        // Create the Networks, which fails.

        restNetworksMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(networks)))
            .andExpect(status().isBadRequest());

        List<Networks> networksList = networksRepository.findAll();
        assertThat(networksList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkShareholderNetworksHlIsRequired() throws Exception {
        int databaseSizeBeforeTest = networksRepository.findAll().size();
        // set the field null
        networks.setShareholderNetworksHl(null);

        // Create the Networks, which fails.

        restNetworksMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(networks)))
            .andExpect(status().isBadRequest());

        List<Networks> networksList = networksRepository.findAll();
        assertThat(networksList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllNetworks() throws Exception {
        // Initialize the database
        networksRepository.saveAndFlush(networks);

        // Get all the networksList
        restNetworksMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(networks.getId().intValue())))
            .andExpect(jsonPath("$.[*].networks3rdSectorHl").value(hasItem(DEFAULT_NETWORKS_3RD_SECTOR_HL.booleanValue())))
            .andExpect(jsonPath("$.[*].academicNetworksHl").value(hasItem(DEFAULT_ACADEMIC_NETWORKS_HL.booleanValue())))
            .andExpect(jsonPath("$.[*].customerNetworksHl").value(hasItem(DEFAULT_CUSTOMER_NETWORKS_HL.booleanValue())))
            .andExpect(jsonPath("$.[*].employeeNetworksHl").value(hasItem(DEFAULT_EMPLOYEE_NETWORKS_HL.booleanValue())))
            .andExpect(jsonPath("$.[*].networksEntFinanHl").value(hasItem(DEFAULT_NETWORKS_ENT_FINAN_HL.booleanValue())))
            .andExpect(jsonPath("$.[*].stateNetworksHl").value(hasItem(DEFAULT_STATE_NETWORKS_HL.booleanValue())))
            .andExpect(jsonPath("$.[*].internationalNetworksHl").value(hasItem(DEFAULT_INTERNATIONAL_NETWORKS_HL.booleanValue())))
            .andExpect(jsonPath("$.[*].mediaNetworksComuncHl").value(hasItem(DEFAULT_MEDIA_NETWORKS_COMUNC_HL.booleanValue())))
            .andExpect(jsonPath("$.[*].communityOrgNetworksHl").value(hasItem(DEFAULT_COMMUNITY_ORG_NETWORKS_HL.booleanValue())))
            .andExpect(jsonPath("$.[*].regulatoryOrganismsNetworks").value(hasItem(DEFAULT_REGULATORY_ORGANISMS_NETWORKS.booleanValue())))
            .andExpect(jsonPath("$.[*].networksProvidersHl").value(hasItem(DEFAULT_NETWORKS_PROVIDERS_HL.booleanValue())))
            .andExpect(jsonPath("$.[*].socialNetworks").value(hasItem(DEFAULT_SOCIAL_NETWORKS.booleanValue())))
            .andExpect(jsonPath("$.[*].shareholderNetworksHl").value(hasItem(DEFAULT_SHAREHOLDER_NETWORKS_HL.booleanValue())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllNetworksWithEagerRelationshipsIsEnabled() throws Exception {
        when(networksRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restNetworksMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(networksRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllNetworksWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(networksRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restNetworksMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(networksRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    void getNetworks() throws Exception {
        // Initialize the database
        networksRepository.saveAndFlush(networks);

        // Get the networks
        restNetworksMockMvc
            .perform(get(ENTITY_API_URL_ID, networks.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(networks.getId().intValue()))
            .andExpect(jsonPath("$.networks3rdSectorHl").value(DEFAULT_NETWORKS_3RD_SECTOR_HL.booleanValue()))
            .andExpect(jsonPath("$.academicNetworksHl").value(DEFAULT_ACADEMIC_NETWORKS_HL.booleanValue()))
            .andExpect(jsonPath("$.customerNetworksHl").value(DEFAULT_CUSTOMER_NETWORKS_HL.booleanValue()))
            .andExpect(jsonPath("$.employeeNetworksHl").value(DEFAULT_EMPLOYEE_NETWORKS_HL.booleanValue()))
            .andExpect(jsonPath("$.networksEntFinanHl").value(DEFAULT_NETWORKS_ENT_FINAN_HL.booleanValue()))
            .andExpect(jsonPath("$.stateNetworksHl").value(DEFAULT_STATE_NETWORKS_HL.booleanValue()))
            .andExpect(jsonPath("$.internationalNetworksHl").value(DEFAULT_INTERNATIONAL_NETWORKS_HL.booleanValue()))
            .andExpect(jsonPath("$.mediaNetworksComuncHl").value(DEFAULT_MEDIA_NETWORKS_COMUNC_HL.booleanValue()))
            .andExpect(jsonPath("$.communityOrgNetworksHl").value(DEFAULT_COMMUNITY_ORG_NETWORKS_HL.booleanValue()))
            .andExpect(jsonPath("$.regulatoryOrganismsNetworks").value(DEFAULT_REGULATORY_ORGANISMS_NETWORKS.booleanValue()))
            .andExpect(jsonPath("$.networksProvidersHl").value(DEFAULT_NETWORKS_PROVIDERS_HL.booleanValue()))
            .andExpect(jsonPath("$.socialNetworks").value(DEFAULT_SOCIAL_NETWORKS.booleanValue()))
            .andExpect(jsonPath("$.shareholderNetworksHl").value(DEFAULT_SHAREHOLDER_NETWORKS_HL.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingNetworks() throws Exception {
        // Get the networks
        restNetworksMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewNetworks() throws Exception {
        // Initialize the database
        networksRepository.saveAndFlush(networks);

        int databaseSizeBeforeUpdate = networksRepository.findAll().size();

        // Update the networks
        Networks updatedNetworks = networksRepository.findById(networks.getId()).get();
        // Disconnect from session so that the updates on updatedNetworks are not directly saved in db
        em.detach(updatedNetworks);
        updatedNetworks
            .networks3rdSectorHl(UPDATED_NETWORKS_3RD_SECTOR_HL)
            .academicNetworksHl(UPDATED_ACADEMIC_NETWORKS_HL)
            .customerNetworksHl(UPDATED_CUSTOMER_NETWORKS_HL)
            .employeeNetworksHl(UPDATED_EMPLOYEE_NETWORKS_HL)
            .networksEntFinanHl(UPDATED_NETWORKS_ENT_FINAN_HL)
            .stateNetworksHl(UPDATED_STATE_NETWORKS_HL)
            .internationalNetworksHl(UPDATED_INTERNATIONAL_NETWORKS_HL)
            .mediaNetworksComuncHl(UPDATED_MEDIA_NETWORKS_COMUNC_HL)
            .communityOrgNetworksHl(UPDATED_COMMUNITY_ORG_NETWORKS_HL)
            .regulatoryOrganismsNetworks(UPDATED_REGULATORY_ORGANISMS_NETWORKS)
            .networksProvidersHl(UPDATED_NETWORKS_PROVIDERS_HL)
            .socialNetworks(UPDATED_SOCIAL_NETWORKS)
            .shareholderNetworksHl(UPDATED_SHAREHOLDER_NETWORKS_HL);

        restNetworksMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedNetworks.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedNetworks))
            )
            .andExpect(status().isOk());

        // Validate the Networks in the database
        List<Networks> networksList = networksRepository.findAll();
        assertThat(networksList).hasSize(databaseSizeBeforeUpdate);
        Networks testNetworks = networksList.get(networksList.size() - 1);
        assertThat(testNetworks.getNetworks3rdSectorHl()).isEqualTo(UPDATED_NETWORKS_3RD_SECTOR_HL);
        assertThat(testNetworks.getAcademicNetworksHl()).isEqualTo(UPDATED_ACADEMIC_NETWORKS_HL);
        assertThat(testNetworks.getCustomerNetworksHl()).isEqualTo(UPDATED_CUSTOMER_NETWORKS_HL);
        assertThat(testNetworks.getEmployeeNetworksHl()).isEqualTo(UPDATED_EMPLOYEE_NETWORKS_HL);
        assertThat(testNetworks.getNetworksEntFinanHl()).isEqualTo(UPDATED_NETWORKS_ENT_FINAN_HL);
        assertThat(testNetworks.getStateNetworksHl()).isEqualTo(UPDATED_STATE_NETWORKS_HL);
        assertThat(testNetworks.getInternationalNetworksHl()).isEqualTo(UPDATED_INTERNATIONAL_NETWORKS_HL);
        assertThat(testNetworks.getMediaNetworksComuncHl()).isEqualTo(UPDATED_MEDIA_NETWORKS_COMUNC_HL);
        assertThat(testNetworks.getCommunityOrgNetworksHl()).isEqualTo(UPDATED_COMMUNITY_ORG_NETWORKS_HL);
        assertThat(testNetworks.getRegulatoryOrganismsNetworks()).isEqualTo(UPDATED_REGULATORY_ORGANISMS_NETWORKS);
        assertThat(testNetworks.getNetworksProvidersHl()).isEqualTo(UPDATED_NETWORKS_PROVIDERS_HL);
        assertThat(testNetworks.getSocialNetworks()).isEqualTo(UPDATED_SOCIAL_NETWORKS);
        assertThat(testNetworks.getShareholderNetworksHl()).isEqualTo(UPDATED_SHAREHOLDER_NETWORKS_HL);
    }

    @Test
    @Transactional
    void putNonExistingNetworks() throws Exception {
        int databaseSizeBeforeUpdate = networksRepository.findAll().size();
        networks.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNetworksMockMvc
            .perform(
                put(ENTITY_API_URL_ID, networks.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(networks))
            )
            .andExpect(status().isBadRequest());

        // Validate the Networks in the database
        List<Networks> networksList = networksRepository.findAll();
        assertThat(networksList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchNetworks() throws Exception {
        int databaseSizeBeforeUpdate = networksRepository.findAll().size();
        networks.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNetworksMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(networks))
            )
            .andExpect(status().isBadRequest());

        // Validate the Networks in the database
        List<Networks> networksList = networksRepository.findAll();
        assertThat(networksList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamNetworks() throws Exception {
        int databaseSizeBeforeUpdate = networksRepository.findAll().size();
        networks.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNetworksMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(networks)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Networks in the database
        List<Networks> networksList = networksRepository.findAll();
        assertThat(networksList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateNetworksWithPatch() throws Exception {
        // Initialize the database
        networksRepository.saveAndFlush(networks);

        int databaseSizeBeforeUpdate = networksRepository.findAll().size();

        // Update the networks using partial update
        Networks partialUpdatedNetworks = new Networks();
        partialUpdatedNetworks.setId(networks.getId());

        partialUpdatedNetworks
            .networks3rdSectorHl(UPDATED_NETWORKS_3RD_SECTOR_HL)
            .academicNetworksHl(UPDATED_ACADEMIC_NETWORKS_HL)
            .customerNetworksHl(UPDATED_CUSTOMER_NETWORKS_HL)
            .employeeNetworksHl(UPDATED_EMPLOYEE_NETWORKS_HL)
            .stateNetworksHl(UPDATED_STATE_NETWORKS_HL)
            .internationalNetworksHl(UPDATED_INTERNATIONAL_NETWORKS_HL)
            .communityOrgNetworksHl(UPDATED_COMMUNITY_ORG_NETWORKS_HL)
            .socialNetworks(UPDATED_SOCIAL_NETWORKS)
            .shareholderNetworksHl(UPDATED_SHAREHOLDER_NETWORKS_HL);

        restNetworksMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNetworks.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNetworks))
            )
            .andExpect(status().isOk());

        // Validate the Networks in the database
        List<Networks> networksList = networksRepository.findAll();
        assertThat(networksList).hasSize(databaseSizeBeforeUpdate);
        Networks testNetworks = networksList.get(networksList.size() - 1);
        assertThat(testNetworks.getNetworks3rdSectorHl()).isEqualTo(UPDATED_NETWORKS_3RD_SECTOR_HL);
        assertThat(testNetworks.getAcademicNetworksHl()).isEqualTo(UPDATED_ACADEMIC_NETWORKS_HL);
        assertThat(testNetworks.getCustomerNetworksHl()).isEqualTo(UPDATED_CUSTOMER_NETWORKS_HL);
        assertThat(testNetworks.getEmployeeNetworksHl()).isEqualTo(UPDATED_EMPLOYEE_NETWORKS_HL);
        assertThat(testNetworks.getNetworksEntFinanHl()).isEqualTo(DEFAULT_NETWORKS_ENT_FINAN_HL);
        assertThat(testNetworks.getStateNetworksHl()).isEqualTo(UPDATED_STATE_NETWORKS_HL);
        assertThat(testNetworks.getInternationalNetworksHl()).isEqualTo(UPDATED_INTERNATIONAL_NETWORKS_HL);
        assertThat(testNetworks.getMediaNetworksComuncHl()).isEqualTo(DEFAULT_MEDIA_NETWORKS_COMUNC_HL);
        assertThat(testNetworks.getCommunityOrgNetworksHl()).isEqualTo(UPDATED_COMMUNITY_ORG_NETWORKS_HL);
        assertThat(testNetworks.getRegulatoryOrganismsNetworks()).isEqualTo(DEFAULT_REGULATORY_ORGANISMS_NETWORKS);
        assertThat(testNetworks.getNetworksProvidersHl()).isEqualTo(DEFAULT_NETWORKS_PROVIDERS_HL);
        assertThat(testNetworks.getSocialNetworks()).isEqualTo(UPDATED_SOCIAL_NETWORKS);
        assertThat(testNetworks.getShareholderNetworksHl()).isEqualTo(UPDATED_SHAREHOLDER_NETWORKS_HL);
    }

    @Test
    @Transactional
    void fullUpdateNetworksWithPatch() throws Exception {
        // Initialize the database
        networksRepository.saveAndFlush(networks);

        int databaseSizeBeforeUpdate = networksRepository.findAll().size();

        // Update the networks using partial update
        Networks partialUpdatedNetworks = new Networks();
        partialUpdatedNetworks.setId(networks.getId());

        partialUpdatedNetworks
            .networks3rdSectorHl(UPDATED_NETWORKS_3RD_SECTOR_HL)
            .academicNetworksHl(UPDATED_ACADEMIC_NETWORKS_HL)
            .customerNetworksHl(UPDATED_CUSTOMER_NETWORKS_HL)
            .employeeNetworksHl(UPDATED_EMPLOYEE_NETWORKS_HL)
            .networksEntFinanHl(UPDATED_NETWORKS_ENT_FINAN_HL)
            .stateNetworksHl(UPDATED_STATE_NETWORKS_HL)
            .internationalNetworksHl(UPDATED_INTERNATIONAL_NETWORKS_HL)
            .mediaNetworksComuncHl(UPDATED_MEDIA_NETWORKS_COMUNC_HL)
            .communityOrgNetworksHl(UPDATED_COMMUNITY_ORG_NETWORKS_HL)
            .regulatoryOrganismsNetworks(UPDATED_REGULATORY_ORGANISMS_NETWORKS)
            .networksProvidersHl(UPDATED_NETWORKS_PROVIDERS_HL)
            .socialNetworks(UPDATED_SOCIAL_NETWORKS)
            .shareholderNetworksHl(UPDATED_SHAREHOLDER_NETWORKS_HL);

        restNetworksMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNetworks.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNetworks))
            )
            .andExpect(status().isOk());

        // Validate the Networks in the database
        List<Networks> networksList = networksRepository.findAll();
        assertThat(networksList).hasSize(databaseSizeBeforeUpdate);
        Networks testNetworks = networksList.get(networksList.size() - 1);
        assertThat(testNetworks.getNetworks3rdSectorHl()).isEqualTo(UPDATED_NETWORKS_3RD_SECTOR_HL);
        assertThat(testNetworks.getAcademicNetworksHl()).isEqualTo(UPDATED_ACADEMIC_NETWORKS_HL);
        assertThat(testNetworks.getCustomerNetworksHl()).isEqualTo(UPDATED_CUSTOMER_NETWORKS_HL);
        assertThat(testNetworks.getEmployeeNetworksHl()).isEqualTo(UPDATED_EMPLOYEE_NETWORKS_HL);
        assertThat(testNetworks.getNetworksEntFinanHl()).isEqualTo(UPDATED_NETWORKS_ENT_FINAN_HL);
        assertThat(testNetworks.getStateNetworksHl()).isEqualTo(UPDATED_STATE_NETWORKS_HL);
        assertThat(testNetworks.getInternationalNetworksHl()).isEqualTo(UPDATED_INTERNATIONAL_NETWORKS_HL);
        assertThat(testNetworks.getMediaNetworksComuncHl()).isEqualTo(UPDATED_MEDIA_NETWORKS_COMUNC_HL);
        assertThat(testNetworks.getCommunityOrgNetworksHl()).isEqualTo(UPDATED_COMMUNITY_ORG_NETWORKS_HL);
        assertThat(testNetworks.getRegulatoryOrganismsNetworks()).isEqualTo(UPDATED_REGULATORY_ORGANISMS_NETWORKS);
        assertThat(testNetworks.getNetworksProvidersHl()).isEqualTo(UPDATED_NETWORKS_PROVIDERS_HL);
        assertThat(testNetworks.getSocialNetworks()).isEqualTo(UPDATED_SOCIAL_NETWORKS);
        assertThat(testNetworks.getShareholderNetworksHl()).isEqualTo(UPDATED_SHAREHOLDER_NETWORKS_HL);
    }

    @Test
    @Transactional
    void patchNonExistingNetworks() throws Exception {
        int databaseSizeBeforeUpdate = networksRepository.findAll().size();
        networks.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNetworksMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, networks.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(networks))
            )
            .andExpect(status().isBadRequest());

        // Validate the Networks in the database
        List<Networks> networksList = networksRepository.findAll();
        assertThat(networksList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchNetworks() throws Exception {
        int databaseSizeBeforeUpdate = networksRepository.findAll().size();
        networks.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNetworksMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(networks))
            )
            .andExpect(status().isBadRequest());

        // Validate the Networks in the database
        List<Networks> networksList = networksRepository.findAll();
        assertThat(networksList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamNetworks() throws Exception {
        int databaseSizeBeforeUpdate = networksRepository.findAll().size();
        networks.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNetworksMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(networks)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Networks in the database
        List<Networks> networksList = networksRepository.findAll();
        assertThat(networksList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteNetworks() throws Exception {
        // Initialize the database
        networksRepository.saveAndFlush(networks);

        int databaseSizeBeforeDelete = networksRepository.findAll().size();

        // Delete the networks
        restNetworksMockMvc
            .perform(delete(ENTITY_API_URL_ID, networks.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Networks> networksList = networksRepository.findAll();
        assertThat(networksList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
