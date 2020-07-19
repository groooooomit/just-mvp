package com.bfu.just.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.bfu.just.mvp.core.contract.LoginContract;
import com.bfu.just.mvp.core.presenter.LoginPresenter;
import com.bfu.just.mvp.databinding.ActivityLoginBinding;

import just.mvp.PresenterActivity;

/**
 * Activity 作为 View 的 java demo
 */
public class Login2Activity extends PresenterActivity<LoginPresenter> implements LoginContract.View {
    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.login.setOnClickListener(v -> {
            final String usernameStr = binding.username.getText().toString().trim();
            final String passwordStr = binding.password.getText().toString().trim();
            getPresenter().login(usernameStr, passwordStr);
        });
        getPresenter().getInfo().observe(this, binding.txtInfo::setText);
    }

    @Override
    public void showLoginStart() {
        binding.loading.setVisibility(View.VISIBLE);
        binding.username.setEnabled(false);
        binding.password.setEnabled(false);
        binding.login.setEnabled(false);
    }

    @Override
    public void showLoginEnd() {
        binding.loading.setVisibility(View.GONE);
        binding.username.setEnabled(true);
        binding.password.setEnabled(true);
        binding.login.setEnabled(true);
    }

    @Override
    public void goHomePage() {
        startActivity(new Intent(this, HomeActivity.class));
        finish();
    }
}