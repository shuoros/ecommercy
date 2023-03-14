package io.github.shuoros.ecommercy.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.github.shuoros.ecommercy.IntegrationTest;
import io.github.shuoros.ecommercy.domain.Attribute;
import io.github.shuoros.ecommercy.repository.AttributeRepository;
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
 * Integration tests for the {@link AttributeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AttributeResourceIT {

    private static final String DEFAULT_KEY = "AAAAAAAAAA";
    private static final String UPDATED_KEY = "BBBBBBBBBB";

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/attributes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AttributeRepository attributeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAttributeMockMvc;

    private Attribute attribute;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Attribute createEntity(EntityManager em) {
        Attribute attribute = new Attribute().key(DEFAULT_KEY).value(DEFAULT_VALUE);
        return attribute;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Attribute createUpdatedEntity(EntityManager em) {
        Attribute attribute = new Attribute().key(UPDATED_KEY).value(UPDATED_VALUE);
        return attribute;
    }

    @BeforeEach
    public void initTest() {
        attribute = createEntity(em);
    }

    @Test
    @Transactional
    void createAttribute() throws Exception {
        int databaseSizeBeforeCreate = attributeRepository.findAll().size();
        // Create the Attribute
        restAttributeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(attribute)))
            .andExpect(status().isCreated());

        // Validate the Attribute in the database
        List<Attribute> attributeList = attributeRepository.findAll();
        assertThat(attributeList).hasSize(databaseSizeBeforeCreate + 1);
        Attribute testAttribute = attributeList.get(attributeList.size() - 1);
        assertThat(testAttribute.getKey()).isEqualTo(DEFAULT_KEY);
        assertThat(testAttribute.getValue()).isEqualTo(DEFAULT_VALUE);
    }

    @Test
    @Transactional
    void createAttributeWithExistingId() throws Exception {
        // Create the Attribute with an existing ID
        attribute.setId(1L);

        int databaseSizeBeforeCreate = attributeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAttributeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(attribute)))
            .andExpect(status().isBadRequest());

        // Validate the Attribute in the database
        List<Attribute> attributeList = attributeRepository.findAll();
        assertThat(attributeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAttributes() throws Exception {
        // Initialize the database
        attributeRepository.saveAndFlush(attribute);

        // Get all the attributeList
        restAttributeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(attribute.getId().intValue())))
            .andExpect(jsonPath("$.[*].key").value(hasItem(DEFAULT_KEY)))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)));
    }

    @Test
    @Transactional
    void getAttribute() throws Exception {
        // Initialize the database
        attributeRepository.saveAndFlush(attribute);

        // Get the attribute
        restAttributeMockMvc
            .perform(get(ENTITY_API_URL_ID, attribute.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(attribute.getId().intValue()))
            .andExpect(jsonPath("$.key").value(DEFAULT_KEY))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE));
    }

    @Test
    @Transactional
    void getNonExistingAttribute() throws Exception {
        // Get the attribute
        restAttributeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewAttribute() throws Exception {
        // Initialize the database
        attributeRepository.saveAndFlush(attribute);

        int databaseSizeBeforeUpdate = attributeRepository.findAll().size();

        // Update the attribute
        Attribute updatedAttribute = attributeRepository.findById(attribute.getId()).get();
        // Disconnect from session so that the updates on updatedAttribute are not directly saved in db
        em.detach(updatedAttribute);
        updatedAttribute.key(UPDATED_KEY).value(UPDATED_VALUE);

        restAttributeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedAttribute.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedAttribute))
            )
            .andExpect(status().isOk());

        // Validate the Attribute in the database
        List<Attribute> attributeList = attributeRepository.findAll();
        assertThat(attributeList).hasSize(databaseSizeBeforeUpdate);
        Attribute testAttribute = attributeList.get(attributeList.size() - 1);
        assertThat(testAttribute.getKey()).isEqualTo(UPDATED_KEY);
        assertThat(testAttribute.getValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    @Transactional
    void putNonExistingAttribute() throws Exception {
        int databaseSizeBeforeUpdate = attributeRepository.findAll().size();
        attribute.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAttributeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, attribute.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(attribute))
            )
            .andExpect(status().isBadRequest());

        // Validate the Attribute in the database
        List<Attribute> attributeList = attributeRepository.findAll();
        assertThat(attributeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAttribute() throws Exception {
        int databaseSizeBeforeUpdate = attributeRepository.findAll().size();
        attribute.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAttributeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(attribute))
            )
            .andExpect(status().isBadRequest());

        // Validate the Attribute in the database
        List<Attribute> attributeList = attributeRepository.findAll();
        assertThat(attributeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAttribute() throws Exception {
        int databaseSizeBeforeUpdate = attributeRepository.findAll().size();
        attribute.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAttributeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(attribute)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Attribute in the database
        List<Attribute> attributeList = attributeRepository.findAll();
        assertThat(attributeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAttributeWithPatch() throws Exception {
        // Initialize the database
        attributeRepository.saveAndFlush(attribute);

        int databaseSizeBeforeUpdate = attributeRepository.findAll().size();

        // Update the attribute using partial update
        Attribute partialUpdatedAttribute = new Attribute();
        partialUpdatedAttribute.setId(attribute.getId());

        partialUpdatedAttribute.key(UPDATED_KEY).value(UPDATED_VALUE);

        restAttributeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAttribute.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAttribute))
            )
            .andExpect(status().isOk());

        // Validate the Attribute in the database
        List<Attribute> attributeList = attributeRepository.findAll();
        assertThat(attributeList).hasSize(databaseSizeBeforeUpdate);
        Attribute testAttribute = attributeList.get(attributeList.size() - 1);
        assertThat(testAttribute.getKey()).isEqualTo(UPDATED_KEY);
        assertThat(testAttribute.getValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    @Transactional
    void fullUpdateAttributeWithPatch() throws Exception {
        // Initialize the database
        attributeRepository.saveAndFlush(attribute);

        int databaseSizeBeforeUpdate = attributeRepository.findAll().size();

        // Update the attribute using partial update
        Attribute partialUpdatedAttribute = new Attribute();
        partialUpdatedAttribute.setId(attribute.getId());

        partialUpdatedAttribute.key(UPDATED_KEY).value(UPDATED_VALUE);

        restAttributeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAttribute.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAttribute))
            )
            .andExpect(status().isOk());

        // Validate the Attribute in the database
        List<Attribute> attributeList = attributeRepository.findAll();
        assertThat(attributeList).hasSize(databaseSizeBeforeUpdate);
        Attribute testAttribute = attributeList.get(attributeList.size() - 1);
        assertThat(testAttribute.getKey()).isEqualTo(UPDATED_KEY);
        assertThat(testAttribute.getValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    @Transactional
    void patchNonExistingAttribute() throws Exception {
        int databaseSizeBeforeUpdate = attributeRepository.findAll().size();
        attribute.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAttributeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, attribute.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(attribute))
            )
            .andExpect(status().isBadRequest());

        // Validate the Attribute in the database
        List<Attribute> attributeList = attributeRepository.findAll();
        assertThat(attributeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAttribute() throws Exception {
        int databaseSizeBeforeUpdate = attributeRepository.findAll().size();
        attribute.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAttributeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(attribute))
            )
            .andExpect(status().isBadRequest());

        // Validate the Attribute in the database
        List<Attribute> attributeList = attributeRepository.findAll();
        assertThat(attributeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAttribute() throws Exception {
        int databaseSizeBeforeUpdate = attributeRepository.findAll().size();
        attribute.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAttributeMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(attribute))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Attribute in the database
        List<Attribute> attributeList = attributeRepository.findAll();
        assertThat(attributeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAttribute() throws Exception {
        // Initialize the database
        attributeRepository.saveAndFlush(attribute);

        int databaseSizeBeforeDelete = attributeRepository.findAll().size();

        // Delete the attribute
        restAttributeMockMvc
            .perform(delete(ENTITY_API_URL_ID, attribute.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Attribute> attributeList = attributeRepository.findAll();
        assertThat(attributeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
