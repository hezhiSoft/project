package com.easydear.user.module.dynamic.data;

/**
 * author: hezhiWu <wuhezhi007@gmail.com>
 * version: V1.0
 * created at 2017/6/29 20:51
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved.
 */
public class DynamicDetailsEntity {

    private String Type;

    private int ArticleForward;

    private String BusinessNO;

    private String BusinessName;

    private String ArticleImage;

    private String Content;

    private String Title;

    private int ArticleId;

    private String IsArticleGood;

    private int ArticleGood;

    private String Logo;

    public void setType(String Type){
        this.Type = Type;
    }
    public String getType(){
        return this.Type;
    }
    public void setArticleForward(int ArticleForward){
        this.ArticleForward = ArticleForward;
    }
    public int getArticleForward(){
        return this.ArticleForward;
    }
    public void setBusinessNO(String BusinessNO){
        this.BusinessNO = BusinessNO;
    }
    public String getBusinessNO(){
        return this.BusinessNO;
    }
    public void setBusinessName(String BusinessName){
        this.BusinessName = BusinessName;
    }
    public String getBusinessName(){
        return this.BusinessName;
    }
    public void setArticleImage(String ArticleImage){
        this.ArticleImage = ArticleImage;
    }
    public String getArticleImage(){
        return this.ArticleImage;
    }
    public void setContent(String Content){
        this.Content = Content;
    }
    public String getContent(){
        return this.Content;
    }
    public void setTitle(String Title){
        this.Title = Title;
    }
    public String getTitle(){
        return this.Title;
    }
    public void setArticleId(int ArticleId){
        this.ArticleId = ArticleId;
    }
    public int getArticleId(){
        return this.ArticleId;
    }
    public void setIsArticleGood(String IsArticleGood){
        this.IsArticleGood = IsArticleGood;
    }
    public String getIsArticleGood(){
        return this.IsArticleGood;
    }
    public void setArticleGood(int ArticleGood){
        this.ArticleGood = ArticleGood;
    }
    public int getArticleGood(){
        return this.ArticleGood;
    }
    public void setLogo(String Logo){
        this.Logo = Logo;
    }
    public String getLogo(){
        return this.Logo;
    }
}
