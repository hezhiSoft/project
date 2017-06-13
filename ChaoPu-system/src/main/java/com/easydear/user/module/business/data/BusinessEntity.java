package com.easydear.user.module.business.data;

import java.util.List;

/**
 * Describe:
 * Author: hezhiWu
 * Date: 2017-06-11
 * Time: 01:45
 * Version:1.0
 */

public class BusinessEntity {
    private String BusinessNo;
    private String BusinessName;
    private String SloganImages;
    private String Slogan;
    private String Logo;
    private List<ActivityQueryListBean> ActivityQueryList;

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

    public String getSloganImages() {
        return SloganImages;
    }

    public void setSloganImages(String SloganImages) {
        this.SloganImages = SloganImages;
    }

    public String getSlogan() {
        return Slogan;
    }

    public void setSlogan(String Slogan) {
        this.Slogan = Slogan;
    }

    public String getLogo() {
        return Logo;
    }

    public void setLogo(String Logo) {
        this.Logo = Logo;
    }

    public List<ActivityQueryListBean> getActivityQueryList() {
        return ActivityQueryList;
    }

    public void setActivityQueryList(List<ActivityQueryListBean> ActivityQueryList) {
        this.ActivityQueryList = ActivityQueryList;
    }

    public static class ActivityQueryListBean {

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
}
