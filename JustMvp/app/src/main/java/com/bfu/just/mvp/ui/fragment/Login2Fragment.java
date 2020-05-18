package com.bfu.just.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bfu.just.mvp.R;
import com.bfu.just.mvp.core.contract.LoginContract;
import com.bfu.just.mvp.core.presenter.LoginPresenter;
import com.bfu.just.mvp.ui.activity.HomeActivity;

import just.mvp.PresenterFragment;
import just.mvp.widget.LayoutResId;

@LayoutResId(R.layout.fragment_login)
public class Login2Fragment extends PresenterFragment<LoginPresenter> implements LoginContract.View {
    private Button btLogin;
    private ProgressBar barLoading;
    private TextView txtUserName;
    private TextView txtPassword;


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        btLogin = view.findViewById(R.id.login);
        barLoading = view.findViewById(R.id.loading);
        txtUserName = view.findViewById(R.id.username);
        txtPassword = view.findViewById(R.id.password);

        btLogin.setOnClickListener(v -> {
            final String usernameStr = txtUserName.getText().toString().trim();
            final String passwordStr = txtPassword.getText().toString().trim();
            getPresenter().login(usernameStr, passwordStr);
        });
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
        startActivity(new Intent(this.getActivity(), HomeActivity.class));
        this.getActivity().finish();
    }
}