package io.github.shuoros.ecommercy.web.rest;

import io.github.shuoros.ecommercy.domain.Attribute;
import io.github.shuoros.ecommercy.repository.AttributeRepository;
import io.github.shuoros.ecommercy.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link io.github.shuoros.ecommercy.domain.Attribute}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class AttributeResource {

    private final Logger log = LoggerFactory.getLogger(AttributeResource.class);

    private static final String ENTITY_NAME = "attribute";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AttributeRepository attributeRepository;

    public AttributeResource(AttributeRepository attributeRepository) {
        this.attributeRepository = attributeRepository;
    }

    /**
     * {@code POST  /attributes} : Create a new attribute.
     *
     * @param attribute the attribute to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new attribute, or with status {@code 400 (Bad Request)} if the attribute has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/attributes")
    public ResponseEntity<Attribute> createAttribute(@RequestBody Attribute attribute) throws URISyntaxException {
        log.debug("REST request to save Attribute : {}", attribute);
        if (attribute.getId() != null) {
            throw new BadRequestAlertException("A new attribute cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Attribute result = attributeRepository.save(attribute);
        return ResponseEntity
            .created(new URI("/api/attributes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /attributes/:id} : Updates an existing attribute.
     *
     * @param id the id of the attribute to save.
     * @param attribute the attribute to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated attribute,
     * or with status {@code 400 (Bad Request)} if the attribute is not valid,
     * or with status {@code 500 (Internal Server Error)} if the attribute couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/attributes/{id}")
    public ResponseEntity<Attribute> updateAttribute(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Attribute attribute
    ) throws URISyntaxException {
        log.debug("REST request to update Attribute : {}, {}", id, attribute);
        if (attribute.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, attribute.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!attributeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Attribute result = attributeRepository.save(attribute);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, attribute.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /attributes/:id} : Partial updates given fields of an existing attribute, field will ignore if it is null
     *
     * @param id the id of the attribute to save.
     * @param attribute the attribute to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated attribute,
     * or with status {@code 400 (Bad Request)} if the attribute is not valid,
     * or with status {@code 404 (Not Found)} if the attribute is not found,
     * or with status {@code 500 (Internal Server Error)} if the attribute couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/attributes/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Attribute> partialUpdateAttribute(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Attribute attribute
    ) throws URISyntaxException {
        log.debug("REST request to partial update Attribute partially : {}, {}", id, attribute);
        if (attribute.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, attribute.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!attributeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Attribute> result = attributeRepository
            .findById(attribute.getId())
            .map(existingAttribute -> {
                if (attribute.getKey() != null) {
                    existingAttribute.setKey(attribute.getKey());
                }
                if (attribute.getValue() != null) {
                    existingAttribute.setValue(attribute.getValue());
                }

                return existingAttribute;
            })
            .map(attributeRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, attribute.getId().toString())
        );
    }

    /**
     * {@code GET  /attributes} : get all the attributes.
     *
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of attributes in body.
     */
    @GetMapping("/attributes")
    public List<Attribute> getAllAttributes(@RequestParam(required = false) String filter) {
        if ("productmainattribute-is-null".equals(filter)) {
            log.debug("REST request to get all Attributes where productMainAttribute is null");
            return StreamSupport
                .stream(attributeRepository.findAll().spliterator(), false)
                .filter(attribute -> attribute.getProductMainAttribute() == null)
                .collect(Collectors.toList());
        }
        log.debug("REST request to get all Attributes");
        return attributeRepository.findAll();
    }

    /**
     * {@code GET  /attributes/:id} : get the "id" attribute.
     *
     * @param id the id of the attribute to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the attribute, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/attributes/{id}")
    public ResponseEntity<Attribute> getAttribute(@PathVariable Long id) {
        log.debug("REST request to get Attribute : {}", id);
        Optional<Attribute> attribute = attributeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(attribute);
    }

    /**
     * {@code DELETE  /attributes/:id} : delete the "id" attribute.
     *
     * @param id the id of the attribute to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/attributes/{id}")
    public ResponseEntity<Void> deleteAttribute(@PathVariable Long id) {
        log.debug("REST request to delete Attribute : {}", id);
        attributeRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
