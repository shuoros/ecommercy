package io.github.shuoros.ecommercy.domain;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.shuoros.ecommercy.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BasketTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Basket.class);
        Basket basket1 = new Basket();
        basket1.setId(1L);
        Basket basket2 = new Basket();
        basket2.setId(basket1.getId());
        assertThat(basket1).isEqualTo(basket2);
        basket2.setId(2L);
        assertThat(basket1).isNotEqualTo(basket2);
        basket1.setId(null);
        assertThat(basket1).isNotEqualTo(basket2);
    }
}
