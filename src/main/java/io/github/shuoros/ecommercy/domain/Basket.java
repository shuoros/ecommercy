package io.github.shuoros.ecommercy.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * A Basket.
 */
@Entity
@Table(name = "basket")
public class Basket implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "score")
    private Integer score;

    @Column(name = "price", precision = 21, scale = 2)
    private BigDecimal price;

    @JsonIgnoreProperties(value = { "address", "coupon", "basket" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private Order order;

    @OneToMany(mappedBy = "basket")
    @JsonIgnoreProperties(value = { "product", "basket" }, allowSetters = true)
    private Set<Item> items = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "user", "baskets", "comments", "addresses", "coupons" }, allowSetters = true)
    private Customer customer;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Basket id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getScore() {
        return this.score;
    }

    public Basket score(Integer score) {
        this.setScore(score);
        return this;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public BigDecimal getPrice() {
        return this.price;
    }

    public Basket price(BigDecimal price) {
        this.setPrice(price);
        return this;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Order getOrder() {
        return this.order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Basket order(Order order) {
        this.setOrder(order);
        return this;
    }

    public Set<Item> getItems() {
        return this.items;
    }

    public void setItems(Set<Item> items) {
        if (this.items != null) {
            this.items.forEach(i -> i.setBasket(null));
        }
        if (items != null) {
            items.forEach(i -> i.setBasket(this));
        }
        this.items = items;
    }

    public Basket items(Set<Item> items) {
        this.setItems(items);
        return this;
    }

    public Basket addItem(Item item) {
        this.items.add(item);
        item.setBasket(this);
        return this;
    }

    public Basket removeItem(Item item) {
        this.items.remove(item);
        item.setBasket(null);
        return this;
    }

    public Customer getCustomer() {
        return this.customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Basket customer(Customer customer) {
        this.setCustomer(customer);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Basket)) {
            return false;
        }
        return id != null && id.equals(((Basket) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Basket{" +
            "id=" + getId() +
            ", score=" + getScore() +
            ", price=" + getPrice() +
            "}";
    }
}
