package co.edu.sena.web.rest;

import co.edu.sena.domain.Relation;
import co.edu.sena.repository.RelationRepository;
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
 * REST controller for managing {@link co.edu.sena.domain.Relation}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class RelationResource {

    private final Logger log = LoggerFactory.getLogger(RelationResource.class);

    private static final String ENTITY_NAME = "relation";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RelationRepository relationRepository;

    public RelationResource(RelationRepository relationRepository) {
        this.relationRepository = relationRepository;
    }

    /**
     * {@code POST  /relations} : Create a new relation.
     *
     * @param relation the relation to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new relation, or with status {@code 400 (Bad Request)} if the relation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/relations")
    public ResponseEntity<Relation> createRelation(@Valid @RequestBody Relation relation) throws URISyntaxException {
        log.debug("REST request to save Relation : {}", relation);
        if (relation.getId() != null) {
            throw new BadRequestAlertException("A new relation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Relation result = relationRepository.save(relation);
        return ResponseEntity
            .created(new URI("/api/relations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /relations/:id} : Updates an existing relation.
     *
     * @param id the id of the relation to save.
     * @param relation the relation to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated relation,
     * or with status {@code 400 (Bad Request)} if the relation is not valid,
     * or with status {@code 500 (Internal Server Error)} if the relation couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/relations/{id}")
    public ResponseEntity<Relation> updateRelation(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Relation relation
    ) throws URISyntaxException {
        log.debug("REST request to update Relation : {}, {}", id, relation);
        if (relation.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, relation.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!relationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Relation result = relationRepository.save(relation);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, relation.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /relations/:id} : Partial updates given fields of an existing relation, field will ignore if it is null
     *
     * @param id the id of the relation to save.
     * @param relation the relation to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated relation,
     * or with status {@code 400 (Bad Request)} if the relation is not valid,
     * or with status {@code 404 (Not Found)} if the relation is not found,
     * or with status {@code 500 (Internal Server Error)} if the relation couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/relations/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Relation> partialUpdateRelation(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Relation relation
    ) throws URISyntaxException {
        log.debug("REST request to partial update Relation partially : {}, {}", id, relation);
        if (relation.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, relation.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!relationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Relation> result = relationRepository
            .findById(relation.getId())
            .map(existingRelation -> {
                if (relation.getFamilyRelation() != null) {
                    existingRelation.setFamilyRelation(relation.getFamilyRelation());
                }
                if (relation.getWorkRelation() != null) {
                    existingRelation.setWorkRelation(relation.getWorkRelation());
                }
                if (relation.getPersonalRelation() != null) {
                    existingRelation.setPersonalRelation(relation.getPersonalRelation());
                }

                return existingRelation;
            })
            .map(relationRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, relation.getId().toString())
        );
    }

    /**
     * {@code GET  /relations} : get all the relations.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of relations in body.
     */
    @GetMapping("/relations")
    public List<Relation> getAllRelations(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all Relations");
        return relationRepository.findAllWithEagerRelationships();
    }

    /**
     * {@code GET  /relations/:id} : get the "id" relation.
     *
     * @param id the id of the relation to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the relation, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/relations/{id}")
    public ResponseEntity<Relation> getRelation(@PathVariable Long id) {
        log.debug("REST request to get Relation : {}", id);
        Optional<Relation> relation = relationRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(relation);
    }

    /**
     * {@code DELETE  /relations/:id} : delete the "id" relation.
     *
     * @param id the id of the relation to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/relations/{id}")
    public ResponseEntity<Void> deleteRelation(@PathVariable Long id) {
        log.debug("REST request to delete Relation : {}", id);
        relationRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
