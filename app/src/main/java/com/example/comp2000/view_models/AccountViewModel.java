package com.example.comp2000.view_models;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.comp2000.repositories.IAccountRepository;
import com.example.comp2000.models.UserPreferences;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public final class AccountViewModel extends ViewModel {

    private final IAccountRepository accountRepository;

    @Inject
    public AccountViewModel(IAccountRepository IAccountRepository) {
        this.accountRepository = IAccountRepository;
    }

    public LiveData<UserPreferences> getUserCommunicationPreferences() {
        return accountRepository.getUserCommunicationPreferences();
    }

    public void changeReceiveNotifications(boolean bool) {
        accountRepository.receiveNotifications(bool);
    }

    public void changeReceiveHolidayNotifications(boolean bool) {
        accountRepository.receiveHolidayNotifications(bool);
    }

    public LiveData<Boolean> isLoggedIn() {
        return accountRepository.isLoggedIn();
    }

    public boolean logIn(String username, String password) {
        return accountRepository.logIn(username, password);
    }

    public void logOut() {
        accountRepository.logOut();
    }
}
