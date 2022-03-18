package com.example.coindesk.service;

import com.example.coindesk.entity.CurrencyTranslationEntity;
import com.example.coindesk.respository.CurrencyTranslationRepository;
import com.example.coindesk.vo.CurrencyTranslationVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CurrencyTranslationServiceImpl implements CurrencyTranslationService {
    @Autowired
    private CurrencyTranslationRepository currencyTranslationRepository;

    @Override
    public List<CurrencyTranslationVo> getCurrencyTranslationList() {
        List<CurrencyTranslationEntity> currencyTranslations = this.currencyTranslationRepository.findAll();
        return currencyTranslations.stream().map(this::getCurrencyTranslationVo).collect(Collectors.toList());
    }

    private CurrencyTranslationVo getCurrencyTranslationVo(CurrencyTranslationEntity currencyTranslationEntity) {
        CurrencyTranslationVo currencyTranslationVo = new CurrencyTranslationVo();
        currencyTranslationVo.setId(String.valueOf(currencyTranslationEntity.getId()));
        currencyTranslationVo.setCode(currencyTranslationEntity.getCode());
        currencyTranslationVo.setName(currencyTranslationEntity.getName());
        return currencyTranslationVo;
    }

    @Override
    public Optional<CurrencyTranslationVo> getCurrencyTranslationById(long id) {
        Optional<CurrencyTranslationEntity> currencyTranslationEntityOptional = this.currencyTranslationRepository.findById(id);
        if (currencyTranslationEntityOptional.isEmpty()) {
            return Optional.empty();
        }
        CurrencyTranslationEntity currencyTranslationEntity = currencyTranslationEntityOptional.get();

        return Optional.of(this.getCurrencyTranslationVo(currencyTranslationEntity));
    }

    @Override
    public Optional<CurrencyTranslationVo> updateCurrencyTranslationById(long id, String code, String name) {
        Optional<CurrencyTranslationEntity> currencyTranslationEntityOptional = this.currencyTranslationRepository.findById(id);
        if (currencyTranslationEntityOptional.isEmpty()) {
            return Optional.empty();
        }

        CurrencyTranslationEntity currencyTranslationEntity = currencyTranslationEntityOptional.get();

        if (StringUtils.isNotEmpty(code)) {
            currencyTranslationEntity.setCode(code);
        }
        if (StringUtils.isNotEmpty(name)) {
            currencyTranslationEntity.setName(name);
        }

        currencyTranslationEntity.setUpdatedTime(new Date());
        this.currencyTranslationRepository.save(currencyTranslationEntity);

        return Optional.of(this.getCurrencyTranslationVo(currencyTranslationEntity));
    }

    @Override
    public boolean isCodeDuplicated(long id, String code) {
        Optional<CurrencyTranslationEntity> currencyTranslationEntityOptional = this.currencyTranslationRepository.findByCode(code);
        if (currencyTranslationEntityOptional.isEmpty()) {
            return true;
        }

        CurrencyTranslationEntity currencyTranslationEntity = currencyTranslationEntityOptional.get();
        if(StringUtils.equals(String.valueOf(currencyTranslationEntity.getId()), String.valueOf(id))){
            return false;
        }

        return true;
    }

    @Override
    public CurrencyTranslationVo createCurrencyTranslation(String code, String name) {
        CurrencyTranslationEntity currencyTranslationEntity = new CurrencyTranslationEntity();
        currencyTranslationEntity.setCode(code);
        currencyTranslationEntity.setName(name);
        currencyTranslationEntity.setUpdatedTime(new Date());
        currencyTranslationEntity.setCreatedTime(new Date());
        currencyTranslationEntity.setCreatedBy("system");
        currencyTranslationEntity.setUpdatedBy("system");

        this.currencyTranslationRepository.save(currencyTranslationEntity);

        return this.getCurrencyTranslationVo(currencyTranslationEntity);
    }

    @Override
    public void deleteCurrencyTranslationById(long id) {

    }
}
