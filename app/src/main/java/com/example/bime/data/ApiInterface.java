package com.example.bime.data;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiInterface {

    @Headers("x-api-key: 676bdb1ce2d84276b8874a41f143c739")
    @FormUrlEncoded
    @POST("api/User/Login")
    Call<ResponseBody> login(@Field("Username") String username, @Field("Password") String password);

    @Headers("x-api-key: 676bdb1ce2d84276b8874a41f143c739")
    @GET("api/Inquery")
    Call<ResponseBody> getInfo(@Query("uniqueIdentifier") String uniqueId);

    @Headers("x-api-key: 676bdb1ce2d84276b8874a41f143c739")
    @FormUrlEncoded
    @POST("api/Damage/Report")
    Call<ResponseBody> report(@Field("FirstName") String firstName, @Field("LastName") String lastName, @Field("MobileNumber") String mobileNumber,
                              @Field("Password") String password, @Field("Address") String address, @Field("NationalCode") String nationalCode,
                              @Field("PolicyFullNumber") String policyFullNumber, @Field("InsurerDescription") String insuranceDesc, @Field("Latitude") double latitude,
                              @Field("Longitude") double longitude, @Field("StateProvinceId") int stateProvinceId,
                              @Field("UserLoggedIn") boolean userLoggedIn, @Field("PolicyType") String policyType, @Field("FilePath") String filePath);

    @Headers("x-api-key: 676bdb1ce2d84276b8874a41f143c739")
    @FormUrlEncoded
    @POST("api/Damage/Tracking")
    Call<ResponseBody> track(@Field("referenceCode") String referenceCode, @Field("nationalCode") String nationalCode);


    @Headers("x-api-key: 676bdb1ce2d84276b8874a41f143c739")
    @FormUrlEncoded
    @POST("api/Damage/Tracking")
    Call<ResponseBody> track(@Field("referenceCode") String trackId, @Field("nationalCode") String nationalCode);

}
