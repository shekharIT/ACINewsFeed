package com.aci.acinews.ui.viewmodels;

import javax.inject.Inject;
import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import java.util.Map;

public class ViewModelFactory implements ViewModelProvider.Factory {
    private final Map<Class<? extends ViewModel>, ViewModel> creators;

    @Inject
    public ViewModelFactory(Map<Class<? extends ViewModel>, ViewModel> creators) {
        this.creators = creators;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        ViewModel viewModel = creators.get(modelClass);
        if (viewModel == null) {
            for (Map.Entry<Class<? extends ViewModel>, ViewModel> entry : creators.entrySet()) {
                if (modelClass.isAssignableFrom(entry.getKey())) {
                    viewModel = entry.getValue();
                    break;
                }
            }
        }
        if (viewModel == null) {
            throw new IllegalArgumentException("Unknown model class: " + modelClass);
        }
        try {
            return (T) viewModel;
        } catch (ClassCastException e) {
            throw new IllegalArgumentException("ViewModel provider is not compatible with requested ViewModel", e);
        }
    }
}

