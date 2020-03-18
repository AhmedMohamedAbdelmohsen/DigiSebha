package com.example.digisebhaa;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.digisebhaa.databinding.FragmentRosaryBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;


/**
 * A simple {@link Fragment} subclass.
 */
public class RosaryFragment extends Fragment {

    private FragmentRosaryBinding binding;
    private Typeface typeface;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

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
        bottomNavigationView.setVisibility(View.VISIBLE);

        ImageButton imageButton = getActivity().findViewById(R.id.btn_exit);
        imageButton.setVisibility(View.VISIBLE);

        typeface = Typeface.createFromAsset(getActivity().getAssets(), "almushaf.ttf");
        binding.tvTitle.setTypeface(typeface);
        binding.tvDescribe.setTypeface(typeface);
        sharedPreferences = this.getActivity().getSharedPreferences("pref", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        binding.tvCounter.setText(String.valueOf(sharedPreferences.getInt("counter", 0)));

        binding.fabCounter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int x = Integer.parseInt(binding.tvCounter.getText().toString());
                if (x < 1000000) {
                    x++;
                } else {
                    Toast.makeText(v.getContext(), "بارك الله لك لقد أتممت مليون تسبيح", Toast.LENGTH_SHORT).show();
                }
                editor.putInt("counter", x);
                editor.apply();
                binding.tvCounter.setText(String.valueOf(x));
            }
        });

        binding.fabResetCounter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int y = sharedPreferences.getInt("counter", 0);
                if (y == 0) {
                    Toast.makeText(v.getContext(), "عدد التسبيحات 0 بالفعل", Toast.LENGTH_SHORT).show();
                } else if (y > 0) {
                    editor.putInt("counter", 0);
                    editor.apply();
                    binding.tvCounter.setText(String.valueOf(0));

                }
            }
        });

    }
}
