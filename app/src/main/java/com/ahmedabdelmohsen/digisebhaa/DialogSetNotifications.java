package com.ahmedabdelmohsen.digisebhaa;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class DialogSetNotifications extends Dialog {
    public Activity activity;
    public Button morningButton, eveningButton, hadithButton;
    public TextView morningTime, eveningTime, hadithTime, closeWindow;

    private Calendar calendarMorning = Calendar.getInstance();
    private Calendar calendarEvening = Calendar.getInstance();
    private Calendar calendarHadith = Calendar.getInstance();
    private Calendar calenderHadithDefault = Calendar.getInstance();
    private Calendar calenderMorningDefault = Calendar.getInstance();
    private Calendar calenderEveningDefault = Calendar.getInstance();
    private String am_pm;
    private String am = " ص ";
    private String pm = " م ";
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    public DialogSetNotifications(@NonNull Activity activity) {
        super(activity);
        this.activity = activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_set_notifications);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setCancelable(false);
        preferences = activity.getSharedPreferences("pref", Context.MODE_PRIVATE);
        editor = preferences.edit();
        morningButton = findViewById(R.id.btn_morning_notif);
        eveningButton = findViewById(R.id.btn_evening_notif);
        hadithButton = findViewById(R.id.btn_hadith_notif);
        closeWindow = findViewById(R.id.btn_close_dialog);
        morningTime = findViewById(R.id.tv_morning_notif);
        eveningTime = findViewById(R.id.tv_evening_notif);
        hadithTime = findViewById(R.id.tv_hadith_notif);
        timePicker();

    }

    public void timePicker() {
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
                editor.putLong("morning_time_notif", calendarMorning.getTimeInMillis()).apply();

                calenderMorningDefault.setTimeInMillis(System.currentTimeMillis());
                calenderMorningDefault.set(Calendar.HOUR_OF_DAY, 6);
                long morningCalendar = preferences.getLong("morning_time_notif", calenderMorningDefault.getTimeInMillis());

                Intent intentMorningReceiver = new Intent(activity, MorningAlertReceiver.class);
                AlarmManager alarmManagerMorning = (AlarmManager) activity.getSystemService(Context.ALARM_SERVICE);
                alarmManagerMorning.setInexactRepeating(AlarmManager.RTC_WAKEUP, morningCalendar, AlarmManager.INTERVAL_DAY
                        , PendingIntent.getBroadcast(activity, 0, intentMorningReceiver, PendingIntent.FLAG_UPDATE_CURRENT));
                morningTime.setText(preferences.getString("morning_time", "06:00" + am));
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

                calenderEveningDefault.setTimeInMillis(System.currentTimeMillis());
                calenderEveningDefault.set(Calendar.HOUR_OF_DAY, 18);
                long eveningCalendar = preferences.getLong("evening_time_notif", calenderEveningDefault.getTimeInMillis());

                Intent intent = new Intent(activity, EveningAlertReceiver.class);
                AlarmManager alarmManagerEvening = (AlarmManager) activity.getSystemService(Context.ALARM_SERVICE);
                alarmManagerEvening.setInexactRepeating(AlarmManager.RTC_WAKEUP, eveningCalendar, AlarmManager.INTERVAL_DAY
                        , PendingIntent.getBroadcast(activity, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT));
                eveningTime.setText(preferences.getString("evening_time", "06:00" + pm));
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

                Intent intent = new Intent(activity, HadithAlertReceiver.class);
                AlarmManager alarmManagerHadith = (AlarmManager) activity.getSystemService(Context.ALARM_SERVICE);
                alarmManagerHadith.setInexactRepeating(AlarmManager.RTC_WAKEUP, hadithCalendar, AlarmManager.INTERVAL_DAY
                        , PendingIntent.getBroadcast(activity, 2, intent, PendingIntent.FLAG_UPDATE_CURRENT));
                hadithTime.setText(preferences.getString("hadith_time", "2:00" + pm));
            }
        };

        morningButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TimePickerDialog(activity, timeMorningSetListener,
                        calendarMorning.get(Calendar.HOUR_OF_DAY),
                        calendarMorning.get(Calendar.MINUTE),
                        false).show();
            }
        });

        eveningButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TimePickerDialog(activity, timeEveningSetListener,
                        calendarEvening.get(Calendar.HOUR_OF_DAY),
                        calendarEvening.get(Calendar.MINUTE),
                        false).show();
            }
        });

        hadithButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TimePickerDialog(activity, timeHadithSetListener,
                        calendarHadith.get(Calendar.HOUR_OF_DAY),
                        calendarHadith.get(Calendar.MINUTE),
                        false).show();
            }
        });

        closeWindow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String morningTimer = morningTime.getText().toString().trim();
                String eveningTimer = eveningTime.getText().toString().trim();
                String hadithTimer = hadithTime.getText().toString().trim();
                if (TextUtils.isEmpty(morningTimer)) {
                    Toast.makeText(activity, "اضبط تنبيهات اذكار الصباح من فضلك", Toast.LENGTH_LONG).show();
                } else if (TextUtils.isEmpty(eveningTimer)) {
                    Toast.makeText(activity, "اضبط تنبيهات اذكار المساء من فضلك", Toast.LENGTH_LONG).show();
                } else if (TextUtils.isEmpty(hadithTimer)) {
                    Toast.makeText(activity, "اضبط تنبيهات الأحاديث من فضلك", Toast.LENGTH_LONG).show();
                } else {
                    dismiss();
                    editor.putBoolean("first_time", false).apply();
                }
            }
        });
    }
}
