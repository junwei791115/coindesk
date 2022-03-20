package com.example.coindesk.controller;

import com.example.coindesk.service.CoinDeskService;
import com.example.coindesk.vo.CoinDesk;
import com.example.coindesk.vo.CoinDeskInfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Optional;


@Controller
public class CoinDeskController {

    @Autowired
    private CoinDeskService coinDeskService;

    @GetMapping(value = "/coinDesk", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<? extends Object> getCoinDesk() {
        Optional<CoinDesk> coinDeskVoOptional = this.coinDeskService.getCoinDesk();
        if (coinDeskVoOptional.isEmpty()) {
            return new ResponseEntity<>("Data not found.", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(coinDeskVoOptional.get(), HttpStatus.OK);
    }

    @GetMapping(value = "/coinDeskInformation", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<? extends Object> getCoinDeskInformation() {
        Optional<CoinDeskInfo> coinDeskInfoOptional = this.coinDeskService.getCoinDeskInformation();
        if (coinDeskInfoOptional.isEmpty()) {
            return new ResponseEntity<>("Data not found.", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(coinDeskInfoOptional.get(), HttpStatus.OK);
    }
}
