package com.example.coindesk.service;

import com.example.coindesk.entity.CurrencyTranslationEntity;
import com.example.coindesk.respository.CurrencyTranslationRepository;
import com.example.coindesk.vo.CurrencyTranslation;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CurrencyTranslationServiceTest {

    @Mock
    private CurrencyTranslationRepository currencyTranslationRepository;

    @InjectMocks
    private CurrencyTranslationService CurrencyTranslationService = new CurrencyTranslationServiceImpl();

    @Test
    void getCurrencyTranslationList_success() {
        List<CurrencyTranslationEntity> currencyTranslations = this.createCurrencyTranslationEntities();

        when(this.currencyTranslationRepository.findAll()).thenReturn(currencyTranslations);

        List<CurrencyTranslation> result = this.CurrencyTranslationService.getCurrencyTranslationList();

        Assert.assertEquals(result.size(), 1);
    }

    private List<CurrencyTranslationEntity> createCurrencyTranslationEntities() {
        List<CurrencyTranslationEntity> currencyTranslations = new ArrayList<>();
        CurrencyTranslationEntity currencyTranslationEntity = this.createCurrencyTranslationEntity();
        currencyTranslations.add(currencyTranslationEntity);
        return currencyTranslations;
    }

    @Test
    void getCurrencyTranslationById_sucess() {
        String id = "1";
        CurrencyTranslationEntity currencyTranslationEntity = this.createCurrencyTranslationEntity();

        when(this.currencyTranslationRepository.findById(id)).thenReturn(Optional.of(currencyTranslationEntity));

        Optional<CurrencyTranslation> result = this.CurrencyTranslationService.getCurrencyTranslationById(id);

        Assert.assertTrue(result.isPresent());
    }

    @Test
    void getCurrencyTranslationById_not_found() {
        String id = "1";

        when(this.currencyTranslationRepository.findById(id)).thenReturn(Optional.empty());

        Optional<CurrencyTranslation> result = this.CurrencyTranslationService.getCurrencyTranslationById(id);

        Assert.assertTrue(result.isEmpty());
    }

    private CurrencyTranslationEntity createCurrencyTranslationEntity() {
        CurrencyTranslationEntity currencyTranslationEntity = new CurrencyTranslationEntity();
        currencyTranslationEntity.setId("1");
        currencyTranslationEntity.setName("美元");
        currencyTranslationEntity.setCode("USD");
        currencyTranslationEntity.setCreatedTime(new Date());
        currencyTranslationEntity.setUpdatedTime(new Date());
        return currencyTranslationEntity;
    }

    @Test
    void updateCurrencyTranslationById_success() {
        String id = "1";
        String name = "美元";
        CurrencyTranslationEntity currencyTranslationEntity = this.createCurrencyTranslationEntity();

        when(this.currencyTranslationRepository.findById(id)).thenReturn(Optional.of(currencyTranslationEntity));

        Optional<CurrencyTranslation> result = this.CurrencyTranslationService.updateCurrencyTranslationById(id, name);
        Assert.assertTrue(result.isPresent());
    }

    @Test
    void updateCurrencyTranslationById_not_found() {
        String id = "1";
        String name = "美元";

        when(this.currencyTranslationRepository.findById(id)).thenReturn(Optional.empty());

        Optional<CurrencyTranslation> result = this.CurrencyTranslationService.updateCurrencyTranslationById(id, name);
        Assert.assertTrue(result.isEmpty());
    }

    @Test
    void isCodeDuplicated_is_existed() {
        String code = "USD";
        CurrencyTranslationEntity currencyTranslationEntity = this.createCurrencyTranslationEntity();

        when(this.currencyTranslationRepository.findByCodeIgnoreCase(code)).thenReturn(Optional.of(currencyTranslationEntity));

        boolean result = this.CurrencyTranslationService.isCodeDuplicated(code);

        Assert.assertTrue(result);
    }

    @Test
    void isCodeDuplicated_is_not_existed() {
        String code = "USD";
        when(this.currencyTranslationRepository.findByCodeIgnoreCase(code)).thenReturn(Optional.empty());

        boolean result = this.CurrencyTranslationService.isCodeDuplicated(code);

        Assert.assertFalse(result);
    }

    @Test
    void createCurrencyTranslation_success() {
        String code = "USD";
        String name = "美元";

        CurrencyTranslation result = this.CurrencyTranslationService.createCurrencyTranslation(code, name);

        Assert.assertNotNull(result);
    }

    @Test
    void deleteCurrencyTranslationById_success() {
        String id = "1";
        CurrencyTranslationEntity currencyTranslationEntity = this.createCurrencyTranslationEntity();

        when(this.currencyTranslationRepository.findById(id)).thenReturn(Optional.of(currencyTranslationEntity));

        Optional<CurrencyTranslation> result = this.CurrencyTranslationService.deleteCurrencyTranslationById(id);

        Assert.assertTrue(result.isPresent());
    }

    @Test
    void deleteCurrencyTranslationById_not_found() {
        String id = "1";

        when(this.currencyTranslationRepository.findById(id)).thenReturn(Optional.empty());

        Optional<CurrencyTranslation> result = this.CurrencyTranslationService.deleteCurrencyTranslationById(id);

        Assert.assertFalse(result.isPresent());
    }

    @Test
    void isCurrencyTranslationExisted() {
        String id = "1";

        when(this.currencyTranslationRepository.existsById(id)).thenReturn(true);

        boolean result = this.CurrencyTranslationService.isCurrencyTranslationExisted(id);

        Assert.assertTrue(result);
    }
}