package com.example.comp2000;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.lifecycle.ViewModelProvider;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.example.comp2000.databinding.ActivityLoginBinding;
import com.example.comp2000.view_models.AccountViewModel;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding viewBinding;
    private AccountViewModel accountViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        // view binding initialization
        viewBinding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(viewBinding.getRoot());

        // ViewModel initialization
        accountViewModel = new ViewModelProvider(this).get(AccountViewModel.class);

        handleLogIn();
    }

    private void handleLogIn() {

        viewBinding.logInButton.setOnClickListener(button -> {

            boolean result =  accountViewModel.logIn(
                    viewBinding.usernameInput.getText().toString(),
                    viewBinding.passwordInput.getText().toString()
            );

            if (result) {

                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);

                // terminate this activity to prevent back navigation
                finish();

            } else {
                viewBinding.errorMessage.setVisibility(View.VISIBLE);
            }

        });
    }
}