package io.github.shuoros.ecommercy.dao;

import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "GROUPS", schema = "ecommercy")
@EntityListeners(AuditingEntityListener.class)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public final class Group {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(updatable = false, nullable = false, columnDefinition = "VARCHAR(255)")
    private String id;

    @Column(nullable = false)
    private String name;

    private String description;

    @OneToMany(mappedBy = "group")
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private List<Category> categories;

    @OneToMany(mappedBy = "group")
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private List<Product> products;

}
