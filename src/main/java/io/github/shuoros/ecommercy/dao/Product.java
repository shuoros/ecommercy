package io.github.shuoros.ecommercy.dao;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.github.shuoros.ecommercy.dao.util.StringListConverter;
import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "PRODUCTS", schema = "ecommercy")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"createdAt", "updatedAt"}, allowGetters = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Product {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(updatable = false, nullable = false, columnDefinition = "VARCHAR(255)")
    private String id;

    @NotNull
    private String name;

    private String description;

    @NotNull
    private double price;

    @Builder.Default
    @NotNull
    private double discount = 0.0;

    @Builder.Default
    @NotNull
    private int point = 0;

    @NotNull
    private int inventory;

    @NotNull
    private String thumbnail;

    @NotNull
    @Convert(converter = StringListConverter.class)
    private List<String> images;

    @OneToMany(mappedBy = "product")
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private List<Comment> comments;

    private int stars;

//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "category_id", referencedColumnName = "id")
//    private Category category;

}
