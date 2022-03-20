package com.example.coindesk.service;

import com.example.coindesk.vo.CoinDesk;
import com.example.coindesk.vo.CoinDeskInfo;
import com.example.coindesk.vo.CurrencyTranslation;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.*;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CoinDeskServiceTest {

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private CurrencyTranslationService currencyTranslationService;

    @InjectMocks
    private CoinDeskService coinDeskService = new CoinDeskServiceImpl();

    @Test
    void getCoinDesk_success() throws JsonProcessingException {
        String data = "{\"time\":{\"updated\":\"Mar 20, 2022 11:28:00 UTC\",\"updatedISO\":\"2022-03-20T11:28:00+00:00\",\"updateduk\":\"Mar 20, 2022 at 11:28 GMT\"},\"disclaimer\":\"This data was produced from the CoinDesk Bitcoin Price Index (USD). Non-USD currency data converted using hourly conversion rate from openexchangerates.org\",\"chartName\":\"Bitcoin\",\"bpi\":{\"USD\":{\"code\":\"USD\",\"symbol\":\"&#36;\",\"rate\":\"41,785.9325\",\"description\":\"United States Dollar\",\"rate_float\":41785.9325},\"GBP\":{\"code\":\"GBP\",\"symbol\":\"&pound;\",\"rate\":\"31,701.6498\",\"description\":\"British Pound Sterling\",\"rate_float\":31701.6498},\"EUR\":{\"code\":\"EUR\",\"symbol\":\"&euro;\",\"rate\":\"37,763.3679\",\"description\":\"Euro\",\"rate_float\":37763.3679}}}";
        String url = "https://api.coindesk.com/v1/bpi/currentprice.json";
        CoinDesk coinDesk = new CoinDesk();

        when(this.restTemplate.getForEntity(url, String.class)).thenReturn(new ResponseEntity(data, HttpStatus.OK));
        when(this.objectMapper.readValue(data, CoinDesk.class)).thenReturn(coinDesk);

        Optional<CoinDesk> result = this.coinDeskService.getCoinDesk();

        Assert.assertTrue(result.isPresent());
    }

    @Test
    void getCoinDesk_JsonProcessingException() throws JsonProcessingException {
        String data = "{\"time\":{\"updated\":\"Mar 20, 2022 11:28:00 UTC\",\"updatedISO\":\"2022-03-20T11:28:00+00:00\",\"updateduk\":\"Mar 20, 2022 at 11:28 GMT\"},\"disclaimer\":\"This data was produced from the CoinDesk Bitcoin Price Index (USD). Non-USD currency data converted using hourly conversion rate from openexchangerates.org\",\"chartName\":\"Bitcoin\",\"bpi\":{\"USD\":{\"code\":\"USD\",\"symbol\":\"&#36;\",\"rate\":\"41,785.9325\",\"description\":\"United States Dollar\",\"rate_float\":41785.9325},\"GBP\":{\"code\":\"GBP\",\"symbol\":\"&pound;\",\"rate\":\"31,701.6498\",\"description\":\"British Pound Sterling\",\"rate_float\":31701.6498},\"EUR\":{\"code\":\"EUR\",\"symbol\":\"&euro;\",\"rate\":\"37,763.3679\",\"description\":\"Euro\",\"rate_float\":37763.3679}}}";
        String url = "https://api.coindesk.com/v1/bpi/currentprice.json";

        when(this.restTemplate.getForEntity(url, String.class)).thenReturn(new ResponseEntity(data, HttpStatus.OK));
        when(this.objectMapper.readValue(data, CoinDesk.class)).thenThrow(new JsonParseException(null, ""));

        Optional<CoinDesk> result = this.coinDeskService.getCoinDesk();

        Assert.assertTrue(result.isEmpty());
    }

    @Test
    void getCoinDeskInformation_success() throws JsonProcessingException {
        String data = "{\"time\":{\"updated\":\"Mar 20, 2022 11:28:00 UTC\",\"updatedISO\":\"2022-03-20T11:28:00+00:00\",\"updateduk\":\"Mar 20, 2022 at 11:28 GMT\"},\"disclaimer\":\"This data was produced from the CoinDesk Bitcoin Price Index (USD). Non-USD currency data converted using hourly conversion rate from openexchangerates.org\",\"chartName\":\"Bitcoin\",\"bpi\":{\"USD\":{\"code\":\"USD\",\"symbol\":\"&#36;\",\"rate\":\"41,785.9325\",\"description\":\"United States Dollar\",\"rate_float\":41785.9325},\"GBP\":{\"code\":\"GBP\",\"symbol\":\"&pound;\",\"rate\":\"31,701.6498\",\"description\":\"British Pound Sterling\",\"rate_float\":31701.6498},\"EUR\":{\"code\":\"EUR\",\"symbol\":\"&euro;\",\"rate\":\"37,763.3679\",\"description\":\"Euro\",\"rate_float\":37763.3679}}}";
        String url = "https://api.coindesk.com/v1/bpi/currentprice.json";
        CoinDesk coinDesk = this.createCoinDesk();
        List<CurrencyTranslation> currencyTranslations = this.createCurrencyTranslations();

        when(this.restTemplate.getForEntity(url, String.class)).thenReturn(new ResponseEntity(data, HttpStatus.OK));
        when(this.objectMapper.readValue(data, CoinDesk.class)).thenReturn(coinDesk);
        when(this.currencyTranslationService.getCurrencyTranslationList()).thenReturn(currencyTranslations);

        Optional<CoinDeskInfo> result = this.coinDeskService.getCoinDeskInformation();
        Assert.assertTrue(result.isPresent());

    }

    @Test
    void getCoinDeskInformation_not_found() throws JsonProcessingException {
        String data = "{\"time\":{\"updated\":\"Mar 20, 2022 11:28:00 UTC\",\"updatedISO\":\"2022-03-20T11:28:00+00:00\",\"updateduk\":\"Mar 20, 2022 at 11:28 GMT\"},\"disclaimer\":\"This data was produced from the CoinDesk Bitcoin Price Index (USD). Non-USD currency data converted using hourly conversion rate from openexchangerates.org\",\"chartName\":\"Bitcoin\",\"bpi\":{\"USD\":{\"code\":\"USD\",\"symbol\":\"&#36;\",\"rate\":\"41,785.9325\",\"description\":\"United States Dollar\",\"rate_float\":41785.9325},\"GBP\":{\"code\":\"GBP\",\"symbol\":\"&pound;\",\"rate\":\"31,701.6498\",\"description\":\"British Pound Sterling\",\"rate_float\":31701.6498},\"EUR\":{\"code\":\"EUR\",\"symbol\":\"&euro;\",\"rate\":\"37,763.3679\",\"description\":\"Euro\",\"rate_float\":37763.3679}}}";
        String url = "https://api.coindesk.com/v1/bpi/currentprice.json";
        CoinDesk coinDesk = this.createCoinDesk();
        List<CurrencyTranslation> currencyTranslations = this.createCurrencyTranslations();

        when(this.restTemplate.getForEntity(url, String.class)).thenReturn(new ResponseEntity(data, HttpStatus.OK));
        when(this.objectMapper.readValue(data, CoinDesk.class)).thenThrow(new JsonParseException(null, ""));

        Optional<CoinDeskInfo> result = this.coinDeskService.getCoinDeskInformation();
        Assert.assertTrue(result.isEmpty());

    }

    private List<CurrencyTranslation> createCurrencyTranslations() {
        List<CurrencyTranslation> currencyTranslations = new ArrayList<>();
        CurrencyTranslation currencyTranslation = new CurrencyTranslation();
        currencyTranslation.setId("1");
        currencyTranslation.setName("美元");
        currencyTranslation.setCode("USD");
        currencyTranslation.setUpdateTime("2022/03/20 02:35:55");
        currencyTranslations.add(currencyTranslation);

        return currencyTranslations;
    }

    private CoinDesk createCoinDesk() {
        CoinDesk coinDesk = new CoinDesk();
        Map<String, String> time = new HashMap<>();
        time.put("updated", "Mar 19, 2022 18:29:00 UTC");
        time.put("updatedISO", "2022-03-19T18:29:00+00:00");
        time.put("updateduk", "Mar 19, 2022 at 18:29 GMT");
        coinDesk.setTime(time);
        String disclaimer = "";
        coinDesk.setDisclaimer(disclaimer);
        coinDesk.setChartName("Bitcoin");

        Map<String, Map<String, String>> bpi = new HashMap<>();

        Map<String, String> usdMap = new HashMap<>();
        usdMap.put("code","USD");
        usdMap.put("symbol","&#36;");
        usdMap.put("rate","41,900.2050");
        usdMap.put("description","United States Dollar");
        usdMap.put("rate_float","41900.205");
        bpi.put("USD", usdMap);
        coinDesk.setBpi(bpi);

        return coinDesk;
    }
}