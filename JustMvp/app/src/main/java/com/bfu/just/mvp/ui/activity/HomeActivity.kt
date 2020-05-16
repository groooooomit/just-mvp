package com.bfu.just.mvp.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bfu.just.mvp.R
import com.bfu.just.mvp.ui.fragment.LoginFragment

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        if (null == supportFragmentManager.findFragmentByTag("LoginFragment")) {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.frame_container, LoginFragment(), "LoginFragment")
                .commit()
        }
    }

}