package com.example.bime.data;

import com.example.bime.data.model.InsuranceInfo;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiInterface {

    @Headers("x-api-key: 676bdb1ce2d84276b8874a41f143c739")
    @FormUrlEncoded
    @POST("api/User/Login")
    Call<ResponseBody> login(@Field("Username") String username, @Field("Password") String password);

    @Headers("x-api-key: 676bdb1ce2d84276b8874a41f143c739")
    @FormUrlEncoded
    @POST
    Call<InsuranceInfo> getInfo(@Field("uniqueid") String uniqueId);

}
