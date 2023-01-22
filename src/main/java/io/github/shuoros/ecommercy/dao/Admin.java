package io.github.shuoros.ecommercy.dao;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Table(name = "ADMINS", schema = "ecommercy")
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public final class Admin extends User {

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "ADMINS_ROLES",
            joinColumns = {@JoinColumn(name = "admin_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_name")})
    private List<Role> roles;

}
