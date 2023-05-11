package com.example.comp2000.network;

import com.example.comp2000.models.Employee;

import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface EmployeeService {

    @POST("employees")
    Call<Void> addEmployee(@Body Employee employee);

    @GET("employees")
    Call<ArrayList<Employee>> getEmployees();

    @PUT("employees/{id}")
    Call<Void> updateEmployee(@Path("id") int id, @Body Employee employee);

    @DELETE("employees/{id}")
    Call<Void> deleteEmployee(@Path("id") int id);

}
