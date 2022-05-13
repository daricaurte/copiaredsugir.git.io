package co.edu.sena.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import co.edu.sena.IntegrationTest;
import co.edu.sena.domain.ContactData;
import co.edu.sena.repository.ContactDataRepository;
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
 * Integration tests for the {@link ContactDataResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ContactDataResourceIT {

    private static final String DEFAULT_DOCUMENT_NUMBER_CONTACT = "AAAAAAAAAA";
    private static final String UPDATED_DOCUMENT_NUMBER_CONTACT = "BBBBBBBBBB";

    private static final String DEFAULT_CELL_PHONE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_CELL_PHONE_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_COUNTRY = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY = "BBBBBBBBBB";

    private static final String DEFAULT_CITY = "AAAAAAAAAA";
    private static final String UPDATED_CITY = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_DEPARTMENT = "AAAAAAAAAA";
    private static final String UPDATED_DEPARTMENT = "BBBBBBBBBB";

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

    private static final String ENTITY_API_URL = "/api/contact-data";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ContactDataRepository contactDataRepository;

    @Mock
    private ContactDataRepository contactDataRepositoryMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restContactDataMockMvc;

    private ContactData contactData;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ContactData createEntity(EntityManager em) {
        ContactData contactData = new ContactData()
            .documentNumberContact(DEFAULT_DOCUMENT_NUMBER_CONTACT)
            .cellPhoneNumber(DEFAULT_CELL_PHONE_NUMBER)
            .country(DEFAULT_COUNTRY)
            .city(DEFAULT_CITY)
            .email(DEFAULT_EMAIL)
            .department(DEFAULT_DEPARTMENT)
            .firstName(DEFAULT_FIRST_NAME)
            .secondName(DEFAULT_SECOND_NAME)
            .firstLastName(DEFAULT_FIRST_LAST_NAME)
            .secondLastName(DEFAULT_SECOND_LAST_NAME)
            .neighborhood(DEFAULT_NEIGHBORHOOD);
        return contactData;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ContactData createUpdatedEntity(EntityManager em) {
        ContactData contactData = new ContactData()
            .documentNumberContact(UPDATED_DOCUMENT_NUMBER_CONTACT)
            .cellPhoneNumber(UPDATED_CELL_PHONE_NUMBER)
            .country(UPDATED_COUNTRY)
            .city(UPDATED_CITY)
            .email(UPDATED_EMAIL)
            .department(UPDATED_DEPARTMENT)
            .firstName(UPDATED_FIRST_NAME)
            .secondName(UPDATED_SECOND_NAME)
            .firstLastName(UPDATED_FIRST_LAST_NAME)
            .secondLastName(UPDATED_SECOND_LAST_NAME)
            .neighborhood(UPDATED_NEIGHBORHOOD);
        return contactData;
    }

    @BeforeEach
    public void initTest() {
        contactData = createEntity(em);
    }

    @Test
    @Transactional
    void createContactData() throws Exception {
        int databaseSizeBeforeCreate = contactDataRepository.findAll().size();
        // Create the ContactData
        restContactDataMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(contactData)))
            .andExpect(status().isCreated());

        // Validate the ContactData in the database
        List<ContactData> contactDataList = contactDataRepository.findAll();
        assertThat(contactDataList).hasSize(databaseSizeBeforeCreate + 1);
        ContactData testContactData = contactDataList.get(contactDataList.size() - 1);
        assertThat(testContactData.getDocumentNumberContact()).isEqualTo(DEFAULT_DOCUMENT_NUMBER_CONTACT);
        assertThat(testContactData.getCellPhoneNumber()).isEqualTo(DEFAULT_CELL_PHONE_NUMBER);
        assertThat(testContactData.getCountry()).isEqualTo(DEFAULT_COUNTRY);
        assertThat(testContactData.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testContactData.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testContactData.getDepartment()).isEqualTo(DEFAULT_DEPARTMENT);
        assertThat(testContactData.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testContactData.getSecondName()).isEqualTo(DEFAULT_SECOND_NAME);
        assertThat(testContactData.getFirstLastName()).isEqualTo(DEFAULT_FIRST_LAST_NAME);
        assertThat(testContactData.getSecondLastName()).isEqualTo(DEFAULT_SECOND_LAST_NAME);
        assertThat(testContactData.getNeighborhood()).isEqualTo(DEFAULT_NEIGHBORHOOD);
    }

    @Test
    @Transactional
    void createContactDataWithExistingId() throws Exception {
        // Create the ContactData with an existing ID
        contactData.setId(1L);

        int databaseSizeBeforeCreate = contactDataRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restContactDataMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(contactData)))
            .andExpect(status().isBadRequest());

        // Validate the ContactData in the database
        List<ContactData> contactDataList = contactDataRepository.findAll();
        assertThat(contactDataList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkDocumentNumberContactIsRequired() throws Exception {
        int databaseSizeBeforeTest = contactDataRepository.findAll().size();
        // set the field null
        contactData.setDocumentNumberContact(null);

        // Create the ContactData, which fails.

        restContactDataMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(contactData)))
            .andExpect(status().isBadRequest());

        List<ContactData> contactDataList = contactDataRepository.findAll();
        assertThat(contactDataList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCellPhoneNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = contactDataRepository.findAll().size();
        // set the field null
        contactData.setCellPhoneNumber(null);

        // Create the ContactData, which fails.

        restContactDataMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(contactData)))
            .andExpect(status().isBadRequest());

        List<ContactData> contactDataList = contactDataRepository.findAll();
        assertThat(contactDataList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCountryIsRequired() throws Exception {
        int databaseSizeBeforeTest = contactDataRepository.findAll().size();
        // set the field null
        contactData.setCountry(null);

        // Create the ContactData, which fails.

        restContactDataMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(contactData)))
            .andExpect(status().isBadRequest());

        List<ContactData> contactDataList = contactDataRepository.findAll();
        assertThat(contactDataList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCityIsRequired() throws Exception {
        int databaseSizeBeforeTest = contactDataRepository.findAll().size();
        // set the field null
        contactData.setCity(null);

        // Create the ContactData, which fails.

        restContactDataMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(contactData)))
            .andExpect(status().isBadRequest());

        List<ContactData> contactDataList = contactDataRepository.findAll();
        assertThat(contactDataList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = contactDataRepository.findAll().size();
        // set the field null
        contactData.setEmail(null);

        // Create the ContactData, which fails.

        restContactDataMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(contactData)))
            .andExpect(status().isBadRequest());

        List<ContactData> contactDataList = contactDataRepository.findAll();
        assertThat(contactDataList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDepartmentIsRequired() throws Exception {
        int databaseSizeBeforeTest = contactDataRepository.findAll().size();
        // set the field null
        contactData.setDepartment(null);

        // Create the ContactData, which fails.

        restContactDataMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(contactData)))
            .andExpect(status().isBadRequest());

        List<ContactData> contactDataList = contactDataRepository.findAll();
        assertThat(contactDataList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFirstNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = contactDataRepository.findAll().size();
        // set the field null
        contactData.setFirstName(null);

        // Create the ContactData, which fails.

        restContactDataMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(contactData)))
            .andExpect(status().isBadRequest());

        List<ContactData> contactDataList = contactDataRepository.findAll();
        assertThat(contactDataList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFirstLastNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = contactDataRepository.findAll().size();
        // set the field null
        contactData.setFirstLastName(null);

        // Create the ContactData, which fails.

        restContactDataMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(contactData)))
            .andExpect(status().isBadRequest());

        List<ContactData> contactDataList = contactDataRepository.findAll();
        assertThat(contactDataList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNeighborhoodIsRequired() throws Exception {
        int databaseSizeBeforeTest = contactDataRepository.findAll().size();
        // set the field null
        contactData.setNeighborhood(null);

        // Create the ContactData, which fails.

        restContactDataMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(contactData)))
            .andExpect(status().isBadRequest());

        List<ContactData> contactDataList = contactDataRepository.findAll();
        assertThat(contactDataList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllContactData() throws Exception {
        // Initialize the database
        contactDataRepository.saveAndFlush(contactData);

        // Get all the contactDataList
        restContactDataMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(contactData.getId().intValue())))
            .andExpect(jsonPath("$.[*].documentNumberContact").value(hasItem(DEFAULT_DOCUMENT_NUMBER_CONTACT)))
            .andExpect(jsonPath("$.[*].cellPhoneNumber").value(hasItem(DEFAULT_CELL_PHONE_NUMBER)))
            .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY)))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].department").value(hasItem(DEFAULT_DEPARTMENT)))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].secondName").value(hasItem(DEFAULT_SECOND_NAME)))
            .andExpect(jsonPath("$.[*].firstLastName").value(hasItem(DEFAULT_FIRST_LAST_NAME)))
            .andExpect(jsonPath("$.[*].secondLastName").value(hasItem(DEFAULT_SECOND_LAST_NAME)))
            .andExpect(jsonPath("$.[*].neighborhood").value(hasItem(DEFAULT_NEIGHBORHOOD)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllContactDataWithEagerRelationshipsIsEnabled() throws Exception {
        when(contactDataRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restContactDataMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(contactDataRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllContactDataWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(contactDataRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restContactDataMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(contactDataRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    void getContactData() throws Exception {
        // Initialize the database
        contactDataRepository.saveAndFlush(contactData);

        // Get the contactData
        restContactDataMockMvc
            .perform(get(ENTITY_API_URL_ID, contactData.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(contactData.getId().intValue()))
            .andExpect(jsonPath("$.documentNumberContact").value(DEFAULT_DOCUMENT_NUMBER_CONTACT))
            .andExpect(jsonPath("$.cellPhoneNumber").value(DEFAULT_CELL_PHONE_NUMBER))
            .andExpect(jsonPath("$.country").value(DEFAULT_COUNTRY))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.department").value(DEFAULT_DEPARTMENT))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME))
            .andExpect(jsonPath("$.secondName").value(DEFAULT_SECOND_NAME))
            .andExpect(jsonPath("$.firstLastName").value(DEFAULT_FIRST_LAST_NAME))
            .andExpect(jsonPath("$.secondLastName").value(DEFAULT_SECOND_LAST_NAME))
            .andExpect(jsonPath("$.neighborhood").value(DEFAULT_NEIGHBORHOOD));
    }

    @Test
    @Transactional
    void getNonExistingContactData() throws Exception {
        // Get the contactData
        restContactDataMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewContactData() throws Exception {
        // Initialize the database
        contactDataRepository.saveAndFlush(contactData);

        int databaseSizeBeforeUpdate = contactDataRepository.findAll().size();

        // Update the contactData
        ContactData updatedContactData = contactDataRepository.findById(contactData.getId()).get();
        // Disconnect from session so that the updates on updatedContactData are not directly saved in db
        em.detach(updatedContactData);
        updatedContactData
            .documentNumberContact(UPDATED_DOCUMENT_NUMBER_CONTACT)
            .cellPhoneNumber(UPDATED_CELL_PHONE_NUMBER)
            .country(UPDATED_COUNTRY)
            .city(UPDATED_CITY)
            .email(UPDATED_EMAIL)
            .department(UPDATED_DEPARTMENT)
            .firstName(UPDATED_FIRST_NAME)
            .secondName(UPDATED_SECOND_NAME)
            .firstLastName(UPDATED_FIRST_LAST_NAME)
            .secondLastName(UPDATED_SECOND_LAST_NAME)
            .neighborhood(UPDATED_NEIGHBORHOOD);

        restContactDataMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedContactData.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedContactData))
            )
            .andExpect(status().isOk());

        // Validate the ContactData in the database
        List<ContactData> contactDataList = contactDataRepository.findAll();
        assertThat(contactDataList).hasSize(databaseSizeBeforeUpdate);
        ContactData testContactData = contactDataList.get(contactDataList.size() - 1);
        assertThat(testContactData.getDocumentNumberContact()).isEqualTo(UPDATED_DOCUMENT_NUMBER_CONTACT);
        assertThat(testContactData.getCellPhoneNumber()).isEqualTo(UPDATED_CELL_PHONE_NUMBER);
        assertThat(testContactData.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(testContactData.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testContactData.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testContactData.getDepartment()).isEqualTo(UPDATED_DEPARTMENT);
        assertThat(testContactData.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testContactData.getSecondName()).isEqualTo(UPDATED_SECOND_NAME);
        assertThat(testContactData.getFirstLastName()).isEqualTo(UPDATED_FIRST_LAST_NAME);
        assertThat(testContactData.getSecondLastName()).isEqualTo(UPDATED_SECOND_LAST_NAME);
        assertThat(testContactData.getNeighborhood()).isEqualTo(UPDATED_NEIGHBORHOOD);
    }

    @Test
    @Transactional
    void putNonExistingContactData() throws Exception {
        int databaseSizeBeforeUpdate = contactDataRepository.findAll().size();
        contactData.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restContactDataMockMvc
            .perform(
                put(ENTITY_API_URL_ID, contactData.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(contactData))
            )
            .andExpect(status().isBadRequest());

        // Validate the ContactData in the database
        List<ContactData> contactDataList = contactDataRepository.findAll();
        assertThat(contactDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchContactData() throws Exception {
        int databaseSizeBeforeUpdate = contactDataRepository.findAll().size();
        contactData.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContactDataMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(contactData))
            )
            .andExpect(status().isBadRequest());

        // Validate the ContactData in the database
        List<ContactData> contactDataList = contactDataRepository.findAll();
        assertThat(contactDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamContactData() throws Exception {
        int databaseSizeBeforeUpdate = contactDataRepository.findAll().size();
        contactData.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContactDataMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(contactData)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ContactData in the database
        List<ContactData> contactDataList = contactDataRepository.findAll();
        assertThat(contactDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateContactDataWithPatch() throws Exception {
        // Initialize the database
        contactDataRepository.saveAndFlush(contactData);

        int databaseSizeBeforeUpdate = contactDataRepository.findAll().size();

        // Update the contactData using partial update
        ContactData partialUpdatedContactData = new ContactData();
        partialUpdatedContactData.setId(contactData.getId());

        partialUpdatedContactData
            .documentNumberContact(UPDATED_DOCUMENT_NUMBER_CONTACT)
            .email(UPDATED_EMAIL)
            .department(UPDATED_DEPARTMENT)
            .firstLastName(UPDATED_FIRST_LAST_NAME)
            .neighborhood(UPDATED_NEIGHBORHOOD);

        restContactDataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedContactData.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedContactData))
            )
            .andExpect(status().isOk());

        // Validate the ContactData in the database
        List<ContactData> contactDataList = contactDataRepository.findAll();
        assertThat(contactDataList).hasSize(databaseSizeBeforeUpdate);
        ContactData testContactData = contactDataList.get(contactDataList.size() - 1);
        assertThat(testContactData.getDocumentNumberContact()).isEqualTo(UPDATED_DOCUMENT_NUMBER_CONTACT);
        assertThat(testContactData.getCellPhoneNumber()).isEqualTo(DEFAULT_CELL_PHONE_NUMBER);
        assertThat(testContactData.getCountry()).isEqualTo(DEFAULT_COUNTRY);
        assertThat(testContactData.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testContactData.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testContactData.getDepartment()).isEqualTo(UPDATED_DEPARTMENT);
        assertThat(testContactData.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testContactData.getSecondName()).isEqualTo(DEFAULT_SECOND_NAME);
        assertThat(testContactData.getFirstLastName()).isEqualTo(UPDATED_FIRST_LAST_NAME);
        assertThat(testContactData.getSecondLastName()).isEqualTo(DEFAULT_SECOND_LAST_NAME);
        assertThat(testContactData.getNeighborhood()).isEqualTo(UPDATED_NEIGHBORHOOD);
    }

    @Test
    @Transactional
    void fullUpdateContactDataWithPatch() throws Exception {
        // Initialize the database
        contactDataRepository.saveAndFlush(contactData);

        int databaseSizeBeforeUpdate = contactDataRepository.findAll().size();

        // Update the contactData using partial update
        ContactData partialUpdatedContactData = new ContactData();
        partialUpdatedContactData.setId(contactData.getId());

        partialUpdatedContactData
            .documentNumberContact(UPDATED_DOCUMENT_NUMBER_CONTACT)
            .cellPhoneNumber(UPDATED_CELL_PHONE_NUMBER)
            .country(UPDATED_COUNTRY)
            .city(UPDATED_CITY)
            .email(UPDATED_EMAIL)
            .department(UPDATED_DEPARTMENT)
            .firstName(UPDATED_FIRST_NAME)
            .secondName(UPDATED_SECOND_NAME)
            .firstLastName(UPDATED_FIRST_LAST_NAME)
            .secondLastName(UPDATED_SECOND_LAST_NAME)
            .neighborhood(UPDATED_NEIGHBORHOOD);

        restContactDataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedContactData.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedContactData))
            )
            .andExpect(status().isOk());

        // Validate the ContactData in the database
        List<ContactData> contactDataList = contactDataRepository.findAll();
        assertThat(contactDataList).hasSize(databaseSizeBeforeUpdate);
        ContactData testContactData = contactDataList.get(contactDataList.size() - 1);
        assertThat(testContactData.getDocumentNumberContact()).isEqualTo(UPDATED_DOCUMENT_NUMBER_CONTACT);
        assertThat(testContactData.getCellPhoneNumber()).isEqualTo(UPDATED_CELL_PHONE_NUMBER);
        assertThat(testContactData.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(testContactData.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testContactData.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testContactData.getDepartment()).isEqualTo(UPDATED_DEPARTMENT);
        assertThat(testContactData.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testContactData.getSecondName()).isEqualTo(UPDATED_SECOND_NAME);
        assertThat(testContactData.getFirstLastName()).isEqualTo(UPDATED_FIRST_LAST_NAME);
        assertThat(testContactData.getSecondLastName()).isEqualTo(UPDATED_SECOND_LAST_NAME);
        assertThat(testContactData.getNeighborhood()).isEqualTo(UPDATED_NEIGHBORHOOD);
    }

    @Test
    @Transactional
    void patchNonExistingContactData() throws Exception {
        int databaseSizeBeforeUpdate = contactDataRepository.findAll().size();
        contactData.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restContactDataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, contactData.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(contactData))
            )
            .andExpect(status().isBadRequest());

        // Validate the ContactData in the database
        List<ContactData> contactDataList = contactDataRepository.findAll();
        assertThat(contactDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchContactData() throws Exception {
        int databaseSizeBeforeUpdate = contactDataRepository.findAll().size();
        contactData.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContactDataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(contactData))
            )
            .andExpect(status().isBadRequest());

        // Validate the ContactData in the database
        List<ContactData> contactDataList = contactDataRepository.findAll();
        assertThat(contactDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamContactData() throws Exception {
        int databaseSizeBeforeUpdate = contactDataRepository.findAll().size();
        contactData.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContactDataMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(contactData))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ContactData in the database
        List<ContactData> contactDataList = contactDataRepository.findAll();
        assertThat(contactDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteContactData() throws Exception {
        // Initialize the database
        contactDataRepository.saveAndFlush(contactData);

        int databaseSizeBeforeDelete = contactDataRepository.findAll().size();

        // Delete the contactData
        restContactDataMockMvc
            .perform(delete(ENTITY_API_URL_ID, contactData.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ContactData> contactDataList = contactDataRepository.findAll();
        assertThat(contactDataList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
