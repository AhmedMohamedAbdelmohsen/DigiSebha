package com.example.digisebhaa;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.example.digisebhaa.databinding.ActivityMainBinding;

import java.util.Calendar;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private View view;
    private NavHostFragment navHostFragment;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        view = binding.getRoot();
        setContentView(view);
        //Hide status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //set navHost fragment
        navHostFragment();
        //set NavBar Visible
        binding.bottomNavBar.setVisibility(View.VISIBLE);
        //move to rosary fragment
        moveToRosaryFragment();
        //sadaka garia dialog
        sadakaGaria();
    }

    private void closeApp() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this, R.style.AlertDialogStyle);
        builder.setMessage("هل تريد الخروج من التطبيق ؟").setCancelable(false)
                .setPositiveButton("نعم", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finishAndRemoveTask();
                    }
                }).setNegativeButton("لا", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.setTitle("تنبيه!!");
        dialog.show();

        final Button lPositiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        // Fetch the LinearLayout.
        final LinearLayout lParent = (LinearLayout) lPositiveButton.getParent();
        // Ensure the Parent of the Buttons aligns it's contents to the right.
        lParent.setGravity(Gravity.START);
        // Hide the LeftSpacer. (Strict dependence on the order of the layout!)
        lParent.getChildAt(1).setVisibility(View.GONE);
    }

    private void navHostFragment() {
        navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(binding.bottomNavBar, Objects.requireNonNull(navHostFragment).getNavController());
    }

    private void moveToRosaryFragment() {
        binding.fabRosary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navHostFragment.getNavController().navigate(R.id.action_to_rosary);
            }
        });
    }

    private void sadakaGaria() {
        binding.fabSadakaGaria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SadakaGariaDialog dialog = new SadakaGariaDialog(MainActivity.this);
                dialog.show();
            }
        });
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (binding.fabMenu.isExpanded()) {
                Rect outRect = new Rect();
                binding.fabMenu.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int) event.getRawX(), (int) event.getRawY())) {
                    binding.fabMenu.collapse();
                    return false;
                }
            }
        }
        return super.dispatchTouchEvent(event);
    }

    void setNotifications() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 5);
        calendar.set(Calendar.MINUTE, 8);

        Calendar currentTime = Calendar.getInstance();
        currentTime.setTimeInMillis(System.currentTimeMillis());
        currentTime.getTimeInMillis();

        SharedPreferences preferences = this.getSharedPreferences("pref", Context.MODE_PRIVATE);
        long hadithCalendar = preferences.getLong("hadith_time_notif", calendar.getTimeInMillis());
        Intent intent = new Intent(this, HadithAlertReceiver.class);
        AlarmManager alarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, hadithCalendar, AlarmManager.INTERVAL_DAY
                , PendingIntent.getBroadcast(this, 2, intent, PendingIntent.FLAG_UPDATE_CURRENT));

    }

    void firstStartOfApp() {
        SharedPreferences preferences = MainActivity.this.getSharedPreferences("pref", Context.MODE_PRIVATE);
        boolean isFirstTime = preferences.getBoolean("first_time", true);
        if (isFirstTime) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());
            calendar.set(Calendar.HOUR_OF_DAY, 5);
            calendar.set(Calendar.MINUTE, 39);
            calendar.add(Calendar.MINUTE, 2);
            long hadithCalendar = preferences.getLong("hadith_time_notif", calendar.getTimeInMillis());
            Intent intent = new Intent(MainActivity.this, HadithAlertReceiver.class);
            AlarmManager alarmManager = (AlarmManager) MainActivity.this.getSystemService(Context.ALARM_SERVICE);
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY
                    , PendingIntent.getBroadcast(MainActivity.this, 2, intent, PendingIntent.FLAG_UPDATE_CURRENT));
            Toast.makeText(MainActivity.this, "first Time open app", Toast.LENGTH_LONG).show();
            preferences.edit().putBoolean("first_time", false).apply();
        }
    }
}
