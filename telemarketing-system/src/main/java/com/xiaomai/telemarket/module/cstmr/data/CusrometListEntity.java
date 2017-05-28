package com.xiaomai.telemarket.module.cstmr.data;

import java.io.Serializable;

/**
 * author: hezhiWu <hezhi.woo@gmail.com>
 * version: V1.0
 * created at 2017/5/15 22:05
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved.
 */
public class CusrometListEntity implements Serializable{

    private String ID;
    private String TenantID;
    private String BatchNo;
    private String UserID;
    private String CustomerName;
    private String CustomerTel;
    private int Sex;
    private int InterestedStatus;
    private int MaritalStatus;
    private int IsSZHukou;
    private int Wage;
    private int NowWorkMonths;
    private int AccumulationFundAccount;
    private int SocialSecurityAccount;
    private int AccountWater;
    private String FollowDate;
    private String Remark;
    private String CreatedBy;
    private String CreatedDate;
    private String ModifiedBy;
    private String ModifiedDate;
    private int DeleteFlag;
    private String RowVersion;
    private int RowIndex;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getTenantID() {
        return TenantID;
    }

    public void setTenantID(String TenantID) {
        this.TenantID = TenantID;
    }

    public String getBatchNo() {
        return BatchNo;
    }

    public void setBatchNo(String BatchNo) {
        this.BatchNo = BatchNo;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String UserID) {
        this.UserID = UserID;
    }

    public String getCustomerName() {
        return CustomerName;
    }

    public void setCustomerName(String CustomerName) {
        this.CustomerName = CustomerName;
    }

    public String getCustomerTel() {
        return CustomerTel;
    }

    public void setCustomerTel(String CustomerTel) {
        this.CustomerTel = CustomerTel;
    }

    public int getSex() {
        return Sex;
    }

    public void setSex(int Sex) {
        this.Sex = Sex;
    }

    public int getInterestedStatus() {
        return InterestedStatus;
    }

    public void setInterestedStatus(int InterestedStatus) {
        this.InterestedStatus = InterestedStatus;
    }

    public int getMaritalStatus() {
        return MaritalStatus;
    }

    public void setMaritalStatus(int MaritalStatus) {
        this.MaritalStatus = MaritalStatus;
    }

    public int getIsSZHukou() {
        return IsSZHukou;
    }

    public void setIsSZHukou(int IsSZHukou) {
        this.IsSZHukou = IsSZHukou;
    }

    public int getWage() {
        return Wage;
    }

    public void setWage(int Wage) {
        this.Wage = Wage;
    }

    public int getNowWorkMonths() {
        return NowWorkMonths;
    }

    public void setNowWorkMonths(int NowWorkMonths) {
        this.NowWorkMonths = NowWorkMonths;
    }

    public int getAccumulationFundAccount() {
        return AccumulationFundAccount;
    }

    public void setAccumulationFundAccount(int AccumulationFundAccount) {
        this.AccumulationFundAccount = AccumulationFundAccount;
    }

    public int getSocialSecurityAccount() {
        return SocialSecurityAccount;
    }

    public void setSocialSecurityAccount(int SocialSecurityAccount) {
        this.SocialSecurityAccount = SocialSecurityAccount;
    }

    public int getAccountWater() {
        return AccountWater;
    }

    public void setAccountWater(int AccountWater) {
        this.AccountWater = AccountWater;
    }

    public String getFollowDate() {
        return FollowDate;
    }

    public void setFollowDate(String FollowDate) {
        this.FollowDate = FollowDate;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String Remark) {
        this.Remark = Remark;
    }

    public String getCreatedBy() {
        return CreatedBy;
    }

    public void setCreatedBy(String CreatedBy) {
        this.CreatedBy = CreatedBy;
    }

    public String getCreatedDate() {
        return CreatedDate;
    }

    public void setCreatedDate(String CreatedDate) {
        this.CreatedDate = CreatedDate;
    }

    public String getModifiedBy() {
        return ModifiedBy;
    }

    public void setModifiedBy(String ModifiedBy) {
        this.ModifiedBy = ModifiedBy;
    }

    public String getModifiedDate() {
        return ModifiedDate;
    }

    public void setModifiedDate(String ModifiedDate) {
        this.ModifiedDate = ModifiedDate;
    }

    public int getDeleteFlag() {
        return DeleteFlag;
    }

    public void setDeleteFlag(int DeleteFlag) {
        this.DeleteFlag = DeleteFlag;
    }

    public String getRowVersion() {
        return RowVersion;
    }

    public void setRowVersion(String RowVersion) {
        this.RowVersion = RowVersion;
    }

    public int getRowIndex() {
        return RowIndex;
    }

    public void setRowIndex(int RowIndex) {
        this.RowIndex = RowIndex;
    }

}
