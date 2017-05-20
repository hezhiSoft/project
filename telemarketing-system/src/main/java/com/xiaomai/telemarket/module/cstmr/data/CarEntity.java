package com.xiaomai.telemarket.module.cstmr.data;

import java.io.Serializable;

/**
 * author: hezhiWu <hezhi.woo@gmail.com>
 * version: V1.0
 * created at 2017/5/20 19:27
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved.
 */
public class CarEntity implements Serializable{
    private String ID;
    private String CustomerID;
    private int NakedCarPrice;
    private int IsMortgage;
    private String BuyDate;
    private int Mileage;
    private int IsRegistrationCertificate;
    private String Brand;
    private String CarModel;
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

    public int getNakedCarPrice() {
        return NakedCarPrice;
    }

    public void setNakedCarPrice(int NakedCarPrice) {
        this.NakedCarPrice = NakedCarPrice;
    }

    public int getIsMortgage() {
        return IsMortgage;
    }

    public void setIsMortgage(int IsMortgage) {
        this.IsMortgage = IsMortgage;
    }

    public String getBuyDate() {
        return BuyDate;
    }

    public void setBuyDate(String BuyDate) {
        this.BuyDate = BuyDate;
    }

    public int getMileage() {
        return Mileage;
    }

    public void setMileage(int Mileage) {
        this.Mileage = Mileage;
    }

    public int getIsRegistrationCertificate() {
        return IsRegistrationCertificate;
    }

    public void setIsRegistrationCertificate(int IsRegistrationCertificate) {
        this.IsRegistrationCertificate = IsRegistrationCertificate;
    }

    public String getBrand() {
        return Brand;
    }

    public void setBrand(String Brand) {
        this.Brand = Brand;
    }

    public String getCarModel() {
        return CarModel;
    }

    public void setCarModel(String CarModel) {
        this.CarModel = CarModel;
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
