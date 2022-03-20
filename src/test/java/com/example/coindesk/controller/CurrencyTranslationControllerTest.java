package com.example.coindesk.controller;

import com.example.coindesk.service.CurrencyTranslationService;
import com.example.coindesk.vo.CurrencyTranslation;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CurrencyTranslationControllerTest {

    @Mock
    private CurrencyTranslationService currencyTranslationService;

    @InjectMocks
    private CurrencyTranslationController currencyTranslationController = new CurrencyTranslationController();


    @Test
    void getCurrencyTranslations_success_with_no_data() {
        List<CurrencyTranslation> currencyTranslations = new ArrayList<>();
        when(this.currencyTranslationService.getCurrencyTranslationList()).thenReturn(currencyTranslations);

        ResponseEntity<List<? extends Object>> result = this.currencyTranslationController.getCurrencyTranslations();

        Assert.assertEquals(result.getStatusCode(), HttpStatus.OK);
    }

    @Test
    void getCurrencyTranslations_success_with_data() {
        List<CurrencyTranslation> currencyTranslations = this.createCurrencyTranslations();
        when(this.currencyTranslationService.getCurrencyTranslationList()).thenReturn(currencyTranslations);

        ResponseEntity<List<? extends Object>> result = this.currencyTranslationController.getCurrencyTranslations();

        Assert.assertEquals(result.getStatusCode(), HttpStatus.OK);
        Assert.assertEquals(result.getBody().size(), 1);
    }

    private List<CurrencyTranslation> createCurrencyTranslations() {
        List<CurrencyTranslation> currencyTranslations = new ArrayList<>();

        CurrencyTranslation currencyTranslation = new CurrencyTranslation();
        currencyTranslation.setId("1");
        currencyTranslation.setCode("USD");
        currencyTranslation.setName("美元");
        currencyTranslations.add(currencyTranslation);

        return currencyTranslations;
    }


    @Test
    void getCurrencyTranslationById_success() {
        String id = "1";
        CurrencyTranslation currencyTranslation = new CurrencyTranslation();
        when(this.currencyTranslationService.getCurrencyTranslationById(id)).thenReturn(Optional.of(currencyTranslation));

        ResponseEntity<? extends Object> result = this.currencyTranslationController.getCurrencyTranslationById(id);

        Assert.assertEquals(result.getStatusCode(), HttpStatus.OK);
    }

    @Test
    void getCurrencyTranslationById_not_found() {
        String id = "1";
        when(this.currencyTranslationService.getCurrencyTranslationById(id)).thenReturn(Optional.empty());

        ResponseEntity<? extends Object> result = this.currencyTranslationController.getCurrencyTranslationById(id);

        Assert.assertEquals(result.getStatusCode(), HttpStatus.NOT_FOUND);
    }


    @Test
    void updateCurrencyTranslation_success() {
        String id = "1";
        String name = "";
        CurrencyTranslation currencyTranslation = new CurrencyTranslation();

        when(this.currencyTranslationService.isCurrencyTranslationExisted(id)).thenReturn(true);
        when(this.currencyTranslationService.updateCurrencyTranslationById(id, name)).thenReturn(Optional.of(currencyTranslation));

        ResponseEntity<? extends Object> result = this.currencyTranslationController.updateCurrencyTranslation(id, name);

        Assert.assertEquals(result.getStatusCode(), HttpStatus.OK);
    }

    @Test
    void updateCurrencyTranslation_not_found() {
        String id = "1";
        String name = "";
        CurrencyTranslation currencyTranslation = new CurrencyTranslation();

        when(this.currencyTranslationService.isCurrencyTranslationExisted(id)).thenReturn(false);

        ResponseEntity<? extends Object> result = this.currencyTranslationController.updateCurrencyTranslation(id, name);

        Assert.assertEquals(result.getStatusCode(), HttpStatus.NOT_FOUND);
    }

    @Test
    void createCurrencyTranslation_success() {
        String code = "USD";
        String name = "美元";
        CurrencyTranslation currencyTranslation = new CurrencyTranslation();

        when(this.currencyTranslationService.isCodeDuplicated(code)).thenReturn(false);
        when(this.currencyTranslationService.createCurrencyTranslation(code, name)).thenReturn(currencyTranslation);

        ResponseEntity<? extends Object> result = this.currencyTranslationController.createCurrencyTranslation(code, name);
        Assert.assertEquals(result.getStatusCode(), HttpStatus.OK);
    }

    @Test
    void createCurrencyTranslation_code_is_empty() {
        String code = "";
        String name = "美元";

        ResponseEntity<? extends Object> result = this.currencyTranslationController.createCurrencyTranslation(code, name);

        Assert.assertEquals(result.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test
    void createCurrencyTranslation_code_is_duplicated() {
        String code = "USD";
        String name = "美元";

        when(this.currencyTranslationService.isCodeDuplicated(code)).thenReturn(true);

        ResponseEntity<? extends Object> result = this.currencyTranslationController.createCurrencyTranslation(code, name);

        Assert.assertEquals(result.getStatusCode(), HttpStatus.CONFLICT);
    }

    @Test
    void createCurrencyTranslation_name_is_empty() {
        String code = "USD";
        String name = "";

        when(this.currencyTranslationService.isCodeDuplicated(code)).thenReturn(false);

        ResponseEntity<? extends Object> result = this.currencyTranslationController.createCurrencyTranslation(code, name);

        Assert.assertEquals(result.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test
    void deleteCurrencyTranslation_success() {
        String id = "1";
        CurrencyTranslation currencyTranslation = new CurrencyTranslation();

        when(this.currencyTranslationService.isCurrencyTranslationExisted(id)).thenReturn(true);
        when(this.currencyTranslationService.deleteCurrencyTranslationById(id)).thenReturn(Optional.of(currencyTranslation));

        ResponseEntity<? extends Object> result = this.currencyTranslationController.deleteCurrencyTranslation(id);

        Assert.assertEquals(result.getStatusCode(), HttpStatus.OK);
    }

    @Test
    void deleteCurrencyTranslation_not_found() {
        String id = "1";

        when(this.currencyTranslationService.isCurrencyTranslationExisted(id)).thenReturn(false);

        ResponseEntity<? extends Object> result = this.currencyTranslationController.deleteCurrencyTranslation(id);

        Assert.assertEquals(result.getStatusCode(), HttpStatus.NOT_FOUND);
    }

}