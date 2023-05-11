package com.example.comp2000.fragments;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.comp2000.R;
import com.example.comp2000.databinding.FragmentHomeBinding;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class HomeFragment extends Fragment {

    public HomeFragment() {
        // Required empty public constructor
    }

    FragmentHomeBinding binding;
    NavController navController;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);

        binding.holidayRecordsCard.setOnClickListener(btn -> {
            navController.navigate(R.id.action_HomeToHolidays);
        });

        binding.employeeRecordsCard.setOnClickListener(btn -> {
            navController.navigate(R.id.action_HomeToRecords);
        });

        binding.accountSettingsCard.setOnClickListener(btn -> {
            navController.navigate(R.id.action_HomeToAccount);
        });

    }
}