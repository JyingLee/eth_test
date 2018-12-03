package com.jying.eth_test.Net;

import com.jying.eth_test.Bean.LotteryBean;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    String baseUrl = "http://134.175.44.227:30109/eth/";

    @GET("save")
    Observable<ResponseBody> savePrize(@Query("key") String key, @Query("count") int count, @Query("amount") String amocunt, @Query("prizes") String prize);

    @GET("query")
    Observable<List<LotteryBean>> getPrize(@Query("key") String key);
}
