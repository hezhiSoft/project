package com.xiaomai.telemarket.module.function.data;

import java.io.Serializable;

/**
 * Created by yangdu on 11/06/2017.
 * 部门按月统计
 */

public class StatisticsByMonthParam implements Serializable{

    /**
     * 部门ID
     */
    private String DeptId;
    /**
     * 统计年份
     */
    private String Year;

    /**
     * 统计类型：call 外呼数量、connect 接通数量、intent 意向用户、appoint 预约上门
     */
    private String Type;

    public String getDeptId() {
        return DeptId;
    }

    public void setDeptId(String deptId) {
        DeptId = deptId;
    }

    public String getYear() {
        return Year;
    }

    public void setYear(String year) {
        Year = year;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }
}
