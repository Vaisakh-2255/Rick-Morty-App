package com.example.rickymorty.views.splashscreen;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.rickymorty.databinding.ActivitySplashBinding;
import com.example.rickymorty.utils.BaseActivity;
import com.example.rickymorty.views.homepage.HomePageActivity;

public class SplashActivity extends BaseActivity {

    private static final int SPLASH_TIME_OUT = 3000;

    ActivitySplashBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, HomePageActivity.class));
                finish();
            }
        }, SPLASH_TIME_OUT);

    }
}