package com.example.comp2000.di;


import com.example.comp2000.repositories.AccountRepository;
import com.example.comp2000.repositories.EmployeeRepository;
import com.example.comp2000.repositories.IAccountRepository;
import com.example.comp2000.repositories.IEmployeeRepository;
import com.example.comp2000.repositories.IHolidayRepository;
import com.example.comp2000.repositories.HolidayRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
final class AppModule {

    @Provides
    @Singleton
    public static IHolidayRepository provideHolidayRepository() {
        return new HolidayRepository();
    }

    @Provides
    @Singleton
    public static IAccountRepository provideAccountRepository() {
        return new AccountRepository();
    }

    @Provides
    @Singleton
    public static IEmployeeRepository provideEmployeeRepository() {
        return new EmployeeRepository();
    }

}
