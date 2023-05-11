package com.example.comp2000.fragments;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.comp2000.adapters.DividerItemDecorator;
import com.example.comp2000.databinding.FragmentHolidaysBinding;
import com.example.comp2000.models.HolidayRequest;
import com.example.comp2000.adapters.HolidayRequestsRecyclerViewAdapter;
import com.example.comp2000.view_models.HolidayViewModel;

import java.util.ArrayList;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class HolidaysFragment extends Fragment {

    public HolidaysFragment() {
        // Required empty public constructor
    }

    private FragmentHolidaysBinding viewBinding;
    private HolidayViewModel viewModel;
    private HolidayRequestsRecyclerViewAdapter holidayRequestsRecyclerViewAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(HolidayViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        viewBinding = FragmentHolidaysBinding.inflate(inflater, container, false);
        return viewBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        displayHolidays();
        setupAdapter();
    }

    private void displayHolidays() {
        viewModel.getHolidayRequests().observe(getViewLifecycleOwner(), holidayRequests ->  {
            holidayRequestsRecyclerViewAdapter.setHolidayList(holidayRequests);
        });
    }

    private void setupAdapter() {
        holidayRequestsRecyclerViewAdapter = new HolidayRequestsRecyclerViewAdapter(viewModel.getHolidayRequests().getValue());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());

        viewBinding.holidayRequests.setLayoutManager(layoutManager);
        viewBinding.holidayRequests.setItemAnimator(new DefaultItemAnimator());
        viewBinding.holidayRequests.setAdapter(holidayRequestsRecyclerViewAdapter);

        RecyclerView.ItemDecoration divider = new DividerItemDecorator(getContext());
        viewBinding.holidayRequests.addItemDecoration(divider);
    }

}