package com.example.movielingo;

import com.example.movielingo.configuration.MyConstants;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MovieLingoApplication {

    public static void main(String[] args) {
        System.setProperty("GOOGLE_API_KEY", MyConstants.getGoogleClientSecret());
        SpringApplication.run(MovieLingoApplication.class, args);
    }

}
