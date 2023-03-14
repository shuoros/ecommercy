package io.github.shuoros.ecommercy.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Attribute.
 */
@Entity
@Table(name = "attribute")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Attribute implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "jhi_key")
    private String key;

    @Column(name = "value")
    private String value;

    @JsonIgnoreProperties(value = { "attribute", "product" }, allowSetters = true)
    @OneToOne(mappedBy = "attribute")
    private ProductMainAttribute productMainAttribute;

    @ManyToMany(mappedBy = "attributes")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "mainPic", "productMainAttribute", "comments", "pictures", "attributes", "items", "categories" },
        allowSetters = true
    )
    private Set<Product> products = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Attribute id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKey() {
        return this.key;
    }

    public Attribute key(String key) {
        this.setKey(key);
        return this;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return this.value;
    }

    public Attribute value(String value) {
        this.setValue(value);
        return this;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public ProductMainAttribute getProductMainAttribute() {
        return this.productMainAttribute;
    }

    public void setProductMainAttribute(ProductMainAttribute productMainAttribute) {
        if (this.productMainAttribute != null) {
            this.productMainAttribute.setAttribute(null);
        }
        if (productMainAttribute != null) {
            productMainAttribute.setAttribute(this);
        }
        this.productMainAttribute = productMainAttribute;
    }

    public Attribute productMainAttribute(ProductMainAttribute productMainAttribute) {
        this.setProductMainAttribute(productMainAttribute);
        return this;
    }

    public Set<Product> getProducts() {
        return this.products;
    }

    public void setProducts(Set<Product> products) {
        if (this.products != null) {
            this.products.forEach(i -> i.removeAttribute(this));
        }
        if (products != null) {
            products.forEach(i -> i.addAttribute(this));
        }
        this.products = products;
    }

    public Attribute products(Set<Product> products) {
        this.setProducts(products);
        return this;
    }

    public Attribute addProduct(Product product) {
        this.products.add(product);
        product.getAttributes().add(this);
        return this;
    }

    public Attribute removeProduct(Product product) {
        this.products.remove(product);
        product.getAttributes().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Attribute)) {
            return false;
        }
        return id != null && id.equals(((Attribute) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Attribute{" +
            "id=" + getId() +
            ", key='" + getKey() + "'" +
            ", value='" + getValue() + "'" +
            "}";
    }
}
