package com.example.bime.data.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class InsuranceInfo implements Serializable {

    @SerializedName("name")
    private String name;


    @SerializedName("familyname")
    private String familyName;

    @SerializedName("id")
    private String id;

}
