package co.edu.sena.web.rest;

import co.edu.sena.domain.PriorityOrder;
import co.edu.sena.repository.PriorityOrderRepository;
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
 * REST controller for managing {@link co.edu.sena.domain.PriorityOrder}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class PriorityOrderResource {

    private final Logger log = LoggerFactory.getLogger(PriorityOrderResource.class);

    private static final String ENTITY_NAME = "priorityOrder";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PriorityOrderRepository priorityOrderRepository;

    public PriorityOrderResource(PriorityOrderRepository priorityOrderRepository) {
        this.priorityOrderRepository = priorityOrderRepository;
    }

    /**
     * {@code POST  /priority-orders} : Create a new priorityOrder.
     *
     * @param priorityOrder the priorityOrder to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new priorityOrder, or with status {@code 400 (Bad Request)} if the priorityOrder has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/priority-orders")
    public ResponseEntity<PriorityOrder> createPriorityOrder(@Valid @RequestBody PriorityOrder priorityOrder) throws URISyntaxException {
        log.debug("REST request to save PriorityOrder : {}", priorityOrder);
        if (priorityOrder.getId() != null) {
            throw new BadRequestAlertException("A new priorityOrder cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PriorityOrder result = priorityOrderRepository.save(priorityOrder);
        return ResponseEntity
            .created(new URI("/api/priority-orders/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /priority-orders/:id} : Updates an existing priorityOrder.
     *
     * @param id the id of the priorityOrder to save.
     * @param priorityOrder the priorityOrder to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated priorityOrder,
     * or with status {@code 400 (Bad Request)} if the priorityOrder is not valid,
     * or with status {@code 500 (Internal Server Error)} if the priorityOrder couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/priority-orders/{id}")
    public ResponseEntity<PriorityOrder> updatePriorityOrder(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody PriorityOrder priorityOrder
    ) throws URISyntaxException {
        log.debug("REST request to update PriorityOrder : {}, {}", id, priorityOrder);
        if (priorityOrder.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, priorityOrder.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!priorityOrderRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PriorityOrder result = priorityOrderRepository.save(priorityOrder);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, priorityOrder.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /priority-orders/:id} : Partial updates given fields of an existing priorityOrder, field will ignore if it is null
     *
     * @param id the id of the priorityOrder to save.
     * @param priorityOrder the priorityOrder to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated priorityOrder,
     * or with status {@code 400 (Bad Request)} if the priorityOrder is not valid,
     * or with status {@code 404 (Not Found)} if the priorityOrder is not found,
     * or with status {@code 500 (Internal Server Error)} if the priorityOrder couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/priority-orders/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PriorityOrder> partialUpdatePriorityOrder(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody PriorityOrder priorityOrder
    ) throws URISyntaxException {
        log.debug("REST request to partial update PriorityOrder partially : {}, {}", id, priorityOrder);
        if (priorityOrder.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, priorityOrder.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!priorityOrderRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PriorityOrder> result = priorityOrderRepository
            .findById(priorityOrder.getId())
            .map(existingPriorityOrder -> {
                if (priorityOrder.getQualifLogarit() != null) {
                    existingPriorityOrder.setQualifLogarit(priorityOrder.getQualifLogarit());
                }
                if (priorityOrder.getQualifAffiliate() != null) {
                    existingPriorityOrder.setQualifAffiliate(priorityOrder.getQualifAffiliate());
                }
                if (priorityOrder.getQualifDefinitive() != null) {
                    existingPriorityOrder.setQualifDefinitive(priorityOrder.getQualifDefinitive());
                }

                return existingPriorityOrder;
            })
            .map(priorityOrderRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, priorityOrder.getId().toString())
        );
    }

    /**
     * {@code GET  /priority-orders} : get all the priorityOrders.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of priorityOrders in body.
     */
    @GetMapping("/priority-orders")
    public List<PriorityOrder> getAllPriorityOrders(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all PriorityOrders");
        return priorityOrderRepository.findAllWithEagerRelationships();
    }

    /**
     * {@code GET  /priority-orders/:id} : get the "id" priorityOrder.
     *
     * @param id the id of the priorityOrder to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the priorityOrder, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/priority-orders/{id}")
    public ResponseEntity<PriorityOrder> getPriorityOrder(@PathVariable Long id) {
        log.debug("REST request to get PriorityOrder : {}", id);
        Optional<PriorityOrder> priorityOrder = priorityOrderRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(priorityOrder);
    }

    /**
     * {@code DELETE  /priority-orders/:id} : delete the "id" priorityOrder.
     *
     * @param id the id of the priorityOrder to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/priority-orders/{id}")
    public ResponseEntity<Void> deletePriorityOrder(@PathVariable Long id) {
        log.debug("REST request to delete PriorityOrder : {}", id);
        priorityOrderRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
