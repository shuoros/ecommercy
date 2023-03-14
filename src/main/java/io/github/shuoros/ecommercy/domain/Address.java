package io.github.shuoros.ecommercy.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Address.
 */
@Entity
@Table(name = "address")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Address implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "street_1")
    private String street1;

    @Column(name = "street_2")
    private String street2;

    @Column(name = "unit")
    private String unit;

    @Column(name = "zip_code")
    private String zipCode;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "latitude")
    private Double latitude;

    @JsonIgnoreProperties(value = { "address", "coupon", "basket" }, allowSetters = true)
    @OneToOne(mappedBy = "address")
    private Order order;

    @ManyToOne
    @JsonIgnoreProperties(value = { "user", "baskets", "comments", "addresses", "coupons" }, allowSetters = true)
    private Customer customer;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Address id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStreet1() {
        return this.street1;
    }

    public Address street1(String street1) {
        this.setStreet1(street1);
        return this;
    }

    public void setStreet1(String street1) {
        this.street1 = street1;
    }

    public String getStreet2() {
        return this.street2;
    }

    public Address street2(String street2) {
        this.setStreet2(street2);
        return this;
    }

    public void setStreet2(String street2) {
        this.street2 = street2;
    }

    public String getUnit() {
        return this.unit;
    }

    public Address unit(String unit) {
        this.setUnit(unit);
        return this;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getZipCode() {
        return this.zipCode;
    }

    public Address zipCode(String zipCode) {
        this.setZipCode(zipCode);
        return this;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public Address phoneNumber(String phoneNumber) {
        this.setPhoneNumber(phoneNumber);
        return this;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Double getLongitude() {
        return this.longitude;
    }

    public Address longitude(Double longitude) {
        this.setLongitude(longitude);
        return this;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return this.latitude;
    }

    public Address latitude(Double latitude) {
        this.setLatitude(latitude);
        return this;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Order getOrder() {
        return this.order;
    }

    public void setOrder(Order order) {
        if (this.order != null) {
            this.order.setAddress(null);
        }
        if (order != null) {
            order.setAddress(this);
        }
        this.order = order;
    }

    public Address order(Order order) {
        this.setOrder(order);
        return this;
    }

    public Customer getCustomer() {
        return this.customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Address customer(Customer customer) {
        this.setCustomer(customer);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Address)) {
            return false;
        }
        return id != null && id.equals(((Address) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Address{" +
            "id=" + getId() +
            ", street1='" + getStreet1() + "'" +
            ", street2='" + getStreet2() + "'" +
            ", unit='" + getUnit() + "'" +
            ", zipCode='" + getZipCode() + "'" +
            ", phoneNumber='" + getPhoneNumber() + "'" +
            ", longitude=" + getLongitude() +
            ", latitude=" + getLatitude() +
            "}";
    }
}
