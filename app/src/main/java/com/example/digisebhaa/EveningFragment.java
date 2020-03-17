package com.example.digisebhaa;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.digisebhaa.databinding.FragmentEveningBinding;
import com.example.digisebhaa.pojo.SebhaModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class EveningFragment extends Fragment {

    private FragmentEveningBinding binding;
    private SebhaViewModel viewModel;
    private Typeface typeface;

    public EveningFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentEveningBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.bottom_nav_bar);
        bottomNavigationView.setVisibility(View.VISIBLE);
        typeface = Typeface.createFromAsset(getActivity().getAssets(), "almushaf.ttf");
        binding.tvTitle.setTypeface(typeface);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final SebhaListAdapter adapter = new SebhaListAdapter(getActivity());
        binding.rvEveningDhikr.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.rvEveningDhikr.setAdapter(adapter);
        viewModel = new ViewModelProvider(getActivity()).get(SebhaViewModel.class);
        viewModel.GetAllEveningDhikr().observe(getViewLifecycleOwner(), new Observer<List<SebhaModel>>() {
            @Override
            public void onChanged(List<SebhaModel> words) {
                adapter.setList(words);
            }
        });
    }
}
