package com.example.digisebhaa;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.example.digisebhaa.databinding.ActivityMainBinding;
import com.example.digisebhaa.pojo.SebhaModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

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


        //Hide status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //set navHost fragment
        navHostFragment();
        //set NavBar Visible
        binding.bottomNavBar.setVisibility(View.VISIBLE);
        //move to rosary fragment
        moveToRosaryFragment();

    }

    private void navHostFragment() {
        navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(binding.bottomNavBar, navHostFragment.getNavController());
    }

    private void moveToRosaryFragment() {
        binding.fabRosary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navHostFragment.getNavController().navigate(R.id.action_to_rosary);
            }
        });
    }


}
