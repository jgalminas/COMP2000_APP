package com.example.comp2000.repositories;

import androidx.lifecycle.LiveData;
import com.example.comp2000.models.HolidayRequest;
import java.util.ArrayList;

public interface IHolidayRepository {

    LiveData<ArrayList<HolidayRequest>> getHolidayRequests();
    int addHolidayRequest(HolidayRequest request);
    void removeHolidayRequest(int index);
}
