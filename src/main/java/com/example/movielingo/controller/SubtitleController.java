package com.example.movielingo.controller;


import com.example.movielingo.configuration.MyConstants;
import com.github.wtekiela.opensub4j.api.OpenSubtitlesClient;
import com.github.wtekiela.opensub4j.impl.OpenSubtitlesClientImpl;
import com.github.wtekiela.opensub4j.response.*;
import org.apache.xmlrpc.XmlRpcException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/subtitle")
public class SubtitleController {
    private final static Logger logger = Logger.getLogger(SubtitleController.class.getName());

    @PostMapping(value = "/new-subtitle", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getSubtitle() throws IOException, XmlRpcException {
//        URL serverUrl = new URL("https", "api.opensubtitles.org", 443, "/xml-rpc");
//        XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
//        config.setServerURL(serverUrl);
//        config.setConnectionTimeout(100);
//        config.setReplyTimeout(100);
//        config.setGzipCompressing(true);
//
//        OpenSubtitlesClient client = new OpenSubtitlesClientImpl(config);

        Response openSubtitleResponse;
        OpenSubtitlesClient osClient;

        //Connection with OpenSubtitles.org API
        URL serverUrl = new URL("https", "api.opensubtitles.org", 443, "/xml-rpc");
        osClient = new OpenSubtitlesClientImpl(serverUrl);
        openSubtitleResponse = osClient.login("", "", "en", MyConstants.getOpensubtitlesName());

        System.out.println(osClient.isLoggedIn());
        System.out.println(openSubtitleResponse.getStatus());
        if (osClient.isLoggedIn()) {
            List<String> finalListOfWords;
            String finalSubtitlesString = "";
            URL oracle = new URL(searchAndCreateSubtitleUrl(osClient, "NothingYet"));
            BufferedReader in = new BufferedReader(new InputStreamReader(oracle.openStream()));

            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                if (inputLine != null && inputLine.length() > 0 && Character.isAlphabetic(inputLine.charAt(0))) {
                    finalSubtitlesString = finalSubtitlesString + " " + inputLine;
                    //System.out.println(inputLine);
                }
            }
            in.close();
            finalListOfWords = parseSubtitle(finalSubtitlesString);

            logger.info(finalListOfWords.toString());

            return ResponseEntity.ok(finalSubtitlesString);
        }

        return ResponseEntity.badRequest().body("Endpoint - new-subtitle : Connection failed");
    }

    String searchAndCreateSubtitleUrl(OpenSubtitlesClient osClient, String movieName) throws XmlRpcException {
        ListResponse<SubtitleInfo> listOfSubtitles;
        List<SubtitleInfo> properSubtitle;
        String downloadSubtitleUrl;
        //ListResponse<SubtitleInfo> listOfSubtitles = osClient.searchSubtitles("eng", "Hercules","1","1");
        listOfSubtitles = osClient.searchSubtitles("eng", "1856101");
        properSubtitle = listOfSubtitles.getData();
        downloadSubtitleUrl = properSubtitle.get(0).getDownloadLink();
        downloadSubtitleUrl = downloadSubtitleUrl.replace(".gz", ".srt");

        return downloadSubtitleUrl;

    }

    List<String> parseSubtitle(String string) {
        string = string.replace("</i>", "");
        string = string.replace(". ", " ");
        string = string.toLowerCase(Locale.ROOT);
        for (int iterator = 0; iterator < string.length(); iterator++) {
            if (string.charAt(iterator) >= 97 && string.charAt(iterator) <= 122) {

            } else {
                string = replaceChar(string, ' ', iterator);
            }
        }
        string = string.trim().replaceAll(" +", " ");
        List<String> myList = new ArrayList<String>(Arrays.asList(string.split(" ")));

        System.out.println(myList);

        System.out.println("test");
        System.out.println("test");
        System.out.println(myList.size());
        System.out.println("test");
        System.out.println("test");


        List<String> listWithoutDuplicates = myList.stream()
                .distinct()
                .collect(Collectors.toList());


        for (int iterator = 0; iterator < listWithoutDuplicates.size(); iterator++) {
            if (listWithoutDuplicates.get(iterator).length() < 3) {
                listWithoutDuplicates.remove(iterator);
            }
        }
         System.out.println(listWithoutDuplicates.size());
        Collections.sort(listWithoutDuplicates);
        //logger.info(listWithoutDuplicates.toString());


        return listWithoutDuplicates;
    }

    public String replaceChar(String str, char ch, int index) {
        return str.substring(0, index) + ch + str.substring(index + 1);
    }

}
