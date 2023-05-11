package com.example.comp2000;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;

import com.example.comp2000.view_models.AccountViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // disable dark theme
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        // setup bottom navigation
        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav);
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.navigationHostFragment);
        NavController navController = navHostFragment.getNavController();
        NavigationUI.setupWithNavController(bottomNav, navController);

        observeLoginStatus();
    }

    private void observeLoginStatus() {
        AccountViewModel accountViewModel = new ViewModelProvider(this).get(AccountViewModel.class);

        accountViewModel.isLoggedIn().observe(this, bool -> {
            if (!bool) {
                // start login activity
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);

                // terminate this activity to prevent back navigation
                finish();
            }
        });
    }

}