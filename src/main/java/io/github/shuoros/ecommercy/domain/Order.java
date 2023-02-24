package io.github.shuoros.ecommercy.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.github.shuoros.ecommercy.domain.enumeration.OrderStatus;
import java.io.Serializable;
import java.time.ZonedDateTime;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Order.
 */
@Entity
@Table(name = "jhi_order")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private OrderStatus status;

    @Column(name = "receive")
    private ZonedDateTime receive;

    @JsonIgnoreProperties(value = { "order", "customer" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private Address address;

    @ManyToOne
    @JsonIgnoreProperties(value = { "customers", "orders" }, allowSetters = true)
    private Coupon coupon;

    @JsonIgnoreProperties(value = { "order", "items", "customer" }, allowSetters = true)
    @OneToOne(mappedBy = "order")
    private Basket basket;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Order id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OrderStatus getStatus() {
        return this.status;
    }

    public Order status(OrderStatus status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public ZonedDateTime getReceive() {
        return this.receive;
    }

    public Order receive(ZonedDateTime receive) {
        this.setReceive(receive);
        return this;
    }

    public void setReceive(ZonedDateTime receive) {
        this.receive = receive;
    }

    public Address getAddress() {
        return this.address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Order address(Address address) {
        this.setAddress(address);
        return this;
    }

    public Coupon getCoupon() {
        return this.coupon;
    }

    public void setCoupon(Coupon coupon) {
        this.coupon = coupon;
    }

    public Order coupon(Coupon coupon) {
        this.setCoupon(coupon);
        return this;
    }

    public Basket getBasket() {
        return this.basket;
    }

    public void setBasket(Basket basket) {
        if (this.basket != null) {
            this.basket.setOrder(null);
        }
        if (basket != null) {
            basket.setOrder(this);
        }
        this.basket = basket;
    }

    public Order basket(Basket basket) {
        this.setBasket(basket);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Order)) {
            return false;
        }
        return id != null && id.equals(((Order) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Order{" +
            "id=" + getId() +
            ", status='" + getStatus() + "'" +
            ", receive='" + getReceive() + "'" +
            "}";
    }
}
