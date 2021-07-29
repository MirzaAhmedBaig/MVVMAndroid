package com.mab.mvvmandroid.ui.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.*
import com.mab.mvvmandroid.R
import com.mab.mvvmandroid.databinding.ActivityLoginBinding
import com.mab.mvvmandroid.ui.MainActivity
import com.mab.mvvmandroid.utils.ApiExceptions
import com.mab.mvvmandroid.utils.NoInternetException
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.generic.instance

class LoginActivity : AppCompatActivity(), KodeinAware {

    override val kodein by kodein()
    private val factory: AuthViewModelFactory by instance()

    private lateinit var binding: ActivityLoginBinding

    private val viewModel by lazy {
        ViewModelProvider(this, factory).get(AuthViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)

        viewModel.getLoggedInUser().observe(this, Observer { user ->
            if (user != null) {
                Intent(this, MainActivity::class.java).also {
                    it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(it)
                }
            }
        })

        setListeners()
    }

    private fun setListeners() {
        binding.loginButton.setOnClickListener {
            loginUser()
        }
    }

    private fun loginUser() {
        val email = binding.inputEmail.text.toString()
        val password = binding.inputPassword.text.toString()
        if (email.isEmpty() || password.isEmpty()) {
            showShortToast("Invalid inputs")
            return
        }
        binding.progressBar.makeVisible()
        lifecycleScope.launch {
            try {
                Log.d("LoginActivity", "Email : $email Password $password")
                val authResponse = viewModel.loginUser(email, password)
                binding.progressBar.makeHidden()
                if (authResponse.user != null) {
                    viewModel.saveLoggedInUser(authResponse.user!!)
                    showShortToast(authResponse.user!!.name)
                } else {
                    showShortToast(authResponse.message!!)
                }
            } catch (e: ApiExceptions) {
                e.printStackTrace()
                binding.progressBar.makeHidden()
                showShortToast(e.message!!)
            } catch (e: NoInternetException) {
                e.printStackTrace()
                binding.progressBar.makeHidden()
                showShortToast(e.message!!)
            }
        }
    }
}