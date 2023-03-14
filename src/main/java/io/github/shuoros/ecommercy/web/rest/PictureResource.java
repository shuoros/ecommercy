package io.github.shuoros.ecommercy.web.rest;

import io.github.shuoros.ecommercy.domain.Picture;
import io.github.shuoros.ecommercy.repository.PictureRepository;
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
 * REST controller for managing {@link io.github.shuoros.ecommercy.domain.Picture}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class PictureResource {

    private final Logger log = LoggerFactory.getLogger(PictureResource.class);

    private static final String ENTITY_NAME = "picture";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PictureRepository pictureRepository;

    public PictureResource(PictureRepository pictureRepository) {
        this.pictureRepository = pictureRepository;
    }

    /**
     * {@code POST  /pictures} : Create a new picture.
     *
     * @param picture the picture to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new picture, or with status {@code 400 (Bad Request)} if the picture has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/pictures")
    public ResponseEntity<Picture> createPicture(@RequestBody Picture picture) throws URISyntaxException {
        log.debug("REST request to save Picture : {}", picture);
        if (picture.getId() != null) {
            throw new BadRequestAlertException("A new picture cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Picture result = pictureRepository.save(picture);
        return ResponseEntity
            .created(new URI("/api/pictures/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /pictures/:id} : Updates an existing picture.
     *
     * @param id the id of the picture to save.
     * @param picture the picture to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated picture,
     * or with status {@code 400 (Bad Request)} if the picture is not valid,
     * or with status {@code 500 (Internal Server Error)} if the picture couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/pictures/{id}")
    public ResponseEntity<Picture> updatePicture(@PathVariable(value = "id", required = false) final Long id, @RequestBody Picture picture)
        throws URISyntaxException {
        log.debug("REST request to update Picture : {}, {}", id, picture);
        if (picture.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, picture.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!pictureRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Picture result = pictureRepository.save(picture);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, picture.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /pictures/:id} : Partial updates given fields of an existing picture, field will ignore if it is null
     *
     * @param id the id of the picture to save.
     * @param picture the picture to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated picture,
     * or with status {@code 400 (Bad Request)} if the picture is not valid,
     * or with status {@code 404 (Not Found)} if the picture is not found,
     * or with status {@code 500 (Internal Server Error)} if the picture couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/pictures/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Picture> partialUpdatePicture(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Picture picture
    ) throws URISyntaxException {
        log.debug("REST request to partial update Picture partially : {}, {}", id, picture);
        if (picture.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, picture.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!pictureRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Picture> result = pictureRepository
            .findById(picture.getId())
            .map(existingPicture -> {
                if (picture.getPath() != null) {
                    existingPicture.setPath(picture.getPath());
                }

                return existingPicture;
            })
            .map(pictureRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, picture.getId().toString())
        );
    }

    /**
     * {@code GET  /pictures} : get all the pictures.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of pictures in body.
     */
    @GetMapping("/pictures")
    public List<Picture> getAllPictures() {
        log.debug("REST request to get all Pictures");
        return pictureRepository.findAll();
    }

    /**
     * {@code GET  /pictures/:id} : get the "id" picture.
     *
     * @param id the id of the picture to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the picture, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/pictures/{id}")
    public ResponseEntity<Picture> getPicture(@PathVariable Long id) {
        log.debug("REST request to get Picture : {}", id);
        Optional<Picture> picture = pictureRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(picture);
    }

    /**
     * {@code DELETE  /pictures/:id} : delete the "id" picture.
     *
     * @param id the id of the picture to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/pictures/{id}")
    public ResponseEntity<Void> deletePicture(@PathVariable Long id) {
        log.debug("REST request to delete Picture : {}", id);
        pictureRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
