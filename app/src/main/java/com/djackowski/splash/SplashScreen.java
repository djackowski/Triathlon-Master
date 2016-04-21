package com.djackowski.splash;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.djackowski.R;

/**
 * Created by Damcios on 2016-02-13.
 */
public class SplashScreen extends AppCompatActivity {

    private ImageView triangle;
    private Animation animation;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashscreen);

        triangle = (ImageView)findViewById(R.id.imageViewTriangle);
        animation = AnimationUtils.loadAnimation(getBaseContext(), R.anim.rotate);

        triangle.startAnimation(animation);

        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                SplashScreen.this.startActivity(new Intent("android.intent.action.NVDRAWER"));
                SplashScreen.this.finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }




    }
