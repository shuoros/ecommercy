package io.github.shuoros.ecommercy.dao;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "BASKETS", schema = "ecommercy")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"createdAt", "updatedAt"}, allowGetters = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Basket {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(updatable = false, nullable = false, columnDefinition = "VARCHAR(255)")
    private String id;

    @NotNull
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @OneToMany(mappedBy = "basket")
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private List<BasketItem> items;

    @NotNull
    @Builder.Default
    private double totalPrice = 0.0;

    @NotNull
    @Builder.Default
    private double discount = 0.0;

}
