package com.xiaomai.telemarket.module.function.data;

/**
 * @description 部门外呼统计
 * @author yangdu <youngdu29@gmail.com>
 * @createtime 11/06/2017 9:44 PM
 **/
public class CallOutDepStaticsEntity {


    /**
     * DeptId : 252d62ce-63d4-4e9b-8328-a722011ca3f7
     * Cnt : 0
     * DateMonth : 1
     */

    private String DeptId;
    private int Cnt;
    private int DateMonth;

    public String getDeptId() {
        return DeptId;
    }

    public void setDeptId(String DeptId) {
        this.DeptId = DeptId;
    }

    public int getCnt() {
        return Cnt;
    }

    public void setCnt(int Cnt) {
        this.Cnt = Cnt;
    }

    public int getDateMonth() {
        return DateMonth;
    }

    public void setDateMonth(int DateMonth) {
        this.DateMonth = DateMonth;
    }
}
