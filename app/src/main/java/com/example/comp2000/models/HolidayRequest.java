package com.example.comp2000.models;

import com.example.comp2000.enums.HolidayStatus;
import java.time.LocalDate;

public class HolidayRequest {

    private final LocalDate startingDate;
    private final LocalDate finishingDate;
    private HolidayStatus status;
    private final String requestedBy;
    private final LocalDate requestDate;

    public HolidayRequest(LocalDate startingDate, LocalDate finishingDate, HolidayStatus status, LocalDate requestDate, String requestedBy) {
        this.status = status;
        this.startingDate = startingDate;
        this.finishingDate = finishingDate;
        this.requestedBy = requestedBy;
        this.requestDate = requestDate;
    }

    public String getRequestedBy() {
        return requestedBy;
    }

    public LocalDate getStartingDate() {
        return startingDate;
    }

    public LocalDate getFinishingDate() {
        return finishingDate;
    }

    public HolidayStatus getStatus() {
        return status;
    }

    public void setStatus(HolidayStatus status) {
        this.status = status;
    }

    public LocalDate getRequestDate() {
        return requestDate;
    }

}
