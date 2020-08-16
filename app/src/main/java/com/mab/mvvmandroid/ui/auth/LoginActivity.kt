package com.mab.mvvmandroid.ui.auth

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.mab.mvvmandroid.R
import com.mab.mvvmandroid.data.db.AppDatabase
import com.mab.mvvmandroid.data.db.entities.User
import com.mab.mvvmandroid.data.network.ApiClient
import com.mab.mvvmandroid.data.network.NetworkConnectionInterceptor
import com.mab.mvvmandroid.data.repositories.UserRepository
import com.mab.mvvmandroid.databinding.ActivityLoginBinding
import com.mab.mvvmandroid.extensions.context.showShortToast
import com.mab.mvvmandroid.extensions.view.makeHidden
import com.mab.mvvmandroid.extensions.view.makeVisible
import com.mab.mvvmandroid.ui.MainActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(), AuthListener {

    private val networkConnectionInterceptor by lazy {
        NetworkConnectionInterceptor(this)
    }

    private val apiManager by lazy {
        ApiClient(networkConnectionInterceptor)
    }

    private val db by lazy {
        AppDatabase(this)
    }

    private val userRepository by lazy {
        UserRepository(apiManager, db)
    }

    private val authViewModel by lazy {
        val factory = AuthViewModelFactory(userRepository)
        ViewModelProviders.of(this, factory).get(AuthViewModel::class.java).apply {
            authListener = this@LoginActivity
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityLoginBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_login)
        binding.authViewModel = authViewModel

        authViewModel.getLoggedInUser().observe(this, Observer { user ->
            if (user != null) {
                Intent(this, MainActivity::class.java).also {
                    it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(it)
                }
            }
        })
    }

    override fun onStarted() {
        progress_bar.makeVisible()
        showShortToast("onStarted")
    }

    override fun onSuccess(user: User) {
        progress_bar.makeHidden()
//        showShortToast("${user.name} is Logged In")
    }

    override fun onFailed(message: String) {
        progress_bar.makeHidden()
        showShortToast(message)
    }
}