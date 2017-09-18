package com.kotlin.whatshappening.activity.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by gaurav on 11/9/17.
 */

public class NewsResponse {
    @SerializedName("name")
    private String name;

    @SerializedName("language")
    private String language;

    @SerializedName("country")
    private String country;

    @SerializedName("category")
    private String category;

    public NewsResponse(String name, String language, String country, String category){
        this.language = language;
        this.name = name;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
