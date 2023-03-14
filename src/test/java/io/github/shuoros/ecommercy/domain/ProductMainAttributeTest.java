package io.github.shuoros.ecommercy.domain;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.shuoros.ecommercy.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProductMainAttributeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductMainAttribute.class);
        ProductMainAttribute productMainAttribute1 = new ProductMainAttribute();
        productMainAttribute1.setId(1L);
        ProductMainAttribute productMainAttribute2 = new ProductMainAttribute();
        productMainAttribute2.setId(productMainAttribute1.getId());
        assertThat(productMainAttribute1).isEqualTo(productMainAttribute2);
        productMainAttribute2.setId(2L);
        assertThat(productMainAttribute1).isNotEqualTo(productMainAttribute2);
        productMainAttribute1.setId(null);
        assertThat(productMainAttribute1).isNotEqualTo(productMainAttribute2);
    }
}
