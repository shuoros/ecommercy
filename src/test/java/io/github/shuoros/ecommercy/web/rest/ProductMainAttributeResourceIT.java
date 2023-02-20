package io.github.shuoros.ecommercy.web.rest;

import static io.github.shuoros.ecommercy.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.github.shuoros.ecommercy.IntegrationTest;
import io.github.shuoros.ecommercy.domain.ProductMainAttribute;
import io.github.shuoros.ecommercy.repository.ProductMainAttributeRepository;
import java.math.BigDecimal;
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
 * Integration tests for the {@link ProductMainAttributeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ProductMainAttributeResourceIT {

    private static final BigDecimal DEFAULT_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_PRICE = new BigDecimal(2);

    private static final Integer DEFAULT_DISCOUNT = 1;
    private static final Integer UPDATED_DISCOUNT = 2;

    private static final String ENTITY_API_URL = "/api/product-main-attributes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ProductMainAttributeRepository productMainAttributeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProductMainAttributeMockMvc;

    private ProductMainAttribute productMainAttribute;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductMainAttribute createEntity(EntityManager em) {
        ProductMainAttribute productMainAttribute = new ProductMainAttribute().price(DEFAULT_PRICE).discount(DEFAULT_DISCOUNT);
        return productMainAttribute;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductMainAttribute createUpdatedEntity(EntityManager em) {
        ProductMainAttribute productMainAttribute = new ProductMainAttribute().price(UPDATED_PRICE).discount(UPDATED_DISCOUNT);
        return productMainAttribute;
    }

    @BeforeEach
    public void initTest() {
        productMainAttribute = createEntity(em);
    }

    @Test
    @Transactional
    void createProductMainAttribute() throws Exception {
        int databaseSizeBeforeCreate = productMainAttributeRepository.findAll().size();
        // Create the ProductMainAttribute
        restProductMainAttributeMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productMainAttribute))
            )
            .andExpect(status().isCreated());

        // Validate the ProductMainAttribute in the database
        List<ProductMainAttribute> productMainAttributeList = productMainAttributeRepository.findAll();
        assertThat(productMainAttributeList).hasSize(databaseSizeBeforeCreate + 1);
        ProductMainAttribute testProductMainAttribute = productMainAttributeList.get(productMainAttributeList.size() - 1);
        assertThat(testProductMainAttribute.getPrice()).isEqualByComparingTo(DEFAULT_PRICE);
        assertThat(testProductMainAttribute.getDiscount()).isEqualTo(DEFAULT_DISCOUNT);
    }

    @Test
    @Transactional
    void createProductMainAttributeWithExistingId() throws Exception {
        // Create the ProductMainAttribute with an existing ID
        productMainAttribute.setId(1L);

        int databaseSizeBeforeCreate = productMainAttributeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductMainAttributeMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productMainAttribute))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductMainAttribute in the database
        List<ProductMainAttribute> productMainAttributeList = productMainAttributeRepository.findAll();
        assertThat(productMainAttributeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllProductMainAttributes() throws Exception {
        // Initialize the database
        productMainAttributeRepository.saveAndFlush(productMainAttribute);

        // Get all the productMainAttributeList
        restProductMainAttributeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productMainAttribute.getId().intValue())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(sameNumber(DEFAULT_PRICE))))
            .andExpect(jsonPath("$.[*].discount").value(hasItem(DEFAULT_DISCOUNT)));
    }

    @Test
    @Transactional
    void getProductMainAttribute() throws Exception {
        // Initialize the database
        productMainAttributeRepository.saveAndFlush(productMainAttribute);

        // Get the productMainAttribute
        restProductMainAttributeMockMvc
            .perform(get(ENTITY_API_URL_ID, productMainAttribute.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(productMainAttribute.getId().intValue()))
            .andExpect(jsonPath("$.price").value(sameNumber(DEFAULT_PRICE)))
            .andExpect(jsonPath("$.discount").value(DEFAULT_DISCOUNT));
    }

    @Test
    @Transactional
    void getNonExistingProductMainAttribute() throws Exception {
        // Get the productMainAttribute
        restProductMainAttributeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewProductMainAttribute() throws Exception {
        // Initialize the database
        productMainAttributeRepository.saveAndFlush(productMainAttribute);

        int databaseSizeBeforeUpdate = productMainAttributeRepository.findAll().size();

        // Update the productMainAttribute
        ProductMainAttribute updatedProductMainAttribute = productMainAttributeRepository.findById(productMainAttribute.getId()).get();
        // Disconnect from session so that the updates on updatedProductMainAttribute are not directly saved in db
        em.detach(updatedProductMainAttribute);
        updatedProductMainAttribute.price(UPDATED_PRICE).discount(UPDATED_DISCOUNT);

        restProductMainAttributeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedProductMainAttribute.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedProductMainAttribute))
            )
            .andExpect(status().isOk());

        // Validate the ProductMainAttribute in the database
        List<ProductMainAttribute> productMainAttributeList = productMainAttributeRepository.findAll();
        assertThat(productMainAttributeList).hasSize(databaseSizeBeforeUpdate);
        ProductMainAttribute testProductMainAttribute = productMainAttributeList.get(productMainAttributeList.size() - 1);
        assertThat(testProductMainAttribute.getPrice()).isEqualByComparingTo(UPDATED_PRICE);
        assertThat(testProductMainAttribute.getDiscount()).isEqualTo(UPDATED_DISCOUNT);
    }

    @Test
    @Transactional
    void putNonExistingProductMainAttribute() throws Exception {
        int databaseSizeBeforeUpdate = productMainAttributeRepository.findAll().size();
        productMainAttribute.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductMainAttributeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, productMainAttribute.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productMainAttribute))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductMainAttribute in the database
        List<ProductMainAttribute> productMainAttributeList = productMainAttributeRepository.findAll();
        assertThat(productMainAttributeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProductMainAttribute() throws Exception {
        int databaseSizeBeforeUpdate = productMainAttributeRepository.findAll().size();
        productMainAttribute.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductMainAttributeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productMainAttribute))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductMainAttribute in the database
        List<ProductMainAttribute> productMainAttributeList = productMainAttributeRepository.findAll();
        assertThat(productMainAttributeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProductMainAttribute() throws Exception {
        int databaseSizeBeforeUpdate = productMainAttributeRepository.findAll().size();
        productMainAttribute.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductMainAttributeMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productMainAttribute))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProductMainAttribute in the database
        List<ProductMainAttribute> productMainAttributeList = productMainAttributeRepository.findAll();
        assertThat(productMainAttributeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProductMainAttributeWithPatch() throws Exception {
        // Initialize the database
        productMainAttributeRepository.saveAndFlush(productMainAttribute);

        int databaseSizeBeforeUpdate = productMainAttributeRepository.findAll().size();

        // Update the productMainAttribute using partial update
        ProductMainAttribute partialUpdatedProductMainAttribute = new ProductMainAttribute();
        partialUpdatedProductMainAttribute.setId(productMainAttribute.getId());

        partialUpdatedProductMainAttribute.discount(UPDATED_DISCOUNT);

        restProductMainAttributeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProductMainAttribute.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProductMainAttribute))
            )
            .andExpect(status().isOk());

        // Validate the ProductMainAttribute in the database
        List<ProductMainAttribute> productMainAttributeList = productMainAttributeRepository.findAll();
        assertThat(productMainAttributeList).hasSize(databaseSizeBeforeUpdate);
        ProductMainAttribute testProductMainAttribute = productMainAttributeList.get(productMainAttributeList.size() - 1);
        assertThat(testProductMainAttribute.getPrice()).isEqualByComparingTo(DEFAULT_PRICE);
        assertThat(testProductMainAttribute.getDiscount()).isEqualTo(UPDATED_DISCOUNT);
    }

    @Test
    @Transactional
    void fullUpdateProductMainAttributeWithPatch() throws Exception {
        // Initialize the database
        productMainAttributeRepository.saveAndFlush(productMainAttribute);

        int databaseSizeBeforeUpdate = productMainAttributeRepository.findAll().size();

        // Update the productMainAttribute using partial update
        ProductMainAttribute partialUpdatedProductMainAttribute = new ProductMainAttribute();
        partialUpdatedProductMainAttribute.setId(productMainAttribute.getId());

        partialUpdatedProductMainAttribute.price(UPDATED_PRICE).discount(UPDATED_DISCOUNT);

        restProductMainAttributeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProductMainAttribute.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProductMainAttribute))
            )
            .andExpect(status().isOk());

        // Validate the ProductMainAttribute in the database
        List<ProductMainAttribute> productMainAttributeList = productMainAttributeRepository.findAll();
        assertThat(productMainAttributeList).hasSize(databaseSizeBeforeUpdate);
        ProductMainAttribute testProductMainAttribute = productMainAttributeList.get(productMainAttributeList.size() - 1);
        assertThat(testProductMainAttribute.getPrice()).isEqualByComparingTo(UPDATED_PRICE);
        assertThat(testProductMainAttribute.getDiscount()).isEqualTo(UPDATED_DISCOUNT);
    }

    @Test
    @Transactional
    void patchNonExistingProductMainAttribute() throws Exception {
        int databaseSizeBeforeUpdate = productMainAttributeRepository.findAll().size();
        productMainAttribute.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductMainAttributeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, productMainAttribute.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productMainAttribute))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductMainAttribute in the database
        List<ProductMainAttribute> productMainAttributeList = productMainAttributeRepository.findAll();
        assertThat(productMainAttributeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProductMainAttribute() throws Exception {
        int databaseSizeBeforeUpdate = productMainAttributeRepository.findAll().size();
        productMainAttribute.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductMainAttributeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productMainAttribute))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductMainAttribute in the database
        List<ProductMainAttribute> productMainAttributeList = productMainAttributeRepository.findAll();
        assertThat(productMainAttributeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProductMainAttribute() throws Exception {
        int databaseSizeBeforeUpdate = productMainAttributeRepository.findAll().size();
        productMainAttribute.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductMainAttributeMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productMainAttribute))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProductMainAttribute in the database
        List<ProductMainAttribute> productMainAttributeList = productMainAttributeRepository.findAll();
        assertThat(productMainAttributeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProductMainAttribute() throws Exception {
        // Initialize the database
        productMainAttributeRepository.saveAndFlush(productMainAttribute);

        int databaseSizeBeforeDelete = productMainAttributeRepository.findAll().size();

        // Delete the productMainAttribute
        restProductMainAttributeMockMvc
            .perform(delete(ENTITY_API_URL_ID, productMainAttribute.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProductMainAttribute> productMainAttributeList = productMainAttributeRepository.findAll();
        assertThat(productMainAttributeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
