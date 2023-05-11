package com.example.comp2000.view_models;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.comp2000.enums.HolidayStatus;
import com.example.comp2000.models.Employee;
import com.example.comp2000.repositories.IAccountRepository;
import com.example.comp2000.repositories.IHolidayRepository;
import com.example.comp2000.models.HolidayRequest;

import java.util.ArrayList;
import java.util.Locale;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public final class HolidayViewModel extends ViewModel {

    private final IHolidayRepository holidayRepository;

    @Inject
    public HolidayViewModel(IHolidayRepository holidayRepository) {

        this.holidayRepository = holidayRepository;
    }

    public LiveData<ArrayList<HolidayRequest>> getHolidayRequests() {
        return holidayRepository.getHolidayRequests();
    }

    public HolidayRequest getHolidayRequest(int index) {
        return holidayRepository.getHolidayRequests().getValue().get(index);
    }

    public int addHolidayRequest(HolidayRequest request) {
        return holidayRepository.addHolidayRequest(request);
    }

    public void removeHoliday(int index) {
        holidayRepository.removeHolidayRequest(index);
    }
}
