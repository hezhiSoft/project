package com.xiaomai.telemarket.module.home.dial;

import com.jinggan.library.net.retrofit.RemetoRepoCallback;
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
        void showRequestFinished(String mag);
        void showRequestNumberStoped();
    }

    public interface Presenter{
        void requestNumberFromPublic();
        void requestNumberFromPrivate(String preid);
        void deleteTempInfo(String userid,final RemetoRepoCallback<Void> callback);
        void stopRequest();
    }
}
