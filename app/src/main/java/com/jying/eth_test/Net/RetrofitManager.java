package com.jying.eth_test.Net;

import com.jying.eth_test.Bean.LotteryBean;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitManager {

    public static final String TAG = "===debug===";
    private static RetrofitManager retrofitManager;
    private ApiService apiService;

    public static RetrofitManager getInstance() {
        if (retrofitManager == null) {
            synchronized (RetrofitManager.class) {
                if (retrofitManager == null) {
                    retrofitManager = new RetrofitManager();
                }
            }
        }
        return retrofitManager;
    }

    public RetrofitManager getApiService() {
        if (apiService == null) {
            OkHttpClient.Builder builder = new OkHttpClient.Builder()
                    .connectTimeout(20, TimeUnit.SECONDS)
                    .connectionPool(new ConnectionPool(50, 20, TimeUnit.SECONDS));
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(ApiService.baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(builder.build())
                    .build();
            apiService = retrofit.create(ApiService.class);
        }
        return retrofitManager;
    }

    public Observable<ResponseBody> savePrizes(String key, int count, String amount, String prize) {

        return apiService.savePrize(key, count, amount, prize)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(io.reactivex.schedulers.Schedulers.io())
                .unsubscribeOn(io.reactivex.schedulers.Schedulers.io());
    }

    public Observable<List<LotteryBean>> getPrizes(String key) {
        return apiService.getPrize(key)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(io.reactivex.schedulers.Schedulers.io())
                .unsubscribeOn(io.reactivex.schedulers.Schedulers.io());
    }
}
