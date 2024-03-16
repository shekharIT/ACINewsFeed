package com.aci.acinews.di.component;

import com.aci.acinews.MainActivity;
import com.aci.acinews.di.module.AppModule;
import com.aci.acinews.di.module.RepositoryModule;
import com.aci.acinews.ui.viewmodels.ViewModelModule;
import com.aci.acinews.ui.news.NewsFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, ViewModelModule.class, RepositoryModule.class})
public interface AppComponent {
    void inject(MainActivity activity);
    void inject(NewsFragment newsFragment);
}

