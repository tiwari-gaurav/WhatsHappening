package com.kotlin.whatshappening.activity.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;

import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.kotlin.whatshappening.R;
import com.kotlin.whatshappening.activity.utils.TypeWriter;

import nl.dionsegijn.konfetti.KonfettiView;
import nl.dionsegijn.konfetti.models.Shape;
import nl.dionsegijn.konfetti.models.Size;

/**
 * Created by gaurav on 15/9/17.
 */

public class SplashActivity extends AppCompatActivity {

    // Splash screen timer
    private static int SPLASH_TIME_OUT = 5000;
    Animation animFadein;
    private TypeWriter mAppText;
    private KonfettiView mParticlesAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mAppText=(TypeWriter) findViewById(R.id.app_text);
        mParticlesAnimation = (KonfettiView)findViewById(R.id.viewKonfetti);
        mParticlesAnimation.build()
                .addColors(Color.YELLOW, Color.BLUE, Color.MAGENTA, Color.CYAN, Color.RED)
                .setDirection(0.0, 359.0)
                .setSpeed(1f, 5f)
                .setFadeOutEnabled(true)
                .setTimeToLive(2000L)
                .addShapes(Shape.RECT, Shape.CIRCLE)
                .addSizes(new Size(12, 5f))
                .setPosition(-50f, mParticlesAnimation.getWidth() + 50f, -50f, -50f)
                .stream(300, 5000L);

       /* Animation a = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        a.reset();
        mAppText.clearAnimation();
        mAppText.startAnimation(a);*/


        //TypeWriter

        //Add a character every 150ms
        mAppText.setCharacterDelay(150);
        mAppText.animateText("What's Happening");

        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                // load the animation

                Intent i = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(i);

                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}
