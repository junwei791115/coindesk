package com.example.coindesk.respository;

import com.example.coindesk.entity.CurrencyTranslationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CurrencyTranslationRepository extends JpaRepository<CurrencyTranslationEntity, String> {
    Optional<CurrencyTranslationEntity> findByCodeIgnoreCase(String code);
}