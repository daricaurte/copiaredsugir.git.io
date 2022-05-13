package co.edu.sena.web.rest;

import co.edu.sena.domain.Affiliate;
import co.edu.sena.repository.AffiliateRepository;
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
 * REST controller for managing {@link co.edu.sena.domain.Affiliate}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class AffiliateResource {

    private final Logger log = LoggerFactory.getLogger(AffiliateResource.class);

    private static final String ENTITY_NAME = "affiliate";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AffiliateRepository affiliateRepository;

    public AffiliateResource(AffiliateRepository affiliateRepository) {
        this.affiliateRepository = affiliateRepository;
    }

    /**
     * {@code POST  /affiliates} : Create a new affiliate.
     *
     * @param affiliate the affiliate to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new affiliate, or with status {@code 400 (Bad Request)} if the affiliate has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/affiliates")
    public ResponseEntity<Affiliate> createAffiliate(@Valid @RequestBody Affiliate affiliate) throws URISyntaxException {
        log.debug("REST request to save Affiliate : {}", affiliate);
        if (affiliate.getId() != null) {
            throw new BadRequestAlertException("A new affiliate cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Affiliate result = affiliateRepository.save(affiliate);
        return ResponseEntity
            .created(new URI("/api/affiliates/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /affiliates/:id} : Updates an existing affiliate.
     *
     * @param id the id of the affiliate to save.
     * @param affiliate the affiliate to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated affiliate,
     * or with status {@code 400 (Bad Request)} if the affiliate is not valid,
     * or with status {@code 500 (Internal Server Error)} if the affiliate couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/affiliates/{id}")
    public ResponseEntity<Affiliate> updateAffiliate(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Affiliate affiliate
    ) throws URISyntaxException {
        log.debug("REST request to update Affiliate : {}, {}", id, affiliate);
        if (affiliate.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, affiliate.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!affiliateRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Affiliate result = affiliateRepository.save(affiliate);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, affiliate.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /affiliates/:id} : Partial updates given fields of an existing affiliate, field will ignore if it is null
     *
     * @param id the id of the affiliate to save.
     * @param affiliate the affiliate to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated affiliate,
     * or with status {@code 400 (Bad Request)} if the affiliate is not valid,
     * or with status {@code 404 (Not Found)} if the affiliate is not found,
     * or with status {@code 500 (Internal Server Error)} if the affiliate couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/affiliates/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Affiliate> partialUpdateAffiliate(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Affiliate affiliate
    ) throws URISyntaxException {
        log.debug("REST request to partial update Affiliate partially : {}, {}", id, affiliate);
        if (affiliate.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, affiliate.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!affiliateRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Affiliate> result = affiliateRepository
            .findById(affiliate.getId())
            .map(existingAffiliate -> {
                if (affiliate.getDocumentNumber() != null) {
                    existingAffiliate.setDocumentNumber(affiliate.getDocumentNumber());
                }
                if (affiliate.getFirstName() != null) {
                    existingAffiliate.setFirstName(affiliate.getFirstName());
                }
                if (affiliate.getSecondName() != null) {
                    existingAffiliate.setSecondName(affiliate.getSecondName());
                }
                if (affiliate.getFirstLastName() != null) {
                    existingAffiliate.setFirstLastName(affiliate.getFirstLastName());
                }
                if (affiliate.getSecondLastName() != null) {
                    existingAffiliate.setSecondLastName(affiliate.getSecondLastName());
                }
                if (affiliate.getNeighborhood() != null) {
                    existingAffiliate.setNeighborhood(affiliate.getNeighborhood());
                }
                if (affiliate.getCellPhoneNumber() != null) {
                    existingAffiliate.setCellPhoneNumber(affiliate.getCellPhoneNumber());
                }
                if (affiliate.getCity() != null) {
                    existingAffiliate.setCity(affiliate.getCity());
                }
                if (affiliate.getDepartment() != null) {
                    existingAffiliate.setDepartment(affiliate.getDepartment());
                }
                if (affiliate.getEmail() != null) {
                    existingAffiliate.setEmail(affiliate.getEmail());
                }
                if (affiliate.getCallsign() != null) {
                    existingAffiliate.setCallsign(affiliate.getCallsign());
                }
                if (affiliate.getCountry() != null) {
                    existingAffiliate.setCountry(affiliate.getCountry());
                }
                if (affiliate.getPhoneNumber() != null) {
                    existingAffiliate.setPhoneNumber(affiliate.getPhoneNumber());
                }
                if (affiliate.getRol() != null) {
                    existingAffiliate.setRol(affiliate.getRol());
                }

                return existingAffiliate;
            })
            .map(affiliateRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, affiliate.getId().toString())
        );
    }

    /**
     * {@code GET  /affiliates} : get all the affiliates.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of affiliates in body.
     */
    @GetMapping("/affiliates")
    public List<Affiliate> getAllAffiliates(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all Affiliates");
        return affiliateRepository.findAllWithEagerRelationships();
    }

    /**
     * {@code GET  /affiliates/:id} : get the "id" affiliate.
     *
     * @param id the id of the affiliate to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the affiliate, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/affiliates/{id}")
    public ResponseEntity<Affiliate> getAffiliate(@PathVariable Long id) {
        log.debug("REST request to get Affiliate : {}", id);
        Optional<Affiliate> affiliate = affiliateRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(affiliate);
    }

    /**
     * {@code DELETE  /affiliates/:id} : delete the "id" affiliate.
     *
     * @param id the id of the affiliate to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/affiliates/{id}")
    public ResponseEntity<Void> deleteAffiliate(@PathVariable Long id) {
        log.debug("REST request to delete Affiliate : {}", id);
        affiliateRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
