package com.example.coindesk.service;

import com.example.coindesk.entity.CurrencyTranslationEntity;
import com.example.coindesk.respository.CurrencyTranslationRepository;
import com.example.coindesk.vo.CurrencyTranslation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CurrencyTranslationServiceImpl implements CurrencyTranslationService {

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    @Autowired
    private CurrencyTranslationRepository currencyTranslationRepository;

    @Override
    public List<CurrencyTranslation> getCurrencyTranslationList() {
        List<CurrencyTranslationEntity> currencyTranslations = this.currencyTranslationRepository.findAll();
        return currencyTranslations.stream().map(this::getCurrencyTranslationVo).collect(Collectors.toList());
    }

    private CurrencyTranslation getCurrencyTranslationVo(CurrencyTranslationEntity currencyTranslationEntity) {
        CurrencyTranslation currencyTranslationVo = new CurrencyTranslation();
        currencyTranslationVo.setId(String.valueOf(currencyTranslationEntity.getId()));
        currencyTranslationVo.setCode(currencyTranslationEntity.getCode());
        currencyTranslationVo.setName(currencyTranslationEntity.getName());
        currencyTranslationVo.setUpdateTime(dateFormat.format(currencyTranslationEntity.getUpdatedTime()));
        return currencyTranslationVo;
    }

    @Override
    public Optional<CurrencyTranslation> getCurrencyTranslationById(String id) {
        Optional<CurrencyTranslationEntity> currencyTranslationEntityOptional = this.currencyTranslationRepository.findById(id);
        if (currencyTranslationEntityOptional.isEmpty()) {
            return Optional.empty();
        }
        CurrencyTranslationEntity currencyTranslationEntity = currencyTranslationEntityOptional.get();

        return Optional.of(this.getCurrencyTranslationVo(currencyTranslationEntity));
    }

    @Override
    public Optional<CurrencyTranslation> updateCurrencyTranslationById(String id, String name) {

        Optional<CurrencyTranslationEntity> currencyTranslationEntityOptional = this.currencyTranslationRepository.findById(id);
        if (currencyTranslationEntityOptional.isEmpty()) {
            return Optional.empty();
        }

        CurrencyTranslationEntity currencyTranslationEntity = currencyTranslationEntityOptional.get();

        if (StringUtils.isNotEmpty(name)) {
            currencyTranslationEntity.setName(name);
        }

        currencyTranslationEntity.setUpdatedTime(new Date());
        this.currencyTranslationRepository.save(currencyTranslationEntity);

        return Optional.of(this.getCurrencyTranslationVo(currencyTranslationEntity));
    }

    @Override
    public boolean isCodeDuplicated(String code) {
        Optional<CurrencyTranslationEntity> currencyTranslationEntityOptional = this.currencyTranslationRepository.findByCodeIgnoreCase(code);
        if (currencyTranslationEntityOptional.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public CurrencyTranslation createCurrencyTranslation(String code, String name) {
        String uuid = UUID.randomUUID().toString();
        CurrencyTranslationEntity currencyTranslationEntity = new CurrencyTranslationEntity();
        currencyTranslationEntity.setId(uuid);
        currencyTranslationEntity.setCode(StringUtils.upperCase(code));
        currencyTranslationEntity.setName(name);
        currencyTranslationEntity.setUpdatedTime(new Date());
        currencyTranslationEntity.setCreatedTime(new Date());
        currencyTranslationEntity.setCreatedBy("system");
        currencyTranslationEntity.setUpdatedBy("system");

        this.currencyTranslationRepository.save(currencyTranslationEntity);

        return this.getCurrencyTranslationVo(currencyTranslationEntity);
    }

    @Override
    public Optional<CurrencyTranslation> deleteCurrencyTranslationById(String id) {
        Optional<CurrencyTranslationEntity> currencyTranslationEntityOptional = this.currencyTranslationRepository.findById(id);
        if (currencyTranslationEntityOptional.isEmpty()) {
            return Optional.empty();
        }
        CurrencyTranslationEntity currencyTranslationEntity = currencyTranslationEntityOptional.get();
        this.currencyTranslationRepository.delete(currencyTranslationEntity);

        return Optional.of(this.getCurrencyTranslationVo(currencyTranslationEntity));
    }

    @Override
    public boolean isCurrencyTranslationExisted(String id) {
        return this.currencyTranslationRepository.existsById(id);
    }
}
