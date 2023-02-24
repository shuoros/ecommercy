package io.github.shuoros.ecommercy.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Customer.
 */
@Entity
@Table(name = "customer")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Customer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "score")
    private Integer score;

    @OneToOne
    @JoinColumn(unique = true)
    private User user;

    @OneToMany(mappedBy = "customer")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "order", "items", "customer" }, allowSetters = true)
    private Set<Basket> baskets = new HashSet<>();

    @OneToMany(mappedBy = "customer")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "product", "customer" }, allowSetters = true)
    private Set<Comment> comments = new HashSet<>();

    @OneToMany(mappedBy = "customer")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "order", "customer" }, allowSetters = true)
    private Set<Address> addresses = new HashSet<>();

    @ManyToMany(mappedBy = "customers")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "customers", "orders" }, allowSetters = true)
    private Set<Coupon> coupons = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Customer id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getScore() {
        return this.score;
    }

    public Customer score(Integer score) {
        this.setScore(score);
        return this;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Customer user(User user) {
        this.setUser(user);
        return this;
    }

    public Set<Basket> getBaskets() {
        return this.baskets;
    }

    public void setBaskets(Set<Basket> baskets) {
        if (this.baskets != null) {
            this.baskets.forEach(i -> i.setCustomer(null));
        }
        if (baskets != null) {
            baskets.forEach(i -> i.setCustomer(this));
        }
        this.baskets = baskets;
    }

    public Customer baskets(Set<Basket> baskets) {
        this.setBaskets(baskets);
        return this;
    }

    public Customer addBasket(Basket basket) {
        this.baskets.add(basket);
        basket.setCustomer(this);
        return this;
    }

    public Customer removeBasket(Basket basket) {
        this.baskets.remove(basket);
        basket.setCustomer(null);
        return this;
    }

    public Set<Comment> getComments() {
        return this.comments;
    }

    public void setComments(Set<Comment> comments) {
        if (this.comments != null) {
            this.comments.forEach(i -> i.setCustomer(null));
        }
        if (comments != null) {
            comments.forEach(i -> i.setCustomer(this));
        }
        this.comments = comments;
    }

    public Customer comments(Set<Comment> comments) {
        this.setComments(comments);
        return this;
    }

    public Customer addComment(Comment comment) {
        this.comments.add(comment);
        comment.setCustomer(this);
        return this;
    }

    public Customer removeComment(Comment comment) {
        this.comments.remove(comment);
        comment.setCustomer(null);
        return this;
    }

    public Set<Address> getAddresses() {
        return this.addresses;
    }

    public void setAddresses(Set<Address> addresses) {
        if (this.addresses != null) {
            this.addresses.forEach(i -> i.setCustomer(null));
        }
        if (addresses != null) {
            addresses.forEach(i -> i.setCustomer(this));
        }
        this.addresses = addresses;
    }

    public Customer addresses(Set<Address> addresses) {
        this.setAddresses(addresses);
        return this;
    }

    public Customer addAddress(Address address) {
        this.addresses.add(address);
        address.setCustomer(this);
        return this;
    }

    public Customer removeAddress(Address address) {
        this.addresses.remove(address);
        address.setCustomer(null);
        return this;
    }

    public Set<Coupon> getCoupons() {
        return this.coupons;
    }

    public void setCoupons(Set<Coupon> coupons) {
        if (this.coupons != null) {
            this.coupons.forEach(i -> i.removeCustomer(this));
        }
        if (coupons != null) {
            coupons.forEach(i -> i.addCustomer(this));
        }
        this.coupons = coupons;
    }

    public Customer coupons(Set<Coupon> coupons) {
        this.setCoupons(coupons);
        return this;
    }

    public Customer addCoupon(Coupon coupon) {
        this.coupons.add(coupon);
        coupon.getCustomers().add(this);
        return this;
    }

    public Customer removeCoupon(Coupon coupon) {
        this.coupons.remove(coupon);
        coupon.getCustomers().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Customer)) {
            return false;
        }
        return id != null && id.equals(((Customer) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Customer{" +
            "id=" + getId() +
            ", score=" + getScore() +
            "}";
    }
}
