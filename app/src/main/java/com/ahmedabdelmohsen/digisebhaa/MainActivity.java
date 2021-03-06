package com.ahmedabdelmohsen.digisebhaa;

import android.content.DialogInterface;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.ahmedabdelmohsen.digisebhaa.databinding.ActivityMainBinding;
import com.ahmedabdelmohsen.digisebhaa.dialogs.DialogContactUs;
import com.ahmedabdelmohsen.digisebhaa.dialogs.SadakaAdsDialog;
import com.ahmedabdelmohsen.digisebhaa.dialogs.SadakaGariaDialog;

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
        sadakaGariaAds();
        //contact us
        contactUs();
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

    private void sadakaGariaAds() {
        binding.fabAds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SadakaAdsDialog dialog = new SadakaAdsDialog(MainActivity.this);
                dialog.show();
            }
        });
    }

    private void contactUs() {
        binding.fabContactUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogContactUs dialog = new DialogContactUs(MainActivity.this);
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

}
