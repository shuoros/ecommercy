package io.github.shuoros.ecommercy.dao;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Cascade;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Table(name = "CUSTOMERS", schema = "ecommercy")
@EntityListeners(AuditingEntityListener.class)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public final class Customer extends User {

    @OneToMany(mappedBy = "customer")
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private List<Address> addresses;

    @OneToOne(mappedBy = "customer")
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private Basket basket;

    @OneToMany(mappedBy = "customer")
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private List<Order> orders;

    @OneToMany(mappedBy = "customer")
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private List<Comment> comments;

    @Builder.Default
    @Column(nullable = false)
    private int points = 0;

}
