package io.github.shuoros.ecommercy.model.domian;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vladmihalcea.hibernate.type.json.JsonType;
import io.github.shuoros.ecommercy.config.Constants;
import io.github.shuoros.ecommercy.model.enumeration.Language;
import io.github.shuoros.ecommercy.model.enumeration.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.TypeDef;
import org.hibernate.type.SqlTypes;

import java.io.Serializable;
import java.util.Set;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "customer")
@TypeDef(name = "json", typeClass = JsonType.class)
@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer extends AbstractTimestampedDomain implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotNull
    @Pattern(regexp = Constants.USERNAME_REGEX)
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
    @Column(name = "password_hash", length = 60, unique = true, nullable = false)
    private String password;

    @Size(max = 50)
    @Column(name = "first_name", length = 50)
    private String firstName;

    @Size(max = 50)
    @Column(name = "last_name", length = 50)
    private String lastName;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Language language = Language.ENGLISH;

    @NotNull
    @Column(nullable = false)
    private boolean activated = false;

    @JsonIgnore
    @Size(max = 36)
    @Column(name = "activation_key")
    private UUID activationKey;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "roles", columnDefinition = "json")
    private Set<Role> roles;
}
