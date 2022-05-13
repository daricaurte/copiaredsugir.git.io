package co.edu.sena.web.rest;

import co.edu.sena.domain.CompanyType;
import co.edu.sena.repository.CompanyTypeRepository;
import co.edu.sena.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link co.edu.sena.domain.CompanyType}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class CompanyTypeResource {

    private final Logger log = LoggerFactory.getLogger(CompanyTypeResource.class);

    private static final String ENTITY_NAME = "companyType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CompanyTypeRepository companyTypeRepository;

    public CompanyTypeResource(CompanyTypeRepository companyTypeRepository) {
        this.companyTypeRepository = companyTypeRepository;
    }

    /**
     * {@code POST  /company-types} : Create a new companyType.
     *
     * @param companyType the companyType to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new companyType, or with status {@code 400 (Bad Request)} if the companyType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/company-types")
    public ResponseEntity<CompanyType> createCompanyType(@RequestBody CompanyType companyType) throws URISyntaxException {
        log.debug("REST request to save CompanyType : {}", companyType);
        if (companyType.getId() != null) {
            throw new BadRequestAlertException("A new companyType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CompanyType result = companyTypeRepository.save(companyType);
        return ResponseEntity
            .created(new URI("/api/company-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /company-types/:id} : Updates an existing companyType.
     *
     * @param id the id of the companyType to save.
     * @param companyType the companyType to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated companyType,
     * or with status {@code 400 (Bad Request)} if the companyType is not valid,
     * or with status {@code 500 (Internal Server Error)} if the companyType couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/company-types/{id}")
    public ResponseEntity<CompanyType> updateCompanyType(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CompanyType companyType
    ) throws URISyntaxException {
        log.debug("REST request to update CompanyType : {}, {}", id, companyType);
        if (companyType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, companyType.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!companyTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CompanyType result = companyTypeRepository.save(companyType);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, companyType.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /company-types/:id} : Partial updates given fields of an existing companyType, field will ignore if it is null
     *
     * @param id the id of the companyType to save.
     * @param companyType the companyType to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated companyType,
     * or with status {@code 400 (Bad Request)} if the companyType is not valid,
     * or with status {@code 404 (Not Found)} if the companyType is not found,
     * or with status {@code 500 (Internal Server Error)} if the companyType couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/company-types/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CompanyType> partialUpdateCompanyType(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CompanyType companyType
    ) throws URISyntaxException {
        log.debug("REST request to partial update CompanyType partially : {}, {}", id, companyType);
        if (companyType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, companyType.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!companyTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CompanyType> result = companyTypeRepository
            .findById(companyType.getId())
            .map(existingCompanyType -> {
                if (companyType.getPrimarySector() != null) {
                    existingCompanyType.setPrimarySector(companyType.getPrimarySector());
                }
                if (companyType.getSecondarySector() != null) {
                    existingCompanyType.setSecondarySector(companyType.getSecondarySector());
                }
                if (companyType.getTertiarySector() != null) {
                    existingCompanyType.setTertiarySector(companyType.getTertiarySector());
                }

                return existingCompanyType;
            })
            .map(companyTypeRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, companyType.getId().toString())
        );
    }

    /**
     * {@code GET  /company-types} : get all the companyTypes.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of companyTypes in body.
     */
    @GetMapping("/company-types")
    public List<CompanyType> getAllCompanyTypes(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all CompanyTypes");
        return companyTypeRepository.findAllWithEagerRelationships();
    }

    /**
     * {@code GET  /company-types/:id} : get the "id" companyType.
     *
     * @param id the id of the companyType to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the companyType, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/company-types/{id}")
    public ResponseEntity<CompanyType> getCompanyType(@PathVariable Long id) {
        log.debug("REST request to get CompanyType : {}", id);
        Optional<CompanyType> companyType = companyTypeRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(companyType);
    }

    /**
     * {@code DELETE  /company-types/:id} : delete the "id" companyType.
     *
     * @param id the id of the companyType to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/company-types/{id}")
    public ResponseEntity<Void> deleteCompanyType(@PathVariable Long id) {
        log.debug("REST request to delete CompanyType : {}", id);
        companyTypeRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
