package com.easydear.user.module.business.data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by LJH on 2017/2/11.
 */

public class BusinessDetailEntity implements Serializable{

    private String StreetAdd;
    private String BusinessNo;
    private String BusinessName;
    private String Address;
    private String Telephone;
    private String BusinessImagesLast;
    private String IsVip;
    private String Latitude;
    private String BusinessImagesNext;
    private String AreaAdd;
    private String Longitude;
    private String Logo;
    private String BusinessDescription;
    private String BrandName;
    private String OpenTime;
    private String BusinessImages;
    private String MerchantServices;
    private String ProvinceAdd;
    private String BusinessTime;
    private String CityAdd;
    private List<?> VipList;
    private List<ActivityListBean> ActivityList;
    private List<CardListBean> CardList;
    private List<?> UserCardList;

    public String getStreetAdd() {
        return StreetAdd;
    }

    public void setStreetAdd(String StreetAdd) {
        this.StreetAdd = StreetAdd;
    }

    public String getBusinessNo() {
        return BusinessNo;
    }

    public void setBusinessNo(String BusinessNo) {
        this.BusinessNo = BusinessNo;
    }

    public String getBusinessName() {
        return BusinessName;
    }

    public void setBusinessName(String BusinessName) {
        this.BusinessName = BusinessName;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String Address) {
        this.Address = Address;
    }

    public String getTelephone() {
        return Telephone;
    }

    public void setTelephone(String Telephone) {
        this.Telephone = Telephone;
    }

    public String getBusinessImagesLast() {
        return BusinessImagesLast;
    }

    public void setBusinessImagesLast(String BusinessImagesLast) {
        this.BusinessImagesLast = BusinessImagesLast;
    }

    public String getIsVip() {
        return IsVip;
    }

    public void setIsVip(String IsVip) {
        this.IsVip = IsVip;
    }

    public String getLatitude() {
        return Latitude;
    }

    public void setLatitude(String Latitude) {
        this.Latitude = Latitude;
    }

    public String getBusinessImagesNext() {
        return BusinessImagesNext;
    }

    public void setBusinessImagesNext(String BusinessImagesNext) {
        this.BusinessImagesNext = BusinessImagesNext;
    }

    public String getAreaAdd() {
        return AreaAdd;
    }

    public void setAreaAdd(String AreaAdd) {
        this.AreaAdd = AreaAdd;
    }

    public String getLongitude() {
        return Longitude;
    }

    public void setLongitude(String Longitude) {
        this.Longitude = Longitude;
    }

    public String getLogo() {
        return Logo;
    }

    public void setLogo(String Logo) {
        this.Logo = Logo;
    }

    public String getBusinessDescription() {
        return BusinessDescription;
    }

    public void setBusinessDescription(String BusinessDescription) {
        this.BusinessDescription = BusinessDescription;
    }

    public String getBrandName() {
        return BrandName;
    }

    public void setBrandName(String BrandName) {
        this.BrandName = BrandName;
    }

    public String getOpenTime() {
        return OpenTime;
    }

    public void setOpenTime(String OpenTime) {
        this.OpenTime = OpenTime;
    }

    public String getBusinessImages() {
        return BusinessImages;
    }

    public void setBusinessImages(String BusinessImages) {
        this.BusinessImages = BusinessImages;
    }

    public String getMerchantServices() {
        return MerchantServices;
    }

    public void setMerchantServices(String MerchantServices) {
        this.MerchantServices = MerchantServices;
    }

    public String getProvinceAdd() {
        return ProvinceAdd;
    }

    public void setProvinceAdd(String ProvinceAdd) {
        this.ProvinceAdd = ProvinceAdd;
    }

    public String getBusinessTime() {
        return BusinessTime;
    }

    public void setBusinessTime(String BusinessTime) {
        this.BusinessTime = BusinessTime;
    }

    public String getCityAdd() {
        return CityAdd;
    }

    public void setCityAdd(String CityAdd) {
        this.CityAdd = CityAdd;
    }

    public List<?> getVipList() {
        return VipList;
    }

    public void setVipList(List<?> VipList) {
        this.VipList = VipList;
    }

    public List<ActivityListBean> getActivityList() {
        return ActivityList;
    }

    public void setActivityList(List<ActivityListBean> ActivityList) {
        this.ActivityList = ActivityList;
    }

    public List<CardListBean> getCardList() {
        return CardList;
    }

    public void setCardList(List<CardListBean> CardList) {
        this.CardList = CardList;
    }

    public List<?> getUserCardList() {
        return UserCardList;
    }

    public void setUserCardList(List<?> UserCardList) {
        this.UserCardList = UserCardList;
    }

    public static class ActivityListBean implements Serializable{

        private String ActivityName;
        private String Title;
        private int Id;

        public String getActivityName() {
            return ActivityName;
        }

        public void setActivityName(String ActivityName) {
            this.ActivityName = ActivityName;
        }

        public String getTitle() {
            return Title;
        }

        public void setTitle(String Title) {
            this.Title = Title;
        }

        public int getId() {
            return Id;
        }

        public void setId(int Id) {
            this.Id = Id;
        }
    }

    public static class CardListBean implements Serializable{

        private String CardNo;
        private String CardEndTime;
        private String CardName;
        private String IsHaveCard;
        private String CardPrice;
        private String Difference;

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

        public String getIsHaveCard() {
            return IsHaveCard;
        }

        public void setIsHaveCard(String IsHaveCard) {
            this.IsHaveCard = IsHaveCard;
        }

        public String getCardPrice() {
            return CardPrice;
        }

        public void setCardPrice(String CardPrice) {
            this.CardPrice = CardPrice;
        }

        public String getDifference() {
            return Difference;
        }

        public void setDifference(String Difference) {
            this.Difference = Difference;
        }
    }
}
