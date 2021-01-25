package com.example.movielingo.controller;


import com.example.movielingo.configuration.MyConstants;
import com.example.movielingo.model.MovieFlashCard;
import com.example.movielingo.model.WordFrequency;

import com.example.movielingo.respository.WordFrequencyRepository;
import com.github.wtekiela.opensub4j.api.OpenSubtitlesClient;
import com.github.wtekiela.opensub4j.impl.OpenSubtitlesClientImpl;
import com.github.wtekiela.opensub4j.response.*;

import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.xmlrpc.XmlRpcException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.io.*;

import java.net.URL;


import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.sql.Connection;
@RestController
@RequestMapping("/subtitle")
public class SubtitleController {


    @Autowired
    private WordFrequencyRepository wordFrequencyRepository;

    private final static Logger logger = Logger.getLogger(SubtitleController.class.getName());


    @PostMapping(value = "/test",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity test() throws IOException {

       //List<WordFrequency> lists=  parseTxtToJson();
       // wordFrequencyRepository.save(lists);
        insertDataToDb();
        return null;
    }


    @PostMapping(value = "/new-subtitle", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getSubtitle(@RequestBody MovieFlashCard movieFlashCard) throws IOException, XmlRpcException {

        System.out.println(movieFlashCard);
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

            String finalSubtitlesString = "";

            //Download Subtitles
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

            List<WordFrequency> listOfWordsWithFrequency = fetchListOfWordFrequencyList();


            //delete unwanted words/chars
            List<String> finalListOfWords;
            finalListOfWords = parseSubtitle(finalSubtitlesString);


            logger.info(String.valueOf(finalListOfWords.size()));
            int temp;
            //delete wrong words
            for(WordFrequency wordFrequency : listOfWordsWithFrequency)
            {
                temp = finalListOfWords.lastIndexOf(wordFrequency.getWord());
                if(wordFrequency.getFrequencyValue()<=50  && temp!=-1)
                {
                    finalListOfWords.remove(temp);
                }
            }

            logger.info(String.valueOf(finalListOfWords.size()));

                //Translate user words
            Translate translate = TranslateOptions.getDefaultInstance().getService();

           // System.out.println(finalListOfWords.toString().replace(", ","| "));
            Translation translation = translate.translate(finalListOfWords.toString().replace(", ","| "));

            System.out.println(translation);



            //parse to list of words (translated words)
            List<String>translatedText;

            String temporaryString = translation.toString();
            //translatedText = Arrays.asList(translation.getTranslatedText().split(" "));
            translatedText = Arrays.asList(temporaryString.split("\\|"));
            //System.out.println(translatedText);
            //System.out.println(translatedText);



            //Collection<String>translatedTextCollection = Arrays.asList(translatedText)

            List<WordFrequency> finalWordFrequencyList= new ArrayList<>();
//            for(String s : translatedText)
//            {
//                for (WordFrequency wordFrequency : listOfWordsWithFrequency)
//                {
//                    if(s.equals(wordFrequency.getWord()))
//                    {
//                        finalWordFrequencyList.add(wordFrequency);
//                    }
//                }
//            }
            logger.info(String.valueOf(finalListOfWords.size()));
            logger.info(String.valueOf(translatedText.size()));
            for(int i=0;i<finalListOfWords.size();i++)
            {
                System.out.println(finalListOfWords.get(i) +"  "+ translatedText.get(i));
            }

//            logger.info(finalListOfWords.toString());
//            logger.info(translatedText.toString());
//            logger.info(translatedText.toString());
//            logger.info(String.valueOf(translatedText.size()));
//            logger.info("\n\n\n");
//           // logger.info(listOfWordsWithFrequency.toString());
//            logger.info("\n\n\n");
//            logger.info(finalWordFrequencyList.toString());
//            logger.info(String.valueOf(finalWordFrequencyList.size()));

//            Long wordFreq;
//            Map<String,Long> userTranslatedWordsWithFrequency = new HashMap<>();
//            for(String s : translatedText)
//            {
//                    wordFreq =  wordFrequencyRepository.findWordFrequencyByFrequencyValueIs(s);
//                userTranslatedWordsWithFrequency.put(s,wordFreq);
//            }

            return ResponseEntity.ok(translatedText);
        }

        return ResponseEntity.badRequest().body("Endpoint - new-subtitle : Connection failed");
    }

    String searchAndCreateSubtitleUrl(OpenSubtitlesClient osClient, String movieName) throws XmlRpcException {
        ListResponse<SubtitleInfo> listOfSubtitles;
        List<SubtitleInfo> properSubtitle;
        String downloadSubtitleUrl;
        //ListResponse<SubtitleInfo> listOfSubtitles = osClient.searchSubtitles("eng", "Hercules","1","1");
        listOfSubtitles = osClient.searchSubtitles("pol", "0050083");
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
            if (string.charAt(iterator) >= 97 && string.charAt(iterator) <= 122
                    ||string.charAt(iterator)=='ś'||string.charAt(iterator)=='ć'||string.charAt(iterator)=='ź'
                        ||string.charAt(iterator)=='ż'||string.charAt(iterator)=='ó'
                  ||string.charAt(iterator)=='ą'||string.charAt(iterator)=='ę'
                    ||string.charAt(iterator)=='ł') {

            } else {
                string = replaceChar(string, ' ', iterator);
            }
        }
        string = string.trim().replaceAll(" +", " ");
        List<String> myList = new ArrayList<String>(Arrays.asList(string.split(" ")));

//        System.out.println(myList);
//
//        System.out.println("test");
//        System.out.println("test");
//        System.out.println(myList.size());
//        System.out.println("test");
//        System.out.println("test");


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
    List<WordFrequency> fetchListOfWordFrequencyList() throws IOException {
        List<WordFrequency>listOfWords = new ArrayList<>();
        String[] parts;
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        //FileWriter csvWriter = new FileWriter("C:\\Users\\Piotr Kacprzak\\Desktop\\new.csv");
//        csvWriter.append("word");
//        csvWriter.append(",");
//        csvWriter.append("word_frequency");
//        csvWriter.append("\n");
        try {
            File myObj = new File("C:\\Users\\Piotr Kacprzak\\Desktop\\pl_full.txt");

           // String json = Json.createObjectBuilder().
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                parts = data.split(" ");
                listOfWords.add(new WordFrequency(parts[0],Long.parseLong(parts[1])));
              //  csvWriter.append(parts[0]);
//                csvWriter.append(",");
//                csvWriter.append(parts[1]);
//                csvWriter.append("\n");

//               // sampleObject.put("id", id);
//                sampleObject.put("word", parts[0]);
//                sampleObject.put("word_frequency", parts[1]);
               // map.put(parts[0],Long.parseLong(parts[1]));

               // jsonArray.add(sampleObject);
               // listOfWords.add(new WordFrequency(parts[0],Long.valueOf(parts[1])));
               // System.out.println(Arrays.toString(parts));

            }
           // String json = gson.toJson(listOfWords);
            //JSONObject jsonMap = new JSONObject(map);
//            csvWriter.flush();
//            csvWriter.close();
            //  System.out.println(map);

//            FileWriter file = new FileWriter("C:\\Users\\Piotr Kacprzak\\Desktop\\eng.json");
//            file.write(json);
//            file.flush();
//            file.close();
//            myReader.close();
            System.out.println("done");
            return  listOfWords;

        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return  listOfWords;
    }
    List<WordFrequency> insertDataToDb() {

        List<WordFrequency>listOfWords = new ArrayList<>();
        String[] parts;


        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try {
            File myObj = new File("C:\\Users\\Piotr Kacprzak\\Desktop\\en_full.txt");

            // String json = Json.createObjectBuilder().
            Scanner myReader = new Scanner(myObj);

            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                parts = data.split(" ");
                //wordFrequencyRepository.save(new WordFrequency(parts[0],Long.parseLong(parts[1])));
                listOfWords.add(new WordFrequency(parts[0],Long.parseLong(parts[1])));

            }
            System.out.println(listOfWords);
            //wordFrequencyRepository.saveAll(listOfWords);
//            String json = gson.toJson(listOfWords);
//
//            FileWriter file = new FileWriter("C:\\Users\\Piotr Kacprzak\\Desktop\\eng.json");
//            file.write(json);
//            file.flush();
//            file.close();
//            myReader.close();
            System.out.println("done");
                return listOfWords;

        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
    Map<String,Long> fetchWordFrequencyFromDataBase(List Us) {
        Map<String,Long> userWordswithFrequency = new HashMap<String,Long>();

            return userWordswithFrequency;
    }

    }


