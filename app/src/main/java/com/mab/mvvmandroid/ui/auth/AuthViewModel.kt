package com.mab.mvvmandroid.ui.auth

import android.view.View
import androidx.lifecycle.ViewModel
import com.mab.mvvmandroid.data.repositories.UserRepository
import com.mab.mvvmandroid.utils.ApiExceptions
import com.mab.mvvmandroid.utils.Coroutines

class AuthViewModel(private val userRepository: UserRepository) : ViewModel() {
    var email: String? = null
    var password: String? = null

    var authListener: AuthListener? = null

    fun getLoggedInUser() = userRepository.getUser()

    fun performLogin(view: View) {
        if (email.isNullOrEmpty() || password.isNullOrEmpty()) {
            authListener?.onFailed("Invalid inputs")
            return
        }
        authListener?.onStarted()

        Coroutines.main {
            try {
                val authResponse = userRepository.performUserAuth(email!!, password!!)
                authResponse.user?.let {
                    authListener?.onSuccess(it)
                    userRepository.saveUser(it)
                } ?: authListener?.onFailed(authResponse.message!!)
            } catch (e: ApiExceptions) {
                authListener?.onFailed("Error : ${e.message} : Code : ${e.code}")
            }
        }
    }

}