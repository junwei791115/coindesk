package com.example.coindesk.vo;

public class CurrencyInfo {
    private String currency;
    private String currencyName;
    private String rate;
    private String rate_float;

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getRate_float() {
        return rate_float;
    }

    public void setRate_float(String rate_float) {
        this.rate_float = rate_float;
    }
}
