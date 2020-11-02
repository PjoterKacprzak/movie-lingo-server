package com.example.movielingo.controller;


import com.example.movielingo.model.User;
import com.example.movielingo.respository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/api/user/")
public class UserController {

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
        System.out.println(user);
        System.out.println(user.getEmail());
            if(userRepository.findByEmail(user.getEmail()) instanceof User) {
                userRepository.save(user);
                return ResponseEntity.ok("User Saved");
            }
            return ResponseEntity.badRequest().build();
    }


    @PostMapping(value = "/login",consumes = MediaType.APPLICATION_JSON_VALUE)
    public String login(@RequestBody User user){


        System.out.println(user);
        System.out.println(user.getEmail());

        String passwordInDb= userRepository.findByEmail(user.getEmail()).getPassword();
        String passwordToCompare = user.getPassword();
        if(userRepository.findByEmail(user.getEmail())instanceof User)
        {
            if(passwordInDb.equals(passwordToCompare))
            {
                long currentTimeMillis = System.currentTimeMillis();
                return Jwts.builder()
                        .setSubject(user.getEmail())
                        .claim("roles","user")
                        .setIssuedAt(new Date(currentTimeMillis))
                        .setExpiration(new Date(currentTimeMillis+20000))
                        .signWith(SignatureAlgorithm.HS256,"secret key")
                        .compact();
            }
        }
        return "";
    }
}

