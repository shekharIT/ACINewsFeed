// Generated by Dagger (https://dagger.dev).
package com.aci.acinews.di.component;

import android.content.Context;
import androidx.lifecycle.ViewModel;
import com.aci.acinews.MainActivity;
import com.aci.acinews.data.repository.NewsRepository;
import com.aci.acinews.di.module.AppModule;
import com.aci.acinews.di.module.AppModule_ProvideContextFactory;
import com.aci.acinews.di.module.RepositoryModule;
import com.aci.acinews.di.module.RepositoryModule_ProvideNewsRepositoryFactory;
import com.aci.acinews.ui.news.NewsFragment;
import com.aci.acinews.ui.news.NewsFragment_MembersInjector;
import com.aci.acinews.ui.viewmodels.NewsViewModel;
import com.aci.acinews.ui.viewmodels.ViewModelFactory;
import dagger.internal.DaggerGenerated;
import dagger.internal.DoubleCheck;
import dagger.internal.Preconditions;
import java.util.Collections;
import java.util.Map;
import javax.inject.Provider;

@DaggerGenerated
@SuppressWarnings({
    "unchecked",
    "rawtypes"
})
public final class DaggerAppComponent implements AppComponent {
  private final RepositoryModule repositoryModule;

  private final DaggerAppComponent appComponent = this;

  private Provider<Context> provideContextProvider;

  private DaggerAppComponent(AppModule appModuleParam, RepositoryModule repositoryModuleParam) {
    this.repositoryModule = repositoryModuleParam;
    initialize(appModuleParam, repositoryModuleParam);

  }

  public static Builder builder() {
    return new Builder();
  }

  private NewsRepository newsRepository() {
    return RepositoryModule_ProvideNewsRepositoryFactory.provideNewsRepository(repositoryModule, provideContextProvider.get());
  }

  private NewsViewModel newsViewModel() {
    return new NewsViewModel(newsRepository());
  }

  private Map<Class<? extends ViewModel>, ViewModel> mapOfClassOfAndViewModel() {
    return Collections.<Class<? extends ViewModel>, ViewModel>singletonMap(NewsViewModel.class, newsViewModel());
  }

  private ViewModelFactory viewModelFactory() {
    return new ViewModelFactory(mapOfClassOfAndViewModel());
  }

  @SuppressWarnings("unchecked")
  private void initialize(final AppModule appModuleParam,
      final RepositoryModule repositoryModuleParam) {
    this.provideContextProvider = DoubleCheck.provider(AppModule_ProvideContextFactory.create(appModuleParam));
  }

  @Override
  public void inject(MainActivity activity) {
  }

  @Override
  public void inject(NewsFragment newsFragment) {
    injectNewsFragment(newsFragment);
  }

  private NewsFragment injectNewsFragment(NewsFragment instance) {
    NewsFragment_MembersInjector.injectFactory(instance, viewModelFactory());
    return instance;
  }

  public static final class Builder {
    private AppModule appModule;

    private RepositoryModule repositoryModule;

    private Builder() {
    }

    public Builder appModule(AppModule appModule) {
      this.appModule = Preconditions.checkNotNull(appModule);
      return this;
    }

    public Builder repositoryModule(RepositoryModule repositoryModule) {
      this.repositoryModule = Preconditions.checkNotNull(repositoryModule);
      return this;
    }

    public AppComponent build() {
      Preconditions.checkBuilderRequirement(appModule, AppModule.class);
      if (repositoryModule == null) {
        this.repositoryModule = new RepositoryModule();
      }
      return new DaggerAppComponent(appModule, repositoryModule);
    }
  }
}