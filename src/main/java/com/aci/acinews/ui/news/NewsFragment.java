package com.aci.acinews.ui.news;

import androidx.annotation.NonNull;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aci.acinews.NewsApp;
import com.aci.acinews.R;
import com.aci.acinews.ui.viewmodels.ViewModelFactory;
import com.aci.acinews.ui.adapters.NewsAdapter;
import com.aci.acinews.ui.viewmodels.NewsViewModel;


import javax.inject.Inject;

public class NewsFragment extends Fragment {

    @Inject
    public ViewModelFactory factory;
    public NewsViewModel newsViewModel;
    private NewsAdapter newsAdapter;
    private EditText searchEditText;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((NewsApp) requireActivity().getApplication()).getAppComponent().inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_news, container, false);
        newsViewModel = new ViewModelProvider(this, factory).get(NewsViewModel.class);
        RecyclerView recyclerView = root.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        newsAdapter = new NewsAdapter(getContext());
        recyclerView.setAdapter(newsAdapter);

        searchEditText = root.findViewById(R.id.search_edit_text);
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                String query = s.toString().trim();
                newsViewModel.searchNews(query);
            }
        });

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        observeViewModel();
    }

    public void observeViewModel() {
        newsViewModel.getNews().observe(getViewLifecycleOwner(), articles -> {
            if (articles != null) {
                newsAdapter.setArticles(articles);
            } else {
                Toast.makeText(requireContext(), "Failed to fetch articles", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

