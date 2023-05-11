package com.example.comp2000.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.comp2000.enums.NetworkStatus;
import com.example.comp2000.models.Employee;
import com.example.comp2000.network.RetrofitClient;
import com.example.comp2000.network.EmployeeService;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmployeeRepository implements IEmployeeRepository {

    private final EmployeeService api;
    private final MutableLiveData<ArrayList<Employee>> records = new MutableLiveData<>(new ArrayList<>());


    public EmployeeRepository() {
        api = RetrofitClient.getInstance().getEmployeeService();
    }

    @Override
    public LiveData<ArrayList<Employee>> getEmployeeRecords() {
        return records;
    }

    @Override
    public void fetchEmployeeRecords() {
        api.getEmployees().enqueue(new Callback<ArrayList<Employee>>() {
            @Override
            public void onResponse(Call<ArrayList<Employee>> call, Response<ArrayList<Employee>> response) {
                if (response.isSuccessful()) {
                    records.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Employee>> call, Throwable t) {

            }
        });
    }

    @Override
    public LiveData<NetworkStatus> createEmployee(Employee record) {

        // initialize livedata and set status to loading
        MutableLiveData<NetworkStatus> status = new MutableLiveData<>(NetworkStatus.LOADING);

        api.addEmployee(record).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {

                    records.getValue().add(record); // add to array list
                    status.setValue(NetworkStatus.SUCCESS); // status is updated

                } else if (response.code() == 400) {
                    status.setValue(NetworkStatus.ALREADY_EXISTS);
                } else if (response.code() == 404) {
                    status.setValue(NetworkStatus.NOT_FOUND);
                } else {
                    status.setValue(NetworkStatus.ERROR);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                status.setValue(NetworkStatus.ERROR); // set status to error
            }
        });

        return status;
    }

    @Override
    public LiveData<NetworkStatus> updateEmployee(Employee record) {
        // initialize livedata and set status to loading
        MutableLiveData<NetworkStatus> status = new MutableLiveData<>(NetworkStatus.LOADING);

        api.updateEmployee(record.getId(), record).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

                if (response.isSuccessful()) {


                    for (Employee e : records.getValue()) {
                        if (e.getId() == record.getId()) {
                            int index = records.getValue().indexOf(e);
                            records.getValue().set(index, record);
                            status.setValue(NetworkStatus.SUCCESS); // status is updated
                        }
                    }

                } else if (response.code() == 400) {
                    status.setValue(NetworkStatus.ALREADY_EXISTS);
                } else if (response.code() == 404) {
                    status.setValue(NetworkStatus.NOT_FOUND);
                } else {
                    status.setValue(NetworkStatus.ERROR);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                status.setValue(NetworkStatus.ERROR); // set status to error
            }
        });

        return status;
    }

    @Override
    public void deleteEmployee(Employee record) {

        try {

            api.deleteEmployee(record.getId()).enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {

                    ArrayList<Employee> newRecords = new ArrayList<>(records.getValue());
                    newRecords.removeIf(r -> r.getId() == record.getId());
                    records.setValue(newRecords);

                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) { }
            });
        } catch (NullPointerException ignored) {  }

    }

}
