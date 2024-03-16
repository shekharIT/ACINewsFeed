package com.aci.acinews.di.module;

import android.content.Context;

import com.aci.acinews.data.repository.NewsRepository;

import dagger.Module;
import dagger.Provides;

@Module
public class RepositoryModule {

    @Provides
    public NewsRepository provideNewsRepository(Context context) {
        return new NewsRepository(context);
    }
}
