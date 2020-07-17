package com.supercasual.braintrainer.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.supercasual.braintrainer.BuildConfig;
import com.supercasual.braintrainer.databinding.ActivityMainBinding;

import timber.log.Timber;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        if (BuildConfig.DEBUG) Timber.plant(new Timber.DebugTree());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}
