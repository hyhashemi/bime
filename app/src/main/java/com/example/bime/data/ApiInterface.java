package com.example.bime.data;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiInterface {

    @Headers("x-api-key: 676bdb1ce2d84276b8874a41f143c739")
    @FormUrlEncoded
    @POST("api/user/login")
    Call<ResponseBody> login(@Field("username") String username, @Field("phone") String password);

}
