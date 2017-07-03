package com.easydear.user.module.business.data;

/**
 * Created by LJH on 2017/7/2.
 */

public class CardItemEntity {

    private String CardNo;
    private String CardName;
    private String IsHaveCard;
    private String CardPrice;
    private String Difference;

    public String getCardNo() {
        return CardNo;
    }

    public void setCardNo(String cardNo) {
        CardNo = cardNo;
    }

    public String getCardName() {
        return CardName;
    }

    public void setCardName(String cardName) {
        CardName = cardName;
    }

    public String getIsHaveCard() {
        return IsHaveCard;
    }

    public void setIsHaveCard(String isHaveCard) {
        IsHaveCard = isHaveCard;
    }

    public String getCardPrice() {
        return CardPrice;
    }

    public void setCardPrice(String cardPrice) {
        CardPrice = cardPrice;
    }

    public String getDifference() {
        return Difference;
    }

    public void setDifference(String difference) {
        Difference = difference;
    }
}
