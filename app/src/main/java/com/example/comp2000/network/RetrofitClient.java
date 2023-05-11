package com.example.comp2000.network;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static RetrofitClient instance;
    private final EmployeeService employeeService;

    public RetrofitClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://web.socem.plymouth.ac.uk/COMP2000/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(new OkHttpClient())
                .build();

        employeeService = retrofit.create(EmployeeService.class);
    }

    public static RetrofitClient getInstance() {
        if (instance == null) {
            instance = new RetrofitClient();
        }

        return instance;
    }

    public EmployeeService getEmployeeService() {
        return employeeService;
    }

}
