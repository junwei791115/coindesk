package com.example.coindesk.service;

import com.example.coindesk.vo.CurrencyTranslation;

import java.util.List;
import java.util.Optional;

public interface CurrencyTranslationService {
    List<CurrencyTranslation> getCurrencyTranslationList();

    Optional<CurrencyTranslation> getCurrencyTranslationById(String id);

    Optional<CurrencyTranslation> updateCurrencyTranslationById(String id, String name);

    boolean isCodeDuplicated(String code);

    CurrencyTranslation createCurrencyTranslation(String code, String name);

    Optional<CurrencyTranslation> deleteCurrencyTranslationById(String id);

    boolean isCurrencyTranslationExisted(String id);
}
