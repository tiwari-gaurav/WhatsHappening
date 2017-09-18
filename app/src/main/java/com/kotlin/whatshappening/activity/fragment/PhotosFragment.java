package com.kotlin.whatshappening.activity.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import com.kotlin.whatshappening.R;
import com.kotlin.whatshappening.activity.adapter.GalleryAdapter;
import com.kotlin.whatshappening.activity.model.News;
import com.kotlin.whatshappening.activity.rest.ApiClient;
import com.kotlin.whatshappening.activity.rest.ApiInterface;
import com.kotlin.whatshappening.activity.utils.ParamConstants;

import java.lang.reflect.Type;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by gaurav on 18/9/17.
 */

public class PhotosFragment extends Fragment {
    private static final String TAG = PhotosFragment.class.getSimpleName();
    private List<News> news;
    private RecyclerView recyclerViewEntertainment, recyclerViewSports, recyclerViewTechnology;
    LinearLayoutManager mLayoutManagerEntertainment,mLayoutManagerSports,mLayoutManagerTechnology;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.gallery,container,false);

        intializeViews(rootView);
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        fetchEntertainment(recyclerViewEntertainment,apiService);
        fetchSports(recyclerViewSports,apiService);
        fetchTechnology(recyclerViewSports,apiService);


        return rootView;
    }

    private void fetchSports(final RecyclerView recyclerViewSports, ApiInterface apiService) {

        Call<JsonElement> sports = apiService.getNews("espn","top",ParamConstants.API_KEY);
        sports.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                Log.d("resonse", response.toString());
                JsonElement object = response.body();
                Type listType = new TypeToken<List<News>>() {
                }.getType();
                news = new Gson().fromJson(object.getAsJsonObject().getAsJsonArray("articles"), listType);
                recyclerViewSports.setAdapter(new GalleryAdapter(news, R.layout.gallery_items, getContext()));
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                Log.e(TAG, t.toString());
            }
        });
    }


    private void fetchTechnology(RecyclerView recyclerViewSports, ApiInterface apiService) {

        Call<JsonElement> technology = apiService.getNews("engadget","top",ParamConstants.API_KEY);
        technology.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                Log.d("resonse", response.toString());
                JsonElement object = response.body();
                Type listType = new TypeToken<List<News>>() {
                }.getType();
                news = new Gson().fromJson(object.getAsJsonObject().getAsJsonArray("articles"), listType);
                recyclerViewTechnology.setAdapter(new GalleryAdapter(news, R.layout.gallery_items, getContext()));
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                Log.e(TAG, t.toString());
            }
        });
    }

    private void fetchEntertainment(final RecyclerView recyclerViewEntertainment, ApiInterface apiService) {

        Call<JsonElement> entertainment = apiService.getNews("entertainment-weekly","top", ParamConstants.API_KEY);
        entertainment.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                Log.d("resonse", response.toString());
                JsonElement object = response.body();
                Type listType = new TypeToken<List<News>>() {
                }.getType();
                news = new Gson().fromJson(object.getAsJsonObject().getAsJsonArray("articles"), listType);
                recyclerViewEntertainment.setAdapter(new GalleryAdapter(news, R.layout.gallery_items, getContext()));

            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                Log.e(TAG, t.toString());
            }
        });
    }

    private void intializeViews(View rootView) {
        recyclerViewEntertainment = (RecyclerView)rootView.findViewById(R.id.entertainment_gallery);
        recyclerViewSports = (RecyclerView)rootView.findViewById(R.id.sports_gallery);
        recyclerViewTechnology = (RecyclerView)rootView.findViewById(R.id.technology_gallery);
        mLayoutManagerEntertainment = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        mLayoutManagerSports = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        mLayoutManagerTechnology = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        recyclerViewEntertainment.setLayoutManager(mLayoutManagerEntertainment);
        recyclerViewSports.setLayoutManager(mLayoutManagerSports);
        recyclerViewTechnology.setLayoutManager(mLayoutManagerTechnology);

    }
}
