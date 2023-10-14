package io.github.shuoros.ecommercy.model.vm;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.shuoros.ecommercy.config.Constants;
import io.github.shuoros.ecommercy.model.enumeration.Language;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RegisterVM {

    @NotNull
    @Pattern(regexp = Constants.USERNAME_REGEX)
    @Size(min = 1, max = 50)
    private String username;

    @Email
    @Size(min = 5, max = 254)
    private String email;

    @NotNull
    @Size(min = 6, max = 100)
    private String password;

    @Size(max = 50)
    @JsonProperty("first_name")
    private String firstName;

    @Size(max = 50)
    @JsonProperty("last_name")
    private String lastName;

    private Language language;
}
