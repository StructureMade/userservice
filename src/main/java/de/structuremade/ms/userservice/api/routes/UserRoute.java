package de.structuremade.ms.userservice.api.routes;

import com.google.gson.Gson;
import de.structuremade.ms.userservice.api.json.CreateUserJson;
import de.structuremade.ms.userservice.api.json.answer.GetAllUserJson;
import de.structuremade.ms.userservice.api.json.answer.GetSingleUserJson;
import de.structuremade.ms.userservice.api.json.answer.array.GetMyUserInformationJson;
import de.structuremade.ms.userservice.service.UserService;
import de.structuremade.ms.userservice.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/service/user")
public class UserRoute {

    @Autowired
    UserService userService;

    @Autowired
    JWTUtil jwtUtil;

    Gson gson = new Gson();

    @PostMapping("/create")
    public void createUser(@RequestBody CreateUserJson userJson, HttpServletResponse response, HttpServletRequest request) {
        switch (userService.create(userJson, request.getHeader("Authorization").substring(7))) {
            case 0:
                response.setStatus(HttpStatus.CREATED.value());
                break;
            case 1:
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                break;
            case 2:
                response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
                break;
        }
    }

    @GetMapping("/get/{userid}")
    public Object getUser(@PathVariable("userid") String userid, HttpServletRequest request, HttpServletResponse response) {
        GetSingleUserJson user = userService.getUser(userid, jwtUtil.extractSpecialClaim(request.getHeader("Authorization").substring(7), "schoolid"));
        if (user.getId().equals("1")) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
            return null;
        } else if (user.getId() == null) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return null;
        } else {
            response.setStatus(HttpStatus.OK.value());
            return gson.toJson(user);
        }
    }

    @GetMapping("/getme")
    public Object getMe(HttpServletRequest request, HttpServletResponse response) {
        GetMyUserInformationJson user = userService.getMyUserInformation(jwtUtil.extractIdOrEmail(request.getHeader("Authorization").substring(7)), jwtUtil.extractSpecialClaim((request.getHeader("Authorization").substring(7)), "schoolid"));
        if (user.getId().equals("1")) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
            return null;
        } else if (user.getId() == null) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return null;
        } else {
            response.setStatus(HttpStatus.OK.value());
            return gson.toJson(user);
        }
    }

    @GetMapping("/getall")
    public Object getAllUser(HttpServletRequest request, HttpServletResponse response) {
        GetAllUserJson getAllUserJson = userService.getAllUser(jwtUtil.extractSpecialClaim(request.getHeader("Authorization").substring(7), "schoolid"));
        if (getAllUserJson != null) {
            response.setStatus(HttpStatus.OK.value());
            return gson.toJson(getAllUserJson);
        } else {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return null;
        }
    }

}
