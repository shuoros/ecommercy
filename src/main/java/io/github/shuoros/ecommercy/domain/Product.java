package io.github.shuoros.ecommercy.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Product.
 */
@Entity
@Table(name = "product")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "brand")
    private String brand;

    @Column(name = "rate")
    private Integer rate;

    @Column(name = "description")
    private String description;

    @JsonIgnoreProperties(value = { "product" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private Picture mainPic;

    @JsonIgnoreProperties(value = { "attribute", "product" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private ProductMainAttribute productMainAttribute;

    @OneToMany(mappedBy = "product")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "product", "customer" }, allowSetters = true)
    private Set<Comment> comments = new HashSet<>();

    @OneToMany(mappedBy = "product")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "product" }, allowSetters = true)
    private Set<Picture> pictures = new HashSet<>();

    @ManyToMany
    @JoinTable(
        name = "rel_product__attribute",
        joinColumns = @JoinColumn(name = "product_id"),
        inverseJoinColumns = @JoinColumn(name = "attribute_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "productMainAttribute", "products" }, allowSetters = true)
    private Set<Attribute> attributes = new HashSet<>();

    @OneToMany(mappedBy = "product")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "product", "basket" }, allowSetters = true)
    private Set<Item> items = new HashSet<>();

    @ManyToMany(mappedBy = "products")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "products", "group" }, allowSetters = true)
    private Set<Category> categories = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Product id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Product name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return this.brand;
    }

    public Product brand(String brand) {
        this.setBrand(brand);
        return this;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Integer getRate() {
        return this.rate;
    }

    public Product rate(Integer rate) {
        this.setRate(rate);
        return this;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }

    public String getDescription() {
        return this.description;
    }

    public Product description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Picture getMainPic() {
        return this.mainPic;
    }

    public void setMainPic(Picture picture) {
        this.mainPic = picture;
    }

    public Product mainPic(Picture picture) {
        this.setMainPic(picture);
        return this;
    }

    public ProductMainAttribute getProductMainAttribute() {
        return this.productMainAttribute;
    }

    public void setProductMainAttribute(ProductMainAttribute productMainAttribute) {
        this.productMainAttribute = productMainAttribute;
    }

    public Product productMainAttribute(ProductMainAttribute productMainAttribute) {
        this.setProductMainAttribute(productMainAttribute);
        return this;
    }

    public Set<Comment> getComments() {
        return this.comments;
    }

    public void setComments(Set<Comment> comments) {
        if (this.comments != null) {
            this.comments.forEach(i -> i.setProduct(null));
        }
        if (comments != null) {
            comments.forEach(i -> i.setProduct(this));
        }
        this.comments = comments;
    }

    public Product comments(Set<Comment> comments) {
        this.setComments(comments);
        return this;
    }

    public Product addComment(Comment comment) {
        this.comments.add(comment);
        comment.setProduct(this);
        return this;
    }

    public Product removeComment(Comment comment) {
        this.comments.remove(comment);
        comment.setProduct(null);
        return this;
    }

    public Set<Picture> getPictures() {
        return this.pictures;
    }

    public void setPictures(Set<Picture> pictures) {
        if (this.pictures != null) {
            this.pictures.forEach(i -> i.setProduct(null));
        }
        if (pictures != null) {
            pictures.forEach(i -> i.setProduct(this));
        }
        this.pictures = pictures;
    }

    public Product pictures(Set<Picture> pictures) {
        this.setPictures(pictures);
        return this;
    }

    public Product addPicture(Picture picture) {
        this.pictures.add(picture);
        picture.setProduct(this);
        return this;
    }

    public Product removePicture(Picture picture) {
        this.pictures.remove(picture);
        picture.setProduct(null);
        return this;
    }

    public Set<Attribute> getAttributes() {
        return this.attributes;
    }

    public void setAttributes(Set<Attribute> attributes) {
        this.attributes = attributes;
    }

    public Product attributes(Set<Attribute> attributes) {
        this.setAttributes(attributes);
        return this;
    }

    public Product addAttribute(Attribute attribute) {
        this.attributes.add(attribute);
        attribute.getProducts().add(this);
        return this;
    }

    public Product removeAttribute(Attribute attribute) {
        this.attributes.remove(attribute);
        attribute.getProducts().remove(this);
        return this;
    }

    public Set<Item> getItems() {
        return this.items;
    }

    public void setItems(Set<Item> items) {
        if (this.items != null) {
            this.items.forEach(i -> i.setProduct(null));
        }
        if (items != null) {
            items.forEach(i -> i.setProduct(this));
        }
        this.items = items;
    }

    public Product items(Set<Item> items) {
        this.setItems(items);
        return this;
    }

    public Product addItem(Item item) {
        this.items.add(item);
        item.setProduct(this);
        return this;
    }

    public Product removeItem(Item item) {
        this.items.remove(item);
        item.setProduct(null);
        return this;
    }

    public Set<Category> getCategories() {
        return this.categories;
    }

    public void setCategories(Set<Category> categories) {
        if (this.categories != null) {
            this.categories.forEach(i -> i.removeProduct(this));
        }
        if (categories != null) {
            categories.forEach(i -> i.addProduct(this));
        }
        this.categories = categories;
    }

    public Product categories(Set<Category> categories) {
        this.setCategories(categories);
        return this;
    }

    public Product addCategory(Category category) {
        this.categories.add(category);
        category.getProducts().add(this);
        return this;
    }

    public Product removeCategory(Category category) {
        this.categories.remove(category);
        category.getProducts().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Product)) {
            return false;
        }
        return id != null && id.equals(((Product) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Product{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", brand='" + getBrand() + "'" +
            ", rate=" + getRate() +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
