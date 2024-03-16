// Generated by Dagger (https://dagger.dev).
package com.aci.acinews.ui.news;

import com.aci.acinews.ui.viewmodels.ViewModelFactory;
import dagger.MembersInjector;
import dagger.internal.DaggerGenerated;
import dagger.internal.InjectedFieldSignature;
import javax.inject.Provider;

@DaggerGenerated
@SuppressWarnings({
    "unchecked",
    "rawtypes"
})
public final class NewsFragment_MembersInjector implements MembersInjector<NewsFragment> {
  private final Provider<ViewModelFactory> factoryProvider;

  public NewsFragment_MembersInjector(Provider<ViewModelFactory> factoryProvider) {
    this.factoryProvider = factoryProvider;
  }

  public static MembersInjector<NewsFragment> create(Provider<ViewModelFactory> factoryProvider) {
    return new NewsFragment_MembersInjector(factoryProvider);
  }

  @Override
  public void injectMembers(NewsFragment instance) {
    injectFactory(instance, factoryProvider.get());
  }

  @InjectedFieldSignature("com.aci.acinews.ui.news.NewsFragment.factory")
  public static void injectFactory(NewsFragment instance, ViewModelFactory factory) {
    instance.factory = factory;
  }
}