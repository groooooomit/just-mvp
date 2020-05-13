package com.bfu.just.mvp.core.presenter;

import com.bfu.just.mvp.core.contract.LoginContract;

import org.jetbrains.annotations.Nullable;

import just.mvp.ProxyViewPresenter;

/**
 * {@link LoginPresenter} 的 ProxyViewPresenter 版本
 */
public class LoginPresenter3 extends ProxyViewPresenter<LoginContract.View> implements LoginContract.Presenter {

    @Override
    public void login(@Nullable String username, @Nullable String password) {
        getView().showLoginStart();

        // check username
        if (null == username || username.isEmpty()) {
            getView().showLoginEnd();
            getView().toast("用户名不能为空");
            return;
        }

        // check password
        if (null == password || password.isEmpty()) {
            getView().showLoginEnd();
            getView().toast("密码不能为空");
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