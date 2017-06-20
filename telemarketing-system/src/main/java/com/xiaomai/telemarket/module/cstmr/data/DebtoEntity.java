package com.xiaomai.telemarket.module.cstmr.data;

import java.io.Serializable;

/**
 * author: hezhiWu <hezhi.woo@gmail.com>
 * version: V1.0
 * created at 2017/5/20 13:21
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved.
 */
public class DebtoEntity implements Serializable{
    private String ID;
    private String CustomerID;
    private String TypeDept;
    private double LoanAmount;
    private int RemainingLoanAmount;
    private int MonthlyPayments;
    private String LoanDate;
    private int LoanMonth;
    private String LoanBank;
    private String RepaymentMode;
    private int DelayDays;
    private int DelayAccount;
    private int DelayNum;
    private String ProductID;
    private int IsClear;
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

    public String getTypeDept() {
        return TypeDept;
    }

    public void setTypeDept(String TypeDept) {
        this.TypeDept = TypeDept;
    }

    public double getLoanAmount() {
        return LoanAmount;
    }

    public void setLoanAmount(double LoanAmount) {
        this.LoanAmount = LoanAmount;
    }

    public int getRemainingLoanAmount() {
        return RemainingLoanAmount;
    }

    public void setRemainingLoanAmount(int RemainingLoanAmount) {
        this.RemainingLoanAmount = RemainingLoanAmount;
    }

    public int getMonthlyPayments() {
        return MonthlyPayments;
    }

    public void setMonthlyPayments(int MonthlyPayments) {
        this.MonthlyPayments = MonthlyPayments;
    }

    public String getLoanDate() {
        return LoanDate;
    }

    public void setLoanDate(String LoanDate) {
        this.LoanDate = LoanDate;
    }

    public int getLoanMonth() {
        return LoanMonth;
    }

    public void setLoanMonth(int LoanMonth) {
        this.LoanMonth = LoanMonth;
    }

    public String getLoanBank() {
        return LoanBank;
    }

    public void setLoanBank(String LoanBank) {
        this.LoanBank = LoanBank;
    }

    public String getRepaymentMode() {
        return RepaymentMode;
    }

    public void setRepaymentMode(String RepaymentMode) {
        this.RepaymentMode = RepaymentMode;
    }

    public int getDelayDays() {
        return DelayDays;
    }

    public void setDelayDays(int DelayDays) {
        this.DelayDays = DelayDays;
    }

    public int getDelayAccount() {
        return DelayAccount;
    }

    public void setDelayAccount(int DelayAccount) {
        this.DelayAccount = DelayAccount;
    }

    public int getDelayNum() {
        return DelayNum;
    }

    public void setDelayNum(int DelayNum) {
        this.DelayNum = DelayNum;
    }

    public String getProductID() {
        return ProductID;
    }

    public void setProductID(String ProductID) {
        this.ProductID = ProductID;
    }

    public int getIsClear() {
        return IsClear;
    }

    public void setIsClear(int IsClear) {
        this.IsClear = IsClear;
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
