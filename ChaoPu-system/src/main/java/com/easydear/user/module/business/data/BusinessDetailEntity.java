package com.easydear.user.module.business.data;

import java.util.ArrayList;

/**
 * Created by LJH on 2017/2/11.
 */

public class BusinessDetailEntity {
    private String BrandName;
    private String BusinessName;
    private String OpenTime;
    private String BusinessNo;
    private String Logo;
    private String ProvinceAdd;
    private String CityAdd;
    private String AreaAdd;
    private String StreetAdd;
    private String Address;
    private String Telephone;
    private String BusinessTime;
    private String BusinessImagesNext;
    private String BusinessImagesLast;
    private String Latitude;
    private String Longitude;
    private ArrayList<ActivityItemEntity> ActivityList;
    private ArrayList<CardItemEntity> CardList;
    private ArrayList<String> UserCardList;
    private String MerchantServices;
    private String IsVip;
    private ArrayList<String> VipList;
    private String BusinessImages;
    private String BusinessDescription;

    public String getBrandName() {
        return BrandName;
    }

    public void setBrandName(String brandName) {
        BrandName = brandName;
    }

    public String getBusinessName() {
        return BusinessName;
    }

    public void setBusinessName(String businessName) {
        BusinessName = businessName;
    }

    public String getOpenTime() {
        return OpenTime;
    }

    public void setOpenTime(String openTime) {
        OpenTime = openTime;
    }

    public String getBusinessNo() {
        return BusinessNo;
    }

    public void setBusinessNo(String businessNo) {
        BusinessNo = businessNo;
    }

    public String getLogo() {
        return Logo;
    }

    public void setLogo(String logo) {
        Logo = logo;
    }

    public String getProvinceAdd() {
        return ProvinceAdd;
    }

    public void setProvinceAdd(String provinceAdd) {
        ProvinceAdd = provinceAdd;
    }

    public String getCityAdd() {
        return CityAdd;
    }

    public void setCityAdd(String cityAdd) {
        CityAdd = cityAdd;
    }

    public String getAreaAdd() {
        return AreaAdd;
    }

    public void setAreaAdd(String areaAdd) {
        AreaAdd = areaAdd;
    }

    public String getStreetAdd() {
        return StreetAdd;
    }

    public void setStreetAdd(String streetAdd) {
        StreetAdd = streetAdd;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getTelephone() {
        return Telephone;
    }

    public void setTelephone(String telephone) {
        Telephone = telephone;
    }

    public String getBusinessTime() {
        return BusinessTime;
    }

    public void setBusinessTime(String businessTime) {
        BusinessTime = businessTime;
    }

    public String getBusinessImagesNext() {
        return BusinessImagesNext;
    }

    public void setBusinessImagesNext(String businessImagesNext) {
        BusinessImagesNext = businessImagesNext;
    }

    public String getBusinessImagesLast() {
        return BusinessImagesLast;
    }

    public void setBusinessImagesLast(String businessImagesLast) {
        BusinessImagesLast = businessImagesLast;
    }

    public String getLatitude() {
        return Latitude;
    }

    public void setLatitude(String latitude) {
        Latitude = latitude;
    }

    public String getLongitude() {
        return Longitude;
    }

    public void setLongitude(String longitude) {
        Longitude = longitude;
    }

    public ArrayList<ActivityItemEntity> getActivityList() {
        return ActivityList;
    }

    public void setActivityList(ArrayList<ActivityItemEntity> activityList) {
        ActivityList = activityList;
    }

    public ArrayList<CardItemEntity> getCardList() {
        return CardList;
    }

    public void setCardList(ArrayList<CardItemEntity> cardList) {
        CardList = cardList;
    }

    public ArrayList<String> getUserCardList() {
        return UserCardList;
    }

    public void setUserCardList(ArrayList<String> userCardList) {
        UserCardList = userCardList;
    }

    public String getMerchantServices() {
        return MerchantServices;
    }

    public void setMerchantServices(String merchantServices) {
        MerchantServices = merchantServices;
    }

    public String getIsVip() {
        return IsVip;
    }

    public void setIsVip(String isVip) {
        IsVip = isVip;
    }

    public ArrayList<String> getVipList() {
        return VipList;
    }

    public void setVipList(ArrayList<String> vipList) {
        VipList = vipList;
    }

    public String getBusinessImages() {
        return BusinessImages;
    }

    public void setBusinessImages(String businessImages) {
        BusinessImages = businessImages;
    }

    public String getBusinessDescription() {
        return BusinessDescription;
    }

    public void setBusinessDescription(String businessDescription) {
        BusinessDescription = businessDescription;
    }

    /**
     * Created by LJH on 2017/7/2.
     */
    public static class CardItemEntity {

        private String CardNo;
        private String CardEndTime;
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

        public String getCardEndTime() {
            return CardEndTime;
        }

        public void setCardEndTime(String cardEndTime) {
            CardEndTime = cardEndTime;
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

    /**
     * Created by LJH on 2017/7/5.
     */
    public static class ActivityItemEntity {

        private String ActivityName;
        private String Title;
        private int Id;

        public String getActivityName() {
            return ActivityName;
        }

        public void setActivityName(String activityName) {
            ActivityName = activityName;
        }

        public String getTitle() {
            return Title;
        }

        public void setTitle(String title) {
            Title = title;
        }

        public int getId() {
            return Id;
        }

        public void setId(int id) {
            Id = id;
        }
    }
}
