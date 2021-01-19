package de.structuremade.ms.userservice.api.json.answer;

import de.structuremade.ms.userservice.api.json.answer.array.RoleArray;
import de.structuremade.ms.userservice.util.database.entity.School;
import de.structuremade.ms.userservice.util.database.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class GetSingleUserJson {
    private String id;
    private String email;
    private String firstname;
    private String name;
    private Date creationDate;
    private boolean verified;
    private List<School> schools;
    private List<RoleArray> roles;

    public GetSingleUserJson(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.firstname = user.getFirstname();
        this.name = user.getName();
        this.creationDate = user.getCreationDate();
        this.verified = user.isVerified();
        this.schools = user.getSchools();
    }
}
