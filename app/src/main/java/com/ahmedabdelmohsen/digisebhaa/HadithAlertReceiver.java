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

public class HadithAlertReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String HADITH_ID = "hadith_channel_id";
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel hadithChannel = new NotificationChannel(HADITH_ID, "My Notifications", importance);
            hadithChannel.setDescription("Channel description");
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            notificationManager.createNotificationChannel(hadithChannel);
        }

        intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 2, intent, 0);
        PendingIntent pendingIntent1 = new NavDeepLinkBuilder(context)
                .setComponentName(MainActivity.class)
                .setGraph(R.navigation.app_nav_graph)
                .setDestination(R.id.hadith_sharif)
                .createPendingIntent();

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, HADITH_ID)
                .setNumber(3)
                .setContentTitle("فضل الصلاة على النبي")
                .setContentText("قال رسول الله صلى الله عليه :من صلَّى عليَّ صلاةً واحدةً صلَّى اللَّهُ عليهِ عشرَ صلواتٍ، وحُطَّت عنهُ عَشرُ خطيئاتٍ، ورُفِعَت لَهُ عشرُ درجات.")
                .setSmallIcon(R.drawable.ic_book);
        builder.setContentIntent(pendingIntent1);
        builder.setDefaults(NotificationCompat.DEFAULT_SOUND);
        builder.setAutoCancel(true);
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
        builder.setVibrate(new long[]{2000});
        builder.setLights(Color.YELLOW, 1000, 1000);
        builder.setStyle(new NotificationCompat.BigTextStyle()
                .bigText("قال رسول الله صلى الله عليه :من صلَّى عليَّ صلاةً واحدةً صلَّى اللَّهُ عليهِ عشرَ صلواتٍ، وحُطَّت عنهُ عَشرُ خطيئاتٍ، ورُفِعَت لَهُ عشرُ درجات."));
        notificationManager.notify(3, builder.build());
    }
}
