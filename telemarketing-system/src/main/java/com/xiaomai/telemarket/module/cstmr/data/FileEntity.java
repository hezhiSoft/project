package com.xiaomai.telemarket.module.cstmr.data;

import java.io.Serializable;

/**
 * author: hezhiWu <hezhi.woo@gmail.com>
 * version: V1.0
 * created at 2017/5/22 22:32
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved.
 */
public class FileEntity implements Serializable{

    private String FileID;
    private String BusinessID;
    private int Duration;
    private String FileName;
    private String FileExtension;
    private int FileSize;
    private String FileUrl;
    private String Remark;
    private String CreatedBy;
    private String CreatedDate;
    private int RowIndex;

    public String getFileID() {
        return FileID;
    }

    public void setFileID(String FileID) {
        this.FileID = FileID;
    }

    public String getBusinessID() {
        return BusinessID;
    }

    public void setBusinessID(String BusinessID) {
        this.BusinessID = BusinessID;
    }

    public int getDuration() {
        return Duration;
    }

    public void setDuration(int Duration) {
        this.Duration = Duration;
    }

    public String getFileName() {
        return FileName;
    }

    public void setFileName(String FileName) {
        this.FileName = FileName;
    }

    public String getFileExtension() {
        return FileExtension;
    }

    public void setFileExtension(String FileExtension) {
        this.FileExtension = FileExtension;
    }

    public int getFileSize() {
        return FileSize;
    }

    public void setFileSize(int FileSize) {
        this.FileSize = FileSize;
    }

    public String getFileUrl() {
        return FileUrl;
    }

    public void setFileUrl(String FileUrl) {
        this.FileUrl = FileUrl;
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
