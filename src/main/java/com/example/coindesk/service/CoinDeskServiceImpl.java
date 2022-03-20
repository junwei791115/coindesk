package com.example.coindesk.service;

import com.example.coindesk.vo.CoinDesk;
import com.example.coindesk.vo.CoinDeskInfo;
import com.example.coindesk.vo.CurrencyInfo;
import com.example.coindesk.vo.CurrencyTranslation;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.time.OffsetDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CoinDeskServiceImpl implements CoinDeskService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private CurrencyTranslationService currencyTranslationService;

    ObjectMapper objectMapper = new ObjectMapper();

    SimpleDateFormat dateFormatString = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    @Override
    public Optional<CoinDesk> getCoinDesk() {
        CoinDesk coinDeskVo;
        String url = "https://api.coindesk.com/v1/bpi/currentprice.json";

        try {
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            coinDeskVo = objectMapper.readValue(response.getBody(), CoinDesk.class);
        } catch (JsonProcessingException e) {
            return Optional.empty();
        }

        return Optional.of(coinDeskVo);
    }

    @Override
    public Optional<CoinDeskInfo> getCoinDeskInformation() {
        CoinDeskInfo coinDeskInfo = new CoinDeskInfo();
        Optional<CoinDesk> coinDeskVoOptional = this.getCoinDesk();
        if (coinDeskVoOptional.isEmpty()) {
            return Optional.empty();
        }
        CoinDesk coinDeskVo = coinDeskVoOptional.get();
        String updateTimeString = this.getUpdateTime(coinDeskVo);
        coinDeskInfo.setUpdateTime(updateTimeString);

        List<CurrencyTranslation> currencyTranslations = this.currencyTranslationService.getCurrencyTranslationList();
        Map<String, String> currencyTranslationsMap = currencyTranslations.stream().collect(Collectors.toMap(CurrencyTranslation::getCode, CurrencyTranslation::getName));

        List<CurrencyInfo> currencyInfos = this.getCurrencyInfos(coinDeskVo, currencyTranslationsMap);
        coinDeskInfo.setCurrencyInfos(currencyInfos);
        return Optional.of(coinDeskInfo);
    }

    private List<CurrencyInfo> getCurrencyInfos(CoinDesk coinDeskVo, Map<String, String> currencyTranslationsMap) {
        List<CurrencyInfo> currencyInfos = new ArrayList<>();
        Map<String, Map<String, String>> bpi = coinDeskVo.getBpi();
        for (Map.Entry<String, Map<String, String>> entry : bpi.entrySet()) {
            String code = entry.getKey();
            Map<String, String> currencyMap = entry.getValue();
            CurrencyInfo currencyInfo = new CurrencyInfo();
            currencyInfo.setCurrency(code);
            currencyInfo.setRate(currencyMap.get("rate"));
            currencyInfo.setRate_float(currencyMap.get("rate_float"));
            currencyInfo.setCurrencyName(currencyTranslationsMap.get(code));
            currencyInfos.add(currencyInfo);
        }
        return currencyInfos;
    }

    private String getUpdateTime(CoinDesk coinDeskVo) {
        Map<String, String> updateTimes = coinDeskVo.getTime();
        OffsetDateTime odt = OffsetDateTime.parse(updateTimes.get("updatedISO"));
        Date updateTime = Date.from(odt.toInstant());
        return dateFormatString.format(updateTime);
    }
}
