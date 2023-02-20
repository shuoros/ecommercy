package io.github.shuoros.ecommercy.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.*;

/**
 * A ProductMainAttribute.
 */
@Entity
@Table(name = "product_main_attribute")
public class ProductMainAttribute implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "price", precision = 21, scale = 2)
    private BigDecimal price;

    @Column(name = "discount")
    private Integer discount;

    @JsonIgnoreProperties(value = { "productMainAttribute", "products" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private Attribute attribute;

    @JsonIgnoreProperties(
        value = { "mainPic", "productMainAttribute", "comments", "pictures", "attributes", "items", "categories" },
        allowSetters = true
    )
    @OneToOne(mappedBy = "productMainAttribute")
    private Product product;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ProductMainAttribute id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getPrice() {
        return this.price;
    }

    public ProductMainAttribute price(BigDecimal price) {
        this.setPrice(price);
        return this;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getDiscount() {
        return this.discount;
    }

    public ProductMainAttribute discount(Integer discount) {
        this.setDiscount(discount);
        return this;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    public Attribute getAttribute() {
        return this.attribute;
    }

    public void setAttribute(Attribute attribute) {
        this.attribute = attribute;
    }

    public ProductMainAttribute attribute(Attribute attribute) {
        this.setAttribute(attribute);
        return this;
    }

    public Product getProduct() {
        return this.product;
    }

    public void setProduct(Product product) {
        if (this.product != null) {
            this.product.setProductMainAttribute(null);
        }
        if (product != null) {
            product.setProductMainAttribute(this);
        }
        this.product = product;
    }

    public ProductMainAttribute product(Product product) {
        this.setProduct(product);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductMainAttribute)) {
            return false;
        }
        return id != null && id.equals(((ProductMainAttribute) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductMainAttribute{" +
            "id=" + getId() +
            ", price=" + getPrice() +
            ", discount=" + getDiscount() +
            "}";
    }
}
