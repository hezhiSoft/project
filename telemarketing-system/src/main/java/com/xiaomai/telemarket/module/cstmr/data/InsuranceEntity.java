package com.xiaomai.telemarket.module.cstmr.data;

import java.io.Serializable;

/**
 * author: hezhiWu <hezhi.woo@gmail.com>
 * version: V1.0
 * created at 2017/5/22 9:03
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved.
 */
public class InsuranceEntity implements Serializable{
    private String ID;
    private String CustomerID;
    private String InsuranceCompany;
    private String BuyDate;
    private int PaymentMethods;
    private int InsuredAmount;
    private String DelayDate;
    private int DelayNum;
    private int DelayDays;
    private String FuXiaoDate;
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

    public String getCustomerID() {
        return CustomerID;
    }

    public void setCustomerID(String CustomerID) {
        this.CustomerID = CustomerID;
    }

    public String getInsuranceCompany() {
        return InsuranceCompany;
    }

    public void setInsuranceCompany(String InsuranceCompany) {
        this.InsuranceCompany = InsuranceCompany;
    }

    public String getBuyDate() {
        return BuyDate;
    }

    public void setBuyDate(String BuyDate) {
        this.BuyDate = BuyDate;
    }

    public int getPaymentMethods() {
        return PaymentMethods;
    }

    public void setPaymentMethods(int PaymentMethods) {
        this.PaymentMethods = PaymentMethods;
    }

    public int getInsuredAmount() {
        return InsuredAmount;
    }

    public void setInsuredAmount(int InsuredAmount) {
        this.InsuredAmount = InsuredAmount;
    }

    public String getDelayDate() {
        return DelayDate;
    }

    public void setDelayDate(String DelayDate) {
        this.DelayDate = DelayDate;
    }

    public int getDelayNum() {
        return DelayNum;
    }

    public void setDelayNum(int DelayNum) {
        this.DelayNum = DelayNum;
    }

    public int getDelayDays() {
        return DelayDays;
    }

    public void setDelayDays(int DelayDays) {
        this.DelayDays = DelayDays;
    }

    public String getFuXiaoDate() {
        return FuXiaoDate;
    }

    public void setFuXiaoDate(String FuXiaoDate) {
        this.FuXiaoDate = FuXiaoDate;
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
