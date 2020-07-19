package com.bfu.just.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bfu.just.mvp.core.contract.LoginContract;
import com.bfu.just.mvp.core.presenter.LoginPresenter;
import com.bfu.just.mvp.databinding.ActivityLoginBinding;

import just.mvp.base.Presenters;

/**
 * Activity 作为 View 的非继承 java demo
 * <p>
 */
public class Login3Activity extends AppCompatActivity implements LoginContract.View {
    /* 1. 声明 Presenter. */
    private LoginContract.Presenter presenter;
    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        /* 2. View 和 Presenter 绑定. */
        presenter = Presenters.bind(this, LoginPresenter.class);

        presenter.getInfo().observe(this, info -> binding.txtInfo.setText(info));

        binding.login.setOnClickListener(v -> {
            final String usernameStr = binding.username.getText().toString().trim();
            final String passwordStr = binding.password.getText().toString().trim();

            /* 3. Presenter 执行业务逻辑. */
            presenter.login(usernameStr, passwordStr);
        });
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