package com.ahmedabdelmohsen.digisebhaa;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.ahmedabdelmohsen.digisebhaa.databinding.FragmentRosaryBinding;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class RosaryFragment extends Fragment {

    private FragmentRosaryBinding binding;
    private Typeface typeface;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Vibrator vibrator;
    private NavHostFragment navHostFragment;

    public RosaryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentRosaryBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        BottomNavigationView bottomNavigationView = Objects.requireNonNull(getActivity()).findViewById(R.id.bottom_nav_bar);
        bottomNavigationView.setVisibility(View.VISIBLE);

        FloatingActionsMenu floatingActionsMenu = getActivity().findViewById(R.id.fab_menu);
        floatingActionsMenu.setVisibility(View.VISIBLE);
        //ads initialize
        MobileAds.initialize(getActivity(), new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        // Ads Banner
        AdRequest adRequest = new AdRequest.Builder().build();
        binding.adView.loadAd(adRequest);

        navHostFragment = (NavHostFragment) getActivity().getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment);
        moveToDarkFragment();
        vibrator = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);

        ImageButton darkModeButton = Objects.requireNonNull(getActivity()).findViewById(R.id.btn_dark_mode);
        darkModeButton.setVisibility(View.VISIBLE);
        binding.tgbtnVibration.setChecked(true);
//        setAlarmHadith();
        sharedPreferences = this.getActivity().getSharedPreferences("pref", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        setNotificationsfromDilaog();
        totalCounter();
        typeface = Typeface.createFromAsset(getActivity().getAssets(), "almushaf.ttf");
        binding.tvTitle.setTypeface(typeface);
        binding.tvDescribe.setTypeface(typeface);

        binding.tvCounter.setText(String.valueOf(sharedPreferences.getInt("counter", 0)));
        binding.fabCounter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //setVibrator(vibrator);
                if (binding.tgbtnVibration.isChecked()) {
                    vibrator.vibrate(100);
                } else {
                    vibrator.cancel();
                }
                int x = Integer.parseInt(binding.tvCounter.getText().toString());
                int y = sharedPreferences.getInt("totalCounter", 0);
                String counter = binding.etGetCounter.getText().toString();
                String status = binding.etGetCounter.getText().toString();
                if (status.isEmpty()) {
                    if (x <= 1000000 && y <= 1000000) {
                        x++;
                        y++;
                    } else {
                        Toast.makeText(v.getContext(), "لقد أتممت مليون تسبيح من فضلك أعد تصفير جميع الأعداد", Toast.LENGTH_SHORT).show();
                        if (binding.tgbtnVibration.isChecked()) {
                            vibrator.vibrate(400);
                        }
                    }
                } else {
                    if (x < Integer.parseInt(counter)) {
                        x++;
                        y++;
                    } else {
                        Toast.makeText(v.getContext(), "لقد أتممت عدد التسبيح المحدد فى الأعلي", Toast.LENGTH_SHORT).show();
                        if (binding.tgbtnVibration.isChecked()) {
                            vibrator.vibrate(400);
                        }
                    }
                }
                editor.putInt("counter", x).apply();
                editor.putInt("totalCounter", y).apply();
                binding.tvTotalCounter.setText(String.valueOf(y));
                binding.tvCounter.setText(String.valueOf(x));

            }
        });

        binding.fbtnResetCounter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.tgbtnVibration.isChecked()) {
                    vibrator.vibrate(300);
                } else {
                    vibrator.cancel();
                }
                int y = sharedPreferences.getInt("counter", 0);
                if (y == 0) {
                    Toast.makeText(v.getContext(), "عدد التسبيحات 0 بالفعل", Toast.LENGTH_SHORT).show();
                } else if (y > 0) {
                    editor.putInt("counter", 0).apply();
                    binding.tvCounter.setText(String.valueOf(0));

                }
            }
        });

    }

    public void displayToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
    }

    private void moveToDarkFragment() {
        binding.btnDarkMode.setOnClickListener(new View.OnClickListener() {

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
//
                navHostFragment.getNavController().navigate(R.id.action_to_dark_fragment);
            }
        });
    }

    void setNotificationsfromDilaog() {
        boolean isFirstTime = sharedPreferences.getBoolean("first_time", true);
        if (isFirstTime) {
            DialogSetNotifications dialog = new DialogSetNotifications(getActivity());
            dialog.show();
        }

    }

    private void totalCounter() {
        binding.tvTotalCounter.setText(String.valueOf(sharedPreferences.getInt("totalCounter", 0)));
        binding.btnTotalCounterReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AlertDialogStyle);
                builder.setMessage("هل تريد تصفير إجمالي عدد التسبيحات الخاصة بك ؟").setCancelable(false)
                        .setPositiveButton("نعم", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                int y = sharedPreferences.getInt("totalCounter", 0);
                                if (y == 0) {
                                    Toast.makeText(getActivity(), "إجمالي عدد التسبيحات 0 بالفعل", Toast.LENGTH_LONG).show();
                                } else if (y > 0) {
                                    editor.putInt("totalCounter", 0).apply();
                                    binding.tvTotalCounter.setText(String.valueOf(0));
                                    Toast.makeText(getActivity(), "تم تصفير إجمالي التسبيحات", Toast.LENGTH_LONG).show();
                                }
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
                lParent.setGravity(Gravity.LEFT);
                // Hide the LeftSpacer. (Strict dependence on the order of the layout!)
                lParent.getChildAt(1).setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        AdRequest adRequest = new AdRequest.Builder().build();
        binding.adView.loadAd(adRequest);
    }
}
