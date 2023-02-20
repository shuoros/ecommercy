package io.github.shuoros.ecommercy.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.github.shuoros.ecommercy.domain.enumeration.CouponType;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * A Coupon.
 */
@Entity
@Table(name = "coupon")
public class Coupon implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "code")
    private String code;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private CouponType type;

    @Column(name = "expiration")
    private ZonedDateTime expiration;

    @ManyToMany
    @JoinTable(
        name = "rel_coupon__customer",
        joinColumns = @JoinColumn(name = "coupon_id"),
        inverseJoinColumns = @JoinColumn(name = "customer_id")
    )
    @JsonIgnoreProperties(value = { "user", "baskets", "comments", "addresses", "coupons" }, allowSetters = true)
    private Set<Customer> customers = new HashSet<>();

    @OneToMany(mappedBy = "coupon")
    @JsonIgnoreProperties(value = { "address", "coupon", "basket" }, allowSetters = true)
    private Set<Order> orders = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Coupon id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return this.code;
    }

    public Coupon code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public CouponType getType() {
        return this.type;
    }

    public Coupon type(CouponType type) {
        this.setType(type);
        return this;
    }

    public void setType(CouponType type) {
        this.type = type;
    }

    public ZonedDateTime getExpiration() {
        return this.expiration;
    }

    public Coupon expiration(ZonedDateTime expiration) {
        this.setExpiration(expiration);
        return this;
    }

    public void setExpiration(ZonedDateTime expiration) {
        this.expiration = expiration;
    }

    public Set<Customer> getCustomers() {
        return this.customers;
    }

    public void setCustomers(Set<Customer> customers) {
        this.customers = customers;
    }

    public Coupon customers(Set<Customer> customers) {
        this.setCustomers(customers);
        return this;
    }

    public Coupon addCustomer(Customer customer) {
        this.customers.add(customer);
        customer.getCoupons().add(this);
        return this;
    }

    public Coupon removeCustomer(Customer customer) {
        this.customers.remove(customer);
        customer.getCoupons().remove(this);
        return this;
    }

    public Set<Order> getOrders() {
        return this.orders;
    }

    public void setOrders(Set<Order> orders) {
        if (this.orders != null) {
            this.orders.forEach(i -> i.setCoupon(null));
        }
        if (orders != null) {
            orders.forEach(i -> i.setCoupon(this));
        }
        this.orders = orders;
    }

    public Coupon orders(Set<Order> orders) {
        this.setOrders(orders);
        return this;
    }

    public Coupon addOrder(Order order) {
        this.orders.add(order);
        order.setCoupon(this);
        return this;
    }

    public Coupon removeOrder(Order order) {
        this.orders.remove(order);
        order.setCoupon(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Coupon)) {
            return false;
        }
        return id != null && id.equals(((Coupon) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Coupon{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", type='" + getType() + "'" +
            ", expiration='" + getExpiration() + "'" +
            "}";
    }
}
