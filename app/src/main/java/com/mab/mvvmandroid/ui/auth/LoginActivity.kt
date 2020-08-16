package com.mab.mvvmandroid.ui.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.mab.mvvmandroid.R
import com.mab.mvvmandroid.databinding.ActivityLoginBinding
import com.mab.mvvmandroid.extensions.context.showShortToast
import com.mab.mvvmandroid.extensions.view.makeHidden
import com.mab.mvvmandroid.extensions.view.makeVisible
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(), AuthListener {

    private val authViewModel by lazy {
        ViewModelProviders.of(this).get(AuthViewModel::class.java).apply {
            authListener = this@LoginActivity
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityLoginBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_login)
        binding.authViewModel = authViewModel
    }

    override fun onStarted() {
        progress_bar.makeVisible()
        showShortToast("onStarted")
    }

    override fun onSuccess(message: String) {
        progress_bar.makeHidden()
        showShortToast(message)
    }

    override fun onFailed(message: String) {
        progress_bar.makeHidden()
        showShortToast(message)
    }
}