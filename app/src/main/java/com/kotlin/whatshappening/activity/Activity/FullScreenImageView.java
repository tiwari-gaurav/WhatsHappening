package com.kotlin.whatshappening.activity.Activity;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kotlin.whatshappening.R;

/**
 * Created by gaurav on 18/9/17.
 */

public class FullScreenImageView extends AppCompatActivity {

    ImageView mLargeImage;
    TextView mImageTitle;
    Toolbar toolbar;

    boolean isImageFitToScreen;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.full_screen_image);
        intializeViews();

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // back button pressed
                finish();
            }
        });

        /*if(isImageFitToScreen) {
            isImageFitToScreen=false;
            mLargeImage.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            mLargeImage.setAdjustViewBounds(true);
        }else{
            isImageFitToScreen=true;
            mLargeImage.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
            mLargeImage.setScaleType(ImageView.ScaleType.FIT_XY);
        }*/
        Glide.with(this).load(getIntent().getStringExtra("banner")).into(mLargeImage);
        mImageTitle.setText(getIntent().getStringExtra("title"));

    }

    private void intializeViews() {

        mLargeImage = (ImageView)findViewById(R.id.imageFull);
        mImageTitle = (TextView)findViewById(R.id.imageText);
        toolbar = (Toolbar)findViewById(R.id.toolbarFull);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
