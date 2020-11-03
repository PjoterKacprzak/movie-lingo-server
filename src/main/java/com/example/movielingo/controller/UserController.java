package com.example.movielingo.controller;


import com.example.movielingo.model.User;
import com.example.movielingo.respository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.NonUniqueResultException;
import javax.xml.ws.Response;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;



//TODO:create Password Encoder
@RestController
@RequestMapping("/api/user/")
public class UserController {

    private final static Logger logger = Logger.getLogger(UserController.class.getName());

    @Autowired
    private UserRepository userRepository;

    @RequestMapping("/getAll")
    public ResponseEntity getAllUsers()
    {
        return ResponseEntity.ok().body(userRepository.findAll());
    }


    @PostMapping(value = "/addUser",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity addUser(@RequestBody User user)
    {
        logger.log(Level.INFO, "Endpoint = /addUser - " + user.toString());
        try {
            if (!(userRepository.findByEmail(user.getEmail())instanceof User)) {
                userRepository.save(user);
                logger.log(Level.INFO,"Endpoint = /addUser - User saved");
                return ResponseEntity.ok("User Saved");
            }
            logger.log(Level.INFO,"Endpoint = /addUser Cannot save new User");
            return ResponseEntity.badRequest().body("Cannot save new User");
        }
        catch(NonUniqueResultException e)
        {
            logger.log(Level.INFO,"Endpoint = /addUser - Non unique result");
            return ResponseEntity.badRequest().body("Email already registered");
        }
    }


    @PostMapping(value = "/login",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity login(@RequestBody User user) {
            User tempUser = userRepository.findByEmail(user.getEmail());
            if(tempUser==null) {
                logger.log(Level.INFO,"Endpoint = /login - User not found");
                return ResponseEntity.badRequest().body("Bad Credentials"); }

            String passwordInDb = tempUser.getPassword();
            String passwordToCompare = user.getPassword();
                if(passwordInDb.equals(passwordToCompare)) {
                    long currentTimeMillis = System.currentTimeMillis();
                    return ResponseEntity.ok().body(Jwts.builder()
                            .setSubject(user.getEmail())
                            .claim("roles", "user")
                            .setIssuedAt(new Date(currentTimeMillis))
                            .setExpiration(new Date(currentTimeMillis + 20000))
                            .signWith(SignatureAlgorithm.HS256, "secret key")
                            .compact());
                }
                else {
                    logger.log(Level.INFO,"Endpoint = /login -Wrong password");
                    return ResponseEntity.badRequest().body("Bad Credentials");

                }
        }
    @RequestMapping("/authByToken")
    public ResponseEntity authorizationByToken(@RequestParam  String token)
    {
        return null;
    }
}

