package com.example.digisebhaa;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.digisebhaa.databinding.FragmentSettingBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class SettingFragment extends Fragment {

    private FragmentSettingBinding binding;
    private Typeface almushafFont, quranFont, janna;


    public SettingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSettingBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        BottomNavigationView bottomNavigationView = Objects.requireNonNull(getActivity()).findViewById(R.id.bottom_nav_bar);
        bottomNavigationView.setVisibility(View.VISIBLE);

        ImageButton darkModeButton = Objects.requireNonNull(getActivity()).findViewById(R.id.btn_dark_mode);
        darkModeButton.setVisibility(View.GONE);
        setMainFonts();
        binding.btnChangeFontType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeFont();
            }
        });

        binding.btnChangeFontColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeColor();
            }
        });

        binding.btnChangeFontSize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeSize();
            }
        });
    }

    private void setMainFonts() {
        almushafFont = Typeface.createFromAsset(getActivity().getAssets(), "almushaf.ttf");
        quranFont = Typeface.createFromAsset(getActivity().getAssets(), "quran.ttf");
        janna = Typeface.createFromAsset(getActivity().getAssets(), "Janna.ttf");
        binding.tvTitle.setTypeface(almushafFont);
        binding.tvFontText.setTypeface(janna);
        binding.btnChangeFontType.setTypeface(janna);
        binding.btnChangeFontColor.setTypeface(janna);
        binding.btnChangeFontSize.setTypeface(janna);
    }

    private void changeFont() {
        FontTypeDialog dialog = new FontTypeDialog(getActivity());
        dialog.show();
    }

    private void changeColor() {
        FontColorDialog dialog = new FontColorDialog(getActivity());
        dialog.show();
    }

    private void changeSize() {
        FontSizeDialog dialog = new FontSizeDialog(getActivity());
        dialog.show();
    }
}
