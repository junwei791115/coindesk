package com.example.coindesk.controller;

import com.example.coindesk.service.CurrencyTranslationService;
import com.example.coindesk.vo.CurrencyTranslation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
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

    /**
     * Get currency translation list
     *
     * @return
     */
    @GetMapping(value = "/currencyTranslations", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<? extends Object>> getCurrencyTranslations() {
        List<CurrencyTranslation> currencyTranslations = this.currencyTranslationService.getCurrencyTranslationList();
        return new ResponseEntity<>(currencyTranslations, HttpStatus.OK);
    }

    /**
     * Get currency translation by id
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/currencyTranslations/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<? extends Object> getCurrencyTranslationById(@PathVariable("id") final String id) {
        Optional<CurrencyTranslation> currencyTranslationVoOptional = this.currencyTranslationService.getCurrencyTranslationById(id);
        if (currencyTranslationVoOptional.isEmpty()) {
            return new ResponseEntity<>("Can not find currency translation by id  '" + id + "'.", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(currencyTranslationVoOptional.get(), HttpStatus.OK);
    }

    /**
     * Update currency translation by id
     *
     * @param id
     * @param name
     * @return
     */
    @PutMapping(value = "/currencyTranslations/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<? extends Object> updateCurrencyTranslation(@PathVariable("id") final String id, String name) {

        if (!this.currencyTranslationService.isCurrencyTranslationExisted(id)) {
            return new ResponseEntity<>("Can not find currency translation by id  '" + id + "'.", HttpStatus.NOT_FOUND);
        }

        Optional<CurrencyTranslation> currencyTranslationVoOptional = this.currencyTranslationService.updateCurrencyTranslationById(id, name);
        return new ResponseEntity<>(currencyTranslationVoOptional.get(), HttpStatus.OK);
    }

    /**
     * Create currency translation.
     *
     * @param code
     * @param name
     * @return
     */
    @PostMapping(value = "/currencyTranslations", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<? extends Object> createCurrencyTranslation(String code, String name) {

        if (StringUtils.isEmpty(code)) {
            return new ResponseEntity<>("The value of code can not to be null ", HttpStatus.BAD_REQUEST);
        }

        if (this.currencyTranslationService.isCodeDuplicated(code)) {
            return new ResponseEntity<>("The value of code is duplicated ' " + code + "'. ", HttpStatus.CONFLICT);
        }

        if (StringUtils.isEmpty(name)) {
            return new ResponseEntity<>("The value of name can not to be null ", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(this.currencyTranslationService.createCurrencyTranslation(code, name), HttpStatus.OK);
    }

    /**
     * Enable/Disable currency translation.
     *
     * @param id
     * @return
     */
    @DeleteMapping(value = "/currencyTranslations/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<? extends Object> deleteCurrencyTranslation(@PathVariable("id") final String id) {
        if (!this.currencyTranslationService.isCurrencyTranslationExisted(id)) {
            return new ResponseEntity<>("Can not find currency translation by id ' " + id + " '. ", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(this.currencyTranslationService.deleteCurrencyTranslationById(id), HttpStatus.OK);
    }

}
