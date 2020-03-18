package com.example.digisebhaa;

import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.example.digisebhaa.databinding.FragmentSplashBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;


/**
 * A simple {@link Fragment} subclass.
 */
public class SplashFragment extends Fragment {

    private FragmentSplashBinding binding;
    private Animation fadIn;
    private AnimationDrawable animationDrawable;
    private BottomNavigationView bottomNavigationView;
    private Handler handler = new Handler();
    private Typeface typeface;

    public SplashFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSplashBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setAnimations();
        setNavigationViewVisibility();
        setPostDelayed(view);

        ImageButton imageButton = getActivity().findViewById(R.id.btn_exit);
        imageButton.setVisibility(View.GONE);

    }
    //set bottom navigation view gone
    public void setNavigationViewVisibility() {
        bottomNavigationView = getActivity().findViewById(R.id.bottom_nav_bar);
        bottomNavigationView.setVisibility(View.GONE);
    }

    //Make Animations
    public void setAnimations() {
        fadIn = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_in);
        typeface = Typeface.createFromAsset(getActivity().getAssets(), "almushaf.ttf");
        binding.tvDigitalSebha.setTypeface(typeface);
        binding.tvDigitalSebha.setAnimation(fadIn);
        animationDrawable = (AnimationDrawable) binding.ivSplash.getBackground();
        animationDrawable.setEnterFadeDuration(1000);
        animationDrawable.setExitFadeDuration(1500);
        animationDrawable.start();
    }

    //set fragment delayed
    public void setPostDelayed(final View view) {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                NavDirections action =
                        SplashFragmentDirections.splashFragmentToRosary();
                Navigation.findNavController(view).navigate(action);
            }
        }, 4500);
    }

}
