package com.mab.mvvmandroid.ui.auth

import android.view.View
import androidx.lifecycle.ViewModel
import com.mab.mvvmandroid.data.repositories.UserRepo

class AuthViewModel : ViewModel() {
    var email: String? = null
    var password: String? = null

    var authListener: AuthListener? = null

    fun performLogin(view: View) {
        if (email.isNullOrEmpty() || password.isNullOrEmpty()) {
            authListener?.onFailed("Invalid inputs")
            return
        }
        authListener?.onStarted()
        UserRepo().checkUserExist(email!!, onSuccess = {
            authListener?.onSuccess(it ?: "null response")
        }, onFailed = {
            authListener?.onFailed(it ?: "null error message")
        })
    }
}