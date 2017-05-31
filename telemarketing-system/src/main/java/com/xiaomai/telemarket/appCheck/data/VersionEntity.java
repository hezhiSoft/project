package com.xiaomai.telemarket.appCheck.data;

/**
 * author: hezhiWu <hezhi.woo@gmail.com>
 * version: V1.0
 * created at 2017/5/31 18:01
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved.
 */
public class VersionEntity {

    private int RelaeseId;
    private String Name;
    private String Title;
    private String Summary;
    private String VersionNo;
    private int VersionCode;
    private int FileSize;
    private String FilePath;
    private int RequiredUpdate;
    private int TotalDownloads;
    private int RowIndex;

    public int getRelaeseId() {
        return RelaeseId;
    }

    public void setRelaeseId(int RelaeseId) {
        this.RelaeseId = RelaeseId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String Title) {
        this.Title = Title;
    }

    public String getSummary() {
        return Summary;
    }

    public void setSummary(String Summary) {
        this.Summary = Summary;
    }

    public String getVersionNo() {
        return VersionNo;
    }

    public void setVersionNo(String VersionNo) {
        this.VersionNo = VersionNo;
    }

    public int getVersionCode() {
        return VersionCode;
    }

    public void setVersionCode(int VersionCode) {
        this.VersionCode = VersionCode;
    }

    public int getFileSize() {
        return FileSize;
    }

    public void setFileSize(int FileSize) {
        this.FileSize = FileSize;
    }

    public String getFilePath() {
        return FilePath;
    }

    public void setFilePath(String FilePath) {
        this.FilePath = FilePath;
    }

    public int getRequiredUpdate() {
        return RequiredUpdate;
    }

    public void setRequiredUpdate(int RequiredUpdate) {
        this.RequiredUpdate = RequiredUpdate;
    }

    public int getTotalDownloads() {
        return TotalDownloads;
    }

    public void setTotalDownloads(int TotalDownloads) {
        this.TotalDownloads = TotalDownloads;
    }

    public int getRowIndex() {
        return RowIndex;
    }

    public void setRowIndex(int RowIndex) {
        this.RowIndex = RowIndex;
    }
}
