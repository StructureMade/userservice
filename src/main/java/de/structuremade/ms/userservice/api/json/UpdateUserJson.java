package de.structuremade.ms.userservice.api.json;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
public class UpdateUserJson {
    @NotNull
    private String id;
    private String email;
    private String name;
}
