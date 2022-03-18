package com.example.coindesk.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;


@Controller
public class Test {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping(value = "/test", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getAssetById() {
        String url = "https://api.coindesk.com/v1/bpi/currentprice.json";
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);


        return new ResponseEntity<>(response.getBody(), HttpStatus.OK);
    }
}
