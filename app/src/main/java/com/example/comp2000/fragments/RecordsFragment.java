package com.example.comp2000.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.comp2000.R;
import com.example.comp2000.adapters.EmployeeRecordsRecyclerViewAdapter;
import com.example.comp2000.databinding.FragmentRecordsBinding;
import com.example.comp2000.models.Employee;
import com.example.comp2000.view_models.RecordsViewModel;

import java.util.ArrayList;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicReference;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class RecordsFragment extends Fragment {

    public RecordsFragment() {
        // Required empty public constructor
    }

    private FragmentRecordsBinding viewBinding;
    private NavController navController;
    private EmployeeRecordsRecyclerViewAdapter employeeRecordsRecyclerViewAdapter;

    private RecordsViewModel recordsViewModel;

    private ArrayList<Employee> employees = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        recordsViewModel = new ViewModelProvider(this).get(RecordsViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        viewBinding = FragmentRecordsBinding.inflate(inflater, container, false);
        return viewBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);

        viewBinding.addRecordButton.setOnClickListener(button -> {
            navController.navigate(R.id.action_addRecord);
        });

        // listen to search input changes
        viewBinding.searchInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                employeeRecordsRecyclerViewAdapter.setRecordsList(filterEmployees(editable.toString(), employees));

            }
        });

        //observe employee record
        recordsViewModel.getEmployeeRecords().observe(getViewLifecycleOwner(), records -> {
            employees = records;
            employeeRecordsRecyclerViewAdapter.setRecordsList(filterEmployees(viewBinding.searchInput.getText().toString(), records));
        });

        initializeRecyclerView();

    }

    private ArrayList<Employee> filterEmployees(String query, ArrayList<Employee> list) {

        ArrayList<Employee> filtered = new ArrayList<>(list);

        filtered.removeIf(record -> {
            String name = record.getName() + " " + record.getSurname();
            return !name.toLowerCase(Locale.ROOT).contains(query.toLowerCase(Locale.ROOT).trim());
        });


        return filtered;
    }

    private void initializeRecyclerView() {
        viewBinding.employeeRecords.setLayoutManager(new LinearLayoutManager(getContext()));
        employeeRecordsRecyclerViewAdapter = new EmployeeRecordsRecyclerViewAdapter(employees);
        viewBinding.employeeRecords.setAdapter(employeeRecordsRecyclerViewAdapter);
    }

}