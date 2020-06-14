package com.bfu.just.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.bfu.just.mvp.R;
import com.bfu.just.mvp.core.contract.LoginContract;
import com.bfu.just.mvp.core.presenter.LoginPresenter;

import just.mvp.PresenterActivity;

/**
 * Activity 作为 View 的 java demo
 */
public class Login2Activity extends PresenterActivity<LoginPresenter> implements LoginContract.View {
    private Button btLogin;
    private ProgressBar barLoading;
    private TextView txtUserName;
    private TextView txtPassword;
    private TextView txtInfo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btLogin = findViewById(R.id.login);
        barLoading = findViewById(R.id.loading);
        txtUserName = findViewById(R.id.username);
        txtPassword = findViewById(R.id.password);
        txtInfo = findViewById(R.id.txt_info);

        btLogin.setOnClickListener(v -> {
            final String usernameStr = txtUserName.getText().toString().trim();
            final String passwordStr = txtPassword.getText().toString().trim();
            getPresenter().login(usernameStr, passwordStr);
        });
    }

    @Override
    public void showInfo(@Nullable String info) {
        txtInfo.setText(info);
    }

    @Override
    public void showLoginStart() {
        barLoading.setVisibility(View.VISIBLE);
        txtUserName.setEnabled(false);
        txtPassword.setEnabled(false);
        btLogin.setEnabled(false);
    }

    @Override
    public void showLoginEnd() {
        barLoading.setVisibility(View.GONE);
        txtUserName.setEnabled(true);
        txtPassword.setEnabled(true);
        btLogin.setEnabled(true);
    }

    @Override
    public void goHomePage() {
        startActivity(new Intent(this, HomeActivity.class));
        finish();
    }
}