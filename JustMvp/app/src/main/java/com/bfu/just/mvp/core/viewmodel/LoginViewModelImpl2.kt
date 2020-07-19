package com.bfu.just.mvp.core.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.bfu.just.mvp.core.contract.LoginViewModel

/**
 * 类委托
 */
class LoginViewModelImpl2(application: Application) : AndroidViewModel(application),
    LoginViewModel by LoginViewModelImpl(application)