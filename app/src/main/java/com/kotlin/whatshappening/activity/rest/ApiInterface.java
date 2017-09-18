package com.kotlin.whatshappening.activity.rest;

import com.google.gson.JsonElement;
import com.kotlin.whatshappening.activity.model.News;
import com.squareup.okhttp.ResponseBody;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * Created by gaurav on 11/9/17.
 */

public interface ApiInterface {
    @GET("articles")
    Call<JsonElement>getNews(@Query("source") String source,@Query("sortBy") String sortBy,@Query("apikey") String apiKey);

    @GET("sources")
    Call<JsonElement>getSources(@Query("category") String category, @Query("language") String language, @Query("country") String country);

    // option 2: using a dynamic URL
@Streaming
    @GET
    Call<ResponseBody> downloadFileWithDynamicUrlSync(@Url String fileUrl);
}
