package de.structuremade.ms.userservice.service;

import de.structuremade.ms.userservice.algorithm.Token;
import de.structuremade.ms.userservice.api.json.CreateUserJson;
import de.structuremade.ms.userservice.api.json.answer.GetAllUserJson;
import de.structuremade.ms.userservice.api.json.answer.GetSingleUserJson;
import de.structuremade.ms.userservice.api.json.answer.array.GetMyUserInformationJson;
import de.structuremade.ms.userservice.api.json.answer.array.PermissionsArray;
import de.structuremade.ms.userservice.api.json.answer.array.RoleArray;
import de.structuremade.ms.userservice.api.json.answer.array.UserInformationsJson;
import de.structuremade.ms.userservice.util.JWTUtil;
import de.structuremade.ms.userservice.util.database.entity.Permissions;
import de.structuremade.ms.userservice.util.database.entity.Role;
import de.structuremade.ms.userservice.util.database.entity.User;
import de.structuremade.ms.userservice.util.database.repo.SchoolRepository;
import de.structuremade.ms.userservice.util.database.repo.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Service
public class UserService {

    private final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    @Autowired
    UserRepository userRepository;


    @Autowired
    SchoolRepository schoolRepository;

    @Autowired
    JWTUtil jwtUtil;

    private User createToken(User user) {
        /*Method Variables*/
        String token;
        boolean codeAlreadyUsed;
        int lenght = 10;
        int tries = 0;
        /*End of Variables*/
        /*Join DO-While Loop*/
        LOGGER.info("Start creating Activationcode");
        do {
            /*Count how many tries */
            if (tries == 20) {
                lenght++;
                tries = 0;
            }
            /*Check if code is already used and if not add it to user*/
            if (!(codeAlreadyUsed = userRepository.existsByToken((token = Token.generate(lenght))))) {
                LOGGER.info("ActivationCode is valid was set");
                user.setToken(token);
            } else {
                tries++;
            }
        } while (codeAlreadyUsed);
        return user;
    }

    public int create(CreateUserJson userJson, String jwt) {
        try {
            if (jwtUtil.isTokenExpired(jwt)) return 1;
            String schoolid = jwtUtil.extractSpecialClaim(jwt, "schoolid");
            User user = new User();
            user.setFirstname(userJson.getFirstname());
            user.setName(userJson.getName());
            user.setLastSchool(schoolid);
            user.setSchools(schoolRepository.findAllById((schoolid)));
            user.setVerified(false);
            user.setCreationDate(Calendar.getInstance().getTime());
            createToken(user);
            userRepository.save(user);
        } catch (Exception e) {
            LOGGER.error(e.getLocalizedMessage());
            e.printStackTrace();
            return 2;
        }
        return 0;
    }

    @Transactional
    public GetSingleUserJson getUser(String userid, String schoolid) {
        /*Method Variables*/
        try {
            User user = userRepository.getOne(userid);
            GetSingleUserJson userJson = new GetSingleUserJson(user);
            List<RoleArray> roles = new ArrayList<>();
            List<PermissionsArray> permissions = new ArrayList<>();
            if (user.getId() == null) {
                userJson.setId("1");
                return userJson;
            }
            /*End of Variables*/
            for (Role role : user.getRoles()) {
                RoleArray roleArray = new RoleArray();
                if (role.getSchool().getId().equals(schoolid)) {
                    roleArray.setId(role.getId());
                    roleArray.setName(role.getName());
                    for (Permissions perms : role.getPermissions()) {
                        PermissionsArray permissionsArray = new PermissionsArray();
                        permissionsArray.setId(perms.getId());
                        permissionsArray.setName(perms.getName());
                        permissions.add(permissionsArray);
                    }
                    roleArray.setPermissions(permissions);
                }
                roles.add(roleArray);
                permissions.clear();
            }
            userJson.setRoles(roles);
            return userJson;
        } catch (Exception e) {
            LOGGER.error("Could not get User", e.fillInStackTrace());
            return null;
        }
    }

    public GetMyUserInformationJson getMyUserInformation(String userid, String schoolid) {
        return new GetMyUserInformationJson(getUser(userid, schoolid));
    }

    public GetAllUserJson getAllUser(String schoolid) {
        try {
            if (schoolid.length() > 0) {
                List<User> users = userRepository.findAllBySchools(schoolRepository.getOne(schoolid));
                List<UserInformationsJson> userInfos = new ArrayList<>();
                for (User user : users) {
                    UserInformationsJson userInfo = new UserInformationsJson();
                    userInfo.setId(user.getId());
                    userInfo.setFirstname(user.getFirstname());
                    userInfo.setName(user.getName());
                    userInfo.setEmail(user.getEmail());
                    userInfos.add(userInfo);
                }
                return new GetAllUserJson(userInfos);
            }
        } catch (Exception e) {
            LOGGER.error("Could not get users", e.fillInStackTrace());
            return null;
        }
        return null;
    }
}
