package io.github.shuoros.ecommercy.model.domian;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vladmihalcea.hibernate.type.json.JsonType;
import io.github.shuoros.ecommercy.model.enumeration.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.type.SqlTypes;

import java.io.Serializable;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "customer")
@TypeDef(name = "json", typeClass = JsonType.class)
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotNull
    @Pattern(regexp = "1")
    @Size(min = 1, max = 50)
    @Column(length = 50, unique = true, nullable = false)
    private String username;

    @Email
    @Size(min = 5, max = 254)
    @Column(length = 254, unique = true)
    private String email;

    @JsonIgnore
    @NotNull
    @Size(min = 60, max = 60)
    @Column(name = "password_hash",length = 60, unique = true, nullable = false)
    private String password;

    @NotNull
    @Column(nullable = false)
    private boolean activated = false;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "system_parameter", columnDefinition = "json")
    private Set<Role> roles;
}
