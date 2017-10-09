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
import android.widget.LinearLayout;
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

public class TopStories extends Fragment {

    private static final String TAG = TopStories.class.getSimpleName();

    private List<NewsResponse> newsResponse;

   private List<News> news;
    private RecyclerView mNewsRecyclerView, mMarketRecyclerView, mlifestyleRecyclerView;
    private LinearLayout mTitleLayout1, mTitleLayout2, mTitleLayout3;

    public TopStories(){

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.top_stories,container,false);
        if (ParamConstants.API_KEY.isEmpty()) {
            Toast.makeText(getContext(), "Please obtain your API KEY first from themoviedb.org", Toast.LENGTH_LONG).show();

        }


        initializeViews(rootView);


        mNewsRecyclerView.setNestedScrollingEnabled(false);
        mMarketRecyclerView.setNestedScrollingEnabled(false);
        mlifestyleRecyclerView.setNestedScrollingEnabled(false);
        mNewsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mMarketRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mlifestyleRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        fetchNews(mNewsRecyclerView,mMarketRecyclerView,mlifestyleRecyclerView);

        return rootView;
    }

    private void initializeViews(View rootView) {
        mNewsRecyclerView = (RecyclerView)rootView.findViewById(R.id.news_recycle);
        mMarketRecyclerView = (RecyclerView)rootView.findViewById(R.id.market_recycle);
        mlifestyleRecyclerView = (RecyclerView)rootView.findViewById(R.id.lifestyle_recycle);
        mTitleLayout1 = (LinearLayout)rootView.findViewById(R.id.title1_layout);
        mTitleLayout2 = (LinearLayout)rootView.findViewById(R.id.title2_layout);
        mTitleLayout3 = (LinearLayout)rootView.findViewById(R.id.title3_layout);
    }

    private void fetchNews(final RecyclerView mNewsRecyclerView, final RecyclerView mMarketRecyclerView, final RecyclerView mlifestyleRecyclerView) {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<JsonElement> call = apiService.getNews("the-times-of-india", "top", ParamConstants.API_KEY);
        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                Log.d("resonse", response.toString());
                JsonElement object = response.body();
                Type listType = new TypeToken<List<News>>() {
                }.getType();
                news = new Gson().fromJson(object.getAsJsonObject().getAsJsonArray("articles"), listType);
                mNewsRecyclerView.setAdapter(new NewsAdapter(news, R.layout.news_list_item, getContext()));
                TopStories.this.mNewsRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL_LIST));
                mTitleLayout1.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                Log.e(TAG, t.toString());
            }
        });


        Call<JsonElement> market = apiService.getNews("cnn","top",ParamConstants.API_KEY);
        market.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                JsonElement object = response.body();
                Type listType = new TypeToken<List<News>>() {
                }.getType();
                news = new Gson().fromJson(object.getAsJsonObject().getAsJsonArray("articles"), listType);
                mMarketRecyclerView.setAdapter(new NewsAdapter(news, R.layout.news_list_item, getContext()));
                TopStories.this.mMarketRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL_LIST));
                mTitleLayout2.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {

            }
        });

        Call<JsonElement> lifestyle = apiService.getNews("mtv-news","top",ParamConstants.API_KEY);
        lifestyle.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                JsonElement object = response.body();
                Type listType = new TypeToken<List<News>>() {
                }.getType();
                news = new Gson().fromJson(object.getAsJsonObject().getAsJsonArray("articles"), listType);
                mlifestyleRecyclerView.setAdapter(new NewsAdapter(news, R.layout.news_list_item, getContext()));
                TopStories.this.mlifestyleRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL_LIST));
                mTitleLayout3.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {

            }
        });

        Call<JsonElement> source = apiService.getSources("", "", "");
        source.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                Log.d("sources", response.body().toString());

                Type listType = new TypeToken<List<NewsResponse>>() {
                }.getType();
                newsResponse = new Gson().fromJson(response.body().getAsJsonObject().getAsJsonArray("sources"), listType);
                newsResponse.get(0).getCategory().toString();
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                Log.e("sourcesfailure", t.toString());
            }
        });
    }
}
