package com.example.comp2000.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.comp2000.enums.HolidayStatus;
import com.example.comp2000.models.HolidayRequest;
import java.time.LocalDate;
import java.util.ArrayList;

public class HolidayRepository implements IHolidayRepository {

    public HolidayRepository() {

        ArrayList<HolidayRequest> holidayRequests = new ArrayList<>();

        holidayRequests.add(new HolidayRequest(LocalDate.of(2022,10,14), LocalDate.of(2022, 10, 15), HolidayStatus.TO_BE_REVIEWED, LocalDate.of(2022, 2, 1), "Jane Doe"));
        holidayRequests.add(new HolidayRequest(LocalDate.of(2022,10,17), LocalDate.of(2022, 10, 23), HolidayStatus.TO_BE_REVIEWED, LocalDate.of(2022, 3, 12), "Alex Jones"));
        holidayRequests.add(new HolidayRequest(LocalDate.of(2022,10,20), LocalDate.of(2022, 10, 24), HolidayStatus.TO_BE_REVIEWED, LocalDate.of(2022, 5, 4), "Sally Allen"));
        holidayRequests.add(new HolidayRequest(LocalDate.of(2022,10,15), LocalDate.of(2022, 10, 28), HolidayStatus.TO_BE_REVIEWED, LocalDate.of(2022, 2, 20), "Fred Smith"));

        requests = new MutableLiveData<>(holidayRequests);
    }

    private final MutableLiveData<ArrayList<HolidayRequest>> requests;

    @Override
    public LiveData<ArrayList<HolidayRequest>> getHolidayRequests() {
        return requests;
    }

    @Override
    public int addHolidayRequest(HolidayRequest request) {

        requests.getValue().add(request);
        return requests.getValue().size() - 1;
    }

    @Override
    public void removeHolidayRequest(int index) {
        requests.getValue().remove(index);
    }

}
