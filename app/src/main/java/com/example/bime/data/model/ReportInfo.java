package com.example.bime.data.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ReportInfo implements Serializable {

    @SerializedName("FirstName")
    String firstName;

    @SerializedName("LastName")
    String lastName;

    @SerializedName("MobileNumber")
    String mobileNumber;

    @SerializedName("Password")
    String password;

    @SerializedName("Address")
    String address;
    @SerializedName("NationalCode")
    String nationalCode;

    @SerializedName("PolicyFullNumber")
    String policyFullNumber;

    @SerializedName("InsurerDescription")
    String insuranceDesc;

    @SerializedName("Latitude")
    double latitude;

    @SerializedName("Longitude")
    double longitude;

    @SerializedName("StateProvinceId")
    int stateProvinceId;

    @SerializedName("UserLoggedIn")
    boolean userLoggedIn;

    @SerializedName("PolicyType")
    int policyType;

    @SerializedName("FilePath")
    String filePath;

    @SerializedName("GeoLocation")
    String geo;

    public ReportInfo(
            String firstName, String lastName, String mobileNumber,
            String password, String address, String nationalCode, String policyFullNumber,
            String insuranceDesc, double latitude, double longitude, int stateProvinceId,
            boolean userLoggedIn, int policyType, String filePath, String geo) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.mobileNumber = mobileNumber;
        this.password = password;
        this.address = address;
        this.nationalCode = nationalCode;
        this.policyFullNumber = policyFullNumber;
        this.insuranceDesc = insuranceDesc;
        this.latitude = latitude;
        this.longitude = longitude;
        this.stateProvinceId = stateProvinceId;
        this.userLoggedIn = userLoggedIn;
        this.policyType = policyType;
        this.filePath = filePath;
        this.geo = geo;
    }

}
