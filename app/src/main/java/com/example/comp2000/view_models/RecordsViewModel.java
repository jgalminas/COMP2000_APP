package com.example.comp2000.view_models;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import com.example.comp2000.enums.NetworkStatus;
import com.example.comp2000.models.Employee;
import com.example.comp2000.repositories.IEmployeeRepository;
import java.util.ArrayList;
import javax.inject.Inject;
import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public final class RecordsViewModel extends ViewModel {

    private final IEmployeeRepository employeeRepository;

    @Inject
    public RecordsViewModel(IEmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;

        employeeRepository.fetchEmployeeRecords();
        records = employeeRepository.getEmployeeRecords();
    }

    // employee records
    private final LiveData<ArrayList<Employee>> records;

    public LiveData<ArrayList<Employee>> getEmployeeRecords() {
        return records;
    }

    public Employee getEmployeeRecord(int id) {

        for (Employee e : records.getValue()) {
            if (e.getId() == id) {
                return e;
            }
        }

        return null;
    }

    public LiveData<NetworkStatus> addEmployeeRecord(Employee record) {
        return employeeRepository.createEmployee(record);
    }

    public LiveData<NetworkStatus> updateEmployeeRecord(Employee record) {
        return employeeRepository.updateEmployee(record);
    }

    public void deleteEmployeeRecord(Employee record) {
        employeeRepository.deleteEmployee(record);
    }

}
