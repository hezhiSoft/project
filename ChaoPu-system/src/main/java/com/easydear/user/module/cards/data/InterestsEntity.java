package com.easydear.user.module.cards.data;

import java.io.Serializable;

/**
 * Describe:
 * Author: hezhiWu
 * Date: 2017-07-01
 * Time: 09:58
 * Version:1.0
 */

public class InterestsEntity implements Serializable {

    private String Status;
    private String CardNo;
    private String CardEndTime;
    private String CardName;
    private String BusinessName;
    private String CardOldPrice;
    private String CardTransactionNo;
    private String CardPrice;
    private String Logo;

    public String getStatus() {
        return Status;
    }

    public void setStatus(String Status) {
        this.Status = Status;
    }

    public String getCardNo() {
        return CardNo;
    }

    public void setCardNo(String CardNo) {
        this.CardNo = CardNo;
    }

    public String getCardEndTime() {
        return CardEndTime;
    }

    public void setCardEndTime(String CardEndTime) {
        this.CardEndTime = CardEndTime;
    }

    public String getCardName() {
        return CardName;
    }

    public void setCardName(String CardName) {
        this.CardName = CardName;
    }

    public String getBusinessName() {
        return BusinessName;
    }

    public void setBusinessName(String BusinessName) {
        this.BusinessName = BusinessName;
    }

    public String getCardOldPrice() {
        return CardOldPrice;
    }

    public void setCardOldPrice(String CardOldPrice) {
        this.CardOldPrice = CardOldPrice;
    }

    public String getCardTransactionNo() {
        return CardTransactionNo;
    }

    public void setCardTransactionNo(String CardTransactionNo) {
        this.CardTransactionNo = CardTransactionNo;
    }

    public String getCardPrice() {
        return CardPrice;
    }

    public void setCardPrice(String CardPrice) {
        this.CardPrice = CardPrice;
    }

    public String getLogo() {
        return Logo;
    }

    public void setLogo(String Logo) {
        this.Logo = Logo;
    }
}
