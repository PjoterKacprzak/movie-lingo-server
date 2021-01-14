package com.example.movielingo.controller;

import com.example.movielingo.configuration.MyConstants;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;
import org.springframework.beans.factory.annotation.Value;

import java.util.logging.Level;
import java.util.logging.Logger;

public class TokenController {

    static boolean isValid(String token) {

        boolean validation = false;
        try {
            Jwts.parser().setSigningKey(MyConstants.getTokenSignKey()).parseClaimsJws(token).getBody().getSubject();
            validation = true;
        } catch (SignatureException e) {
            Logger.getLogger(TokenController.class.getName()).log(Level.INFO,"Token Expired");
        }
        return validation;
    }


    static String refreshToken(){

        return null;
    }

}
