package com.example.digisebhaa;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
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
import androidx.navigation.fragment.NavHostFragment;

import com.example.digisebhaa.databinding.FragmentRosaryBinding;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class RosaryFragment extends Fragment {

    private FragmentRosaryBinding binding;
    private Typeface typeface;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Vibrator vibrator;
    private NavHostFragment navHostFragment;

    public RosaryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentRosaryBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        BottomNavigationView bottomNavigationView = Objects.requireNonNull(getActivity()).findViewById(R.id.bottom_nav_bar);
        bottomNavigationView.setVisibility(View.VISIBLE);

        FloatingActionsMenu floatingActionsMenu = getActivity().findViewById(R.id.fab_menu);
        floatingActionsMenu.setVisibility(View.VISIBLE);

        navHostFragment = (NavHostFragment) getActivity().getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment);
        moveToDarkFragment();
        vibrator = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);

        ImageButton darkModeButton = Objects.requireNonNull(getActivity()).findViewById(R.id.btn_dark_mode);
        darkModeButton.setVisibility(View.VISIBLE);
        binding.tgbtnVibration.setChecked(true);
//        setAlarmHadith();

        typeface = Typeface.createFromAsset(getActivity().getAssets(), "almushaf.ttf");
        binding.tvTitle.setTypeface(typeface);
        binding.tvDescribe.setTypeface(typeface);
        sharedPreferences = this.getActivity().getSharedPreferences("pref", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        binding.tvCounter.setText(String.valueOf(sharedPreferences.getInt("counter", 0)));

        binding.fabCounter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //setVibrator(vibrator);
                if (binding.tgbtnVibration.isChecked()) {
                    vibrator.vibrate(100);
                } else {
                    vibrator.cancel();
                }
                int x = Integer.parseInt(binding.tvCounter.getText().toString());
                String counter = binding.etGetCounter.getText().toString();
                String status = binding.etGetCounter.getText().toString();
                if (status.isEmpty()) {
                    if (x <= 1000000) {
                        x++;
                    } else {
                        Toast.makeText(v.getContext(), "لقد أتممت عدد التسبيح المحدد فى الأعلي", Toast.LENGTH_SHORT).show();
                        if (binding.tgbtnVibration.isChecked()) {
                            vibrator.vibrate(400);
                        }
                    }
                } else {
                    if (x < Integer.parseInt(counter)) {
                        x++;
                    } else {
                        Toast.makeText(v.getContext(), "لقد أتممت عدد التسبيح المحدد فى الأعلي", Toast.LENGTH_SHORT).show();
                        if (binding.tgbtnVibration.isChecked()) {
                            vibrator.vibrate(400);
                        }
                    }
                }
                editor.putInt("counter", x);
                editor.apply();
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

    }

    private void moveToDarkFragment() {
        binding.btnDarkMode.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                    if (!Settings.System.canWrite(getApplicationContext())) {
//                        //set brightness value
//                        Settings.System.putInt(getApplicationContext().getContentResolver(),
//                                Settings.System.SCREEN_BRIGHTNESS, 10);
//                    }
//                } else if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.M) {
//                    //set brightness value
//                    Settings.System.putInt(getApplicationContext().getContentResolver(),
//                            Settings.System.SCREEN_BRIGHTNESS, 10);
//                } else {
//                    Toast.makeText(MainActivity.this, "Dark mode", Toast.LENGTH_SHORT).show();
//                }
//
                navHostFragment.getNavController().navigate(R.id.action_to_dark_fragment);
            }
        });
    }


}
