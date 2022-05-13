package co.edu.sena.web.rest;

import co.edu.sena.domain.Networks;
import co.edu.sena.repository.NetworksRepository;
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
 * REST controller for managing {@link co.edu.sena.domain.Networks}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class NetworksResource {

    private final Logger log = LoggerFactory.getLogger(NetworksResource.class);

    private static final String ENTITY_NAME = "networks";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NetworksRepository networksRepository;

    public NetworksResource(NetworksRepository networksRepository) {
        this.networksRepository = networksRepository;
    }

    /**
     * {@code POST  /networks} : Create a new networks.
     *
     * @param networks the networks to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new networks, or with status {@code 400 (Bad Request)} if the networks has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/networks")
    public ResponseEntity<Networks> createNetworks(@Valid @RequestBody Networks networks) throws URISyntaxException {
        log.debug("REST request to save Networks : {}", networks);
        if (networks.getId() != null) {
            throw new BadRequestAlertException("A new networks cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Networks result = networksRepository.save(networks);
        return ResponseEntity
            .created(new URI("/api/networks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /networks/:id} : Updates an existing networks.
     *
     * @param id the id of the networks to save.
     * @param networks the networks to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated networks,
     * or with status {@code 400 (Bad Request)} if the networks is not valid,
     * or with status {@code 500 (Internal Server Error)} if the networks couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/networks/{id}")
    public ResponseEntity<Networks> updateNetworks(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Networks networks
    ) throws URISyntaxException {
        log.debug("REST request to update Networks : {}, {}", id, networks);
        if (networks.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, networks.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!networksRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Networks result = networksRepository.save(networks);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, networks.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /networks/:id} : Partial updates given fields of an existing networks, field will ignore if it is null
     *
     * @param id the id of the networks to save.
     * @param networks the networks to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated networks,
     * or with status {@code 400 (Bad Request)} if the networks is not valid,
     * or with status {@code 404 (Not Found)} if the networks is not found,
     * or with status {@code 500 (Internal Server Error)} if the networks couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/networks/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Networks> partialUpdateNetworks(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Networks networks
    ) throws URISyntaxException {
        log.debug("REST request to partial update Networks partially : {}, {}", id, networks);
        if (networks.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, networks.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!networksRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Networks> result = networksRepository
            .findById(networks.getId())
            .map(existingNetworks -> {
                if (networks.getNetworks3rdSectorHl() != null) {
                    existingNetworks.setNetworks3rdSectorHl(networks.getNetworks3rdSectorHl());
                }
                if (networks.getAcademicNetworksHl() != null) {
                    existingNetworks.setAcademicNetworksHl(networks.getAcademicNetworksHl());
                }
                if (networks.getCustomerNetworksHl() != null) {
                    existingNetworks.setCustomerNetworksHl(networks.getCustomerNetworksHl());
                }
                if (networks.getEmployeeNetworksHl() != null) {
                    existingNetworks.setEmployeeNetworksHl(networks.getEmployeeNetworksHl());
                }
                if (networks.getNetworksEntFinanHl() != null) {
                    existingNetworks.setNetworksEntFinanHl(networks.getNetworksEntFinanHl());
                }
                if (networks.getStateNetworksHl() != null) {
                    existingNetworks.setStateNetworksHl(networks.getStateNetworksHl());
                }
                if (networks.getInternationalNetworksHl() != null) {
                    existingNetworks.setInternationalNetworksHl(networks.getInternationalNetworksHl());
                }
                if (networks.getMediaNetworksComuncHl() != null) {
                    existingNetworks.setMediaNetworksComuncHl(networks.getMediaNetworksComuncHl());
                }
                if (networks.getCommunityOrgNetworksHl() != null) {
                    existingNetworks.setCommunityOrgNetworksHl(networks.getCommunityOrgNetworksHl());
                }
                if (networks.getRegulatoryOrganismsNetworks() != null) {
                    existingNetworks.setRegulatoryOrganismsNetworks(networks.getRegulatoryOrganismsNetworks());
                }
                if (networks.getNetworksProvidersHl() != null) {
                    existingNetworks.setNetworksProvidersHl(networks.getNetworksProvidersHl());
                }
                if (networks.getSocialNetworks() != null) {
                    existingNetworks.setSocialNetworks(networks.getSocialNetworks());
                }
                if (networks.getShareholderNetworksHl() != null) {
                    existingNetworks.setShareholderNetworksHl(networks.getShareholderNetworksHl());
                }

                return existingNetworks;
            })
            .map(networksRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, networks.getId().toString())
        );
    }

    /**
     * {@code GET  /networks} : get all the networks.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of networks in body.
     */
    @GetMapping("/networks")
    public List<Networks> getAllNetworks(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all Networks");
        return networksRepository.findAllWithEagerRelationships();
    }

    /**
     * {@code GET  /networks/:id} : get the "id" networks.
     *
     * @param id the id of the networks to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the networks, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/networks/{id}")
    public ResponseEntity<Networks> getNetworks(@PathVariable Long id) {
        log.debug("REST request to get Networks : {}", id);
        Optional<Networks> networks = networksRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(networks);
    }

    /**
     * {@code DELETE  /networks/:id} : delete the "id" networks.
     *
     * @param id the id of the networks to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/networks/{id}")
    public ResponseEntity<Void> deleteNetworks(@PathVariable Long id) {
        log.debug("REST request to delete Networks : {}", id);
        networksRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
