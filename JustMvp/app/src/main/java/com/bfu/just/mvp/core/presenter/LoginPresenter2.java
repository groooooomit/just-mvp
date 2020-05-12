package com.bfu.just.mvp.core.presenter;

import com.bfu.just.mvp.core.contract.LoginContract;

import org.jetbrains.annotations.Nullable;

import java.util.Objects;

import just.mvp.BasePresenter;

/**
 * {@link LoginPresenter} 的 ProxyPresenter 版本
 */
public class LoginPresenter2 extends BasePresenter<LoginContract.View> implements LoginContract.Presenter {

    @Override
    public void login(@Nullable String username, @Nullable String password) {
        // Optional.ofNullable(getView()).ifPresent(LoginContract.View::showLoginStart);
        Objects.requireNonNull(getView()).showLoginStart();

        // check username
        if (null == username || username.isEmpty()) {
            runOnUi(view -> {
                view.showLoginEnd();
                view.toast("用户名不能为空");
            });
            return;
        }

        // check password
        if (null == password || password.isEmpty()) {
            runOnUi(view -> {
                view.showLoginEnd();
                view.toast("密码不能为空");
            });
            return;
        }

        // 模拟耗时登录
        new Thread(() -> {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if ("user".equals(username) && "123456".equals(password)) {
                runOnUi(it -> {
                    it.showLoginEnd();
                    it.toastLong("登录成功");
                    it.goMainPage("abc123");
                });
            } else {
                runOnUi(it -> {
                    it.showLoginEnd();
                    it.toastLong("用户名或密码错误");
                });
            }
        }).start();
    }

}