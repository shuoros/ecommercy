package io.github.shuoros.ecommercy.dao;

import lombok.*;
import org.hibernate.annotations.Cascade;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "ROLES", schema = "ecommercy")
@EntityListeners(AuditingEntityListener.class)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public final class Role {

    @Id
    @Column(updatable = false, nullable = false, unique  = true)
    private String name;

    @ManyToMany(mappedBy = "roles")
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private List<Admin> admins;

    @ManyToMany
    @JoinTable(
            name = "ROLES_PRIVILEGES",
            joinColumns = @JoinColumn(
                    name = "role_name", referencedColumnName = "name"),
            inverseJoinColumns = @JoinColumn(
                    name = "privilege_id", referencedColumnName = "id"))
    private List<Privilege> privileges;

}
