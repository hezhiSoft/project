package com.xiaomai.telemarket.module.home.setting.data;

/**
 * @description 用户状态实体
 * @author yangdu <youngdu29@gmail.com>
 * @createtime 20/05/2017 5:06 PM
 **/
public class UserStateEntity {

    private String Name;

    private String Code;

    private boolean isSelect;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }
}
