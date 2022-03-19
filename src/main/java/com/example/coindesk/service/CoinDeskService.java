package com.example.coindesk.service;

import com.example.coindesk.vo.CoinDesk;
import com.example.coindesk.vo.CoinDeskInfo;

import java.util.Optional;

public interface CoinDeskService {
    Optional<CoinDesk> getCoinDesk();

    Optional<CoinDeskInfo> getCoinDeskInformation();
}
