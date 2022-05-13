package co.edu.sena.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import co.edu.sena.IntegrationTest;
import co.edu.sena.domain.Potential;
import co.edu.sena.repository.PotentialRepository;
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
 * Integration tests for the {@link PotentialResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class PotentialResourceIT {

    private static final Boolean DEFAULT_EXCHANGE_PARTNER = false;
    private static final Boolean UPDATED_EXCHANGE_PARTNER = true;

    private static final Boolean DEFAULT_CONSULTANT = false;
    private static final Boolean UPDATED_CONSULTANT = true;

    private static final Boolean DEFAULT_CLIENT = false;
    private static final Boolean UPDATED_CLIENT = true;

    private static final Boolean DEFAULT_COLLABORATOR = false;
    private static final Boolean UPDATED_COLLABORATOR = true;

    private static final Boolean DEFAULT_PROVIDER = false;
    private static final Boolean UPDATED_PROVIDER = true;

    private static final Boolean DEFAULT_REFERRER = false;
    private static final Boolean UPDATED_REFERRER = true;

    private static final String ENTITY_API_URL = "/api/potentials";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PotentialRepository potentialRepository;

    @Mock
    private PotentialRepository potentialRepositoryMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPotentialMockMvc;

    private Potential potential;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Potential createEntity(EntityManager em) {
        Potential potential = new Potential()
            .exchangePartner(DEFAULT_EXCHANGE_PARTNER)
            .consultant(DEFAULT_CONSULTANT)
            .client(DEFAULT_CLIENT)
            .collaborator(DEFAULT_COLLABORATOR)
            .provider(DEFAULT_PROVIDER)
            .referrer(DEFAULT_REFERRER);
        return potential;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Potential createUpdatedEntity(EntityManager em) {
        Potential potential = new Potential()
            .exchangePartner(UPDATED_EXCHANGE_PARTNER)
            .consultant(UPDATED_CONSULTANT)
            .client(UPDATED_CLIENT)
            .collaborator(UPDATED_COLLABORATOR)
            .provider(UPDATED_PROVIDER)
            .referrer(UPDATED_REFERRER);
        return potential;
    }

    @BeforeEach
    public void initTest() {
        potential = createEntity(em);
    }

    @Test
    @Transactional
    void createPotential() throws Exception {
        int databaseSizeBeforeCreate = potentialRepository.findAll().size();
        // Create the Potential
        restPotentialMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(potential)))
            .andExpect(status().isCreated());

        // Validate the Potential in the database
        List<Potential> potentialList = potentialRepository.findAll();
        assertThat(potentialList).hasSize(databaseSizeBeforeCreate + 1);
        Potential testPotential = potentialList.get(potentialList.size() - 1);
        assertThat(testPotential.getExchangePartner()).isEqualTo(DEFAULT_EXCHANGE_PARTNER);
        assertThat(testPotential.getConsultant()).isEqualTo(DEFAULT_CONSULTANT);
        assertThat(testPotential.getClient()).isEqualTo(DEFAULT_CLIENT);
        assertThat(testPotential.getCollaborator()).isEqualTo(DEFAULT_COLLABORATOR);
        assertThat(testPotential.getProvider()).isEqualTo(DEFAULT_PROVIDER);
        assertThat(testPotential.getReferrer()).isEqualTo(DEFAULT_REFERRER);
    }

    @Test
    @Transactional
    void createPotentialWithExistingId() throws Exception {
        // Create the Potential with an existing ID
        potential.setId(1L);

        int databaseSizeBeforeCreate = potentialRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPotentialMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(potential)))
            .andExpect(status().isBadRequest());

        // Validate the Potential in the database
        List<Potential> potentialList = potentialRepository.findAll();
        assertThat(potentialList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkExchangePartnerIsRequired() throws Exception {
        int databaseSizeBeforeTest = potentialRepository.findAll().size();
        // set the field null
        potential.setExchangePartner(null);

        // Create the Potential, which fails.

        restPotentialMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(potential)))
            .andExpect(status().isBadRequest());

        List<Potential> potentialList = potentialRepository.findAll();
        assertThat(potentialList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkConsultantIsRequired() throws Exception {
        int databaseSizeBeforeTest = potentialRepository.findAll().size();
        // set the field null
        potential.setConsultant(null);

        // Create the Potential, which fails.

        restPotentialMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(potential)))
            .andExpect(status().isBadRequest());

        List<Potential> potentialList = potentialRepository.findAll();
        assertThat(potentialList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkClientIsRequired() throws Exception {
        int databaseSizeBeforeTest = potentialRepository.findAll().size();
        // set the field null
        potential.setClient(null);

        // Create the Potential, which fails.

        restPotentialMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(potential)))
            .andExpect(status().isBadRequest());

        List<Potential> potentialList = potentialRepository.findAll();
        assertThat(potentialList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCollaboratorIsRequired() throws Exception {
        int databaseSizeBeforeTest = potentialRepository.findAll().size();
        // set the field null
        potential.setCollaborator(null);

        // Create the Potential, which fails.

        restPotentialMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(potential)))
            .andExpect(status().isBadRequest());

        List<Potential> potentialList = potentialRepository.findAll();
        assertThat(potentialList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkProviderIsRequired() throws Exception {
        int databaseSizeBeforeTest = potentialRepository.findAll().size();
        // set the field null
        potential.setProvider(null);

        // Create the Potential, which fails.

        restPotentialMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(potential)))
            .andExpect(status().isBadRequest());

        List<Potential> potentialList = potentialRepository.findAll();
        assertThat(potentialList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkReferrerIsRequired() throws Exception {
        int databaseSizeBeforeTest = potentialRepository.findAll().size();
        // set the field null
        potential.setReferrer(null);

        // Create the Potential, which fails.

        restPotentialMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(potential)))
            .andExpect(status().isBadRequest());

        List<Potential> potentialList = potentialRepository.findAll();
        assertThat(potentialList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPotentials() throws Exception {
        // Initialize the database
        potentialRepository.saveAndFlush(potential);

        // Get all the potentialList
        restPotentialMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(potential.getId().intValue())))
            .andExpect(jsonPath("$.[*].exchangePartner").value(hasItem(DEFAULT_EXCHANGE_PARTNER.booleanValue())))
            .andExpect(jsonPath("$.[*].consultant").value(hasItem(DEFAULT_CONSULTANT.booleanValue())))
            .andExpect(jsonPath("$.[*].client").value(hasItem(DEFAULT_CLIENT.booleanValue())))
            .andExpect(jsonPath("$.[*].collaborator").value(hasItem(DEFAULT_COLLABORATOR.booleanValue())))
            .andExpect(jsonPath("$.[*].provider").value(hasItem(DEFAULT_PROVIDER.booleanValue())))
            .andExpect(jsonPath("$.[*].referrer").value(hasItem(DEFAULT_REFERRER.booleanValue())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllPotentialsWithEagerRelationshipsIsEnabled() throws Exception {
        when(potentialRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restPotentialMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(potentialRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllPotentialsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(potentialRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restPotentialMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(potentialRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    void getPotential() throws Exception {
        // Initialize the database
        potentialRepository.saveAndFlush(potential);

        // Get the potential
        restPotentialMockMvc
            .perform(get(ENTITY_API_URL_ID, potential.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(potential.getId().intValue()))
            .andExpect(jsonPath("$.exchangePartner").value(DEFAULT_EXCHANGE_PARTNER.booleanValue()))
            .andExpect(jsonPath("$.consultant").value(DEFAULT_CONSULTANT.booleanValue()))
            .andExpect(jsonPath("$.client").value(DEFAULT_CLIENT.booleanValue()))
            .andExpect(jsonPath("$.collaborator").value(DEFAULT_COLLABORATOR.booleanValue()))
            .andExpect(jsonPath("$.provider").value(DEFAULT_PROVIDER.booleanValue()))
            .andExpect(jsonPath("$.referrer").value(DEFAULT_REFERRER.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingPotential() throws Exception {
        // Get the potential
        restPotentialMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPotential() throws Exception {
        // Initialize the database
        potentialRepository.saveAndFlush(potential);

        int databaseSizeBeforeUpdate = potentialRepository.findAll().size();

        // Update the potential
        Potential updatedPotential = potentialRepository.findById(potential.getId()).get();
        // Disconnect from session so that the updates on updatedPotential are not directly saved in db
        em.detach(updatedPotential);
        updatedPotential
            .exchangePartner(UPDATED_EXCHANGE_PARTNER)
            .consultant(UPDATED_CONSULTANT)
            .client(UPDATED_CLIENT)
            .collaborator(UPDATED_COLLABORATOR)
            .provider(UPDATED_PROVIDER)
            .referrer(UPDATED_REFERRER);

        restPotentialMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPotential.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPotential))
            )
            .andExpect(status().isOk());

        // Validate the Potential in the database
        List<Potential> potentialList = potentialRepository.findAll();
        assertThat(potentialList).hasSize(databaseSizeBeforeUpdate);
        Potential testPotential = potentialList.get(potentialList.size() - 1);
        assertThat(testPotential.getExchangePartner()).isEqualTo(UPDATED_EXCHANGE_PARTNER);
        assertThat(testPotential.getConsultant()).isEqualTo(UPDATED_CONSULTANT);
        assertThat(testPotential.getClient()).isEqualTo(UPDATED_CLIENT);
        assertThat(testPotential.getCollaborator()).isEqualTo(UPDATED_COLLABORATOR);
        assertThat(testPotential.getProvider()).isEqualTo(UPDATED_PROVIDER);
        assertThat(testPotential.getReferrer()).isEqualTo(UPDATED_REFERRER);
    }

    @Test
    @Transactional
    void putNonExistingPotential() throws Exception {
        int databaseSizeBeforeUpdate = potentialRepository.findAll().size();
        potential.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPotentialMockMvc
            .perform(
                put(ENTITY_API_URL_ID, potential.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(potential))
            )
            .andExpect(status().isBadRequest());

        // Validate the Potential in the database
        List<Potential> potentialList = potentialRepository.findAll();
        assertThat(potentialList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPotential() throws Exception {
        int databaseSizeBeforeUpdate = potentialRepository.findAll().size();
        potential.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPotentialMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(potential))
            )
            .andExpect(status().isBadRequest());

        // Validate the Potential in the database
        List<Potential> potentialList = potentialRepository.findAll();
        assertThat(potentialList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPotential() throws Exception {
        int databaseSizeBeforeUpdate = potentialRepository.findAll().size();
        potential.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPotentialMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(potential)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Potential in the database
        List<Potential> potentialList = potentialRepository.findAll();
        assertThat(potentialList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePotentialWithPatch() throws Exception {
        // Initialize the database
        potentialRepository.saveAndFlush(potential);

        int databaseSizeBeforeUpdate = potentialRepository.findAll().size();

        // Update the potential using partial update
        Potential partialUpdatedPotential = new Potential();
        partialUpdatedPotential.setId(potential.getId());

        partialUpdatedPotential.consultant(UPDATED_CONSULTANT).client(UPDATED_CLIENT).collaborator(UPDATED_COLLABORATOR);

        restPotentialMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPotential.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPotential))
            )
            .andExpect(status().isOk());

        // Validate the Potential in the database
        List<Potential> potentialList = potentialRepository.findAll();
        assertThat(potentialList).hasSize(databaseSizeBeforeUpdate);
        Potential testPotential = potentialList.get(potentialList.size() - 1);
        assertThat(testPotential.getExchangePartner()).isEqualTo(DEFAULT_EXCHANGE_PARTNER);
        assertThat(testPotential.getConsultant()).isEqualTo(UPDATED_CONSULTANT);
        assertThat(testPotential.getClient()).isEqualTo(UPDATED_CLIENT);
        assertThat(testPotential.getCollaborator()).isEqualTo(UPDATED_COLLABORATOR);
        assertThat(testPotential.getProvider()).isEqualTo(DEFAULT_PROVIDER);
        assertThat(testPotential.getReferrer()).isEqualTo(DEFAULT_REFERRER);
    }

    @Test
    @Transactional
    void fullUpdatePotentialWithPatch() throws Exception {
        // Initialize the database
        potentialRepository.saveAndFlush(potential);

        int databaseSizeBeforeUpdate = potentialRepository.findAll().size();

        // Update the potential using partial update
        Potential partialUpdatedPotential = new Potential();
        partialUpdatedPotential.setId(potential.getId());

        partialUpdatedPotential
            .exchangePartner(UPDATED_EXCHANGE_PARTNER)
            .consultant(UPDATED_CONSULTANT)
            .client(UPDATED_CLIENT)
            .collaborator(UPDATED_COLLABORATOR)
            .provider(UPDATED_PROVIDER)
            .referrer(UPDATED_REFERRER);

        restPotentialMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPotential.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPotential))
            )
            .andExpect(status().isOk());

        // Validate the Potential in the database
        List<Potential> potentialList = potentialRepository.findAll();
        assertThat(potentialList).hasSize(databaseSizeBeforeUpdate);
        Potential testPotential = potentialList.get(potentialList.size() - 1);
        assertThat(testPotential.getExchangePartner()).isEqualTo(UPDATED_EXCHANGE_PARTNER);
        assertThat(testPotential.getConsultant()).isEqualTo(UPDATED_CONSULTANT);
        assertThat(testPotential.getClient()).isEqualTo(UPDATED_CLIENT);
        assertThat(testPotential.getCollaborator()).isEqualTo(UPDATED_COLLABORATOR);
        assertThat(testPotential.getProvider()).isEqualTo(UPDATED_PROVIDER);
        assertThat(testPotential.getReferrer()).isEqualTo(UPDATED_REFERRER);
    }

    @Test
    @Transactional
    void patchNonExistingPotential() throws Exception {
        int databaseSizeBeforeUpdate = potentialRepository.findAll().size();
        potential.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPotentialMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, potential.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(potential))
            )
            .andExpect(status().isBadRequest());

        // Validate the Potential in the database
        List<Potential> potentialList = potentialRepository.findAll();
        assertThat(potentialList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPotential() throws Exception {
        int databaseSizeBeforeUpdate = potentialRepository.findAll().size();
        potential.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPotentialMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(potential))
            )
            .andExpect(status().isBadRequest());

        // Validate the Potential in the database
        List<Potential> potentialList = potentialRepository.findAll();
        assertThat(potentialList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPotential() throws Exception {
        int databaseSizeBeforeUpdate = potentialRepository.findAll().size();
        potential.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPotentialMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(potential))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Potential in the database
        List<Potential> potentialList = potentialRepository.findAll();
        assertThat(potentialList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePotential() throws Exception {
        // Initialize the database
        potentialRepository.saveAndFlush(potential);

        int databaseSizeBeforeDelete = potentialRepository.findAll().size();

        // Delete the potential
        restPotentialMockMvc
            .perform(delete(ENTITY_API_URL_ID, potential.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Potential> potentialList = potentialRepository.findAll();
        assertThat(potentialList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
