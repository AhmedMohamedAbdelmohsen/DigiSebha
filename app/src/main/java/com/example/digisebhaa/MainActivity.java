package com.example.digisebhaa;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.example.digisebhaa.databinding.ActivityMainBinding;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private View view;
    private NavHostFragment navHostFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        view = binding.getRoot();
        setContentView(view);


        ImageButton imageButton = findViewById(R.id.btn_exit);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeApp();
            }
        });

        //Hide status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //set navHost fragment
        navHostFragment();
        //set NavBar Visible
        binding.bottomNavBar.setVisibility(View.VISIBLE);
        //move to rosary fragment
        moveToRosaryFragment();
        moveToDarkFragment();

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

    private void moveToDarkFragment() {
        binding.btnDarkMode.setOnClickListener(new View.OnClickListener() {
            @Override
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
                navHostFragment.getNavController().navigate(R.id.action_to_dark_fragment);
            }
        });
    }

}
