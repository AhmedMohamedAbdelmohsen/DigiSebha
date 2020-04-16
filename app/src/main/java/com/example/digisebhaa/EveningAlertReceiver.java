package com.example.digisebhaa;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;

import androidx.core.app.NotificationCompat;
import androidx.navigation.NavDeepLinkBuilder;

public class EveningAlertReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 1, new Intent(context, MainActivity.class), 0);
        PendingIntent pendingIntent1 = new NavDeepLinkBuilder(context)
                .setComponentName(MainActivity.class)
                .setGraph(R.navigation.app_nav_graph)
                .setDestination(R.id.evening)
                .createPendingIntent();
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setNumber(1)
                .setContentTitle("أذكار المساء")
                .setContentText("حان الأن موعد قراءة أذكار المساء")
                .setSmallIcon(R.drawable.ic_moon);
        builder.setContentIntent(pendingIntent1);
        builder.setDefaults(NotificationCompat.DEFAULT_SOUND);
        builder.setAutoCancel(true);
        builder.setVibrate(new long[]{2000});
        builder.setLights(Color.YELLOW, 1000, 1000);
        builder.setStyle(new NotificationCompat.BigTextStyle()
                .bigText("حان الأن موعد قراءة أذكار المساء"));
        notificationManager.cancel(2);
        notificationManager.notify(1, builder.build());
    }
}
