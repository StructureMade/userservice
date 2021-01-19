package de.structuremade.ms.userservice.api.json.answer.array;

import de.structuremade.ms.userservice.api.json.answer.GetSingleUserJson;

public class GetMyUserInformationJson extends GetSingleUserJson {
    public GetMyUserInformationJson(GetSingleUserJson userJson) {
        this.setId(userJson.getId());
        this.setEmail(userJson.getEmail());
        this.setFirstname(userJson.getFirstname());
        this.setName(userJson.getName());
        this.setCreationDate(userJson.getCreationDate());
        this.setVerified(userJson.isVerified());
        this.setRoles(userJson.getRoles());
        this.setSchools(userJson.getSchools());
    }
}
