package co.edu.sena.web.rest;

import co.edu.sena.domain.ContactData;
import co.edu.sena.repository.ContactDataRepository;
import co.edu.sena.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link co.edu.sena.domain.ContactData}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ContactDataResource {

    private final Logger log = LoggerFactory.getLogger(ContactDataResource.class);

    private static final String ENTITY_NAME = "contactData";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ContactDataRepository contactDataRepository;

    public ContactDataResource(ContactDataRepository contactDataRepository) {
        this.contactDataRepository = contactDataRepository;
    }

    /**
     * {@code POST  /contact-data} : Create a new contactData.
     *
     * @param contactData the contactData to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new contactData, or with status {@code 400 (Bad Request)} if the contactData has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/contact-data")
    public ResponseEntity<ContactData> createContactData(@Valid @RequestBody ContactData contactData) throws URISyntaxException {
        log.debug("REST request to save ContactData : {}", contactData);
        if (contactData.getId() != null) {
            throw new BadRequestAlertException("A new contactData cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ContactData result = contactDataRepository.save(contactData);
        return ResponseEntity
            .created(new URI("/api/contact-data/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /contact-data/:id} : Updates an existing contactData.
     *
     * @param id the id of the contactData to save.
     * @param contactData the contactData to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated contactData,
     * or with status {@code 400 (Bad Request)} if the contactData is not valid,
     * or with status {@code 500 (Internal Server Error)} if the contactData couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/contact-data/{id}")
    public ResponseEntity<ContactData> updateContactData(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ContactData contactData
    ) throws URISyntaxException {
        log.debug("REST request to update ContactData : {}, {}", id, contactData);
        if (contactData.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, contactData.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!contactDataRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ContactData result = contactDataRepository.save(contactData);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, contactData.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /contact-data/:id} : Partial updates given fields of an existing contactData, field will ignore if it is null
     *
     * @param id the id of the contactData to save.
     * @param contactData the contactData to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated contactData,
     * or with status {@code 400 (Bad Request)} if the contactData is not valid,
     * or with status {@code 404 (Not Found)} if the contactData is not found,
     * or with status {@code 500 (Internal Server Error)} if the contactData couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/contact-data/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ContactData> partialUpdateContactData(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ContactData contactData
    ) throws URISyntaxException {
        log.debug("REST request to partial update ContactData partially : {}, {}", id, contactData);
        if (contactData.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, contactData.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!contactDataRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ContactData> result = contactDataRepository
            .findById(contactData.getId())
            .map(existingContactData -> {
                if (contactData.getDocumentNumberContact() != null) {
                    existingContactData.setDocumentNumberContact(contactData.getDocumentNumberContact());
                }
                if (contactData.getCellPhoneNumber() != null) {
                    existingContactData.setCellPhoneNumber(contactData.getCellPhoneNumber());
                }
                if (contactData.getCountry() != null) {
                    existingContactData.setCountry(contactData.getCountry());
                }
                if (contactData.getCity() != null) {
                    existingContactData.setCity(contactData.getCity());
                }
                if (contactData.getEmail() != null) {
                    existingContactData.setEmail(contactData.getEmail());
                }
                if (contactData.getDepartment() != null) {
                    existingContactData.setDepartment(contactData.getDepartment());
                }
                if (contactData.getFirstName() != null) {
                    existingContactData.setFirstName(contactData.getFirstName());
                }
                if (contactData.getSecondName() != null) {
                    existingContactData.setSecondName(contactData.getSecondName());
                }
                if (contactData.getFirstLastName() != null) {
                    existingContactData.setFirstLastName(contactData.getFirstLastName());
                }
                if (contactData.getSecondLastName() != null) {
                    existingContactData.setSecondLastName(contactData.getSecondLastName());
                }
                if (contactData.getNeighborhood() != null) {
                    existingContactData.setNeighborhood(contactData.getNeighborhood());
                }

                return existingContactData;
            })
            .map(contactDataRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, contactData.getId().toString())
        );
    }

    /**
     * {@code GET  /contact-data} : get all the contactData.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of contactData in body.
     */
    @GetMapping("/contact-data")
    public List<ContactData> getAllContactData(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all ContactData");
        return contactDataRepository.findAllWithEagerRelationships();
    }

    /**
     * {@code GET  /contact-data/:id} : get the "id" contactData.
     *
     * @param id the id of the contactData to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the contactData, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/contact-data/{id}")
    public ResponseEntity<ContactData> getContactData(@PathVariable Long id) {
        log.debug("REST request to get ContactData : {}", id);
        Optional<ContactData> contactData = contactDataRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(contactData);
    }

    /**
     * {@code DELETE  /contact-data/:id} : delete the "id" contactData.
     *
     * @param id the id of the contactData to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/contact-data/{id}")
    public ResponseEntity<Void> deleteContactData(@PathVariable Long id) {
        log.debug("REST request to delete ContactData : {}", id);
        contactDataRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
