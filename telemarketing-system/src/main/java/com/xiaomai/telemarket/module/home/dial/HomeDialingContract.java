package com.xiaomai.telemarket.module.home.dial;

import com.xiaomai.telemarket.module.cstmr.data.CusrometListEntity;

/**
 * @description
 * @author yangdu <youngdu29@gmail.com>
 * @createtime 27/05/2017 2:32 PM
 **/
public class HomeDialingContract {

    public interface View{
        void showDialingByGroupStarted();

        void showDialingOutStarted(CusrometListEntity entity);

        void showDialingStopped();

        void showRequestNumberFailed(String msg);

        void showDialingFinished(String msg);

        void showIsDialingGroupUnStopped(boolean isStopped);

    }

    public interface Presenter{
        void startDialingBySingle();

        void startDialingByGroup();

        void stopDialingByGroup();

        void checkIsDialingGroupUnStoppedAndDialingOut();

    }
}
