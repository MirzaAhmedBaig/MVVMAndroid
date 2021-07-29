package com.mab.mvvmandroid.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mab.mvvmandroid.data.repositories.UserRepository
import com.mab.mvvmandroid.ui.auth.AuthViewModel

/**
 * Created by Mirza Ahmed Baig on 8/22/2020.
 * mirza@avantari.org
 * Avantari Technologies
 */

class AuthViewModelFactory(private val userRepository: UserRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return AuthViewModel(userRepository) as T
    }
}