package com.xiaomai.telemarket.module.function.data;

/**
 * Created by yangdu on 11/06/2017.
 */

public class StatisticByUserParam {

    /// <summary>
    /// 客户名称
    /// </summary>
    public String Name ;
    /// <summary>
    /// 呼叫日期起
    /// </summary>
    public String FromDate ;
    /// <summary>
    /// 呼叫日期止
    /// </summary>
    public String ToDate ;
    /// <summary>
    /// 外呼数量起
    /// </summary>
    public int FromCall ;
    /// <summary>
    /// 外呼数量止
    /// </summary>
    public int ToCall ;
    /// <summary>
    /// 接通数量起
    /// </summary>
    public int FromConnect ;
    /// <summary>
    /// 接通数量止
    /// </summary>
    public int ToConnect ;
    /// <summary>
    /// 预约上门起
    /// </summary>
    public int FromAppoint ;
    /// <summary>
    /// 预约上门止
    /// </summary>
    public int ToAppoint ;
    /// <summary>
    /// 接通数量起
    /// </summary>
    public int FromDuration ;
    /// <summary>
    /// 接通数量止
    /// </summary>
    public int ToDuration ;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getFromDate() {
        return FromDate;
    }

    public void setFromDate(String fromDate) {
        FromDate = fromDate;
    }

    public String getToDate() {
        return ToDate;
    }

    public void setToDate(String toDate) {
        ToDate = toDate;
    }

    public int getFromCall() {
        return FromCall;
    }

    public void setFromCall(int fromCall) {
        FromCall = fromCall;
    }

    public int getToCall() {
        return ToCall;
    }

    public void setToCall(int toCall) {
        ToCall = toCall;
    }

    public int getFromConnect() {
        return FromConnect;
    }

    public void setFromConnect(int fromConnect) {
        FromConnect = fromConnect;
    }

    public int getToConnect() {
        return ToConnect;
    }

    public void setToConnect(int toConnect) {
        ToConnect = toConnect;
    }

    public int getFromAppoint() {
        return FromAppoint;
    }

    public void setFromAppoint(int fromAppoint) {
        FromAppoint = fromAppoint;
    }

    public int getToAppoint() {
        return ToAppoint;
    }

    public void setToAppoint(int toAppoint) {
        ToAppoint = toAppoint;
    }

    public int getFromDuration() {
        return FromDuration;
    }

    public void setFromDuration(int fromDuration) {
        FromDuration = fromDuration;
    }

    public int getToDuration() {
        return ToDuration;
    }

    public void setToDuration(int toDuration) {
        ToDuration = toDuration;
    }
}
