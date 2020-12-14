package de.structuremade.ms.userservice.api.routes;

import com.google.gson.Gson;
import de.structuremade.ms.userservice.api.json.CreateUserJson;
import de.structuremade.ms.userservice.api.json.answer.CreateUserResponse;
import de.structuremade.ms.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/service/user")
public class UserRoute {

    @Autowired
    UserService userService;

    @PostMapping("/create")
    public void createUser(@RequestBody CreateUserJson userJson, HttpServletResponse response, HttpServletRequest request){
        switch (userService.create(userJson,request.getHeader("Authorization").substring(7))) {
            case 0:
                response.setStatus(HttpStatus.CREATED.value());
                break;
            case 1:
                response.setStatus(HttpStatus.BAD_REQUEST.value());
                break;
            case 2:
                response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
                break;
        }
    }
}
