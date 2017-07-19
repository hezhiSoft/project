package com.easydear.user.module.cards.data;

import java.io.Serializable;

/**
 * Describe:
 * Date: 2017-07-16
 * Time: 09:58
 * Version:1.0
 */

public class InterestDetailEntity implements Serializable {

    private String Status;
    private String CardNo;
    private String CardName;
    private String BusinessNo;
    private String BusinessName;
    private String BusinessTelephone;
    private String CardStartTime;
    private String CardEndTime;
    private String IsHaveCard;
    private String CardOldPrice;
    private String CardTransactionNo;
    private String CardPrice;
    private String CardImg;
    private String Logo;
    private String Remind;
    private String Difference;
    private String Remark;
    private String Rule;
    private String Content;
    private String CardGetSize;

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

    public String getBusinessNo() {
        return BusinessNo;
    }

    public void setBusinessNo(String businessNo) {
        BusinessNo = businessNo;
    }

    public String getBusinessTelephone() {
        return BusinessTelephone;
    }

    public void setBusinessTelephone(String businessTelephone) {
        BusinessTelephone = businessTelephone;
    }

    public String getCardStartTime() {
        return CardStartTime;
    }

    public void setCardStartTime(String cardStartTime) {
        CardStartTime = cardStartTime;
    }

    public String getIsHaveCard() {
        return IsHaveCard;
    }

    public void setIsHaveCard(String isHaveCard) {
        IsHaveCard = isHaveCard;
    }

    public String getCardImg() {
        return CardImg;
    }

    public void setCardImg(String cardImg) {
        CardImg = cardImg;
    }

    public String getRemind() {
        return Remind;
    }

    public void setRemind(String remind) {
        Remind = remind;
    }

    public String getDifference() {
        return Difference;
    }

    public void setDifference(String difference) {
        Difference = difference;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }

    public String getRule() {
        return Rule;
    }

    public void setRule(String rule) {
        Rule = rule;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public String getCardGetSize() {
        return CardGetSize;
    }

    public void setCardGetSize(String cardGetSize) {
        CardGetSize = cardGetSize;
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
