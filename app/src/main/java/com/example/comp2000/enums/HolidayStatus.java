package com.example.comp2000.enums;

import androidx.annotation.NonNull;

public enum HolidayStatus {
    TO_BE_REVIEWED("Waiting for review");

    private final String text;

    HolidayStatus(final String text) {
        this.text = text;
    }

    @NonNull
    @Override
    public String toString() {
        return text;
    }
}
