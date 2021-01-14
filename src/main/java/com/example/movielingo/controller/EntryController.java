package com.example.movielingo.controller;


import com.example.movielingo.configuration.MyConstants;
import com.example.movielingo.model.User;
import com.example.movielingo.respository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.persistence.NonUniqueResultException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
@RequestMapping("/")
public class EntryController {

    private final static Logger logger = Logger.getLogger(EntryController.class.getName());

    @Autowired
    private UserRepository userRepository;

    @RequestMapping("/hello")
    public String hello() {
        return "hello";
    }

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity autoLogin(@RequestBody User user) {
        User tempUser = userRepository.findByEmail(user.getEmail());
        if (tempUser == null) {
            logger.log(Level.INFO, "Endpoint = /Auto-login - User not found");
            return ResponseEntity.badRequest().body("Bad Credentials");
        }
        String passwordInDb = tempUser.getPassword();
        String passwordToCompare = user.getPassword();
        if (passwordInDb.equals(passwordToCompare)) {
            long currentTimeMillis = System.currentTimeMillis();
            return ResponseEntity.ok().body(Jwts.builder()
                    .setSubject(user.getEmail())
                    .claim("password", user.getPassword())
                    .claim("roles", user.getRole())
                    .setIssuedAt(new Date(currentTimeMillis))
                    .setExpiration(new Date(currentTimeMillis + 604800000))
                    .signWith(SignatureAlgorithm.HS256, MyConstants.getTokenSignKey())
                    .compact());
        } else {
            logger.log(Level.INFO, "Endpoint = /login -Wrong password");
            return ResponseEntity.badRequest().body("Bad Credentials");

        }
    }


    @PostMapping(value = "/addUser", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity addUser(@RequestBody User user) {
        logger.log(Level.INFO, "Endpoint = /addUser - " + user.toString());
        try {
            EmailService.sendVerificationMail(user.getEmail(),MyConstants.getEmailServiceUsername(),MyConstants.getEmailServicePassword(),user.getLoginName());

            if (!(userRepository.findByEmail(user.getEmail()) instanceof User)) {
                if(!(userRepository.findByLogin(user.getLoginName()) instanceof User))
                    {
                        userRepository.save(user);
                       // EmailService.sendVerificationMail(user.getEmail(),MyConstants.EMAIL_SERVICE_USERNAME,MyConstants.EMAIL_SERVICE_PASSWORD,user.getLoginName());
                        logger.log(Level.INFO, "Endpoint = /addUser - User saved");
                        return ResponseEntity.ok("User Saved");
                    }
                        else {
                            logger.log(Level.INFO, "Endpoint = /addUser - Login Used");
                            return ResponseEntity.badRequest().body("Login Used"); }
                        }
            else
            {
                logger.log(Level.INFO, "Endpoint = /addUser - Email Used");
                return ResponseEntity.badRequest().body("Email Used");
            }
//            logger.log(Level.INFO, "Endpoint = /addUser Cannot save new User");
//            return ResponseEntity.badRequest().body("Cannot save new User");
        } catch (NonUniqueResultException e) {
            logger.log(Level.INFO, "Endpoint = /addUser - Non unique result");
            return ResponseEntity.badRequest().body("Email already registered");
        }
    }



    @RequestMapping("/account-confirmation/{email}")
    public String accountConfirmation(@PathVariable String email) {
        userRepository.setAccountActive(email);
        return "index.html";
    }

}