package com.example.digisebhaa;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

public class HadithAlertReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 2, intent, 0);
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setNumber(3)
                .setContentTitle("حديث شريف")
                .setContentText("قال رسول الله صلى الله عليه وسلم")
                .setSmallIcon(R.drawable.ic_moon);
        builder.setContentIntent(pendingIntent);
        builder.setDefaults(NotificationCompat.DEFAULT_SOUND);
        builder.setAutoCancel(true);
        builder.setStyle(new NotificationCompat.BigTextStyle()
                .bigText("قال رسول الله صلى الله عليه وسلم"));
        notificationManager.notify(3, builder.build());
    }
}
