package com.example.comp2000.models;

public class UserPreferences {
    
    private boolean receiveNotifications = true;
    private boolean receiveHolidayNotifications = true;

    public boolean willReceiveNotifications() {
        return receiveNotifications;
    }

    public void setReceiveNotifications(boolean receiveNotifications) {
        if (this.receiveNotifications != receiveNotifications) {
            this.receiveNotifications = receiveNotifications;
        }
    }

    public boolean willReceiveHolidayNotifications() {
        return receiveHolidayNotifications;
    }

    public void setReceiveHolidayNotifications(boolean receiveHolidayNotifications) {
        if (this.receiveHolidayNotifications != receiveHolidayNotifications) {
            this.receiveHolidayNotifications = receiveHolidayNotifications;
        }
    }
}
