package com.xiaomai.telemarket.module.cstmr.data;

import java.io.Serializable;

/**
 * 房产实体
 * author: hezhiWu <hezhi.woo@gmail.com>
 * version: V1.0
 * created at 2017/5/22 8:57
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved.
 */
public class PropertyEntity implements Serializable{
    private String ID;
    private String CustomerID;
    private int AreaM;
    private int LandUse;
    private String CompletionDate;
    private String Area;
    private String VillageName;
    private String DetailedAddress;
    private int PropertyRightsYear;
    private String MortgageBank;
    private int RegistrationPrice;
    private int IsMortgage;
    private int MonthlyPaymentLoan;
    private int MortgageTimeLimit;
    private int RemainingMortgage;
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

    public int getAreaM() {
        return AreaM;
    }

    public void setAreaM(int AreaM) {
        this.AreaM = AreaM;
    }

    public int getLandUse() {
        return LandUse;
    }

    public void setLandUse(int LandUse) {
        this.LandUse = LandUse;
    }

    public String getCompletionDate() {
        return CompletionDate;
    }

    public void setCompletionDate(String CompletionDate) {
        this.CompletionDate = CompletionDate;
    }

    public String getArea() {
        return Area;
    }

    public void setArea(String Area) {
        this.Area = Area;
    }

    public String getVillageName() {
        return VillageName;
    }

    public void setVillageName(String VillageName) {
        this.VillageName = VillageName;
    }

    public String getDetailedAddress() {
        return DetailedAddress;
    }

    public void setDetailedAddress(String DetailedAddress) {
        this.DetailedAddress = DetailedAddress;
    }

    public int getPropertyRightsYear() {
        return PropertyRightsYear;
    }

    public void setPropertyRightsYear(int PropertyRightsYear) {
        this.PropertyRightsYear = PropertyRightsYear;
    }

    public String getMortgageBank() {
        return MortgageBank;
    }

    public void setMortgageBank(String MortgageBank) {
        this.MortgageBank = MortgageBank;
    }

    public int getRegistrationPrice() {
        return RegistrationPrice;
    }

    public void setRegistrationPrice(int RegistrationPrice) {
        this.RegistrationPrice = RegistrationPrice;
    }

    public int getIsMortgage() {
        return IsMortgage;
    }

    public void setIsMortgage(int IsMortgage) {
        this.IsMortgage = IsMortgage;
    }

    public int getMonthlyPaymentLoan() {
        return MonthlyPaymentLoan;
    }

    public void setMonthlyPaymentLoan(int MonthlyPaymentLoan) {
        this.MonthlyPaymentLoan = MonthlyPaymentLoan;
    }

    public int getMortgageTimeLimit() {
        return MortgageTimeLimit;
    }

    public void setMortgageTimeLimit(int MortgageTimeLimit) {
        this.MortgageTimeLimit = MortgageTimeLimit;
    }

    public int getRemainingMortgage() {
        return RemainingMortgage;
    }

    public void setRemainingMortgage(int RemainingMortgage) {
        this.RemainingMortgage = RemainingMortgage;
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
