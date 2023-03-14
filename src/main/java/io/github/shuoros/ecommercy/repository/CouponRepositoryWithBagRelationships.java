package io.github.shuoros.ecommercy.repository;

import io.github.shuoros.ecommercy.domain.Coupon;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface CouponRepositoryWithBagRelationships {
    Optional<Coupon> fetchBagRelationships(Optional<Coupon> coupon);

    List<Coupon> fetchBagRelationships(List<Coupon> coupons);

    Page<Coupon> fetchBagRelationships(Page<Coupon> coupons);
}
