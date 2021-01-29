package de.structuremade.ms.userservice.api.json;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
public class CreateUserJson {
    @NotNull(message = "Firstname is required")
    private String firstname;
    @NotNull(message = "Name is required")
    private String name;
    private List<String> roles;
}
