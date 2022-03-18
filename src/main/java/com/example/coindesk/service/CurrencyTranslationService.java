package com.example.coindesk.service;

import com.example.coindesk.vo.CurrencyTranslationVo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface CurrencyTranslationService {
    List<CurrencyTranslationVo> getCurrencyTranslationList();

    Optional<CurrencyTranslationVo> getCurrencyTranslationById(long id);

    Optional<CurrencyTranslationVo> updateCurrencyTranslationById(long id, String code, String name);

    boolean isCodeDuplicated(long id, String code);

    CurrencyTranslationVo createCurrencyTranslation(String code, String name);

    void deleteCurrencyTranslationById(long id);
}
