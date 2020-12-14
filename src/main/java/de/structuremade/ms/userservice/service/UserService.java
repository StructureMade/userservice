package de.structuremade.ms.userservice.service;

import de.structuremade.ms.userservice.algorithm.Token;
import de.structuremade.ms.userservice.api.json.CreateUserJson;
import de.structuremade.ms.userservice.util.JWTUtil;
import de.structuremade.ms.userservice.util.database.entity.User;
import de.structuremade.ms.userservice.util.database.repo.SchoolRepository;
import de.structuremade.ms.userservice.util.database.repo.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;

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
                LOGGER.info("ActivationCode is valid was set [User]");
                user.setToken(token);
            } else {
                tries++;
            }
        } while (codeAlreadyUsed);
        return user;
    }

    public int create(CreateUserJson userJson, String jwt){
        try{
            if (userRepository.existsByEmail(userJson.getEmail())){
                return 1;
            }
            String schoolid = jwtUtil.extractSpecialClaim(jwt, "schoolid");
            User user = new User();
            user.setEmail(userJson.getEmail());
            user.setFirstname(userJson.getFirstname());
            user.setName(userJson.getName());
            user.setLastSchool(schoolid);
            user.setSchools(schoolRepository.findAllById((schoolid)));
            user.setVerified(false);
            user.setCreationDate(Calendar.getInstance().getTime());
            createToken(user);
            userRepository.save(user);
        }catch (Exception e){
            e.printStackTrace();
            return 2;
        }
        return 0;
    }

}
