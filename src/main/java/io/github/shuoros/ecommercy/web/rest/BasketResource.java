package io.github.shuoros.ecommercy.web.rest;

import io.github.shuoros.ecommercy.domain.Basket;
import io.github.shuoros.ecommercy.repository.BasketRepository;
import io.github.shuoros.ecommercy.web.rest.errors.BadRequestAlertException;
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
 * REST controller for managing {@link io.github.shuoros.ecommercy.domain.Basket}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class BasketResource {

    private final Logger log = LoggerFactory.getLogger(BasketResource.class);

    private static final String ENTITY_NAME = "basket";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BasketRepository basketRepository;

    public BasketResource(BasketRepository basketRepository) {
        this.basketRepository = basketRepository;
    }

    /**
     * {@code POST  /baskets} : Create a new basket.
     *
     * @param basket the basket to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new basket, or with status {@code 400 (Bad Request)} if the basket has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/baskets")
    public ResponseEntity<Basket> createBasket(@RequestBody Basket basket) throws URISyntaxException {
        log.debug("REST request to save Basket : {}", basket);
        if (basket.getId() != null) {
            throw new BadRequestAlertException("A new basket cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Basket result = basketRepository.save(basket);
        return ResponseEntity
            .created(new URI("/api/baskets/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /baskets/:id} : Updates an existing basket.
     *
     * @param id the id of the basket to save.
     * @param basket the basket to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated basket,
     * or with status {@code 400 (Bad Request)} if the basket is not valid,
     * or with status {@code 500 (Internal Server Error)} if the basket couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/baskets/{id}")
    public ResponseEntity<Basket> updateBasket(@PathVariable(value = "id", required = false) final Long id, @RequestBody Basket basket)
        throws URISyntaxException {
        log.debug("REST request to update Basket : {}, {}", id, basket);
        if (basket.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, basket.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!basketRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Basket result = basketRepository.save(basket);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, basket.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /baskets/:id} : Partial updates given fields of an existing basket, field will ignore if it is null
     *
     * @param id the id of the basket to save.
     * @param basket the basket to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated basket,
     * or with status {@code 400 (Bad Request)} if the basket is not valid,
     * or with status {@code 404 (Not Found)} if the basket is not found,
     * or with status {@code 500 (Internal Server Error)} if the basket couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/baskets/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Basket> partialUpdateBasket(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Basket basket
    ) throws URISyntaxException {
        log.debug("REST request to partial update Basket partially : {}, {}", id, basket);
        if (basket.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, basket.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!basketRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Basket> result = basketRepository
            .findById(basket.getId())
            .map(existingBasket -> {
                if (basket.getScore() != null) {
                    existingBasket.setScore(basket.getScore());
                }
                if (basket.getPrice() != null) {
                    existingBasket.setPrice(basket.getPrice());
                }

                return existingBasket;
            })
            .map(basketRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, basket.getId().toString())
        );
    }

    /**
     * {@code GET  /baskets} : get all the baskets.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of baskets in body.
     */
    @GetMapping("/baskets")
    public List<Basket> getAllBaskets() {
        log.debug("REST request to get all Baskets");
        return basketRepository.findAll();
    }

    /**
     * {@code GET  /baskets/:id} : get the "id" basket.
     *
     * @param id the id of the basket to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the basket, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/baskets/{id}")
    public ResponseEntity<Basket> getBasket(@PathVariable Long id) {
        log.debug("REST request to get Basket : {}", id);
        Optional<Basket> basket = basketRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(basket);
    }

    /**
     * {@code DELETE  /baskets/:id} : delete the "id" basket.
     *
     * @param id the id of the basket to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/baskets/{id}")
    public ResponseEntity<Void> deleteBasket(@PathVariable Long id) {
        log.debug("REST request to delete Basket : {}", id);
        basketRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
