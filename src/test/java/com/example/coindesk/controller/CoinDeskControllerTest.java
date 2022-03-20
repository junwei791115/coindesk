package com.example.coindesk.controller;

import com.example.coindesk.service.CoinDeskService;
import com.example.coindesk.vo.CoinDesk;
import com.example.coindesk.vo.CoinDeskInfo;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CoinDeskControllerTest {

    @Mock
    private CoinDeskService coinDeskService;

    @InjectMocks
    private CoinDeskController coinDeskController;

    @Test
    void getCoinDesk_success() {
        CoinDesk coinDesk = new CoinDesk();

        when(this.coinDeskService.getCoinDesk()).thenReturn(Optional.of(coinDesk));

        ResponseEntity<? extends Object> result = this.coinDeskController.getCoinDesk();

        Assert.assertEquals(result.getStatusCode(), HttpStatus.OK);
    }

    @Test
    void getCoinDesk_not_found() {
        CoinDesk coinDesk = new CoinDesk();

        when(this.coinDeskService.getCoinDesk()).thenReturn(Optional.empty());

        ResponseEntity<? extends Object> result = this.coinDeskController.getCoinDesk();

        Assert.assertEquals(result.getStatusCode(), HttpStatus.NOT_FOUND);
    }

    @Test
    void getCoinDeskInformation_success() {
        CoinDeskInfo coinDeskInfo = new CoinDeskInfo();

        when(this.coinDeskService.getCoinDeskInformation()).thenReturn(Optional.of(coinDeskInfo));

        ResponseEntity<? extends Object> result = this.coinDeskController.getCoinDeskInformation();

        Assert.assertEquals(result.getStatusCode(), HttpStatus.OK);
    }

    @Test
    void getCoinDeskInformation_not_found() {
        CoinDeskInfo coinDeskInfo = new CoinDeskInfo();

        when(this.coinDeskService.getCoinDeskInformation()).thenReturn(Optional.empty());

        ResponseEntity<? extends Object> result = this.coinDeskController.getCoinDeskInformation();

        Assert.assertEquals(result.getStatusCode(), HttpStatus.NOT_FOUND);
    }
}