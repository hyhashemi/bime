package com.example.bime.data.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class InsuranceInfo implements Serializable {

    @SerializedName("InsuredFullName")
    private String name;

    @SerializedName("InsuredNationalCode")
    private String nationalCode;

    @SerializedName("InsurerCompany")
    private String insurerCompany;

    @SerializedName("PolicyUniqueNumber")
    private String policyUniqueNumber;

    @SerializedName("PolicyNumber")
    private String policyNumer;

    @SerializedName("InsuranceField")
    private String insuranceField;

    @SerializedName("InsuranceRegistrationDate")
    private String insuranceRegistrationDta;

    @SerializedName("InsuranceStartDate")
    private String insuranceStartDate;

    @SerializedName("InsuranceEndDate")
    private String insuranceEndDate;

    @SerializedName("Fund")
    private String fund;

    public String getName() {
        return name;
    }

    public String getNationalCode() {
        return nationalCode;
    }

    public String getInsuranceField() {
        return insuranceField;
    }

    public String getPolicyNumer() {
        return policyNumer;
    }

    public String getInsurerCompany() {
        return insurerCompany;
    }

    public String getPolicyUniqueNumber() {
        return policyUniqueNumber;
    }
}
