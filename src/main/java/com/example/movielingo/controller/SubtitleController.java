package com.example.movielingo.controller;




import com.github.wtekiela.opensub4j.api.OpenSubtitlesClient;
import com.github.wtekiela.opensub4j.impl.OpenSubtitlesClientImpl;
import com.github.wtekiela.opensub4j.response.*;
import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

@Controller
@RequestMapping("/subtitle")
public class SubtitleController {


    @PostMapping(value = "/new-subtitle",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getSubtitle() throws MalformedURLException, XmlRpcException {
//        URL serverUrl = new URL("https", "api.opensubtitles.org", 443, "/xml-rpc");
//        XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
//        config.setServerURL(serverUrl);
//        config.setConnectionTimeout(100);
//        config.setReplyTimeout(100);
//        config.setGzipCompressing(true);
//
//        OpenSubtitlesClient client = new OpenSubtitlesClientImpl(config);

        URL serverUrl = new URL("https", "api.opensubtitles.org", 443, "/xml-rpc");
        OpenSubtitlesClient osClient = new OpenSubtitlesClientImpl(serverUrl);

        ServerInfo serverInfo = osClient.serverInfo();
        Response response = osClient.login("","" ,"en", "");

        assert response.getStatus() == ResponseStatus.OK;
        assert osClient.isLoggedIn() == true;
        System.out.println((osClient.isLoggedIn()));
        System.out.println((response.getStatus()));

        ListResponse<SubtitleInfo> response1 = osClient.searchSubtitles("eng", "Friends", "1", "1");
        List<SubtitleInfo> subtitles = response1.getData();

        System.out.println(subtitles);
        return ResponseEntity.ok(subtitles);
    }

}
