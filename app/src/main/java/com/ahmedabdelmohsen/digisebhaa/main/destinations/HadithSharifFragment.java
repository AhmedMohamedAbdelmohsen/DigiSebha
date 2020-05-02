package com.ahmedabdelmohsen.digisebhaa.main.destinations;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.ahmedabdelmohsen.digisebhaa.R;
import com.ahmedabdelmohsen.digisebhaa.SebhaListAdapter;
import com.ahmedabdelmohsen.digisebhaa.databinding.FragmentHadithSharifBinding;
import com.ahmedabdelmohsen.digisebhaa.main.viewmodel.SebhaViewModel;
import com.ahmedabdelmohsen.digisebhaa.pojo.SebhaModel;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class HadithSharifFragment extends Fragment {
    private FragmentHadithSharifBinding binding;
    private SebhaViewModel viewModel;
    private Typeface typeface;

    public HadithSharifFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentHadithSharifBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setVisibility();
        typeface = Typeface.createFromAsset(getActivity().getAssets(), "almushaf.ttf");
        binding.tvTitle.setTypeface(typeface);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final SebhaListAdapter adapter = new SebhaListAdapter(getActivity());
        binding.rvHadith.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.rvHadith.setAdapter(adapter);
        viewModel = new ViewModelProvider(Objects.requireNonNull(getActivity())).get(SebhaViewModel.class);
        viewModel.GetAllHadith().observe(getViewLifecycleOwner(), new Observer<List<SebhaModel>>() {
            @Override
            public void onChanged(List<SebhaModel> words) {
                adapter.setList(words);
            }
        });
    }

    public void setVisibility() {
        BottomNavigationView bottomNavigationView = Objects.requireNonNull(getActivity()).findViewById(R.id.bottom_nav_bar);
        bottomNavigationView.setVisibility(View.VISIBLE);

        FloatingActionsMenu floatingActionsMenu = getActivity().findViewById(R.id.fab_menu);
        floatingActionsMenu.setVisibility(View.VISIBLE);
    }
}
