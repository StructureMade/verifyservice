package de.structuremade.ms.verifyservice.api.routes;

import de.structuremade.ms.verifyservice.api.json.VerifyUserJson;
import de.structuremade.ms.verifyservice.util.database.entity.User;
import de.structuremade.ms.verifyservice.util.database.repo.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("service/")
public class VerifyRoute {

    @Autowired
    private UserRepository userRepository;

    private final Logger LOGGER = LoggerFactory.getLogger(VerifyRoute.class);

    @PutMapping(path = "/verify", produces = "application/json", consumes = "application/json")
    public Object verifyUser(@RequestBody @Valid VerifyUserJson userJson, HttpServletResponse response, HttpServletRequest request) {
        /*Method Variables*/
        User user;
        /*End of Variables*/
        try {
            /*Get User by Activationcode*/
            if (userRepository.existsByEmail(userJson.getEmail())) {
                response.setStatus(HttpStatus.BAD_REQUEST.value());
                return null;
            }
            user = userRepository.findByToken(userJson.getCode());
            if (user != null) {
                /*Set all important Data in Userentity and save it into Database*/
                user.setVerified(true);
                user.setEmail(userJson.getEmail());
                user.setPassword(BCrypt.hashpw(userJson.getPassword(), BCrypt.gensalt()));
                user.setToken(null);
                userRepository.save(user);
                response.setStatus(HttpStatus.OK.value());
                return null;
            } else {
                LOGGER.info("Code could not found");
                response.setStatus(HttpStatus.NOT_FOUND.value());
                return null;
            }
        } catch (Exception e) {
            LOGGER.error("Veriy failed", e.fillInStackTrace());
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return null;
        }
    }
}
