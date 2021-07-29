package com.mab.mvvmandroid.ui.auth

import androidx.lifecycle.ViewModel
import com.mab.mvvmandroid.data.db.entities.User
import com.mab.mvvmandroid.data.repositories.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AuthViewModel(private val userRepository: UserRepository) : ViewModel() {
    fun getLoggedInUser() = userRepository.getUser()

    suspend fun loginUser(email: String, password: String) = withContext(Dispatchers.IO) {
        userRepository.performUserAuth(email, password)
    }

    suspend fun saveLoggedInUser(user: User) = userRepository.saveUser(user)
}