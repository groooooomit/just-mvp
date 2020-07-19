package com.bfu.just.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.bfu.just.mvp.core.contract.LoginViewModel;
import com.bfu.just.mvp.core.viewmodel.LoginViewModelImpl;
import com.bfu.just.mvp.databinding.ActivityLoginBinding;

import just.mvp.ext.livedata.EventObserver;

public class Login5Activity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /* Set content view. */
        final ActivityLoginBinding binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        /* Get viewModel. */
        final LoginViewModel loginViewModel = new ViewModelProvider(this).get(LoginViewModelImpl.class);

        /* Init View. */
        binding.login.setOnClickListener(v -> {
            final String usernameStr = binding.username.getText().toString().trim();
            final String passwordStr = binding.password.getText().toString().trim();
            loginViewModel.login(usernameStr, passwordStr);
        });

        /* Binding data to view. */
        loginViewModel.getInfo().observe(this, binding.txtInfo::setText);
        loginViewModel.getToast().observe(this, (EventObserver<String>) message -> Toast.makeText(Login5Activity.this, message, Toast.LENGTH_SHORT).show());
        loginViewModel.isLogining().observe(this, isLogining -> {
            binding.loading.setVisibility(isLogining ? View.VISIBLE : View.GONE);
            binding.username.setEnabled(!isLogining);
            binding.password.setEnabled(!isLogining);
            binding.login.setEnabled(!isLogining);
        });
        loginViewModel.getGoHomePage().observe(this, () -> {
            startActivity(new Intent(Login5Activity.this, HomeActivity.class));
            finish();
        });


    }

}
