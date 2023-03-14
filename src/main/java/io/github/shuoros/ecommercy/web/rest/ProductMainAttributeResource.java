package io.github.shuoros.ecommercy.web.rest;

import io.github.shuoros.ecommercy.domain.ProductMainAttribute;
import io.github.shuoros.ecommercy.repository.ProductMainAttributeRepository;
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
 * REST controller for managing {@link io.github.shuoros.ecommercy.domain.ProductMainAttribute}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ProductMainAttributeResource {

    private final Logger log = LoggerFactory.getLogger(ProductMainAttributeResource.class);

    private static final String ENTITY_NAME = "productMainAttribute";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProductMainAttributeRepository productMainAttributeRepository;

    public ProductMainAttributeResource(ProductMainAttributeRepository productMainAttributeRepository) {
        this.productMainAttributeRepository = productMainAttributeRepository;
    }

    /**
     * {@code POST  /product-main-attributes} : Create a new productMainAttribute.
     *
     * @param productMainAttribute the productMainAttribute to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new productMainAttribute, or with status {@code 400 (Bad Request)} if the productMainAttribute has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/product-main-attributes")
    public ResponseEntity<ProductMainAttribute> createProductMainAttribute(@RequestBody ProductMainAttribute productMainAttribute)
        throws URISyntaxException {
        log.debug("REST request to save ProductMainAttribute : {}", productMainAttribute);
        if (productMainAttribute.getId() != null) {
            throw new BadRequestAlertException("A new productMainAttribute cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProductMainAttribute result = productMainAttributeRepository.save(productMainAttribute);
        return ResponseEntity
            .created(new URI("/api/product-main-attributes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /product-main-attributes/:id} : Updates an existing productMainAttribute.
     *
     * @param id the id of the productMainAttribute to save.
     * @param productMainAttribute the productMainAttribute to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productMainAttribute,
     * or with status {@code 400 (Bad Request)} if the productMainAttribute is not valid,
     * or with status {@code 500 (Internal Server Error)} if the productMainAttribute couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/product-main-attributes/{id}")
    public ResponseEntity<ProductMainAttribute> updateProductMainAttribute(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ProductMainAttribute productMainAttribute
    ) throws URISyntaxException {
        log.debug("REST request to update ProductMainAttribute : {}, {}", id, productMainAttribute);
        if (productMainAttribute.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, productMainAttribute.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!productMainAttributeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ProductMainAttribute result = productMainAttributeRepository.save(productMainAttribute);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, productMainAttribute.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /product-main-attributes/:id} : Partial updates given fields of an existing productMainAttribute, field will ignore if it is null
     *
     * @param id the id of the productMainAttribute to save.
     * @param productMainAttribute the productMainAttribute to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productMainAttribute,
     * or with status {@code 400 (Bad Request)} if the productMainAttribute is not valid,
     * or with status {@code 404 (Not Found)} if the productMainAttribute is not found,
     * or with status {@code 500 (Internal Server Error)} if the productMainAttribute couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/product-main-attributes/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ProductMainAttribute> partialUpdateProductMainAttribute(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ProductMainAttribute productMainAttribute
    ) throws URISyntaxException {
        log.debug("REST request to partial update ProductMainAttribute partially : {}, {}", id, productMainAttribute);
        if (productMainAttribute.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, productMainAttribute.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!productMainAttributeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ProductMainAttribute> result = productMainAttributeRepository
            .findById(productMainAttribute.getId())
            .map(existingProductMainAttribute -> {
                if (productMainAttribute.getPrice() != null) {
                    existingProductMainAttribute.setPrice(productMainAttribute.getPrice());
                }
                if (productMainAttribute.getDiscount() != null) {
                    existingProductMainAttribute.setDiscount(productMainAttribute.getDiscount());
                }

                return existingProductMainAttribute;
            })
            .map(productMainAttributeRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, productMainAttribute.getId().toString())
        );
    }

    /**
     * {@code GET  /product-main-attributes} : get all the productMainAttributes.
     *
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of productMainAttributes in body.
     */
    @GetMapping("/product-main-attributes")
    public List<ProductMainAttribute> getAllProductMainAttributes(@RequestParam(required = false) String filter) {
        if ("product-is-null".equals(filter)) {
            log.debug("REST request to get all ProductMainAttributes where product is null");
            return StreamSupport
                .stream(productMainAttributeRepository.findAll().spliterator(), false)
                .filter(productMainAttribute -> productMainAttribute.getProduct() == null)
                .collect(Collectors.toList());
        }
        log.debug("REST request to get all ProductMainAttributes");
        return productMainAttributeRepository.findAll();
    }

    /**
     * {@code GET  /product-main-attributes/:id} : get the "id" productMainAttribute.
     *
     * @param id the id of the productMainAttribute to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the productMainAttribute, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/product-main-attributes/{id}")
    public ResponseEntity<ProductMainAttribute> getProductMainAttribute(@PathVariable Long id) {
        log.debug("REST request to get ProductMainAttribute : {}", id);
        Optional<ProductMainAttribute> productMainAttribute = productMainAttributeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(productMainAttribute);
    }

    /**
     * {@code DELETE  /product-main-attributes/:id} : delete the "id" productMainAttribute.
     *
     * @param id the id of the productMainAttribute to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/product-main-attributes/{id}")
    public ResponseEntity<Void> deleteProductMainAttribute(@PathVariable Long id) {
        log.debug("REST request to delete ProductMainAttribute : {}", id);
        productMainAttributeRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
