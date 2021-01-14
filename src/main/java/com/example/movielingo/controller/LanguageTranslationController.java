package com.example.movielingo.controller;

import com.example.movielingo.model.SingleWordTranslation;
import com.google.cloud.translate.*;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;

import java.net.URL;

import java.util.Locale;
import java.util.logging.Logger;


@Controller
@RequestMapping(value = "/translation-api")
public class LanguageTranslationController {

    private final static Logger logger = Logger.getLogger(LanguageTranslationController.class.getName());


    @PostMapping(value = "/single-word-translation", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity singleWordTranslation(@RequestBody SingleWordTranslation singleWordTranslation) {
        String sourceLang = singleWordTranslation.getSourceLanguage();
        String targetLang = singleWordTranslation.getTargetLanguage();
        String word = singleWordTranslation.getWord();
        word = word.replace(" ", "+");
        int responseCode;
        try {
            URL url = new URL("https://translate.googleapis.com/translate_a/single?client=gtx&sl="
                    + sourceLang + "&tl=" + targetLang + "&dt=t&q=" + word);

            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            responseCode = con.getResponseCode();
            System.out.println(responseCode);

            if (responseCode == HttpURLConnection.HTTP_OK) { // success
                BufferedReader in = new BufferedReader(new InputStreamReader(
                        con.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {

                    response.append(inputLine);
                }
                in.close();

                String responseString = response.toString();


                return ResponseEntity.ok(cleanString(responseString));

            } else {
                return ResponseEntity.badRequest().body("cannot translate word");
            }
        } catch (MalformedURLException e) {
            logger.info("MalformedURLException");

        } catch (IOException e) {
            logger.info("IOException");
        }


        return ResponseEntity.ok("done");
    }


    @PostMapping(value = "/multiple-word-translation", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity multipleWordTranslation() {

        Translate translate = TranslateOptions.getDefaultInstance().getService();

        Translation translation = translate.translate("cześć mam na imie piotr i jestem stundetem");
        return ResponseEntity.ok(translate);
    }



    @GetMapping(value = "/supported-languages")
    public ResponseEntity supportedLanguages() {

        Translate translate = TranslateOptions.getDefaultInstance().getService();
        return ResponseEntity.ok(translate.listSupportedLanguages());
    }


    public String cleanString(String word) {


        word = word.replace('[', ' ');
        word = word.replace('"', ' ');
        word = word.trim();
        String[] temporary = word.split(",");
        word = temporary[0];
        word = word.trim();
        word = word.toLowerCase(Locale.ROOT);
        return word;
    }

}
