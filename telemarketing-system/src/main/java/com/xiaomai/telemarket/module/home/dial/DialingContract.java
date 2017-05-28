package com.xiaomai.telemarket.module.home.dial;

import com.xiaomai.telemarket.module.cstmr.data.CusrometListEntity;

/**
 * @description
 * @author yangdu <youngdu29@gmail.com>
 * @createtime 27/05/2017 2:32 PM
 **/
public class DialingContract {

    public interface View{
        void showRequestNumberStar();
        void showRequestNumberSuccess(CusrometListEntity entity);
        void showRequestNumberFailed(String msg);
        void showRequestNumberStoped();
    }

    public interface Presenter{
        void requestNumberFromPublic();
        void requestNumberFromPrivate(String preid);
        void stopRequest();
    }
}
