package com.example.digisebhaa;

import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.digisebhaa.databinding.FragmentRosaryBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;


/**
 * A simple {@link Fragment} subclass.
 */
public class RosaryFragment extends Fragment {

    private FragmentRosaryBinding binding;
    private Typeface typeface;

    public RosaryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentRosaryBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.bottom_nav_bar);
        bottomNavigationView.setVisibility(view.VISIBLE);
        typeface = Typeface.createFromAsset(getActivity().getAssets(), "almushaf.ttf");
        binding.tvTitle.setTypeface(typeface);
        binding.tvDescribe.setTypeface(typeface);

    }
}
