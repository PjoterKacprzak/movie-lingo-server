package com.example.movielingo.controller;

import com.example.movielingo.model.PasswordChange;
import com.example.movielingo.model.User;
import com.example.movielingo.model.UserFlashCard;

import com.example.movielingo.respository.UserFlashCardRepository;
import com.example.movielingo.respository.UserRepository;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.util.logging.Level;
import java.util.logging.Logger;



//TODO:create Password Encoder
@RestController
@RequestMapping("/api/user/")
public class UserController {

    private final static Logger logger = Logger.getLogger(UserController.class.getName());

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserFlashCardRepository userFlashCardRepository;


    @RequestMapping("/getAll")
    public ResponseEntity getAllUsers()
    {
        return ResponseEntity.ok().body(userRepository.findAll());
    }

    @PostMapping(value = "/authByToken",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity authorizationByToken(ServletRequest servletRequest)
    {

        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;

        String token = httpServletRequest.getHeader("authorization");

        if(!TokenController.isValid(token))
        {
            return ResponseEntity.badRequest().body("Token expired");
        }
        else{

            Claims claims = (Claims) servletRequest.getAttribute("claims");

            System.out.println("Token = "+ claims);
            String passwordFromToken =  claims.get("password").toString();
            String emailFromToken = claims.getSubject();
            System.out.println(emailFromToken);
            User tempUser = userRepository.findByEmail(emailFromToken);
            if (tempUser == null) {
                logger.log(Level.INFO, "Endpoint = /Auto-Login - User not found");
                return ResponseEntity.badRequest().body("Bad Credentials");
            }
            String passwordInDb = tempUser.getPassword();

            if (passwordInDb.equals(passwordFromToken)) {

                return ResponseEntity.ok().body("Ok Credentials");
            } else {
                logger.log(Level.INFO, "Endpoint = /Auto-login -Wrong password");
                return ResponseEntity.badRequest().body("Bad Credentials");

            }


        }


    }
    @PostMapping(value = "/user-information",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity userInformaion(ServletRequest servletRequest){
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;

        Claims claims = (Claims) servletRequest.getAttribute("claims");
        System.out.println(claims);
        String passwordFromToken =  claims.get("password").toString();
        String emailFromToken = claims.getSubject();
        User tempUser = userRepository.findByEmail(emailFromToken);
        if (tempUser == null) {
            logger.log(Level.INFO, "Endpoint = /User information - User not found");
            return ResponseEntity.badRequest().body("User not found");
        }
        String passwordInDb = tempUser.getPassword();

        if (passwordInDb.equals(passwordFromToken)) {

            return ResponseEntity.ok().body(tempUser);
        } else {
            logger.log(Level.INFO, "Endpoint = /User information - Wrong password");
            return ResponseEntity.badRequest().body("Bad Credentials");

        }
    }

    @PatchMapping(value = "/change-password",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity changePassword(ServletRequest servletRequest, @RequestBody PasswordChange passwordChange){


        System.out.println(passwordChange);
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;

        Claims claims = (Claims) servletRequest.getAttribute("claims");
        System.out.println(claims);
        String passwordFromToken =  claims.get("password").toString();
        String emailFromToken = claims.getSubject();
        User tempUser = userRepository.findByEmail(emailFromToken);
        if (tempUser == null) {
            logger.log(Level.INFO, "Endpoint = /change-password - User not found");
            return ResponseEntity.badRequest().body("User not found");
        }
        else if(!tempUser.getPassword().equals(passwordChange.getOldPassword()))
        {
            logger.log(Level.INFO, "Endpoint = /change-password Old password is incorrect");
            return ResponseEntity.badRequest().body("Old password is incorrect");
        }
        String passwordInDb = tempUser.getPassword();

        if (passwordInDb.equals(passwordFromToken)) {

            tempUser.setPassword(passwordChange.getNewPassword());
            userRepository.save(tempUser);
            return ResponseEntity.ok().body("Password Changed");
        } else {
            logger.log(Level.INFO, "Endpoint = /change-password - Wrong password");
            return ResponseEntity.badRequest().body("Bad Credentials");

        }
    }

    @PostMapping(value = "/new-flash-card")
    public ResponseEntity newFlashCard(ServletRequest servletRequest,@RequestBody UserFlashCard userFlashCard)
    {

        Claims claims = (Claims) servletRequest.getAttribute("claims");

        String emailFromToken = claims.getSubject();
        userFlashCard.setEmail(emailFromToken);
        userFlashCardRepository.save(userFlashCard);
        System.out.println(userFlashCardRepository.findAll());

        return  ResponseEntity.ok().body(userFlashCardRepository.save(userFlashCard));
    }

    @PatchMapping(value = "/edit-flash-card")
    public ResponseEntity editFlashCard(ServletRequest servletRequest,@RequestBody UserFlashCard userFlashCard)
    {

        Claims claims = (Claims) servletRequest.getAttribute("claims");

        String emailFromToken = claims.getSubject();
        userFlashCard.setEmail(emailFromToken);
        System.out.println(userFlashCard);

        userFlashCardRepository.deleteById(userFlashCard.getId());

        return  ResponseEntity.ok().body(userFlashCardRepository.save(userFlashCard));
    }


    @GetMapping(value ="/get-card-by-user" )
    public ResponseEntity getCardsByUser(ServletRequest servletRequest)
    {
        Claims claims = (Claims) servletRequest.getAttribute("claims");

        String emailFromToken = claims.getSubject();

        System.out.println(userFlashCardRepository.findFlashCardByEmail(emailFromToken));

        return ResponseEntity.ok().body(userFlashCardRepository.findFlashCardByEmail(emailFromToken));
    }

}




