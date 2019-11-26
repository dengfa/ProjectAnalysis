package com.vincent.interview.android.retrofit;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IpService{
    @GET("getIpInfo.php")
    Call<IpModel> getIp(@Query("ip")String ip);
}