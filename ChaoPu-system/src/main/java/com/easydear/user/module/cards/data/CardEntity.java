package com.easydear.user.module.cards.data;

/**
 * author: hezhiWu <wuhezhi007@gmail.com>
 * version: V1.0
 * created at 2017/6/16$ 下午8:23$
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved.
 */

public class CardEntity {

    private String BusinessNo;
    private String BusinessName;
    private String Logo;
    private String SimplyDescription;
    private String BackgroundColor;
    private String VipLevel;
    private int CardSize;

    public String getBusinessNo() {
        return BusinessNo;
    }

    public void setBusinessNo(String businessNo) {
        BusinessNo = businessNo;
    }

    public String getBusinessName() {
        return BusinessName;
    }

    public void setBusinessName(String businessName) {
        BusinessName = businessName;
    }

    public String getLogo() {
        return Logo;
    }

    public void setLogo(String logo) {
        Logo = logo;
    }

    public String getSimplyDescription() {
        return SimplyDescription;
    }

    public void setSimplyDescription(String simplyDescription) {
        SimplyDescription = simplyDescription;
    }

    public String getBackgroundColor() {
        return BackgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        BackgroundColor = backgroundColor;
    }

    public String getVipLevel() {
        return VipLevel;
    }

    public void setVipLevel(String vipLevel) {
        VipLevel = vipLevel;
    }

    public int getCardSize() {
        return CardSize;
    }

    public void setCardSize(int cardSize) {
        CardSize = cardSize;
    }
}
