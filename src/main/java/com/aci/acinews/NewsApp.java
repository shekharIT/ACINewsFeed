package com.aci.acinews;

import android.app.Application;

import com.aci.acinews.di.component.AppComponent;
import com.aci.acinews.di.component.DaggerAppComponent;
import com.aci.acinews.di.module.AppModule;
import com.bumptech.glide.Glide;

public class NewsApp extends Application {

    private AppComponent mAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        Glide.get(this).clearMemory();
        new Thread(() -> {
            //Clear glide memory and disk cache in a background thread

            Glide.get(this).clearDiskCache();
        }).start();
        if (null == mAppComponent) {
            mAppComponent = DaggerAppComponent.builder()
                    .appModule(new AppModule(this))
                    .build();
        }
    }

    public AppComponent getAppComponent() {
        return mAppComponent;
    }
}
