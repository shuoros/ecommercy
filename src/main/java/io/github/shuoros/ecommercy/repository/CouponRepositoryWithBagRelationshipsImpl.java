package io.github.shuoros.ecommercy.repository;

import io.github.shuoros.ecommercy.domain.Coupon;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.hibernate.annotations.QueryHints;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class CouponRepositoryWithBagRelationshipsImpl implements CouponRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Coupon> fetchBagRelationships(Optional<Coupon> coupon) {
        return coupon.map(this::fetchCustomers);
    }

    @Override
    public Page<Coupon> fetchBagRelationships(Page<Coupon> coupons) {
        return new PageImpl<>(fetchBagRelationships(coupons.getContent()), coupons.getPageable(), coupons.getTotalElements());
    }

    @Override
    public List<Coupon> fetchBagRelationships(List<Coupon> coupons) {
        return Optional.of(coupons).map(this::fetchCustomers).orElse(Collections.emptyList());
    }

    Coupon fetchCustomers(Coupon result) {
        return entityManager
            .createQuery("select coupon from Coupon coupon left join fetch coupon.customers where coupon is :coupon", Coupon.class)
            .setParameter("coupon", result)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getSingleResult();
    }

    List<Coupon> fetchCustomers(List<Coupon> coupons) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, coupons.size()).forEach(index -> order.put(coupons.get(index).getId(), index));
        List<Coupon> result = entityManager
            .createQuery(
                "select distinct coupon from Coupon coupon left join fetch coupon.customers where coupon in :coupons",
                Coupon.class
            )
            .setParameter("coupons", coupons)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
