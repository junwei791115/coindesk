package com.example.coindesk.controller;

import com.example.coindesk.entity.CurrencyTranslationEntity;
import com.example.coindesk.service.CurrencyTranslationService;
import com.example.coindesk.vo.CurrencyTranslationVo;
import com.sun.istack.NotNull;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class CurrencyTranslationController {

    @Autowired
    private CurrencyTranslationService currencyTranslationService;

    @GetMapping(value = "/currencyTranslations", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CurrencyTranslationVo>> getCurrencyTranslations() {
        List<CurrencyTranslationVo> currencyTranslations = this.currencyTranslationService.getCurrencyTranslationList();
        return new ResponseEntity<>(currencyTranslations, HttpStatus.OK);
    }

    @GetMapping(value = "/currencyTranslations/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CurrencyTranslationVo> getCurrencyTranslationById(@PathVariable("id") final String id) {
        Optional<CurrencyTranslationVo> currencyTranslationVoOptional = this.currencyTranslationService.getCurrencyTranslationById(Long.valueOf(id));
        if (currencyTranslationVoOptional.isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(currencyTranslationVoOptional.get(), HttpStatus.OK);
    }

    @PutMapping(value = "/currencyTranslations/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CurrencyTranslationVo> updateCurrencyTranslation(@PathVariable("id") final String id, String code, String name) {
        if (this.currencyTranslationService.isCodeDuplicated(Long.valueOf(id), code)) {
            return new ResponseEntity<>(null, HttpStatus.CONFLICT);
        }
        Optional<CurrencyTranslationVo> currencyTranslationVoOptional = this.currencyTranslationService.updateCurrencyTranslationById(Long.valueOf(id), code, name);
        if (currencyTranslationVoOptional.isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(currencyTranslationVoOptional.get(), HttpStatus.OK);
    }

    @PostMapping(value = "/currencyTranslations", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CurrencyTranslationVo> createCurrencyTranslation(String code, String name) {

        if (StringUtils.isEmpty(code)) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        if (StringUtils.isEmpty(name)) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(this.currencyTranslationService.createCurrencyTranslation(code, name), HttpStatus.OK);
    }

    @DeleteMapping(value = "/currencyTranslations/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteCurrencyTranslation(@PathVariable("id") final String id) {

        return new ResponseEntity<>("", HttpStatus.OK);
    }

}
