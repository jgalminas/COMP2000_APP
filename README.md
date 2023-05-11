# Employee Management Application

https://github.com/jgalminas/COMP2000_APP/assets/63853949/89f7874f-b95b-455f-807c-678095f61b9a

## Background

A native Android application that provides administration staff within companies an easy and portable way of viewing and managing employee data, this
includes viewing, editing, creating, and deleting employee records as well as granting, rejecting, and reviewing holiday requests.

## Architecture

The application was built using the Model-View-ViewModel (MVVM) structural design pattern. This allows for the application logic to be separated into different layers, making it more robust, de-coupled and easier to develop.

## Structure

### Model
The model layer is reponsible for the business logic and contains the data classes, data sources and repositories.

- Data Classes: Classes representing the data
- Data Sources: Network and Database sources used within the application
- Repositories: Classes containing the data and all of the communication data source communication logic.

### ViewModel
ViewModels are reponsible for communicating with repositories and adapting and holding UI state for a given view.

### View
Classes which contain the interface logic.

- Fragments
- Activities

### Additional design patterns used
- Observer
- Singleton
- Builder
- Dependency Injection

## Tools & Technologies

### The application is built using
- Java
- Android SDK

### Libraries & Frameworks used
- [Retrofit](https://square.github.io/retrofit/) - Network communication library
- [Dagger Hilt](https://dagger.dev/hilt/) - Dependency Injection library

## Resources Used

- [Feather Icons](https://feathericons.com/) - Open source icons which I used within the application.
- [Official Android Docummentation](https://developer.android.com/docs)
- [Stack Overflow](https://stackoverflow.com/)
