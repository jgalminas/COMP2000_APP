package com.example.comp2000.repositories;


import androidx.lifecycle.LiveData;

import com.example.comp2000.enums.NetworkStatus;
import com.example.comp2000.models.Employee;
import java.util.ArrayList;

public interface IEmployeeRepository {

    LiveData<ArrayList<Employee>> getEmployeeRecords();
    void fetchEmployeeRecords();
    LiveData<NetworkStatus> createEmployee(Employee record);
    LiveData<NetworkStatus> updateEmployee(Employee record);
    void deleteEmployee(Employee record);

}
