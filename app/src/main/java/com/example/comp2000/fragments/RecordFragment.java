package com.example.comp2000.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import android.widget.Button;
import android.widget.Toast;

import com.example.comp2000.R;
import com.example.comp2000.databinding.FragmentRecordBinding;
import com.example.comp2000.models.Employee;
import com.example.comp2000.view_models.RecordsViewModel;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class RecordFragment extends Fragment {

    public RecordFragment() {
        // Required empty public constructor
    }

    private FragmentRecordBinding viewBinding;
    private NavController navController;
    private RecordsViewModel recordsViewModel;

    private int employeeIndex;
    Employee employee;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        recordsViewModel = new ViewModelProvider(this).get(RecordsViewModel.class);

        // argument passed from previous fragment
        employeeIndex = RecordFragmentArgs.fromBundle(getArguments()).getEmployeeIndex();

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        viewBinding = FragmentRecordBinding.inflate(inflater, container, false);
        return viewBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);

        employee = recordsViewModel.getEmployeeRecord(employeeIndex);
        viewBinding.recordIdValue.setText(String.valueOf(employee.getId()));
        viewBinding.recordForenameValue.setText(employee.getName());
        viewBinding.recordSurnameValue.setText(employee.getSurname());

        viewBinding.editRecordButton.setOnClickListener(button -> {
            editEmployeeRecord();
        });

        viewBinding.deleteRecordButton.setOnClickListener(button -> {
            onDelete();
        });

        handleBackNavigation();
    }

    private void editEmployeeRecord() {

        // creating action with argument
        RecordFragmentDirections.ActionEditRecord action = RecordFragmentDirections.actionEditRecord();
        action.setIsEditing(true);
        action.setEmployeeIndex(employeeIndex);

        navController.navigate(action);
    }

    private void handleBackNavigation() {
        viewBinding.recordToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.popBackStack();
            }
        });
    }

    private void onDelete() {

        // build alert dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        builder.setMessage(R.string.delete_confirmation);
        builder.setTitle(R.string.delete_record);

        builder.setPositiveButton(R.string.confirm, (dialog, i) -> {
            recordsViewModel.deleteEmployeeRecord(employee);
            navController.popBackStack();
        });

        builder.setNegativeButton(R.string.cancel, (dialog, i) -> {

        });

        // create dialog
        AlertDialog dialog = builder.create();

        // change button colours
        dialog.setOnShowListener(alert -> {

            dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(requireContext().getColor(R.color.gray_600));
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(requireContext().getColor(R.color.red_600));
        });

        // show
        dialog.show();

    }
}