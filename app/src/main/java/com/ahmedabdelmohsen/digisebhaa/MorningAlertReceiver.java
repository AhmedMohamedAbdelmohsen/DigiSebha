package com.ahmedabdelmohsen.digisebhaa;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.navigation.NavDeepLinkBuilder;

public class MorningAlertReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String MORNING_ID = "morning_channel_id";
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel morningChannel = new NotificationChannel(MORNING_ID, "My morning Dhikr", importance);
            morningChannel.setDescription("Channel description");
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            notificationManager.createNotificationChannel(morningChannel);
        }

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, new Intent(context, MainActivity.class), 0);
        PendingIntent pendingIntent1 = new NavDeepLinkBuilder(context)
                .setComponentName(MainActivity.class)
                .setGraph(R.navigation.app_nav_graph)
                .setDestination(R.id.morning)
                .createPendingIntent();
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, MORNING_ID)
                .setNumber(1)
                .setContentTitle("أذكار الصباح")
                .setContentText("حان الأن موعد قراءة أذكار الصباح")
                .setSmallIcon(R.drawable.ic_morning);
        builder.setContentIntent(pendingIntent1);
        builder.setDefaults(NotificationCompat.DEFAULT_SOUND);
        builder.setAutoCancel(true);
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
        builder.setVibrate(new long[]{2000});
        builder.setLights(Color.YELLOW, 1000, 1000);
        builder.setStyle(new NotificationCompat.BigTextStyle()
                .bigText("حان الأن موعد قراءة أذكار الصباح"));
        notificationManager.notify(1, builder.build());
    }
}
