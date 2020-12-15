package de.structuremade.ms.userservice.api.json.answer.array;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class RoleArray {
    private String id;
    private String name;
    private List<PermissionsArray> permissions;
}
