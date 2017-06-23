package com.easydear.user.module.message.data;

/**
 * @author hezhiWu
 * @version V1.0
 * @Package com.yunwei.easyDear.function.mainFuncations.messageFunction.data
 * @Description:
 * @date 2017/1/22 17:11
 */

public class MessageDetailEntity {

    private String BusinessNo;
    private String Logo;
    private String BusinessName;
    private String Content;
    private String CreateTime;
    private String UserNo;

    public String getBusinessNo() {
        return BusinessNo;
    }

    public void setBusinessNo(String businessNo) {
        BusinessNo = businessNo;
    }

    public String getLogo() {
        return Logo;
    }

    public void setLogo(String logo) {
        Logo = logo;
    }

    public String getBusinessName() {
        return BusinessName;
    }

    public void setBusinessName(String businessName) {
        BusinessName = businessName;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String createTime) {
        CreateTime = createTime;
    }

    public String getUserNo() {
        return UserNo;
    }

    public void setUserNo(String userNo) {
        UserNo = userNo;
    }
}
