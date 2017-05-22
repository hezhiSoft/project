package com.xiaomai.telemarket.module.cstmr.data;

import java.io.Serializable;

/**
 * author: hezhiWu <hezhi.woo@gmail.com>
 * version: V1.0
 * created at 2017/5/22 9:17
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved.
 */
public class CompanyEntity implements Serializable{

    private String ID;
    private String CustomerID;
    private String CompanyName;
    private String Industry;
    private String RegisterDate;
    private String BusinessScope;
    private int SharesProportion;
    private int AccountWater;
    private int LocationRental;
    private int IsRentTransfer;
    private int AmountDebt;
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

    public String getCompanyName() {
        return CompanyName;
    }

    public void setCompanyName(String CompanyName) {
        this.CompanyName = CompanyName;
    }

    public String getIndustry() {
        return Industry;
    }

    public void setIndustry(String Industry) {
        this.Industry = Industry;
    }

    public String getRegisterDate() {
        return RegisterDate;
    }

    public void setRegisterDate(String RegisterDate) {
        this.RegisterDate = RegisterDate;
    }

    public String getBusinessScope() {
        return BusinessScope;
    }

    public void setBusinessScope(String BusinessScope) {
        this.BusinessScope = BusinessScope;
    }

    public int getSharesProportion() {
        return SharesProportion;
    }

    public void setSharesProportion(int SharesProportion) {
        this.SharesProportion = SharesProportion;
    }

    public int getAccountWater() {
        return AccountWater;
    }

    public void setAccountWater(int AccountWater) {
        this.AccountWater = AccountWater;
    }

    public int getLocationRental() {
        return LocationRental;
    }

    public void setLocationRental(int LocationRental) {
        this.LocationRental = LocationRental;
    }

    public int getIsRentTransfer() {
        return IsRentTransfer;
    }

    public void setIsRentTransfer(int IsRentTransfer) {
        this.IsRentTransfer = IsRentTransfer;
    }

    public int getAmountDebt() {
        return AmountDebt;
    }

    public void setAmountDebt(int AmountDebt) {
        this.AmountDebt = AmountDebt;
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
