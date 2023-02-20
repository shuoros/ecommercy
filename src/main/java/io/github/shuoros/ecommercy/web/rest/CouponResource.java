package io.github.shuoros.ecommercy.web.rest;

import io.github.shuoros.ecommercy.domain.Coupon;
import io.github.shuoros.ecommercy.repository.CouponRepository;
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
 * REST controller for managing {@link io.github.shuoros.ecommercy.domain.Coupon}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class CouponResource {

    private final Logger log = LoggerFactory.getLogger(CouponResource.class);

    private static final String ENTITY_NAME = "coupon";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CouponRepository couponRepository;

    public CouponResource(CouponRepository couponRepository) {
        this.couponRepository = couponRepository;
    }

    /**
     * {@code POST  /coupons} : Create a new coupon.
     *
     * @param coupon the coupon to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new coupon, or with status {@code 400 (Bad Request)} if the coupon has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/coupons")
    public ResponseEntity<Coupon> createCoupon(@RequestBody Coupon coupon) throws URISyntaxException {
        log.debug("REST request to save Coupon : {}", coupon);
        if (coupon.getId() != null) {
            throw new BadRequestAlertException("A new coupon cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Coupon result = couponRepository.save(coupon);
        return ResponseEntity
            .created(new URI("/api/coupons/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /coupons/:id} : Updates an existing coupon.
     *
     * @param id the id of the coupon to save.
     * @param coupon the coupon to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated coupon,
     * or with status {@code 400 (Bad Request)} if the coupon is not valid,
     * or with status {@code 500 (Internal Server Error)} if the coupon couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/coupons/{id}")
    public ResponseEntity<Coupon> updateCoupon(@PathVariable(value = "id", required = false) final Long id, @RequestBody Coupon coupon)
        throws URISyntaxException {
        log.debug("REST request to update Coupon : {}, {}", id, coupon);
        if (coupon.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, coupon.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!couponRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Coupon result = couponRepository.save(coupon);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, coupon.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /coupons/:id} : Partial updates given fields of an existing coupon, field will ignore if it is null
     *
     * @param id the id of the coupon to save.
     * @param coupon the coupon to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated coupon,
     * or with status {@code 400 (Bad Request)} if the coupon is not valid,
     * or with status {@code 404 (Not Found)} if the coupon is not found,
     * or with status {@code 500 (Internal Server Error)} if the coupon couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/coupons/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Coupon> partialUpdateCoupon(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Coupon coupon
    ) throws URISyntaxException {
        log.debug("REST request to partial update Coupon partially : {}, {}", id, coupon);
        if (coupon.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, coupon.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!couponRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Coupon> result = couponRepository
            .findById(coupon.getId())
            .map(existingCoupon -> {
                if (coupon.getCode() != null) {
                    existingCoupon.setCode(coupon.getCode());
                }
                if (coupon.getType() != null) {
                    existingCoupon.setType(coupon.getType());
                }
                if (coupon.getExpiration() != null) {
                    existingCoupon.setExpiration(coupon.getExpiration());
                }

                return existingCoupon;
            })
            .map(couponRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, coupon.getId().toString())
        );
    }

    /**
     * {@code GET  /coupons} : get all the coupons.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of coupons in body.
     */
    @GetMapping("/coupons")
    public List<Coupon> getAllCoupons(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all Coupons");
        if (eagerload) {
            return couponRepository.findAllWithEagerRelationships();
        } else {
            return couponRepository.findAll();
        }
    }

    /**
     * {@code GET  /coupons/:id} : get the "id" coupon.
     *
     * @param id the id of the coupon to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the coupon, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/coupons/{id}")
    public ResponseEntity<Coupon> getCoupon(@PathVariable Long id) {
        log.debug("REST request to get Coupon : {}", id);
        Optional<Coupon> coupon = couponRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(coupon);
    }

    /**
     * {@code DELETE  /coupons/:id} : delete the "id" coupon.
     *
     * @param id the id of the coupon to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/coupons/{id}")
    public ResponseEntity<Void> deleteCoupon(@PathVariable Long id) {
        log.debug("REST request to delete Coupon : {}", id);
        couponRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
