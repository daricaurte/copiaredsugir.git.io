package co.edu.sena.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import co.edu.sena.IntegrationTest;
import co.edu.sena.domain.Contact;
import co.edu.sena.repository.ContactRepository;
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
 * Integration tests for the {@link ContactResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ContactResourceIT {

    private static final Boolean DEFAULT_CONTACT_3RD_SECTOR_HL = false;
    private static final Boolean UPDATED_CONTACT_3RD_SECTOR_HL = true;

    private static final Boolean DEFAULT_CONTACT_ACADEMY_HL = false;
    private static final Boolean UPDATED_CONTACT_ACADEMY_HL = true;

    private static final Boolean DEFAULT_CONTACT_TEETH_HL = false;
    private static final Boolean UPDATED_CONTACT_TEETH_HL = true;

    private static final Boolean DEFAULT_CONTACT_EMPLOYEE_HL = false;
    private static final Boolean UPDATED_CONTACT_EMPLOYEE_HL = true;

    private static final Boolean DEFAULT_CONTACT_ENT_FINAN_HL = false;
    private static final Boolean UPDATED_CONTACT_ENT_FINAN_HL = true;

    private static final Boolean DEFAULT_CONTACT_STATUS_HL = false;
    private static final Boolean UPDATED_CONTACT_STATUS_HL = true;

    private static final Boolean DEFAULT_INTERNATIONAL_CONTACT_HL = false;
    private static final Boolean UPDATED_INTERNATIONAL_CONTACT_HL = true;

    private static final Boolean DEFAULT_CONTACT_MEDIA_COMUNC_HL = false;
    private static final Boolean UPDATED_CONTACT_MEDIA_COMUNC_HL = true;

    private static final Boolean DEFAULT_CONTACT_ORG_COMMUNITY_HL = false;
    private static final Boolean UPDATED_CONTACT_ORG_COMMUNITY_HL = true;

    private static final Boolean DEFAULT_CONTACT_REGULATORY_ORGANISMS_HL = false;
    private static final Boolean UPDATED_CONTACT_REGULATORY_ORGANISMS_HL = true;

    private static final Boolean DEFAULT_CONTACT_PROVEEDORES_HL = false;
    private static final Boolean UPDATED_CONTACT_PROVEEDORES_HL = true;

    private static final Boolean DEFAULT_CONTACT_SOCIAL_NETWORKS = false;
    private static final Boolean UPDATED_CONTACT_SOCIAL_NETWORKS = true;

    private static final Boolean DEFAULT_CONTACT_SHAREHOLDERS_HL = false;
    private static final Boolean UPDATED_CONTACT_SHAREHOLDERS_HL = true;

    private static final String ENTITY_API_URL = "/api/contacts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ContactRepository contactRepository;

    @Mock
    private ContactRepository contactRepositoryMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restContactMockMvc;

    private Contact contact;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Contact createEntity(EntityManager em) {
        Contact contact = new Contact()
            .contact3rdSectorHl(DEFAULT_CONTACT_3RD_SECTOR_HL)
            .contactAcademyHl(DEFAULT_CONTACT_ACADEMY_HL)
            .contactTeethHl(DEFAULT_CONTACT_TEETH_HL)
            .contactEmployeeHl(DEFAULT_CONTACT_EMPLOYEE_HL)
            .contactEntFinanHl(DEFAULT_CONTACT_ENT_FINAN_HL)
            .contactStatusHl(DEFAULT_CONTACT_STATUS_HL)
            .internationalContactHl(DEFAULT_INTERNATIONAL_CONTACT_HL)
            .contactMediaComuncHl(DEFAULT_CONTACT_MEDIA_COMUNC_HL)
            .contactOrgCommunityHl(DEFAULT_CONTACT_ORG_COMMUNITY_HL)
            .contactRegulatoryOrganismsHl(DEFAULT_CONTACT_REGULATORY_ORGANISMS_HL)
            .contactProveedoresHl(DEFAULT_CONTACT_PROVEEDORES_HL)
            .contactSocialNetworks(DEFAULT_CONTACT_SOCIAL_NETWORKS)
            .contactShareholdersHl(DEFAULT_CONTACT_SHAREHOLDERS_HL);
        return contact;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Contact createUpdatedEntity(EntityManager em) {
        Contact contact = new Contact()
            .contact3rdSectorHl(UPDATED_CONTACT_3RD_SECTOR_HL)
            .contactAcademyHl(UPDATED_CONTACT_ACADEMY_HL)
            .contactTeethHl(UPDATED_CONTACT_TEETH_HL)
            .contactEmployeeHl(UPDATED_CONTACT_EMPLOYEE_HL)
            .contactEntFinanHl(UPDATED_CONTACT_ENT_FINAN_HL)
            .contactStatusHl(UPDATED_CONTACT_STATUS_HL)
            .internationalContactHl(UPDATED_INTERNATIONAL_CONTACT_HL)
            .contactMediaComuncHl(UPDATED_CONTACT_MEDIA_COMUNC_HL)
            .contactOrgCommunityHl(UPDATED_CONTACT_ORG_COMMUNITY_HL)
            .contactRegulatoryOrganismsHl(UPDATED_CONTACT_REGULATORY_ORGANISMS_HL)
            .contactProveedoresHl(UPDATED_CONTACT_PROVEEDORES_HL)
            .contactSocialNetworks(UPDATED_CONTACT_SOCIAL_NETWORKS)
            .contactShareholdersHl(UPDATED_CONTACT_SHAREHOLDERS_HL);
        return contact;
    }

    @BeforeEach
    public void initTest() {
        contact = createEntity(em);
    }

    @Test
    @Transactional
    void createContact() throws Exception {
        int databaseSizeBeforeCreate = contactRepository.findAll().size();
        // Create the Contact
        restContactMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(contact)))
            .andExpect(status().isCreated());

        // Validate the Contact in the database
        List<Contact> contactList = contactRepository.findAll();
        assertThat(contactList).hasSize(databaseSizeBeforeCreate + 1);
        Contact testContact = contactList.get(contactList.size() - 1);
        assertThat(testContact.getContact3rdSectorHl()).isEqualTo(DEFAULT_CONTACT_3RD_SECTOR_HL);
        assertThat(testContact.getContactAcademyHl()).isEqualTo(DEFAULT_CONTACT_ACADEMY_HL);
        assertThat(testContact.getContactTeethHl()).isEqualTo(DEFAULT_CONTACT_TEETH_HL);
        assertThat(testContact.getContactEmployeeHl()).isEqualTo(DEFAULT_CONTACT_EMPLOYEE_HL);
        assertThat(testContact.getContactEntFinanHl()).isEqualTo(DEFAULT_CONTACT_ENT_FINAN_HL);
        assertThat(testContact.getContactStatusHl()).isEqualTo(DEFAULT_CONTACT_STATUS_HL);
        assertThat(testContact.getInternationalContactHl()).isEqualTo(DEFAULT_INTERNATIONAL_CONTACT_HL);
        assertThat(testContact.getContactMediaComuncHl()).isEqualTo(DEFAULT_CONTACT_MEDIA_COMUNC_HL);
        assertThat(testContact.getContactOrgCommunityHl()).isEqualTo(DEFAULT_CONTACT_ORG_COMMUNITY_HL);
        assertThat(testContact.getContactRegulatoryOrganismsHl()).isEqualTo(DEFAULT_CONTACT_REGULATORY_ORGANISMS_HL);
        assertThat(testContact.getContactProveedoresHl()).isEqualTo(DEFAULT_CONTACT_PROVEEDORES_HL);
        assertThat(testContact.getContactSocialNetworks()).isEqualTo(DEFAULT_CONTACT_SOCIAL_NETWORKS);
        assertThat(testContact.getContactShareholdersHl()).isEqualTo(DEFAULT_CONTACT_SHAREHOLDERS_HL);
    }

    @Test
    @Transactional
    void createContactWithExistingId() throws Exception {
        // Create the Contact with an existing ID
        contact.setId(1L);

        int databaseSizeBeforeCreate = contactRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restContactMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(contact)))
            .andExpect(status().isBadRequest());

        // Validate the Contact in the database
        List<Contact> contactList = contactRepository.findAll();
        assertThat(contactList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkContact3rdSectorHlIsRequired() throws Exception {
        int databaseSizeBeforeTest = contactRepository.findAll().size();
        // set the field null
        contact.setContact3rdSectorHl(null);

        // Create the Contact, which fails.

        restContactMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(contact)))
            .andExpect(status().isBadRequest());

        List<Contact> contactList = contactRepository.findAll();
        assertThat(contactList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkContactAcademyHlIsRequired() throws Exception {
        int databaseSizeBeforeTest = contactRepository.findAll().size();
        // set the field null
        contact.setContactAcademyHl(null);

        // Create the Contact, which fails.

        restContactMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(contact)))
            .andExpect(status().isBadRequest());

        List<Contact> contactList = contactRepository.findAll();
        assertThat(contactList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkContactTeethHlIsRequired() throws Exception {
        int databaseSizeBeforeTest = contactRepository.findAll().size();
        // set the field null
        contact.setContactTeethHl(null);

        // Create the Contact, which fails.

        restContactMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(contact)))
            .andExpect(status().isBadRequest());

        List<Contact> contactList = contactRepository.findAll();
        assertThat(contactList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkContactEmployeeHlIsRequired() throws Exception {
        int databaseSizeBeforeTest = contactRepository.findAll().size();
        // set the field null
        contact.setContactEmployeeHl(null);

        // Create the Contact, which fails.

        restContactMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(contact)))
            .andExpect(status().isBadRequest());

        List<Contact> contactList = contactRepository.findAll();
        assertThat(contactList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkContactEntFinanHlIsRequired() throws Exception {
        int databaseSizeBeforeTest = contactRepository.findAll().size();
        // set the field null
        contact.setContactEntFinanHl(null);

        // Create the Contact, which fails.

        restContactMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(contact)))
            .andExpect(status().isBadRequest());

        List<Contact> contactList = contactRepository.findAll();
        assertThat(contactList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkContactStatusHlIsRequired() throws Exception {
        int databaseSizeBeforeTest = contactRepository.findAll().size();
        // set the field null
        contact.setContactStatusHl(null);

        // Create the Contact, which fails.

        restContactMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(contact)))
            .andExpect(status().isBadRequest());

        List<Contact> contactList = contactRepository.findAll();
        assertThat(contactList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkInternationalContactHlIsRequired() throws Exception {
        int databaseSizeBeforeTest = contactRepository.findAll().size();
        // set the field null
        contact.setInternationalContactHl(null);

        // Create the Contact, which fails.

        restContactMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(contact)))
            .andExpect(status().isBadRequest());

        List<Contact> contactList = contactRepository.findAll();
        assertThat(contactList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkContactMediaComuncHlIsRequired() throws Exception {
        int databaseSizeBeforeTest = contactRepository.findAll().size();
        // set the field null
        contact.setContactMediaComuncHl(null);

        // Create the Contact, which fails.

        restContactMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(contact)))
            .andExpect(status().isBadRequest());

        List<Contact> contactList = contactRepository.findAll();
        assertThat(contactList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkContactOrgCommunityHlIsRequired() throws Exception {
        int databaseSizeBeforeTest = contactRepository.findAll().size();
        // set the field null
        contact.setContactOrgCommunityHl(null);

        // Create the Contact, which fails.

        restContactMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(contact)))
            .andExpect(status().isBadRequest());

        List<Contact> contactList = contactRepository.findAll();
        assertThat(contactList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkContactRegulatoryOrganismsHlIsRequired() throws Exception {
        int databaseSizeBeforeTest = contactRepository.findAll().size();
        // set the field null
        contact.setContactRegulatoryOrganismsHl(null);

        // Create the Contact, which fails.

        restContactMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(contact)))
            .andExpect(status().isBadRequest());

        List<Contact> contactList = contactRepository.findAll();
        assertThat(contactList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkContactProveedoresHlIsRequired() throws Exception {
        int databaseSizeBeforeTest = contactRepository.findAll().size();
        // set the field null
        contact.setContactProveedoresHl(null);

        // Create the Contact, which fails.

        restContactMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(contact)))
            .andExpect(status().isBadRequest());

        List<Contact> contactList = contactRepository.findAll();
        assertThat(contactList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkContactSocialNetworksIsRequired() throws Exception {
        int databaseSizeBeforeTest = contactRepository.findAll().size();
        // set the field null
        contact.setContactSocialNetworks(null);

        // Create the Contact, which fails.

        restContactMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(contact)))
            .andExpect(status().isBadRequest());

        List<Contact> contactList = contactRepository.findAll();
        assertThat(contactList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkContactShareholdersHlIsRequired() throws Exception {
        int databaseSizeBeforeTest = contactRepository.findAll().size();
        // set the field null
        contact.setContactShareholdersHl(null);

        // Create the Contact, which fails.

        restContactMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(contact)))
            .andExpect(status().isBadRequest());

        List<Contact> contactList = contactRepository.findAll();
        assertThat(contactList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllContacts() throws Exception {
        // Initialize the database
        contactRepository.saveAndFlush(contact);

        // Get all the contactList
        restContactMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(contact.getId().intValue())))
            .andExpect(jsonPath("$.[*].contact3rdSectorHl").value(hasItem(DEFAULT_CONTACT_3RD_SECTOR_HL.booleanValue())))
            .andExpect(jsonPath("$.[*].contactAcademyHl").value(hasItem(DEFAULT_CONTACT_ACADEMY_HL.booleanValue())))
            .andExpect(jsonPath("$.[*].contactTeethHl").value(hasItem(DEFAULT_CONTACT_TEETH_HL.booleanValue())))
            .andExpect(jsonPath("$.[*].contactEmployeeHl").value(hasItem(DEFAULT_CONTACT_EMPLOYEE_HL.booleanValue())))
            .andExpect(jsonPath("$.[*].contactEntFinanHl").value(hasItem(DEFAULT_CONTACT_ENT_FINAN_HL.booleanValue())))
            .andExpect(jsonPath("$.[*].contactStatusHl").value(hasItem(DEFAULT_CONTACT_STATUS_HL.booleanValue())))
            .andExpect(jsonPath("$.[*].internationalContactHl").value(hasItem(DEFAULT_INTERNATIONAL_CONTACT_HL.booleanValue())))
            .andExpect(jsonPath("$.[*].contactMediaComuncHl").value(hasItem(DEFAULT_CONTACT_MEDIA_COMUNC_HL.booleanValue())))
            .andExpect(jsonPath("$.[*].contactOrgCommunityHl").value(hasItem(DEFAULT_CONTACT_ORG_COMMUNITY_HL.booleanValue())))
            .andExpect(
                jsonPath("$.[*].contactRegulatoryOrganismsHl").value(hasItem(DEFAULT_CONTACT_REGULATORY_ORGANISMS_HL.booleanValue()))
            )
            .andExpect(jsonPath("$.[*].contactProveedoresHl").value(hasItem(DEFAULT_CONTACT_PROVEEDORES_HL.booleanValue())))
            .andExpect(jsonPath("$.[*].contactSocialNetworks").value(hasItem(DEFAULT_CONTACT_SOCIAL_NETWORKS.booleanValue())))
            .andExpect(jsonPath("$.[*].contactShareholdersHl").value(hasItem(DEFAULT_CONTACT_SHAREHOLDERS_HL.booleanValue())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllContactsWithEagerRelationshipsIsEnabled() throws Exception {
        when(contactRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restContactMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(contactRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllContactsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(contactRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restContactMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(contactRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    void getContact() throws Exception {
        // Initialize the database
        contactRepository.saveAndFlush(contact);

        // Get the contact
        restContactMockMvc
            .perform(get(ENTITY_API_URL_ID, contact.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(contact.getId().intValue()))
            .andExpect(jsonPath("$.contact3rdSectorHl").value(DEFAULT_CONTACT_3RD_SECTOR_HL.booleanValue()))
            .andExpect(jsonPath("$.contactAcademyHl").value(DEFAULT_CONTACT_ACADEMY_HL.booleanValue()))
            .andExpect(jsonPath("$.contactTeethHl").value(DEFAULT_CONTACT_TEETH_HL.booleanValue()))
            .andExpect(jsonPath("$.contactEmployeeHl").value(DEFAULT_CONTACT_EMPLOYEE_HL.booleanValue()))
            .andExpect(jsonPath("$.contactEntFinanHl").value(DEFAULT_CONTACT_ENT_FINAN_HL.booleanValue()))
            .andExpect(jsonPath("$.contactStatusHl").value(DEFAULT_CONTACT_STATUS_HL.booleanValue()))
            .andExpect(jsonPath("$.internationalContactHl").value(DEFAULT_INTERNATIONAL_CONTACT_HL.booleanValue()))
            .andExpect(jsonPath("$.contactMediaComuncHl").value(DEFAULT_CONTACT_MEDIA_COMUNC_HL.booleanValue()))
            .andExpect(jsonPath("$.contactOrgCommunityHl").value(DEFAULT_CONTACT_ORG_COMMUNITY_HL.booleanValue()))
            .andExpect(jsonPath("$.contactRegulatoryOrganismsHl").value(DEFAULT_CONTACT_REGULATORY_ORGANISMS_HL.booleanValue()))
            .andExpect(jsonPath("$.contactProveedoresHl").value(DEFAULT_CONTACT_PROVEEDORES_HL.booleanValue()))
            .andExpect(jsonPath("$.contactSocialNetworks").value(DEFAULT_CONTACT_SOCIAL_NETWORKS.booleanValue()))
            .andExpect(jsonPath("$.contactShareholdersHl").value(DEFAULT_CONTACT_SHAREHOLDERS_HL.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingContact() throws Exception {
        // Get the contact
        restContactMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewContact() throws Exception {
        // Initialize the database
        contactRepository.saveAndFlush(contact);

        int databaseSizeBeforeUpdate = contactRepository.findAll().size();

        // Update the contact
        Contact updatedContact = contactRepository.findById(contact.getId()).get();
        // Disconnect from session so that the updates on updatedContact are not directly saved in db
        em.detach(updatedContact);
        updatedContact
            .contact3rdSectorHl(UPDATED_CONTACT_3RD_SECTOR_HL)
            .contactAcademyHl(UPDATED_CONTACT_ACADEMY_HL)
            .contactTeethHl(UPDATED_CONTACT_TEETH_HL)
            .contactEmployeeHl(UPDATED_CONTACT_EMPLOYEE_HL)
            .contactEntFinanHl(UPDATED_CONTACT_ENT_FINAN_HL)
            .contactStatusHl(UPDATED_CONTACT_STATUS_HL)
            .internationalContactHl(UPDATED_INTERNATIONAL_CONTACT_HL)
            .contactMediaComuncHl(UPDATED_CONTACT_MEDIA_COMUNC_HL)
            .contactOrgCommunityHl(UPDATED_CONTACT_ORG_COMMUNITY_HL)
            .contactRegulatoryOrganismsHl(UPDATED_CONTACT_REGULATORY_ORGANISMS_HL)
            .contactProveedoresHl(UPDATED_CONTACT_PROVEEDORES_HL)
            .contactSocialNetworks(UPDATED_CONTACT_SOCIAL_NETWORKS)
            .contactShareholdersHl(UPDATED_CONTACT_SHAREHOLDERS_HL);

        restContactMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedContact.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedContact))
            )
            .andExpect(status().isOk());

        // Validate the Contact in the database
        List<Contact> contactList = contactRepository.findAll();
        assertThat(contactList).hasSize(databaseSizeBeforeUpdate);
        Contact testContact = contactList.get(contactList.size() - 1);
        assertThat(testContact.getContact3rdSectorHl()).isEqualTo(UPDATED_CONTACT_3RD_SECTOR_HL);
        assertThat(testContact.getContactAcademyHl()).isEqualTo(UPDATED_CONTACT_ACADEMY_HL);
        assertThat(testContact.getContactTeethHl()).isEqualTo(UPDATED_CONTACT_TEETH_HL);
        assertThat(testContact.getContactEmployeeHl()).isEqualTo(UPDATED_CONTACT_EMPLOYEE_HL);
        assertThat(testContact.getContactEntFinanHl()).isEqualTo(UPDATED_CONTACT_ENT_FINAN_HL);
        assertThat(testContact.getContactStatusHl()).isEqualTo(UPDATED_CONTACT_STATUS_HL);
        assertThat(testContact.getInternationalContactHl()).isEqualTo(UPDATED_INTERNATIONAL_CONTACT_HL);
        assertThat(testContact.getContactMediaComuncHl()).isEqualTo(UPDATED_CONTACT_MEDIA_COMUNC_HL);
        assertThat(testContact.getContactOrgCommunityHl()).isEqualTo(UPDATED_CONTACT_ORG_COMMUNITY_HL);
        assertThat(testContact.getContactRegulatoryOrganismsHl()).isEqualTo(UPDATED_CONTACT_REGULATORY_ORGANISMS_HL);
        assertThat(testContact.getContactProveedoresHl()).isEqualTo(UPDATED_CONTACT_PROVEEDORES_HL);
        assertThat(testContact.getContactSocialNetworks()).isEqualTo(UPDATED_CONTACT_SOCIAL_NETWORKS);
        assertThat(testContact.getContactShareholdersHl()).isEqualTo(UPDATED_CONTACT_SHAREHOLDERS_HL);
    }

    @Test
    @Transactional
    void putNonExistingContact() throws Exception {
        int databaseSizeBeforeUpdate = contactRepository.findAll().size();
        contact.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restContactMockMvc
            .perform(
                put(ENTITY_API_URL_ID, contact.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(contact))
            )
            .andExpect(status().isBadRequest());

        // Validate the Contact in the database
        List<Contact> contactList = contactRepository.findAll();
        assertThat(contactList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchContact() throws Exception {
        int databaseSizeBeforeUpdate = contactRepository.findAll().size();
        contact.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContactMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(contact))
            )
            .andExpect(status().isBadRequest());

        // Validate the Contact in the database
        List<Contact> contactList = contactRepository.findAll();
        assertThat(contactList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamContact() throws Exception {
        int databaseSizeBeforeUpdate = contactRepository.findAll().size();
        contact.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContactMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(contact)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Contact in the database
        List<Contact> contactList = contactRepository.findAll();
        assertThat(contactList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateContactWithPatch() throws Exception {
        // Initialize the database
        contactRepository.saveAndFlush(contact);

        int databaseSizeBeforeUpdate = contactRepository.findAll().size();

        // Update the contact using partial update
        Contact partialUpdatedContact = new Contact();
        partialUpdatedContact.setId(contact.getId());

        partialUpdatedContact
            .contact3rdSectorHl(UPDATED_CONTACT_3RD_SECTOR_HL)
            .contactTeethHl(UPDATED_CONTACT_TEETH_HL)
            .contactEmployeeHl(UPDATED_CONTACT_EMPLOYEE_HL)
            .internationalContactHl(UPDATED_INTERNATIONAL_CONTACT_HL)
            .contactMediaComuncHl(UPDATED_CONTACT_MEDIA_COMUNC_HL)
            .contactOrgCommunityHl(UPDATED_CONTACT_ORG_COMMUNITY_HL)
            .contactRegulatoryOrganismsHl(UPDATED_CONTACT_REGULATORY_ORGANISMS_HL);

        restContactMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedContact.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedContact))
            )
            .andExpect(status().isOk());

        // Validate the Contact in the database
        List<Contact> contactList = contactRepository.findAll();
        assertThat(contactList).hasSize(databaseSizeBeforeUpdate);
        Contact testContact = contactList.get(contactList.size() - 1);
        assertThat(testContact.getContact3rdSectorHl()).isEqualTo(UPDATED_CONTACT_3RD_SECTOR_HL);
        assertThat(testContact.getContactAcademyHl()).isEqualTo(DEFAULT_CONTACT_ACADEMY_HL);
        assertThat(testContact.getContactTeethHl()).isEqualTo(UPDATED_CONTACT_TEETH_HL);
        assertThat(testContact.getContactEmployeeHl()).isEqualTo(UPDATED_CONTACT_EMPLOYEE_HL);
        assertThat(testContact.getContactEntFinanHl()).isEqualTo(DEFAULT_CONTACT_ENT_FINAN_HL);
        assertThat(testContact.getContactStatusHl()).isEqualTo(DEFAULT_CONTACT_STATUS_HL);
        assertThat(testContact.getInternationalContactHl()).isEqualTo(UPDATED_INTERNATIONAL_CONTACT_HL);
        assertThat(testContact.getContactMediaComuncHl()).isEqualTo(UPDATED_CONTACT_MEDIA_COMUNC_HL);
        assertThat(testContact.getContactOrgCommunityHl()).isEqualTo(UPDATED_CONTACT_ORG_COMMUNITY_HL);
        assertThat(testContact.getContactRegulatoryOrganismsHl()).isEqualTo(UPDATED_CONTACT_REGULATORY_ORGANISMS_HL);
        assertThat(testContact.getContactProveedoresHl()).isEqualTo(DEFAULT_CONTACT_PROVEEDORES_HL);
        assertThat(testContact.getContactSocialNetworks()).isEqualTo(DEFAULT_CONTACT_SOCIAL_NETWORKS);
        assertThat(testContact.getContactShareholdersHl()).isEqualTo(DEFAULT_CONTACT_SHAREHOLDERS_HL);
    }

    @Test
    @Transactional
    void fullUpdateContactWithPatch() throws Exception {
        // Initialize the database
        contactRepository.saveAndFlush(contact);

        int databaseSizeBeforeUpdate = contactRepository.findAll().size();

        // Update the contact using partial update
        Contact partialUpdatedContact = new Contact();
        partialUpdatedContact.setId(contact.getId());

        partialUpdatedContact
            .contact3rdSectorHl(UPDATED_CONTACT_3RD_SECTOR_HL)
            .contactAcademyHl(UPDATED_CONTACT_ACADEMY_HL)
            .contactTeethHl(UPDATED_CONTACT_TEETH_HL)
            .contactEmployeeHl(UPDATED_CONTACT_EMPLOYEE_HL)
            .contactEntFinanHl(UPDATED_CONTACT_ENT_FINAN_HL)
            .contactStatusHl(UPDATED_CONTACT_STATUS_HL)
            .internationalContactHl(UPDATED_INTERNATIONAL_CONTACT_HL)
            .contactMediaComuncHl(UPDATED_CONTACT_MEDIA_COMUNC_HL)
            .contactOrgCommunityHl(UPDATED_CONTACT_ORG_COMMUNITY_HL)
            .contactRegulatoryOrganismsHl(UPDATED_CONTACT_REGULATORY_ORGANISMS_HL)
            .contactProveedoresHl(UPDATED_CONTACT_PROVEEDORES_HL)
            .contactSocialNetworks(UPDATED_CONTACT_SOCIAL_NETWORKS)
            .contactShareholdersHl(UPDATED_CONTACT_SHAREHOLDERS_HL);

        restContactMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedContact.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedContact))
            )
            .andExpect(status().isOk());

        // Validate the Contact in the database
        List<Contact> contactList = contactRepository.findAll();
        assertThat(contactList).hasSize(databaseSizeBeforeUpdate);
        Contact testContact = contactList.get(contactList.size() - 1);
        assertThat(testContact.getContact3rdSectorHl()).isEqualTo(UPDATED_CONTACT_3RD_SECTOR_HL);
        assertThat(testContact.getContactAcademyHl()).isEqualTo(UPDATED_CONTACT_ACADEMY_HL);
        assertThat(testContact.getContactTeethHl()).isEqualTo(UPDATED_CONTACT_TEETH_HL);
        assertThat(testContact.getContactEmployeeHl()).isEqualTo(UPDATED_CONTACT_EMPLOYEE_HL);
        assertThat(testContact.getContactEntFinanHl()).isEqualTo(UPDATED_CONTACT_ENT_FINAN_HL);
        assertThat(testContact.getContactStatusHl()).isEqualTo(UPDATED_CONTACT_STATUS_HL);
        assertThat(testContact.getInternationalContactHl()).isEqualTo(UPDATED_INTERNATIONAL_CONTACT_HL);
        assertThat(testContact.getContactMediaComuncHl()).isEqualTo(UPDATED_CONTACT_MEDIA_COMUNC_HL);
        assertThat(testContact.getContactOrgCommunityHl()).isEqualTo(UPDATED_CONTACT_ORG_COMMUNITY_HL);
        assertThat(testContact.getContactRegulatoryOrganismsHl()).isEqualTo(UPDATED_CONTACT_REGULATORY_ORGANISMS_HL);
        assertThat(testContact.getContactProveedoresHl()).isEqualTo(UPDATED_CONTACT_PROVEEDORES_HL);
        assertThat(testContact.getContactSocialNetworks()).isEqualTo(UPDATED_CONTACT_SOCIAL_NETWORKS);
        assertThat(testContact.getContactShareholdersHl()).isEqualTo(UPDATED_CONTACT_SHAREHOLDERS_HL);
    }

    @Test
    @Transactional
    void patchNonExistingContact() throws Exception {
        int databaseSizeBeforeUpdate = contactRepository.findAll().size();
        contact.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restContactMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, contact.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(contact))
            )
            .andExpect(status().isBadRequest());

        // Validate the Contact in the database
        List<Contact> contactList = contactRepository.findAll();
        assertThat(contactList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchContact() throws Exception {
        int databaseSizeBeforeUpdate = contactRepository.findAll().size();
        contact.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContactMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(contact))
            )
            .andExpect(status().isBadRequest());

        // Validate the Contact in the database
        List<Contact> contactList = contactRepository.findAll();
        assertThat(contactList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamContact() throws Exception {
        int databaseSizeBeforeUpdate = contactRepository.findAll().size();
        contact.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContactMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(contact)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Contact in the database
        List<Contact> contactList = contactRepository.findAll();
        assertThat(contactList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteContact() throws Exception {
        // Initialize the database
        contactRepository.saveAndFlush(contact);

        int databaseSizeBeforeDelete = contactRepository.findAll().size();

        // Delete the contact
        restContactMockMvc
            .perform(delete(ENTITY_API_URL_ID, contact.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Contact> contactList = contactRepository.findAll();
        assertThat(contactList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
