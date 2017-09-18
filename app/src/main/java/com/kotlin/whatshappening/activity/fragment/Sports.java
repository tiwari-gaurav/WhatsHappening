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
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import com.kotlin.whatshappening.R;
import com.kotlin.whatshappening.activity.adapter.NewsAdapter;
import com.kotlin.whatshappening.activity.helper.DividerItemDecoration;
import com.kotlin.whatshappening.activity.model.News;
import com.kotlin.whatshappening.activity.model.NewsResponse;
import com.kotlin.whatshappening.activity.rest.ApiClient;
import com.kotlin.whatshappening.activity.rest.ApiInterface;
import com.kotlin.whatshappening.activity.utils.ParamConstants;

import java.lang.reflect.Type;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by gaurav on 13/9/17.
 */

public class Sports extends Fragment {

    private static final String TAG = Sports.class.getSimpleName();

    private List<NewsResponse> newsResponse;

    private List<News> news;
    private RecyclerView recyclerView;

    public Sports(){

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.popular_fragment,container,false);


        if (ParamConstants.API_KEY.isEmpty()) {
            Toast.makeText(getContext(), "Please obtain your API KEY first from themoviedb.org", Toast.LENGTH_LONG).show();

        }

        recyclerView = (RecyclerView)rootView.findViewById(R.id.popular);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        fetchTrendingNews(recyclerView);

        return rootView;

    }

    private void fetchTrendingNews(final RecyclerView recyclerView) {

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<JsonElement> call = apiService.getNews("bbc-sport", "top", ParamConstants.API_KEY);
        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                Log.d("resonse", response.toString());
                JsonElement object = response.body();
                Type listType = new TypeToken<List<News>>() {
                }.getType();
                news = new Gson().fromJson(object.getAsJsonObject().getAsJsonArray("articles"), listType);
                recyclerView.setAdapter(new NewsAdapter(news, R.layout.news_list_item, getContext()));
                recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL_LIST));
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                Log.e(TAG, t.toString());
            }
        });
    }
}
