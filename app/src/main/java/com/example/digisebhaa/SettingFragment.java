package com.example.digisebhaa;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.digisebhaa.databinding.FragmentSettingBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class SettingFragment extends Fragment {

    private FragmentSettingBinding binding;
    private Typeface almushafFont, quranFont, janna;
    private Calendar calendar = Calendar.getInstance();
    private String am_pm;
    private String am = " ص ";
    private String pm = " م ";
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

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
        preferences = this.getActivity().getSharedPreferences("pref", Context.MODE_PRIVATE);
        editor = preferences.edit();
        setMainFonts();
        timePicker();
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
//        binding.switchDhikr.setSwitchTypeface(ResourcesCompat.getFont(getActivity(), R.font.janna));
//        binding.switchHadith.setSwitchTypeface(janna);
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

    public void timePicker() {
        binding.tvMorningNotifTime.setText(preferences.getString("morning_time", "06:00" + am));
        binding.tvEveningNotifTime.setText(preferences.getString("evening_time", "06:00" + pm));
        binding.tvHadithNotifTime.setText(preferences.getString("hadith_time", "12:00" + pm));

        final TimePickerDialog.OnTimeSetListener timeMorningSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendar.set(Calendar.MINUTE, minute);
                String myFormat = "HH:mm";
                if (calendar.get(Calendar.AM_PM) == Calendar.AM)
                    am_pm = " ص ";
                else if (calendar.get(Calendar.AM_PM) == Calendar.PM)
                    am_pm = " م ";
                SimpleDateFormat format = new SimpleDateFormat(myFormat, Locale.getDefault());
                editor.putString("morning_time", format.format(calendar.getTime()) + am_pm).apply();
                binding.tvMorningNotifTime.setText(preferences.getString("morning_time", "06:00" + am));
            }
        };

        final TimePickerDialog.OnTimeSetListener timeEveningSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendar.set(Calendar.MINUTE, minute);
                String myFormat = "HH:mm";
                if (calendar.get(Calendar.AM_PM) == Calendar.AM)
                    am_pm = " ص ";
                else if (calendar.get(Calendar.AM_PM) == Calendar.PM)
                    am_pm = " م ";
                SimpleDateFormat format = new SimpleDateFormat(myFormat, Locale.getDefault());
                editor.putString("evening_time", format.format(calendar.getTime()) + am_pm).apply();
                binding.tvEveningNotifTime.setText(preferences.getString("evening_time", "06:00" + pm));
            }
        };

        final TimePickerDialog.OnTimeSetListener timeHadithSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendar.set(Calendar.MINUTE, minute);
                String myFormat = "HH:mm";
                if (calendar.get(Calendar.AM_PM) == Calendar.AM)
                    am_pm = " ص ";
                else if (calendar.get(Calendar.AM_PM) == Calendar.PM)
                    am_pm = " م ";
                SimpleDateFormat format = new SimpleDateFormat(myFormat, Locale.getDefault());
                editor.putString("hadith_time", format.format(calendar.getTime()) + am_pm).apply();
                binding.tvHadithNotifTime.setText(preferences.getString("hadith_time", "12:00" + pm));
            }
        };

        binding.btnNotifMorningDhikr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TimePickerDialog(getActivity(), timeMorningSetListener,
                        calendar.get(Calendar.HOUR_OF_DAY),
                        calendar.get(Calendar.MINUTE),
                        false).show();
            }
        });

        binding.btnNotifEveningDhikr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TimePickerDialog(getActivity(), timeEveningSetListener,
                        calendar.get(Calendar.HOUR_OF_DAY),
                        calendar.get(Calendar.MINUTE),
                        false).show();
            }
        });

        binding.btnNotifHadith.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TimePickerDialog(getActivity(), timeHadithSetListener,
                        calendar.get(Calendar.HOUR_OF_DAY),
                        calendar.get(Calendar.MINUTE),
                        false).show();
            }
        });
    }
}
