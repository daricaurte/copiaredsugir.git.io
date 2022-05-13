package co.edu.sena.web.rest;

import co.edu.sena.domain.Qualification;
import co.edu.sena.repository.QualificationRepository;
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
 * REST controller for managing {@link co.edu.sena.domain.Qualification}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class QualificationResource {

    private final Logger log = LoggerFactory.getLogger(QualificationResource.class);

    private static final String ENTITY_NAME = "qualification";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final QualificationRepository qualificationRepository;

    public QualificationResource(QualificationRepository qualificationRepository) {
        this.qualificationRepository = qualificationRepository;
    }

    /**
     * {@code POST  /qualifications} : Create a new qualification.
     *
     * @param qualification the qualification to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new qualification, or with status {@code 400 (Bad Request)} if the qualification has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/qualifications")
    public ResponseEntity<Qualification> createQualification(@Valid @RequestBody Qualification qualification) throws URISyntaxException {
        log.debug("REST request to save Qualification : {}", qualification);
        if (qualification.getId() != null) {
            throw new BadRequestAlertException("A new qualification cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Qualification result = qualificationRepository.save(qualification);
        return ResponseEntity
            .created(new URI("/api/qualifications/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /qualifications/:id} : Updates an existing qualification.
     *
     * @param id the id of the qualification to save.
     * @param qualification the qualification to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated qualification,
     * or with status {@code 400 (Bad Request)} if the qualification is not valid,
     * or with status {@code 500 (Internal Server Error)} if the qualification couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/qualifications/{id}")
    public ResponseEntity<Qualification> updateQualification(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Qualification qualification
    ) throws URISyntaxException {
        log.debug("REST request to update Qualification : {}, {}", id, qualification);
        if (qualification.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, qualification.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!qualificationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Qualification result = qualificationRepository.save(qualification);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, qualification.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /qualifications/:id} : Partial updates given fields of an existing qualification, field will ignore if it is null
     *
     * @param id the id of the qualification to save.
     * @param qualification the qualification to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated qualification,
     * or with status {@code 400 (Bad Request)} if the qualification is not valid,
     * or with status {@code 404 (Not Found)} if the qualification is not found,
     * or with status {@code 500 (Internal Server Error)} if the qualification couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/qualifications/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Qualification> partialUpdateQualification(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Qualification qualification
    ) throws URISyntaxException {
        log.debug("REST request to partial update Qualification partially : {}, {}", id, qualification);
        if (qualification.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, qualification.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!qualificationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Qualification> result = qualificationRepository
            .findById(qualification.getId())
            .map(existingQualification -> {
                if (qualification.getQualif1() != null) {
                    existingQualification.setQualif1(qualification.getQualif1());
                }
                if (qualification.getQualif2() != null) {
                    existingQualification.setQualif2(qualification.getQualif2());
                }
                if (qualification.getQualif3() != null) {
                    existingQualification.setQualif3(qualification.getQualif3());
                }
                if (qualification.getQualif4() != null) {
                    existingQualification.setQualif4(qualification.getQualif4());
                }
                if (qualification.getQualif1BussinessViability() != null) {
                    existingQualification.setQualif1BussinessViability(qualification.getQualif1BussinessViability());
                }

                return existingQualification;
            })
            .map(qualificationRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, qualification.getId().toString())
        );
    }

    /**
     * {@code GET  /qualifications} : get all the qualifications.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of qualifications in body.
     */
    @GetMapping("/qualifications")
    public List<Qualification> getAllQualifications(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all Qualifications");
        return qualificationRepository.findAllWithEagerRelationships();
    }

    /**
     * {@code GET  /qualifications/:id} : get the "id" qualification.
     *
     * @param id the id of the qualification to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the qualification, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/qualifications/{id}")
    public ResponseEntity<Qualification> getQualification(@PathVariable Long id) {
        log.debug("REST request to get Qualification : {}", id);
        Optional<Qualification> qualification = qualificationRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(qualification);
    }

    /**
     * {@code DELETE  /qualifications/:id} : delete the "id" qualification.
     *
     * @param id the id of the qualification to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/qualifications/{id}")
    public ResponseEntity<Void> deleteQualification(@PathVariable Long id) {
        log.debug("REST request to delete Qualification : {}", id);
        qualificationRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
