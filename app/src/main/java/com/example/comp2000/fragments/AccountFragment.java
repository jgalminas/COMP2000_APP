package com.example.comp2000.fragments;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.comp2000.LoginActivity;
import com.example.comp2000.databinding.FragmentAccountBinding;
import com.example.comp2000.view_models.AccountViewModel;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class AccountFragment extends Fragment {

    public AccountFragment() {
        // Required empty public constructor
    }

    private FragmentAccountBinding viewBinding;
    private AccountViewModel accountViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        accountViewModel = new ViewModelProvider(this).get(AccountViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        viewBinding = FragmentAccountBinding.inflate(inflater, container, false);
        return viewBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        subscribeToPreferences();
        handleNotificationChanges();
        handleLogOut();
    }

    private void subscribeToPreferences() {
        // subscribe to user preferences data
        accountViewModel.getUserCommunicationPreferences().observe(getViewLifecycleOwner(), userPreferences ->  {
            viewBinding.receiveNotificationsCheckBox.setChecked(userPreferences.willReceiveNotifications());
            viewBinding.receiveHolidayNotificationsCheckBox.setChecked(userPreferences.willReceiveHolidayNotifications());
        });
    }

    private void handleNotificationChanges() {
        // update all notifications preferences on check box tick
        viewBinding.receiveNotificationsCheckBox.setOnCheckedChangeListener((button, bool) -> {
            accountViewModel.changeReceiveNotifications(bool);
        });

        // update holiday notification preferences on check box tick
        viewBinding.receiveHolidayNotificationsCheckBox.setOnCheckedChangeListener((button, bool) -> {
            accountViewModel.changeReceiveHolidayNotifications(bool);
        });
    }

    private void handleLogOut() {
        viewBinding.logOutButton.setOnClickListener(button -> {
            accountViewModel.logOut();
        });
    }
}