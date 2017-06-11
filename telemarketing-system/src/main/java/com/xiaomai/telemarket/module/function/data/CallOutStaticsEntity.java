package com.xiaomai.telemarket.module.function.data;

/**
 * @description 员工外呼统计
 * @author yangdu <youngdu29@gmail.com>
 * @createtime 11/06/2017 9:38 PM
 **/
public class CallOutStaticsEntity {


    /**
     * CustomerName : 吴和志
     * CustomerTel : 18617147625
     * CntCall : 4
     * CntConnect : 0
     * RateConnect : 0
     * CntDuration : 0
     * CntIntent : 4
     * CntAppoint : 0
     */

    private String CustomerName;
    private String CustomerTel;
    private int CntCall;
    private int CntConnect;
    private int RateConnect;
    private int CntDuration;
    private int CntIntent;
    private int CntAppoint;

    public String getCustomerName() {
        return CustomerName;
    }

    public void setCustomerName(String CustomerName) {
        this.CustomerName = CustomerName;
    }

    public String getCustomerTel() {
        return CustomerTel;
    }

    public void setCustomerTel(String CustomerTel) {
        this.CustomerTel = CustomerTel;
    }

    public int getCntCall() {
        return CntCall;
    }

    public void setCntCall(int CntCall) {
        this.CntCall = CntCall;
    }

    public int getCntConnect() {
        return CntConnect;
    }

    public void setCntConnect(int CntConnect) {
        this.CntConnect = CntConnect;
    }

    public int getRateConnect() {
        return RateConnect;
    }

    public void setRateConnect(int RateConnect) {
        this.RateConnect = RateConnect;
    }

    public int getCntDuration() {
        return CntDuration;
    }

    public void setCntDuration(int CntDuration) {
        this.CntDuration = CntDuration;
    }

    public int getCntIntent() {
        return CntIntent;
    }

    public void setCntIntent(int CntIntent) {
        this.CntIntent = CntIntent;
    }

    public int getCntAppoint() {
        return CntAppoint;
    }

    public void setCntAppoint(int CntAppoint) {
        this.CntAppoint = CntAppoint;
    }
}
