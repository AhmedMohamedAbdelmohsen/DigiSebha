package com.example.digisebhaa;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.digisebhaa.databinding.FragmentDarkModeBinding;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Objects;

public class DarkModeFragment extends Fragment {

    private FragmentDarkModeBinding binding;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Vibrator vibrator;

    public DarkModeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentDarkModeBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        BottomNavigationView bottomNavigationView = Objects.requireNonNull(getActivity()).findViewById(R.id.bottom_nav_bar);
        bottomNavigationView.setVisibility(View.GONE);

        ImageButton darkModeButton = Objects.requireNonNull(getActivity()).findViewById(R.id.btn_dark_mode);
        darkModeButton.setVisibility(View.GONE);

        FloatingActionsMenu floatingActionsMenu = getActivity().findViewById(R.id.fab_menu);
        floatingActionsMenu.setVisibility(View.GONE);

        //ads initialize
        MobileAds.initialize(getActivity(), new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        // Ads Banner
        AdRequest adRequest = new AdRequest.Builder().build();
        binding.adView.loadAd(adRequest);

        vibrator = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
        binding.tgbtnVibration.setChecked(true);
        sharedPreferences = this.getActivity().getSharedPreferences("pref", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        binding.tvCounter.setText(String.valueOf(sharedPreferences.getInt("counter", 0)));

        binding.viewCounter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //setVibrator(vibrator);
                if (binding.tgbtnVibration.isChecked()) {
                    vibrator.vibrate(100);
                } else {
                    vibrator.cancel();
                }
                int x = Integer.parseInt(binding.tvCounter.getText().toString());
                int y = sharedPreferences.getInt("totalCounter", 0);
                String counter = binding.etGetCounter.getText().toString();
                String status = binding.etGetCounter.getText().toString();
                if (status.isEmpty()) {
                    if (x <= 1000000 && y <= 1000000) {
                        x++;
                        y++;
                    } else {
                        Toast.makeText(v.getContext(), "لقد أتممت عدد التسبيح المحدد فى الأعلي", Toast.LENGTH_SHORT).show();
                        if (binding.tgbtnVibration.isChecked()) {
                            vibrator.vibrate(400);
                        }
                    }
                } else {
                    if (x < Integer.parseInt(counter)) {
                        x++;
                        y++;
                    } else {
                        Toast.makeText(v.getContext(), "لقد أتممت عدد التسبيح المحدد فى الأعلي", Toast.LENGTH_SHORT).show();
                        if (binding.tgbtnVibration.isChecked()) {
                            vibrator.vibrate(400);
                        }
                    }
                }
                editor.putInt("counter", x).apply();
                editor.putInt("totalCounter", y).apply();
                binding.tvCounter.setText(String.valueOf(x));

            }
        });

        binding.fbtnResetCounter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.tgbtnVibration.isChecked()) {
                    vibrator.vibrate(300);
                } else {
                    vibrator.cancel();
                }
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

        binding.btnLightMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        AdRequest adRequest = new AdRequest.Builder().build();
        binding.adView.loadAd(adRequest);
    }

    @Override
    public void onStop() {
        super.onStop();
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            if (!Settings.System.canWrite(getActivity().getApplicationContext())) {
//                //set brightness value
//                Settings.System.putInt(getActivity().getApplicationContext().getContentResolver(),
//                        Settings.System.SCREEN_BRIGHTNESS, 60);
//            }
//        } else if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.M) {
//            //set brightness value
//            Settings.System.putInt(getActivity().getApplicationContext().getContentResolver(),
//                    Settings.System.SCREEN_BRIGHTNESS, 60);
//        } else {
//            Toast.makeText(getContext(), "Dark mode", Toast.LENGTH_SHORT).show();
//        }
    }
}