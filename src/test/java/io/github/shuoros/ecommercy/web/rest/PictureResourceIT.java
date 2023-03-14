package io.github.shuoros.ecommercy.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.github.shuoros.ecommercy.IntegrationTest;
import io.github.shuoros.ecommercy.domain.Picture;
import io.github.shuoros.ecommercy.repository.PictureRepository;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link PictureResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PictureResourceIT {

    private static final String DEFAULT_PATH = "AAAAAAAAAA";
    private static final String UPDATED_PATH = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/pictures";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PictureRepository pictureRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPictureMockMvc;

    private Picture picture;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Picture createEntity(EntityManager em) {
        Picture picture = new Picture().path(DEFAULT_PATH);
        return picture;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Picture createUpdatedEntity(EntityManager em) {
        Picture picture = new Picture().path(UPDATED_PATH);
        return picture;
    }

    @BeforeEach
    public void initTest() {
        picture = createEntity(em);
    }

    @Test
    @Transactional
    void createPicture() throws Exception {
        int databaseSizeBeforeCreate = pictureRepository.findAll().size();
        // Create the Picture
        restPictureMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(picture)))
            .andExpect(status().isCreated());

        // Validate the Picture in the database
        List<Picture> pictureList = pictureRepository.findAll();
        assertThat(pictureList).hasSize(databaseSizeBeforeCreate + 1);
        Picture testPicture = pictureList.get(pictureList.size() - 1);
        assertThat(testPicture.getPath()).isEqualTo(DEFAULT_PATH);
    }

    @Test
    @Transactional
    void createPictureWithExistingId() throws Exception {
        // Create the Picture with an existing ID
        picture.setId(1L);

        int databaseSizeBeforeCreate = pictureRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPictureMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(picture)))
            .andExpect(status().isBadRequest());

        // Validate the Picture in the database
        List<Picture> pictureList = pictureRepository.findAll();
        assertThat(pictureList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPictures() throws Exception {
        // Initialize the database
        pictureRepository.saveAndFlush(picture);

        // Get all the pictureList
        restPictureMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(picture.getId().intValue())))
            .andExpect(jsonPath("$.[*].path").value(hasItem(DEFAULT_PATH)));
    }

    @Test
    @Transactional
    void getPicture() throws Exception {
        // Initialize the database
        pictureRepository.saveAndFlush(picture);

        // Get the picture
        restPictureMockMvc
            .perform(get(ENTITY_API_URL_ID, picture.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(picture.getId().intValue()))
            .andExpect(jsonPath("$.path").value(DEFAULT_PATH));
    }

    @Test
    @Transactional
    void getNonExistingPicture() throws Exception {
        // Get the picture
        restPictureMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPicture() throws Exception {
        // Initialize the database
        pictureRepository.saveAndFlush(picture);

        int databaseSizeBeforeUpdate = pictureRepository.findAll().size();

        // Update the picture
        Picture updatedPicture = pictureRepository.findById(picture.getId()).get();
        // Disconnect from session so that the updates on updatedPicture are not directly saved in db
        em.detach(updatedPicture);
        updatedPicture.path(UPDATED_PATH);

        restPictureMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPicture.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPicture))
            )
            .andExpect(status().isOk());

        // Validate the Picture in the database
        List<Picture> pictureList = pictureRepository.findAll();
        assertThat(pictureList).hasSize(databaseSizeBeforeUpdate);
        Picture testPicture = pictureList.get(pictureList.size() - 1);
        assertThat(testPicture.getPath()).isEqualTo(UPDATED_PATH);
    }

    @Test
    @Transactional
    void putNonExistingPicture() throws Exception {
        int databaseSizeBeforeUpdate = pictureRepository.findAll().size();
        picture.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPictureMockMvc
            .perform(
                put(ENTITY_API_URL_ID, picture.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(picture))
            )
            .andExpect(status().isBadRequest());

        // Validate the Picture in the database
        List<Picture> pictureList = pictureRepository.findAll();
        assertThat(pictureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPicture() throws Exception {
        int databaseSizeBeforeUpdate = pictureRepository.findAll().size();
        picture.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPictureMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(picture))
            )
            .andExpect(status().isBadRequest());

        // Validate the Picture in the database
        List<Picture> pictureList = pictureRepository.findAll();
        assertThat(pictureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPicture() throws Exception {
        int databaseSizeBeforeUpdate = pictureRepository.findAll().size();
        picture.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPictureMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(picture)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Picture in the database
        List<Picture> pictureList = pictureRepository.findAll();
        assertThat(pictureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePictureWithPatch() throws Exception {
        // Initialize the database
        pictureRepository.saveAndFlush(picture);

        int databaseSizeBeforeUpdate = pictureRepository.findAll().size();

        // Update the picture using partial update
        Picture partialUpdatedPicture = new Picture();
        partialUpdatedPicture.setId(picture.getId());

        restPictureMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPicture.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPicture))
            )
            .andExpect(status().isOk());

        // Validate the Picture in the database
        List<Picture> pictureList = pictureRepository.findAll();
        assertThat(pictureList).hasSize(databaseSizeBeforeUpdate);
        Picture testPicture = pictureList.get(pictureList.size() - 1);
        assertThat(testPicture.getPath()).isEqualTo(DEFAULT_PATH);
    }

    @Test
    @Transactional
    void fullUpdatePictureWithPatch() throws Exception {
        // Initialize the database
        pictureRepository.saveAndFlush(picture);

        int databaseSizeBeforeUpdate = pictureRepository.findAll().size();

        // Update the picture using partial update
        Picture partialUpdatedPicture = new Picture();
        partialUpdatedPicture.setId(picture.getId());

        partialUpdatedPicture.path(UPDATED_PATH);

        restPictureMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPicture.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPicture))
            )
            .andExpect(status().isOk());

        // Validate the Picture in the database
        List<Picture> pictureList = pictureRepository.findAll();
        assertThat(pictureList).hasSize(databaseSizeBeforeUpdate);
        Picture testPicture = pictureList.get(pictureList.size() - 1);
        assertThat(testPicture.getPath()).isEqualTo(UPDATED_PATH);
    }

    @Test
    @Transactional
    void patchNonExistingPicture() throws Exception {
        int databaseSizeBeforeUpdate = pictureRepository.findAll().size();
        picture.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPictureMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, picture.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(picture))
            )
            .andExpect(status().isBadRequest());

        // Validate the Picture in the database
        List<Picture> pictureList = pictureRepository.findAll();
        assertThat(pictureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPicture() throws Exception {
        int databaseSizeBeforeUpdate = pictureRepository.findAll().size();
        picture.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPictureMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(picture))
            )
            .andExpect(status().isBadRequest());

        // Validate the Picture in the database
        List<Picture> pictureList = pictureRepository.findAll();
        assertThat(pictureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPicture() throws Exception {
        int databaseSizeBeforeUpdate = pictureRepository.findAll().size();
        picture.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPictureMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(picture)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Picture in the database
        List<Picture> pictureList = pictureRepository.findAll();
        assertThat(pictureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePicture() throws Exception {
        // Initialize the database
        pictureRepository.saveAndFlush(picture);

        int databaseSizeBeforeDelete = pictureRepository.findAll().size();

        // Delete the picture
        restPictureMockMvc
            .perform(delete(ENTITY_API_URL_ID, picture.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Picture> pictureList = pictureRepository.findAll();
        assertThat(pictureList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
