package com.example.digisebhaa;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.digisebhaa.databinding.FragmentSettingBinding;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
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
    private Calendar calendarMorning = Calendar.getInstance();
    private Calendar calendarEvening = Calendar.getInstance();
    private Calendar calendarHadith = Calendar.getInstance();
    private Calendar calenderHadithDefault = Calendar.getInstance();
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

        FloatingActionsMenu floatingActionsMenu = getActivity().findViewById(R.id.fab_menu);
        floatingActionsMenu.setVisibility(View.GONE);

        preferences = this.getActivity().getSharedPreferences("pref", Context.MODE_PRIVATE);
        editor = preferences.edit();

        setMainFonts();
        timePicker();
        saveVibrationStatus();
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

        binding.btnSadakaGaria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sadakaGaria();
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

    private void sadakaGaria() {
        SadakaGariaDialog dialog = new SadakaGariaDialog(getActivity());
        dialog.show();
    }

    public void timePicker() {
        binding.tvMorningNotifTime.setText(preferences.getString("morning_time", "06:00" + am));
        binding.tvEveningNotifTime.setText(preferences.getString("evening_time", "06:00" + pm));
        binding.tvHadithNotifTime.setText(preferences.getString("hadith_time", "3:00" + pm));

        getSwitchDhikrStatus();
        getSwitchHadithStatus();

        binding.switchDhikr.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    editor.putBoolean("dhikr_status", true).apply();
                    binding.tvSwitchDhikrStatus.setVisibility(View.GONE);
                    binding.viewMorningDisabled.setVisibility(View.GONE);
                    binding.viewEveningDisabled.setVisibility(View.GONE);
                    binding.btnNotifMorningDhikr.setEnabled(true);
                    binding.btnNotifEveningDhikr.setEnabled(true);
                } else {
                    editor.putBoolean("dhikr_status", false).apply();
                    binding.tvSwitchDhikrStatus.setVisibility(View.VISIBLE);
                    binding.viewMorningDisabled.setVisibility(View.VISIBLE);
                    binding.viewEveningDisabled.setVisibility(View.VISIBLE);
                    binding.btnNotifMorningDhikr.setEnabled(false);
                    binding.btnNotifEveningDhikr.setEnabled(false);
                }
            }
        });

        binding.switchHadith.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                calenderHadithDefault.setTimeInMillis(System.currentTimeMillis());
                calenderHadithDefault.set(Calendar.HOUR_OF_DAY, 14);
                long hadithCalendar = preferences.getLong("hadith_time_notif", calenderHadithDefault.getTimeInMillis());
                Intent intent = new Intent(getActivity(), HadithAlertReceiver.class);
                AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
                if (isChecked) {
                    editor.putBoolean("switch_hadith_status", true).apply();
                    binding.tvSwitchHadithStatus.setVisibility(View.GONE);
                    binding.viewHadithDisabled.setVisibility(View.GONE);
                    binding.btnNotifHadith.setEnabled(true);
                    alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, hadithCalendar, AlarmManager.INTERVAL_DAY
                            , PendingIntent.getBroadcast(getActivity(), 2, intent, PendingIntent.FLAG_UPDATE_CURRENT));
                } else {
                    editor.putBoolean("switch_hadith_status", false).apply();
                    binding.tvSwitchHadithStatus.setVisibility(View.VISIBLE);
                    binding.viewHadithDisabled.setVisibility(View.VISIBLE);
                    binding.btnNotifHadith.setEnabled(false);
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity(), 2, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                    alarmManager.cancel(pendingIntent);
                }
            }
        });

        final TimePickerDialog.OnTimeSetListener timeMorningSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                calendarMorning.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendarMorning.set(Calendar.MINUTE, minute);
                calendarMorning.set(Calendar.SECOND, 0);
                String myFormat = "hh:mm";
                if (calendarMorning.get(Calendar.AM_PM) == Calendar.AM)
                    am_pm = " ص ";
                else if (calendarMorning.get(Calendar.AM_PM) == Calendar.PM)
                    am_pm = " م ";
                SimpleDateFormat format = new SimpleDateFormat(myFormat, Locale.getDefault());
                editor.putString("morning_time", format.format(calendarMorning.getTime()) + am_pm).apply();
                Intent intent = new Intent(getActivity(), MorningAlertReceiver.class);
                AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendarMorning.getTimeInMillis(), AlarmManager.INTERVAL_DAY
                        , PendingIntent.getBroadcast(getActivity(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT));
                binding.tvMorningNotifTime.setText(preferences.getString("morning_time", "06:00" + am));
            }
        };

        final TimePickerDialog.OnTimeSetListener timeEveningSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                calendarEvening.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendarEvening.set(Calendar.MINUTE, minute);
                calendarEvening.set(Calendar.SECOND, 0);
                String myFormat = "hh:mm";
                if (calendarEvening.get(Calendar.AM_PM) == Calendar.AM)
                    am_pm = " ص ";
                else if (calendarEvening.get(Calendar.AM_PM) == Calendar.PM)
                    am_pm = " م ";
                SimpleDateFormat format = new SimpleDateFormat(myFormat, Locale.getDefault());
                editor.putString("evening_time", format.format(calendarEvening.getTime()) + am_pm).apply();
                editor.putLong("evening_time_notif", calendarEvening.getTimeInMillis()).apply();
                Intent intent = new Intent(getActivity(), EveningAlertReceiver.class);
                AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendarEvening.getTimeInMillis(), AlarmManager.INTERVAL_DAY
                        , PendingIntent.getBroadcast(getActivity(), 1, intent, PendingIntent.FLAG_UPDATE_CURRENT));
                binding.tvEveningNotifTime.setText(preferences.getString("evening_time", "06:00" + pm));
            }
        };

        final TimePickerDialog.OnTimeSetListener timeHadithSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                calendarHadith.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendarHadith.set(Calendar.MINUTE, minute);
                calendarHadith.set(Calendar.SECOND, 0);
                String myFormat = "hh:mm";
                if (calendarHadith.get(Calendar.AM_PM) == Calendar.AM)
                    am_pm = " ص ";
                else if (calendarHadith.get(Calendar.AM_PM) == Calendar.PM)
                    am_pm = " م ";
                SimpleDateFormat format = new SimpleDateFormat(myFormat, Locale.getDefault());
                editor.putString("hadith_time", format.format(calendarHadith.getTime()) + am_pm).apply();
                editor.putLong("hadith_time_notif", calendarHadith.getTimeInMillis()).apply();

                calenderHadithDefault.setTimeInMillis(System.currentTimeMillis());
                calenderHadithDefault.set(Calendar.HOUR_OF_DAY, 14);

                long hadithCalendar = preferences.getLong("hadith_time_notif", calenderHadithDefault.getTimeInMillis());
                Intent intent = new Intent(getActivity(), HadithAlertReceiver.class);
                AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
                alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, hadithCalendar, AlarmManager.INTERVAL_DAY
                        , PendingIntent.getBroadcast(getActivity(), 2, intent, PendingIntent.FLAG_UPDATE_CURRENT));
                binding.tvHadithNotifTime.setText(preferences.getString("hadith_time", "2:00" + pm));
            }
        };

        binding.btnNotifMorningDhikr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TimePickerDialog(getActivity(), timeMorningSetListener,
                        calendarMorning.get(Calendar.HOUR_OF_DAY),
                        calendarMorning.get(Calendar.MINUTE),
                        false).show();
            }
        });

        binding.btnNotifEveningDhikr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TimePickerDialog(getActivity(), timeEveningSetListener,
                        calendarEvening.get(Calendar.HOUR_OF_DAY),
                        calendarEvening.get(Calendar.MINUTE),
                        false).show();
            }
        });

        binding.btnNotifHadith.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TimePickerDialog(getActivity(), timeHadithSetListener,
                        calendarHadith.get(Calendar.HOUR_OF_DAY),
                        calendarHadith.get(Calendar.MINUTE),
                        false).show();
            }
        });
    }

    void getSwitchDhikrStatus() {
        binding.switchDhikr.setChecked(preferences.getBoolean("dhikr_status", true));
        if (binding.switchDhikr.isChecked()) {
            binding.tvSwitchDhikrStatus.setVisibility(View.GONE);
            binding.viewMorningDisabled.setVisibility(View.GONE);
            binding.viewEveningDisabled.setVisibility(View.GONE);
            binding.btnNotifMorningDhikr.setEnabled(true);
            binding.btnNotifEveningDhikr.setEnabled(true);
        } else {
            binding.tvSwitchDhikrStatus.setVisibility(View.VISIBLE);
            binding.viewMorningDisabled.setVisibility(View.VISIBLE);
            binding.viewEveningDisabled.setVisibility(View.VISIBLE);
            binding.btnNotifMorningDhikr.setEnabled(false);
            binding.btnNotifEveningDhikr.setEnabled(false);
        }
    }

    void getSwitchHadithStatus() {
        binding.switchHadith.setChecked(preferences.getBoolean("switch_hadith_status", true));
        if (binding.switchHadith.isChecked()) {
            binding.tvSwitchHadithStatus.setVisibility(View.GONE);
            binding.viewHadithDisabled.setVisibility(View.GONE);
            binding.btnNotifHadith.setEnabled(true);

        } else {
            binding.tvSwitchHadithStatus.setVisibility(View.VISIBLE);
            binding.viewHadithDisabled.setVisibility(View.VISIBLE);
            binding.btnNotifHadith.setEnabled(false);
        }
    }

    private void saveVibrationStatus() {
        binding.switchVibrationDhikr.setChecked(preferences.getBoolean("vibration", true));
        if (binding.switchVibrationDhikr.isChecked()) {
            binding.tvSwitchVibrationStatus.setVisibility(View.GONE);
        } else {
            binding.tvSwitchVibrationStatus.setVisibility(View.VISIBLE);
        }
        binding.switchVibrationDhikr.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    editor.putBoolean("vibration", true).apply();
                    binding.tvSwitchVibrationStatus.setVisibility(View.GONE);
                    Toast.makeText(getContext(), "لقد فعلت وضع الإهتزاز", Toast.LENGTH_SHORT).show();
                } else {
                    editor.putBoolean("vibration", false).apply();
                    binding.tvSwitchVibrationStatus.setVisibility(View.VISIBLE);
                    Toast.makeText(getContext(), "لقد أغلقت وضع الإهتزاز", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

}
