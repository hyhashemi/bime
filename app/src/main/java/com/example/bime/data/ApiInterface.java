package com.example.bime.data;

import com.example.bime.data.model.ReportInfo;

import org.json.JSONObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiInterface {

    @FormUrlEncoded
    @POST("api/User/Login")
    Call<ResponseBody> login(@Field("Username") String username, @Field("Password") String password);

    @GET("api/Inquery")
    Call<ResponseBody> getInfo(@Query("uniqueIdentifier") String uniqueId);

    @POST("api/Damage/Report")
    Call<ResponseBody> report(@Body ReportInfo reportInfo);

    @POST("api/Damage/Tracking")
    Call<ResponseBody> track(@Query("referenceCode") String trackId, @Query("nationalCode") String nationalCode);

    @POST("api/Country/StateProvinces")
    Call<ResponseBody> getStates();

}
