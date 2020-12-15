package de.structuremade.ms.userservice.api.json.answer;

import de.structuremade.ms.userservice.api.json.answer.array.RoleArray;
import de.structuremade.ms.userservice.util.database.entity.School;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class GetSingleUserJson {
    private String id;
    private String email;
    private String firstname;
    private String name;
    private Date creationDate;
    private boolean verified;
    private List<School> schools;
    private List<RoleArray> roles;
}
