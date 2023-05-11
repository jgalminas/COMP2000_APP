package com.example.comp2000.repositories;

import androidx.lifecycle.LiveData;

import com.example.comp2000.models.UserPreferences;

public interface IAccountRepository {

    LiveData<UserPreferences> getUserCommunicationPreferences();
    LiveData<Boolean> isLoggedIn();
    boolean logIn(String username, String password);
    void logOut();
    void receiveNotifications(boolean bool);
    void receiveHolidayNotifications(boolean bool);
}
