package com.example.comp2000.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.comp2000.models.UserPreferences;
import java.util.Objects;

public class AccountRepository implements IAccountRepository {

    // data in this repository is static for demonstration purposes, in a real application
    // it would be retrieved from either an API or SharedPreferences depending on the preferred
    // implementation

    private final MutableLiveData<UserPreferences> preferences = new MutableLiveData<>(new UserPreferences());
    private final MutableLiveData<Boolean> isLoggedIn = new MutableLiveData<>(false);

    @Override
    public LiveData<UserPreferences> getUserCommunicationPreferences() {
        return preferences;
    }

    @Override
    public LiveData<Boolean> isLoggedIn() {
        return isLoggedIn;
    }

    @Override
    public boolean logIn(String username, String password) {

        //static data for demonstration purposes
        String correctUsername = "username";
        String correctPassword = "password";

        if (Objects.equals(username.trim(), correctUsername) && Objects.equals(password.trim(), correctPassword)) {
            isLoggedIn.setValue(true);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void logOut() {
        isLoggedIn.setValue(false);
    }

    @Override
    public void receiveNotifications(boolean bool) {
        preferences.getValue().setReceiveNotifications(bool);
    }

    @Override
    public void receiveHolidayNotifications(boolean bool) {
        preferences.getValue().setReceiveHolidayNotifications(bool);
    }

}
