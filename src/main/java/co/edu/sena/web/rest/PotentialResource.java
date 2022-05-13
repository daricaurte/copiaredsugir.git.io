package co.edu.sena.web.rest;

import co.edu.sena.domain.Potential;
import co.edu.sena.repository.PotentialRepository;
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
 * REST controller for managing {@link co.edu.sena.domain.Potential}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class PotentialResource {

    private final Logger log = LoggerFactory.getLogger(PotentialResource.class);

    private static final String ENTITY_NAME = "potential";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PotentialRepository potentialRepository;

    public PotentialResource(PotentialRepository potentialRepository) {
        this.potentialRepository = potentialRepository;
    }

    /**
     * {@code POST  /potentials} : Create a new potential.
     *
     * @param potential the potential to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new potential, or with status {@code 400 (Bad Request)} if the potential has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/potentials")
    public ResponseEntity<Potential> createPotential(@Valid @RequestBody Potential potential) throws URISyntaxException {
        log.debug("REST request to save Potential : {}", potential);
        if (potential.getId() != null) {
            throw new BadRequestAlertException("A new potential cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Potential result = potentialRepository.save(potential);
        return ResponseEntity
            .created(new URI("/api/potentials/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /potentials/:id} : Updates an existing potential.
     *
     * @param id the id of the potential to save.
     * @param potential the potential to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated potential,
     * or with status {@code 400 (Bad Request)} if the potential is not valid,
     * or with status {@code 500 (Internal Server Error)} if the potential couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/potentials/{id}")
    public ResponseEntity<Potential> updatePotential(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Potential potential
    ) throws URISyntaxException {
        log.debug("REST request to update Potential : {}, {}", id, potential);
        if (potential.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, potential.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!potentialRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Potential result = potentialRepository.save(potential);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, potential.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /potentials/:id} : Partial updates given fields of an existing potential, field will ignore if it is null
     *
     * @param id the id of the potential to save.
     * @param potential the potential to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated potential,
     * or with status {@code 400 (Bad Request)} if the potential is not valid,
     * or with status {@code 404 (Not Found)} if the potential is not found,
     * or with status {@code 500 (Internal Server Error)} if the potential couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/potentials/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Potential> partialUpdatePotential(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Potential potential
    ) throws URISyntaxException {
        log.debug("REST request to partial update Potential partially : {}, {}", id, potential);
        if (potential.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, potential.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!potentialRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Potential> result = potentialRepository
            .findById(potential.getId())
            .map(existingPotential -> {
                if (potential.getExchangePartner() != null) {
                    existingPotential.setExchangePartner(potential.getExchangePartner());
                }
                if (potential.getConsultant() != null) {
                    existingPotential.setConsultant(potential.getConsultant());
                }
                if (potential.getClient() != null) {
                    existingPotential.setClient(potential.getClient());
                }
                if (potential.getCollaborator() != null) {
                    existingPotential.setCollaborator(potential.getCollaborator());
                }
                if (potential.getProvider() != null) {
                    existingPotential.setProvider(potential.getProvider());
                }
                if (potential.getReferrer() != null) {
                    existingPotential.setReferrer(potential.getReferrer());
                }

                return existingPotential;
            })
            .map(potentialRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, potential.getId().toString())
        );
    }

    /**
     * {@code GET  /potentials} : get all the potentials.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of potentials in body.
     */
    @GetMapping("/potentials")
    public List<Potential> getAllPotentials(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all Potentials");
        return potentialRepository.findAllWithEagerRelationships();
    }

    /**
     * {@code GET  /potentials/:id} : get the "id" potential.
     *
     * @param id the id of the potential to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the potential, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/potentials/{id}")
    public ResponseEntity<Potential> getPotential(@PathVariable Long id) {
        log.debug("REST request to get Potential : {}", id);
        Optional<Potential> potential = potentialRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(potential);
    }

    /**
     * {@code DELETE  /potentials/:id} : delete the "id" potential.
     *
     * @param id the id of the potential to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/potentials/{id}")
    public ResponseEntity<Void> deletePotential(@PathVariable Long id) {
        log.debug("REST request to delete Potential : {}", id);
        potentialRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
