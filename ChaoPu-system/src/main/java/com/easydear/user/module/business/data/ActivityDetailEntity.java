package com.easydear.user.module.business.data;

/**
 * Created by LJH on 2017/8/2.
 */

public class ActivityDetailEntity {

    private int Id;
    private String ActivityName;
    private String Starttime;
    private String Endtime;
    private String Remark;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getActivityName() {
        return ActivityName;
    }

    public void setActivityName(String activityName) {
        ActivityName = activityName;
    }

    public String getStarttime() {
        return Starttime;
    }

    public void setStarttime(String starttime) {
        Starttime = starttime;
    }

    public String getEndtime() {
        return Endtime;
    }

    public void setEndtime(String endtime) {
        Endtime = endtime;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }
}
