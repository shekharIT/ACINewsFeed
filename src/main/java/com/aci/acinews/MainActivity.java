package com.aci.acinews;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.aci.acinews.ui.news.NewsFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ((NewsApp) getApplication()).getAppComponent().inject(this);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new NewsFragment())
                .commit();
    }
}