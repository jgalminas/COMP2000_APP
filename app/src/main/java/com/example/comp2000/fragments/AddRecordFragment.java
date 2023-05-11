package com.example.comp2000.fragments;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDeepLinkBuilder;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.comp2000.MainActivity;
import com.example.comp2000.R;
import com.example.comp2000.databinding.FragmentAddRecordBinding;
import com.example.comp2000.enums.HolidayStatus;
import com.example.comp2000.enums.NetworkStatus;
import com.example.comp2000.models.Employee;
import com.example.comp2000.models.HolidayRequest;
import com.example.comp2000.models.UserPreferences;
import com.example.comp2000.view_models.AccountViewModel;
import com.example.comp2000.view_models.HolidayViewModel;
import com.example.comp2000.view_models.RecordsViewModel;

import java.time.LocalDate;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class AddRecordFragment extends Fragment {

    private FragmentAddRecordBinding viewBinding;
    private NavController navController;
    private RecordsViewModel recordsViewModel;
    private AccountViewModel accountViewModel;
    private HolidayViewModel holidayViewModel;

    private boolean isEditing;
    private int employeeIndex;

    public AddRecordFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        recordsViewModel = new ViewModelProvider(this).get(RecordsViewModel.class);
        accountViewModel = new ViewModelProvider(this).get(AccountViewModel.class);
        holidayViewModel = new ViewModelProvider(this).get(HolidayViewModel.class);

        // argument passed from previous fragment
        isEditing = AddRecordFragmentArgs.fromBundle(getArguments()).getIsEditing();
        employeeIndex = AddRecordFragmentArgs.fromBundle(getArguments()).getEmployeeIndex();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        viewBinding = FragmentAddRecordBinding.inflate(inflater, container, false);
        return viewBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);

        setActionBarTitle(); // change title bar if in edit mode
        isInEditMode(); // pre-populate fields if in editing mode

        handleSubmit();
        handleBackNavigation();
    }

    private void handleBackNavigation() {

        viewBinding.addRecordCancelBtn.setOnClickListener(button -> {
            navController.popBackStack();
        });

        viewBinding.addRecordToolbar.setNavigationOnClickListener(toolbar -> {
            navController.popBackStack();
        });
    }

    private void handleSubmit() {

        viewBinding.addRecordSubmitBtn.setOnClickListener(button -> {
            // validate the input
            if (TextUtils.isEmpty(viewBinding.addRecordIdValue.getText())
                    || TextUtils.isEmpty(viewBinding.addRecordNameValue.getText())
                    || TextUtils.isEmpty(viewBinding.addRecordSurnameValue.getText())) {

                toggleErrorMessage(true, R.string.required_fields);

            } else {

                int id = Integer.parseInt(viewBinding.addRecordIdValue.getText().toString());
                String surname = viewBinding.addRecordSurnameValue.getText().toString();
                String forename = viewBinding.addRecordNameValue.getText().toString();

                Employee record = new Employee(id, forename, surname);

                if (isEditing) {
                    recordsViewModel.updateEmployeeRecord(record).observe(getViewLifecycleOwner(), status -> {
                        handleResult(true, record, status);
                    });
                } else {
                    recordsViewModel.addEmployeeRecord(record).observe(getViewLifecycleOwner(), status -> {
                        handleResult(false, record, status);
                    });
                }
            }
        });

    }

    private void handleResult(boolean update, Employee record, NetworkStatus status) {
        switch (status) {
            case LOADING:
                toggleSubmitState(true);
                toggleErrorMessage(false, R.string.empty);
                break;
            case SUCCESS:
                // simulate notification when a new employee record is added
                if (!update) {
                    simulateNotification(record);
                }
                navController.popBackStack();
                break;
            case ALREADY_EXISTS:
                toggleSubmitState(false);
                toggleErrorMessage(true, R.string.already_exists);
                break;
            case ERROR:
                toggleSubmitState(false);
                toggleErrorMessage(true, R.string.server_error);
                break;
        }
    }

    private void toggleErrorMessage(boolean visible, int message) {
        if (visible) {
            viewBinding.addRecordErrorMessage.setVisibility(View.VISIBLE);
            viewBinding.addRecordErrorMessage.setText(message);
        } else {
            viewBinding.addRecordErrorMessage.setVisibility(View.GONE);
            viewBinding.addRecordErrorMessage.setText(message);
        }
    }

    private void toggleSubmitState(boolean loading) {
        if (loading) {
            viewBinding.addRecordSubmitBtn.setEnabled(false);
            viewBinding.addRecordSubmitBtn.setText(R.string.loading);
        } else {
            viewBinding.addRecordSubmitBtn.setEnabled(true);
            viewBinding.addRecordSubmitBtn.setText(R.string.submit);
        }
    }

    private void setActionBarTitle() {
        if (isEditing) {
            viewBinding.addRecordToolbarTitle.setText(R.string.edit_record);
        }
    }

    private void isInEditMode() {
        if (isEditing) {
            Employee record = recordsViewModel.getEmployeeRecord(employeeIndex);
            viewBinding.addRecordIdValue.setText(String.valueOf(record.getId()));
            viewBinding.addRecordNameValue.setText(record.getName());
            viewBinding.addRecordSurnameValue.setText(record.getSurname());

            // disable ID edit field
            viewBinding.addRecordIdValue.setEnabled(false);
            viewBinding.addRecordIdValue.setTextColor(requireContext().getColor(R.color.gray_400));
            viewBinding.addRecordIdValue.setTypeface(viewBinding.addRecordIdValue.getTypeface(), Typeface.ITALIC);
        }
    }

    // notification simulation
    private void simulateNotification(Employee record) {

        UserPreferences preferences = accountViewModel.getUserCommunicationPreferences().getValue();

        if (preferences.willReceiveNotifications() && preferences.willReceiveHolidayNotifications()) {

            NotificationManager notificationManager = (NotificationManager) requireActivity().getSystemService(Context.NOTIFICATION_SERVICE);

            Thread thread = new Thread() {
                @Override
                public void run() {

                    try {
                        Thread.sleep(5000);

                        // create new holiday request
                        LocalDate from = LocalDate.now().plusDays(7);
                        LocalDate to = from.plusDays((int)(Math.random() * 7));
                        String name = record.getName() + " " + record.getSurname();

                        // create new record
                        int index = holidayViewModel.addHolidayRequest(new HolidayRequest(
                                from,
                                to,
                                HolidayStatus.TO_BE_REVIEWED,
                                LocalDate.now(),
                                name
                        ));

                        // create bundle with arguments
                        Bundle args = new Bundle();
                        args.putInt("holidayIndex", index);

                        // create intent for the notification
                        PendingIntent intent = new NavDeepLinkBuilder(requireContext())
                                .setGraph(R.navigation.navigation_graph)
                                .setDestination(R.id.holidayRequestFragment)
                                .setArguments(args)
                                .setComponentName(MainActivity.class)
                                .createPendingIntent();

                        // build notification
                        Notification.Builder notificationBuilder = null;
                        notificationBuilder = new Notification.Builder(getContext(), "new_request_channel_0");

                        notificationBuilder
                                .setSmallIcon(R.drawable.ic_calendar)
                                .setContentTitle("New Holiday Request")
                                .setContentText(name + " has requested a holiday.")
                                .setAutoCancel(true)
                                .setContentIntent(intent);

                        // create channel
                        NotificationChannel channel = null;
                        channel = new NotificationChannel("new_request_channel_0", "new_request", NotificationManager.IMPORTANCE_DEFAULT);
                        notificationManager.createNotificationChannel(channel);

                        //send notification
                        notificationManager.notify((int) System.currentTimeMillis() / 1000, notificationBuilder.build());

                    } catch (InterruptedException ignored) {
                    }

                    super.run();
                }
            };

            thread.start();
        }

    }

}