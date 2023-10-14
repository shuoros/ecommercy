package io.github.shuoros.ecommercy.model.vm;

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
    @Pattern(regexp = "^(?>[a-zA-Z0-9!$&*+=?^_`{|}~.-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*)|(?>[_.@A-Za-z0-9-]+)$")
    @Size(min = 1, max = 50)
    private String username;

    @Email
    @Size(min = 5, max = 254)
    private String email;

    @NotNull
    @Size(min = 6, max = 100)
    private String password;
}
