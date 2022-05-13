package co.edu.sena.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import co.edu.sena.IntegrationTest;
import co.edu.sena.domain.PriorityOrder;
import co.edu.sena.domain.enumeration.Designation;
import co.edu.sena.domain.enumeration.Designation;
import co.edu.sena.domain.enumeration.Designation;
import co.edu.sena.repository.PriorityOrderRepository;
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
 * Integration tests for the {@link PriorityOrderResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class PriorityOrderResourceIT {

    private static final Designation DEFAULT_QUALIF_LOGARIT = Designation.One;
    private static final Designation UPDATED_QUALIF_LOGARIT = Designation.Two;

    private static final Designation DEFAULT_QUALIF_AFFILIATE = Designation.One;
    private static final Designation UPDATED_QUALIF_AFFILIATE = Designation.Two;

    private static final Designation DEFAULT_QUALIF_DEFINITIVE = Designation.One;
    private static final Designation UPDATED_QUALIF_DEFINITIVE = Designation.Two;

    private static final String ENTITY_API_URL = "/api/priority-orders";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PriorityOrderRepository priorityOrderRepository;

    @Mock
    private PriorityOrderRepository priorityOrderRepositoryMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPriorityOrderMockMvc;

    private PriorityOrder priorityOrder;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PriorityOrder createEntity(EntityManager em) {
        PriorityOrder priorityOrder = new PriorityOrder()
            .qualifLogarit(DEFAULT_QUALIF_LOGARIT)
            .qualifAffiliate(DEFAULT_QUALIF_AFFILIATE)
            .qualifDefinitive(DEFAULT_QUALIF_DEFINITIVE);
        return priorityOrder;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PriorityOrder createUpdatedEntity(EntityManager em) {
        PriorityOrder priorityOrder = new PriorityOrder()
            .qualifLogarit(UPDATED_QUALIF_LOGARIT)
            .qualifAffiliate(UPDATED_QUALIF_AFFILIATE)
            .qualifDefinitive(UPDATED_QUALIF_DEFINITIVE);
        return priorityOrder;
    }

    @BeforeEach
    public void initTest() {
        priorityOrder = createEntity(em);
    }

    @Test
    @Transactional
    void createPriorityOrder() throws Exception {
        int databaseSizeBeforeCreate = priorityOrderRepository.findAll().size();
        // Create the PriorityOrder
        restPriorityOrderMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(priorityOrder)))
            .andExpect(status().isCreated());

        // Validate the PriorityOrder in the database
        List<PriorityOrder> priorityOrderList = priorityOrderRepository.findAll();
        assertThat(priorityOrderList).hasSize(databaseSizeBeforeCreate + 1);
        PriorityOrder testPriorityOrder = priorityOrderList.get(priorityOrderList.size() - 1);
        assertThat(testPriorityOrder.getQualifLogarit()).isEqualTo(DEFAULT_QUALIF_LOGARIT);
        assertThat(testPriorityOrder.getQualifAffiliate()).isEqualTo(DEFAULT_QUALIF_AFFILIATE);
        assertThat(testPriorityOrder.getQualifDefinitive()).isEqualTo(DEFAULT_QUALIF_DEFINITIVE);
    }

    @Test
    @Transactional
    void createPriorityOrderWithExistingId() throws Exception {
        // Create the PriorityOrder with an existing ID
        priorityOrder.setId(1L);

        int databaseSizeBeforeCreate = priorityOrderRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPriorityOrderMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(priorityOrder)))
            .andExpect(status().isBadRequest());

        // Validate the PriorityOrder in the database
        List<PriorityOrder> priorityOrderList = priorityOrderRepository.findAll();
        assertThat(priorityOrderList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkQualifLogaritIsRequired() throws Exception {
        int databaseSizeBeforeTest = priorityOrderRepository.findAll().size();
        // set the field null
        priorityOrder.setQualifLogarit(null);

        // Create the PriorityOrder, which fails.

        restPriorityOrderMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(priorityOrder)))
            .andExpect(status().isBadRequest());

        List<PriorityOrder> priorityOrderList = priorityOrderRepository.findAll();
        assertThat(priorityOrderList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkQualifAffiliateIsRequired() throws Exception {
        int databaseSizeBeforeTest = priorityOrderRepository.findAll().size();
        // set the field null
        priorityOrder.setQualifAffiliate(null);

        // Create the PriorityOrder, which fails.

        restPriorityOrderMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(priorityOrder)))
            .andExpect(status().isBadRequest());

        List<PriorityOrder> priorityOrderList = priorityOrderRepository.findAll();
        assertThat(priorityOrderList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkQualifDefinitiveIsRequired() throws Exception {
        int databaseSizeBeforeTest = priorityOrderRepository.findAll().size();
        // set the field null
        priorityOrder.setQualifDefinitive(null);

        // Create the PriorityOrder, which fails.

        restPriorityOrderMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(priorityOrder)))
            .andExpect(status().isBadRequest());

        List<PriorityOrder> priorityOrderList = priorityOrderRepository.findAll();
        assertThat(priorityOrderList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPriorityOrders() throws Exception {
        // Initialize the database
        priorityOrderRepository.saveAndFlush(priorityOrder);

        // Get all the priorityOrderList
        restPriorityOrderMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(priorityOrder.getId().intValue())))
            .andExpect(jsonPath("$.[*].qualifLogarit").value(hasItem(DEFAULT_QUALIF_LOGARIT.toString())))
            .andExpect(jsonPath("$.[*].qualifAffiliate").value(hasItem(DEFAULT_QUALIF_AFFILIATE.toString())))
            .andExpect(jsonPath("$.[*].qualifDefinitive").value(hasItem(DEFAULT_QUALIF_DEFINITIVE.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllPriorityOrdersWithEagerRelationshipsIsEnabled() throws Exception {
        when(priorityOrderRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restPriorityOrderMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(priorityOrderRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllPriorityOrdersWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(priorityOrderRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restPriorityOrderMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(priorityOrderRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    void getPriorityOrder() throws Exception {
        // Initialize the database
        priorityOrderRepository.saveAndFlush(priorityOrder);

        // Get the priorityOrder
        restPriorityOrderMockMvc
            .perform(get(ENTITY_API_URL_ID, priorityOrder.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(priorityOrder.getId().intValue()))
            .andExpect(jsonPath("$.qualifLogarit").value(DEFAULT_QUALIF_LOGARIT.toString()))
            .andExpect(jsonPath("$.qualifAffiliate").value(DEFAULT_QUALIF_AFFILIATE.toString()))
            .andExpect(jsonPath("$.qualifDefinitive").value(DEFAULT_QUALIF_DEFINITIVE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingPriorityOrder() throws Exception {
        // Get the priorityOrder
        restPriorityOrderMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPriorityOrder() throws Exception {
        // Initialize the database
        priorityOrderRepository.saveAndFlush(priorityOrder);

        int databaseSizeBeforeUpdate = priorityOrderRepository.findAll().size();

        // Update the priorityOrder
        PriorityOrder updatedPriorityOrder = priorityOrderRepository.findById(priorityOrder.getId()).get();
        // Disconnect from session so that the updates on updatedPriorityOrder are not directly saved in db
        em.detach(updatedPriorityOrder);
        updatedPriorityOrder
            .qualifLogarit(UPDATED_QUALIF_LOGARIT)
            .qualifAffiliate(UPDATED_QUALIF_AFFILIATE)
            .qualifDefinitive(UPDATED_QUALIF_DEFINITIVE);

        restPriorityOrderMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPriorityOrder.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPriorityOrder))
            )
            .andExpect(status().isOk());

        // Validate the PriorityOrder in the database
        List<PriorityOrder> priorityOrderList = priorityOrderRepository.findAll();
        assertThat(priorityOrderList).hasSize(databaseSizeBeforeUpdate);
        PriorityOrder testPriorityOrder = priorityOrderList.get(priorityOrderList.size() - 1);
        assertThat(testPriorityOrder.getQualifLogarit()).isEqualTo(UPDATED_QUALIF_LOGARIT);
        assertThat(testPriorityOrder.getQualifAffiliate()).isEqualTo(UPDATED_QUALIF_AFFILIATE);
        assertThat(testPriorityOrder.getQualifDefinitive()).isEqualTo(UPDATED_QUALIF_DEFINITIVE);
    }

    @Test
    @Transactional
    void putNonExistingPriorityOrder() throws Exception {
        int databaseSizeBeforeUpdate = priorityOrderRepository.findAll().size();
        priorityOrder.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPriorityOrderMockMvc
            .perform(
                put(ENTITY_API_URL_ID, priorityOrder.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(priorityOrder))
            )
            .andExpect(status().isBadRequest());

        // Validate the PriorityOrder in the database
        List<PriorityOrder> priorityOrderList = priorityOrderRepository.findAll();
        assertThat(priorityOrderList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPriorityOrder() throws Exception {
        int databaseSizeBeforeUpdate = priorityOrderRepository.findAll().size();
        priorityOrder.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPriorityOrderMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(priorityOrder))
            )
            .andExpect(status().isBadRequest());

        // Validate the PriorityOrder in the database
        List<PriorityOrder> priorityOrderList = priorityOrderRepository.findAll();
        assertThat(priorityOrderList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPriorityOrder() throws Exception {
        int databaseSizeBeforeUpdate = priorityOrderRepository.findAll().size();
        priorityOrder.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPriorityOrderMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(priorityOrder)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the PriorityOrder in the database
        List<PriorityOrder> priorityOrderList = priorityOrderRepository.findAll();
        assertThat(priorityOrderList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePriorityOrderWithPatch() throws Exception {
        // Initialize the database
        priorityOrderRepository.saveAndFlush(priorityOrder);

        int databaseSizeBeforeUpdate = priorityOrderRepository.findAll().size();

        // Update the priorityOrder using partial update
        PriorityOrder partialUpdatedPriorityOrder = new PriorityOrder();
        partialUpdatedPriorityOrder.setId(priorityOrder.getId());

        partialUpdatedPriorityOrder.qualifLogarit(UPDATED_QUALIF_LOGARIT).qualifDefinitive(UPDATED_QUALIF_DEFINITIVE);

        restPriorityOrderMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPriorityOrder.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPriorityOrder))
            )
            .andExpect(status().isOk());

        // Validate the PriorityOrder in the database
        List<PriorityOrder> priorityOrderList = priorityOrderRepository.findAll();
        assertThat(priorityOrderList).hasSize(databaseSizeBeforeUpdate);
        PriorityOrder testPriorityOrder = priorityOrderList.get(priorityOrderList.size() - 1);
        assertThat(testPriorityOrder.getQualifLogarit()).isEqualTo(UPDATED_QUALIF_LOGARIT);
        assertThat(testPriorityOrder.getQualifAffiliate()).isEqualTo(DEFAULT_QUALIF_AFFILIATE);
        assertThat(testPriorityOrder.getQualifDefinitive()).isEqualTo(UPDATED_QUALIF_DEFINITIVE);
    }

    @Test
    @Transactional
    void fullUpdatePriorityOrderWithPatch() throws Exception {
        // Initialize the database
        priorityOrderRepository.saveAndFlush(priorityOrder);

        int databaseSizeBeforeUpdate = priorityOrderRepository.findAll().size();

        // Update the priorityOrder using partial update
        PriorityOrder partialUpdatedPriorityOrder = new PriorityOrder();
        partialUpdatedPriorityOrder.setId(priorityOrder.getId());

        partialUpdatedPriorityOrder
            .qualifLogarit(UPDATED_QUALIF_LOGARIT)
            .qualifAffiliate(UPDATED_QUALIF_AFFILIATE)
            .qualifDefinitive(UPDATED_QUALIF_DEFINITIVE);

        restPriorityOrderMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPriorityOrder.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPriorityOrder))
            )
            .andExpect(status().isOk());

        // Validate the PriorityOrder in the database
        List<PriorityOrder> priorityOrderList = priorityOrderRepository.findAll();
        assertThat(priorityOrderList).hasSize(databaseSizeBeforeUpdate);
        PriorityOrder testPriorityOrder = priorityOrderList.get(priorityOrderList.size() - 1);
        assertThat(testPriorityOrder.getQualifLogarit()).isEqualTo(UPDATED_QUALIF_LOGARIT);
        assertThat(testPriorityOrder.getQualifAffiliate()).isEqualTo(UPDATED_QUALIF_AFFILIATE);
        assertThat(testPriorityOrder.getQualifDefinitive()).isEqualTo(UPDATED_QUALIF_DEFINITIVE);
    }

    @Test
    @Transactional
    void patchNonExistingPriorityOrder() throws Exception {
        int databaseSizeBeforeUpdate = priorityOrderRepository.findAll().size();
        priorityOrder.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPriorityOrderMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, priorityOrder.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(priorityOrder))
            )
            .andExpect(status().isBadRequest());

        // Validate the PriorityOrder in the database
        List<PriorityOrder> priorityOrderList = priorityOrderRepository.findAll();
        assertThat(priorityOrderList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPriorityOrder() throws Exception {
        int databaseSizeBeforeUpdate = priorityOrderRepository.findAll().size();
        priorityOrder.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPriorityOrderMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(priorityOrder))
            )
            .andExpect(status().isBadRequest());

        // Validate the PriorityOrder in the database
        List<PriorityOrder> priorityOrderList = priorityOrderRepository.findAll();
        assertThat(priorityOrderList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPriorityOrder() throws Exception {
        int databaseSizeBeforeUpdate = priorityOrderRepository.findAll().size();
        priorityOrder.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPriorityOrderMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(priorityOrder))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PriorityOrder in the database
        List<PriorityOrder> priorityOrderList = priorityOrderRepository.findAll();
        assertThat(priorityOrderList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePriorityOrder() throws Exception {
        // Initialize the database
        priorityOrderRepository.saveAndFlush(priorityOrder);

        int databaseSizeBeforeDelete = priorityOrderRepository.findAll().size();

        // Delete the priorityOrder
        restPriorityOrderMockMvc
            .perform(delete(ENTITY_API_URL_ID, priorityOrder.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PriorityOrder> priorityOrderList = priorityOrderRepository.findAll();
        assertThat(priorityOrderList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
