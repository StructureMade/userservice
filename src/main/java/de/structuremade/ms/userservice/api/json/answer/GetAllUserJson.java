package de.structuremade.ms.userservice.api.json.answer;

import de.structuremade.ms.userservice.api.json.answer.array.UserInformationsJson;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class GetAllUserJson {
    private List<UserInformationsJson> users;
}
