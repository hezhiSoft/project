package com.xiaomai.telemarket.module.cstmr.data;

import java.io.Serializable;

/**
 * author: hezhiWu <hezhi.woo@gmail.com>
 * version: V1.0
 * created at 2017/5/22 22:32
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved.
 */
public class FollowEntity implements Serializable{

    private String ID;
    private String CustomerID;
    private int FollowType;
    private String FollowDate;
    private int LoanType;
    private int Amount;
    private int InterestedStatus;
    private String NextFollowDate;
    private int NextFollowTime;
    private int NextFollowType;
    private int WhetherProcessing;
    private String Remark;
    private String CreatedBy;
    private String CreatedDate;
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

    public int getFollowType() {
        return FollowType;
    }

    public void setFollowType(int FollowType) {
        this.FollowType = FollowType;
    }

    public String getFollowDate() {
        return FollowDate;
    }

    public void setFollowDate(String FollowDate) {
        this.FollowDate = FollowDate;
    }

    public int getLoanType() {
        return LoanType;
    }

    public void setLoanType(int LoanType) {
        this.LoanType = LoanType;
    }

    public int getAmount() {
        return Amount;
    }

    public void setAmount(int Amount) {
        this.Amount = Amount;
    }

    public int getInterestedStatus() {
        return InterestedStatus;
    }

    public void setInterestedStatus(int InterestedStatus) {
        this.InterestedStatus = InterestedStatus;
    }

    public String getNextFollowDate() {
        return NextFollowDate;
    }

    public void setNextFollowDate(String NextFollowDate) {
        this.NextFollowDate = NextFollowDate;
    }

    public int getNextFollowTime() {
        return NextFollowTime;
    }

    public void setNextFollowTime(int NextFollowTime) {
        this.NextFollowTime = NextFollowTime;
    }

    public int getNextFollowType() {
        return NextFollowType;
    }

    public void setNextFollowType(int NextFollowType) {
        this.NextFollowType = NextFollowType;
    }

    public int getWhetherProcessing() {
        return WhetherProcessing;
    }

    public void setWhetherProcessing(int WhetherProcessing) {
        this.WhetherProcessing = WhetherProcessing;
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

    public int getRowIndex() {
        return RowIndex;
    }

    public void setRowIndex(int RowIndex) {
        this.RowIndex = RowIndex;
    }
}
