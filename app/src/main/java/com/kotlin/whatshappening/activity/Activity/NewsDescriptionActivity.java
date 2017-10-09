package com.kotlin.whatshappening.activity.Activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.transition.Fade;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Slide;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kotlin.whatshappening.R;
import com.kotlin.whatshappening.activity.utils.Utilities;


/**
 * Created by gaurav on 15/9/17.
 */

public class NewsDescriptionActivity extends AppCompatActivity {
    private ImageView mNewsBanner;
    private TextView mNewsDescription, mNewsUrl, mTitle, mAuthor, mReleaseDate;
    private Toolbar mToolBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_description);
        initializeViews();
        setToolBar();
        setClickEvents();
        setTextToLayout();
        setupWindowAnimations();
    }


    private void setupWindowAnimations() {
        Slide slide = null;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            slide = new Slide();
            slide.setDuration(1000);
            getWindow().setEnterTransition(slide);
        }
    }
    private void setTextToLayout() {
        Glide.with(this).load(getIntent().getStringExtra("banner")).into(mNewsBanner);
        mNewsDescription.setText(getIntent().getStringExtra("description"));
        mNewsUrl.setText(getIntent().getStringExtra("url"));
        mTitle.setText(getIntent().getStringExtra("title"));
        if(getIntent().getStringExtra("author")==null || getIntent().getStringExtra("author")=="null" || getIntent().getStringExtra("author").isEmpty()){
            mAuthor.setVisibility(View.INVISIBLE);
        }
        else{
            mAuthor.setText(getIntent().getStringExtra("author"));
        }

        if(getIntent().getStringExtra("release_date")==null || getIntent().getStringExtra("release_date")=="null" || getIntent().getStringExtra("release_date").isEmpty()){
            mReleaseDate.setVisibility(View.INVISIBLE);
        }
        else{
            mReleaseDate.setText(getIntent().getStringExtra("release_date"));
        }


    }

    private void setClickEvents() {
        mNewsUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Utilities.isNetworkAvailable(NewsDescriptionActivity.this)){
                    Intent intent = new Intent(NewsDescriptionActivity.this, WbViewActivity.class);
                    intent.putExtra("url_to_web", getIntent().getStringExtra("url"));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
                else {
                    Snackbar snackbar = Snackbar
                            .make(view, "Please check your internet connection", Snackbar.LENGTH_LONG);
                            snackbar.getView().setBackgroundColor(ContextCompat.getColor(NewsDescriptionActivity.this,R.color.new_pink));
                    snackbar.show();
                }

            }
        });
    }

    private void setToolBar() {
        setSupportActionBar(mToolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //do something you want
                finish();
            }
        });
    }

    private void initializeViews() {
        mNewsBanner = (ImageView)findViewById(R.id.news_banner);
        mNewsDescription = (TextView)findViewById(R.id.news_description);
        mNewsUrl = (TextView)findViewById(R.id.news_url);
        mToolBar = (Toolbar)findViewById(R.id.description_toolbar);
        mTitle =(TextView)findViewById(R.id.title);
        mAuthor =(TextView)findViewById(R.id.author);
        mReleaseDate =(TextView)findViewById(R.id.release_date);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
