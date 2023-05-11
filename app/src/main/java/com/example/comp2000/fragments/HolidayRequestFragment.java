package com.example.comp2000.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.comp2000.R;
import com.example.comp2000.databinding.FragmentHolidayRequestBinding;
import com.example.comp2000.enums.HolidayStatus;
import com.example.comp2000.models.HolidayRequest;
import com.example.comp2000.view_models.HolidayViewModel;

import java.time.LocalDate;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class HolidayRequestFragment extends Fragment {

    public HolidayRequestFragment() {
        // Required empty public constructor
    }

    private FragmentHolidayRequestBinding viewBinding;
    private NavController navController;
    private HolidayViewModel holidayViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        holidayViewModel = new ViewModelProvider(this).get(HolidayViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        viewBinding = FragmentHolidayRequestBinding.inflate(inflater, container, false);
        return viewBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);

        // argument passed from previous fragment
        int holidayIndex = HolidayRequestFragmentArgs.fromBundle(getArguments()).getHolidayIndex();

        // check that holiday index is not out of bounds
        if (holidayIndex >= holidayViewModel.getHolidayRequests().getValue().size()) {
            navController.navigate(R.id.holidaysFragment);
        } else {
            HolidayRequest holidayRequest = holidayViewModel.getHolidayRequest(holidayIndex);

            // bind the values
            viewBinding.requestFromValue.setText(holidayRequest.getStartingDate().toString());
            viewBinding.requestToValue.setText(holidayRequest.getFinishingDate().toString());
            viewBinding.requestStatusValue.setText(holidayRequest.getStatus().toString());
            viewBinding.requestRequestedByValue.setText(holidayRequest.getRequestedBy());

            // handle approve (simply removes it from the static list)
            viewBinding.approveButton.setOnClickListener(button -> {
                holidayViewModel.removeHoliday(holidayIndex);
                navController.popBackStack();
            });

            // handle reject (simply removes it from the static list)
            viewBinding.rejectButton.setOnClickListener(button -> {
                holidayViewModel.removeHoliday(holidayIndex);
                navController.popBackStack();
            });
        }

        handleBackNavigation();
    }

    private void handleBackNavigation() {
        viewBinding.holidayRequestToolbar.setNavigationOnClickListener(button -> {
            navController.popBackStack();
        });
    }

}

