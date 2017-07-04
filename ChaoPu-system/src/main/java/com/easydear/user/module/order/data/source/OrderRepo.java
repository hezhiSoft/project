package com.easydear.user.module.order.data.source;

import com.easydear.user.DataApplication;
import com.easydear.user.api.ChaoPuRetrofitManamer;
import com.easydear.user.api.ResponseEntity;
import com.easydear.user.module.order.data.OrderDetailsEntity;
import com.easydear.user.module.order.data.OrderEntity;
import com.jinggan.library.base.BaseDataSourse;
import com.jinggan.library.net.retrofit.RemetoRepoCallbackV2;
import com.jinggan.library.net.retrofit.RetrofitCallbackV2;

import org.json.JSONObject;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.http.Query;

/**
 * Describe:
 * Author: hezhiWu
 * Date: 2017-07-01
 * Time: 09:29
 * Version:1.0
 */

public class OrderRepo implements BaseDataSourse{

    private Call<ResponseEntity<List<OrderEntity>>> ordersCall;
    private Call<ResponseEntity<OrderDetailsEntity>> orderDetailsCall;

    private static OrderRepo instance;

    public static OrderRepo getInstance(){
        if (instance==null){
            instance=new OrderRepo();
        }

        return instance;
    }

    public void queryOrders(int pageSize, int pageCount, final RemetoRepoCallbackV2<List<OrderEntity>> callback){
        callback.onReqStart();
        ordersCall= ChaoPuRetrofitManamer.getService().queryOrders(DataApplication.getInstance().getUserInfoEntity().getUserNo(),pageSize,pageCount);
        ordersCall.enqueue(new RetrofitCallbackV2<ResponseEntity<List<OrderEntity>>>() {
            @Override
            public void onSuccess(ResponseEntity<List<OrderEntity>> data) {
                if (data.getCode()==200){
                    callback.onSuccess(data.getData());
                }else {
                    callback.onFailure(data.getCode(),data.getMessage());
                }
            }

            @Override
            public void onFailure(int code, String msg) {
                callback.onFailure(code,msg);
            }

            @Override
            public void onFinish() {
                callback.onFinish();
            }
        });
    }


    public void queryOrderDetails(String billNo, final RemetoRepoCallbackV2<OrderDetailsEntity> callback){
        callback.onReqStart();
        orderDetailsCall=ChaoPuRetrofitManamer.getService().queryOrderDetails(billNo);
        orderDetailsCall.enqueue(new RetrofitCallbackV2<ResponseEntity<OrderDetailsEntity>>() {
            @Override
            public void onSuccess(ResponseEntity<OrderDetailsEntity> data) {
                if (data.getCode()==200){
                    callback.onSuccess(data.getData());
                }else {
                    callback.onFailure(data.getCode(),data.getMessage());
                }
            }

            @Override
            public void onFailure(int code, String msg) {
                callback.onFailure(code,msg);
            }

            @Override
            public void onFinish() {
                callback.onFinish();
            }
        });
    }

    public void addArticleGood(int articleId, final RemetoRepoCallbackV2<String> callback){
        Call<ResponseEntity<Object>> call=ChaoPuRetrofitManamer.getAPIService().addArticleGood(articleId);
        call.enqueue(new RetrofitCallbackV2<ResponseEntity<Object>>() {
            @Override
            public void onSuccess(ResponseEntity<Object> data) {
                if (data.getCode()==200){
                    try {
                        JSONObject jsonObject=new JSONObject(data.getData().toString());
                        String ArticleGoodSize=jsonObject.getString("ArticleGoodSize");
                        callback.onSuccess(ArticleGoodSize);
                    }catch (Exception e){
                        e.printStackTrace();
                        callback.onFailure(501,"数据异常!");
                    }
                }else {
                    callback.onFailure(data.getCode(),data.getMessage());
                }
            }

            @Override
            public void onFailure(int code, String msg) {
                callback.onFailure(code,msg);
            }

            @Override
            public void onFinish() {
                callback.onFinish();
            }
        });
    }

    @Override
    public void cancelRequest() {

    }
}
