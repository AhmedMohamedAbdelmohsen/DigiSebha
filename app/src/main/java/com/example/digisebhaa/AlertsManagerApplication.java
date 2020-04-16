package com.example.digisebhaa;

import android.app.Application;

public class AlertsManagerApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
//        SharedPreferences preferences = this.getSharedPreferences("pref", Context.MODE_PRIVATE);
//        boolean isFirstTime = preferences.getBoolean("first_time", true);
//        if (isFirstTime) {
//
//            Calendar calendar = Calendar.getInstance();
//            calendar.setTimeInMillis(System.currentTimeMillis());
//            calendar.set(Calendar.HOUR_OF_DAY, 6);
//            calendar.set(Calendar.MINUTE, 1);
//            calendar.add(Calendar.MINUTE, 2);
//
//            long hadithCalendar = preferences.getLong("hadith_time_notif", calendar.getTimeInMillis());
//
//            Intent intent = new Intent(this, HadithAlertReceiver.class);
//            AlarmManager alarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
//            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, hadithCalendar, AlarmManager.INTERVAL_DAY
//                    , PendingIntent.getBroadcast(this, 2, intent, PendingIntent.FLAG_UPDATE_CURRENT));
//            Toast.makeText(this, "first Time open app", Toast.LENGTH_LONG).show();
//            preferences.edit().putBoolean("first_time", false).apply();
//        }

    }
}
