package io.github.shuoros.ecommercy.dao;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@Table(name = "ADDRESSES", schema = "ecommercy")
@EntityListeners(AuditingEntityListener.class)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public final class Address {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(updatable = false, nullable = false, columnDefinition = "VARCHAR(255)")
    private String id;

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Customer customer;

    @OneToOne
    @JoinColumn(name = "country_id", referencedColumnName = "id", nullable = false)
    private Country country;

    @OneToOne
    @JoinColumn(name = "state_id", referencedColumnName = "id", nullable = false)
    private State state;

    @OneToOne
    @JoinColumn(name = "county_id", referencedColumnName = "id", nullable = false)
    private County county;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String addressLine1;

    private String addressLine2;

    @Column(nullable = false)
    private String building;

    @Column(nullable = false)
    private String zipCode;

    @Column(nullable = false)
    private String houseNumber;

}
