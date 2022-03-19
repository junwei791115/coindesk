package com.example.coindesk.vo;

import java.util.List;

public class CoinDeskInfo {
    private String updateTime;
    private List<CurrencyInfo> CurrencyInfos;

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public List<CurrencyInfo> getCurrencyInfos() {
        return CurrencyInfos;
    }

    public void setCurrencyInfos(List<CurrencyInfo> currencyInfos) {
        CurrencyInfos = currencyInfos;
    }
}
