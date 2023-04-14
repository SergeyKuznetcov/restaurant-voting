package ru.topjava.graduate.restaurantvoting.to;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.topjava.graduate.restaurantvoting.web.HasIdAndEmail;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Getter
@Setter
public class UserTo extends NamedTo implements HasIdAndEmail {
    @Email
    @NotBlank
    @Size(min = 5, max = 32)
    private String email;
    @NotBlank
    @Size(min = 5, max = 32)
    private String password;

    public UserTo(Integer id, String name, String email, String password) {
        super(id, name);
        this.email = email;
        this.password = password;
    }
}
